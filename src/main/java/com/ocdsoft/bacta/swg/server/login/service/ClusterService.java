package com.ocdsoft.bacta.swg.server.login.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.data.ConnectionDatabaseConnector;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.swg.server.login.GameTcpClient;
import com.ocdsoft.bacta.swg.server.login.object.ClusterServer;
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
    private transient final Map<GameTcpClient, ClusterServer> clusterConnectionMap;
    private transient final Constructor<ClusterServer> clusterEntryConstructor;

    private final boolean allowDynamicRegistration;

    @Inject
    public ClusterService(final ConnectionDatabaseConnector dbConnector,
                          final BactaConfiguration bactaConfiguration) throws Exception {

        this.clusterServerSet = new TreeSet<>();
        this.clusterConnectionMap = new HashMap<>();
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

        final InetSocketAddress tcpAddress = new InetSocketAddress(clusterServer.getRemoteAddress().getHostString(), clusterServer.getTcpPort());
        if (!clusterConnectionMap.containsKey(tcpAddress)) {

            final GameTcpClient tcpClient = new GameTcpClient(tcpAddress);
            tcpClient.addObserver(this);

            clusterConnectionMap.put(tcpClient, clusterServer);
            tcpClient.start();
        }

        clusterServerSet.add(clusterServer);
        //update();
    }

    @Override
    public void update(final Observable o, final Object arg) {
        GameTcpClient tcpClient = (GameTcpClient) o;
        final ClusterServer clusterServer = clusterConnectionMap.get(tcpClient);
        clusterServer.getStatusClusterData().setStatus(ServerStatus.DOWN);
    }

    public Collection<ClusterServer> getClusterEntries() {
        return clusterServerSet;
    }

    private void update() {
        dbConnector.updateObject("ClusterServerSet", clusterServerSet);
    }


}
