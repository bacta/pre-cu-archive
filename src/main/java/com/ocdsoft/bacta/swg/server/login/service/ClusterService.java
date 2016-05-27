package com.ocdsoft.bacta.swg.server.login.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.data.ConnectionDatabaseConnector;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.engine.network.client.TcpClient;
import com.ocdsoft.bacta.engine.network.io.tcp.TcpServer;
import com.ocdsoft.bacta.swg.server.login.GameClientTcpHandler;
import com.ocdsoft.bacta.swg.server.login.object.ClusterServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * Created by kburkhardt on 1/18/15.
 */
@Singleton
public class ClusterService implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterService.class);

    private transient final ConnectionDatabaseConnector dbConnector;
    private transient final Set<ClusterServer> clusterServerSet;
    private transient final Map<InetSocketAddress, ClusterServer> clusterTcpConnectionMap;
    private transient final Constructor<ClusterServer> clusterEntryConstructor;

    private final boolean allowDynamicRegistration;

    @Inject
    public ClusterService(final ConnectionDatabaseConnector dbConnector,
                          final BactaConfiguration bactaConfiguration) throws Exception {

        this.clusterServerSet = new TreeSet<>();
        this.clusterTcpConnectionMap = new HashMap<>();
        this.dbConnector = dbConnector;
        this.allowDynamicRegistration = bactaConfiguration.getBooleanWithDefault("Bacta/LoginServer", "AllowDynamicRegistration", false);
        this.clusterEntryConstructor = ClusterServer.class.getConstructor(Map.class);

        loadData();
    }

    private void loadData() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {

            Set<Map<String, Object>> servers = dbConnector.getObject("ClusterServerSet", Set.class);

            if (servers != null) {
                for (Map<String, Object> clusterInfoMap : servers) {
                    clusterInfoMap.put("status", ServerStatus.DOWN);
                    ClusterServer clusterServer = clusterEntryConstructor.newInstance(clusterInfoMap);
                    clusterServerSet.add(clusterServer);
                }
            }
        } catch(NullPointerException e) {
            LOGGER.error("Null Pointer", e);
        }
    }

    public void notifyGameServerOnline(final ClusterServer clusterServer) {

        final InetSocketAddress tcpAddress = new InetSocketAddress(clusterServer.getRemoteAddress().getAddress(), clusterServer.getTcpPort());
        if (!clusterTcpConnectionMap.containsKey(tcpAddress)) {

            final TcpClient tcpClient = new TcpClient(tcpAddress, new ChannelInitializer< SocketChannel >() { // (4)
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addFirst(new IdleStateHandler(0, 25, 0));
                    ch.pipeline().addLast(new GameClientTcpHandler());
                }
            });

            tcpClient.addObserver(this);

            clusterTcpConnectionMap.put(tcpAddress, clusterServer);
            tcpClient.start();
        }

        clusterServerSet.add(clusterServer);
        //update();
    }

    @Override
    public void update(final Observable o, final Object arg) {

        TcpServer.Status status = (TcpServer.Status) arg;
        if(status == TcpServer.Status.CONNECTED) {

        } else if ( status == TcpServer.Status.DISCONNECTED) {
            TcpClient tcpClient = (TcpClient) o;
            final ClusterServer clusterServer = clusterTcpConnectionMap.remove(tcpClient.getRemoteAddress());
            clusterServer.getStatusClusterData().setStatus(ServerStatus.DOWN);

        }
    }

    public Collection<ClusterServer> getClusterEntries() {
        return clusterServerSet;
    }

    private void update() {
        dbConnector.updateObject("ClusterServerSet", clusterServerSet);
    }


}
