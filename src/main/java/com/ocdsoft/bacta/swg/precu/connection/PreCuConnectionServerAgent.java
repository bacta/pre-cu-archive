package com.ocdsoft.bacta.swg.precu.connection;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.connection.ConnectionServerAgent;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;
import com.ocdsoft.bacta.swg.precu.message.GameServerStatus;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by kburkhardt on 1/26/15.
 */

@Singleton
public class PreCuConnectionServerAgent implements ConnectionServerAgent {

    private static final Logger logger = LoggerFactory.getLogger(PreCuConnectionServerAgent.class);

    private final GameServerState<ClusterEntry> serverState;
    private final int udpSize;
    private final int protocolVersion;

    private final OutgoingConnectionService outgoingConnectionService;
    private final InetSocketAddress remoteAddress;
    
    private SoeUdpConnection connection;

    @Inject
    public PreCuConnectionServerAgent(final GameServerState<ClusterEntry> serverState,
                                   final OutgoingConnectionService outgoingConnectionService,
                                   final BactaConfiguration configuration) throws UnknownHostException {
        
        this.serverState = serverState;

        udpSize = configuration.getIntWithDefault("SharedNetwork", "udpMaxSize", 496);
        protocolVersion = configuration.getIntWithDefault("SharedNetwork", "protocolVersion", 2);
        String address = configuration.getStringWithDefault("Bacta/LoginServer", "BindIp", "127.0.0.1");
        int port = configuration.getIntWithDefault("Bacta/LoginServer", "Port", 44463);
        
        this.outgoingConnectionService = outgoingConnectionService;
        remoteAddress = new InetSocketAddress(address, port);
    }

    @Override
    public void run() {

        try {
            while (true) {

                // 30 Seconds between status updates
                Thread.sleep(1000 * 30);
                update();
            }

        } catch(InterruptedException e) {
            logger.warn("Updater thread interrupted", e);
        }
    }

    @Override
    public void update() {
        connection = outgoingConnectionService.createOutgoingConnection(remoteAddress, this::onConnect);
        connection.connect(protocolVersion, connection.getId(), udpSize);
    }
    

    private void onConnect(SoeUdpConnection connection) {
        GameServerStatus gameServerStatus = new GameServerStatus(serverState);
        connection.sendMessage(gameServerStatus);
    }
}
