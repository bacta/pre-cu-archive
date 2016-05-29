package com.ocdsoft.bacta.swg.server.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.SoeTransceiver;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;
import com.ocdsoft.bacta.swg.server.chat.message.ChatServerOnline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Created by crush on 5/23/2016.
 */
public class ChatServer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);

    private final ChatServerState serverState;
    private final SoeTransceiver soeTransceiver;
    private final ChatServerConfiguration configuration;
    private final OutgoingConnectionService outgoingConnectionService;

    private final Timer timer;
    private final SwgChatServer chatServer;

    @Inject
    public ChatServer(final ChatServerConfiguration configuration,
                      final ChatServerState serverState,
                      final SwgChatServer chatServer,
                      final SoeTransceiver soeTransceiver,
                      final OutgoingConnectionService outgoingConnectionService) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        this.chatServer = chatServer;
        this.configuration = configuration;
        this.serverState = serverState;
        this.soeTransceiver = soeTransceiver;
        this.outgoingConnectionService = outgoingConnectionService;
        this.timer = new Timer();

        ((ChatOutgoingConnectionService) outgoingConnectionService).createConnection = soeTransceiver::createOutgoingConnection;
    }

    @Override
    public void run() {
        LOGGER.info("Starting.");

        try {

            serverState.setServerStatus(ServerStatus.UP);

            broadcastOnline();

            soeTransceiver.run();

            serverState.setServerStatus(ServerStatus.DOWN);

        } catch (Exception ex) {
            LOGGER.error("Error in chat transceiver.", ex);
        }
    }

    private void broadcastOnline() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateGameServer();
            }
        }, 1000); //We only want to send it once.
    }

    private void updateGameServer() {
        try {
            LOGGER.info("Updating game server that Chat Server is now online.");

            final InetSocketAddress gameServerAddress = configuration.getGameServerAddress();
            final SoeUdpConnection connection = outgoingConnectionService.createOutgoingConnection(gameServerAddress, this::onConnect);
            connection.connect();
        } catch (UnknownHostException ex) {
            LOGGER.error("Unable to get game server binding information: {}", ex.getMessage());
        }
    }

    private void onConnect(final SoeUdpConnection connection) {
        try {
            LOGGER.info("Connection to GameServer established. Sending ChatServerOnline.");
            final InetSocketAddress address = configuration.getBindAddress();
            final ChatServerOnline msg = new ChatServerOnline(address.getHostName(), address.getPort());
            connection.sendMessage(msg);
            //Notify ourselves that they are online since we were able to connect.
            this.chatServer.notifyGameServerOnline(connection);
        } catch (UnknownHostException ex) {
            LOGGER.error("Unable to get chat server binding information: {}", ex.getMessage());
        }
    }

    public void stop() {
        if (soeTransceiver != null) {
            soeTransceiver.stop();
        }
    }

    @Singleton
    final static public class ChatOutgoingConnectionService implements OutgoingConnectionService {

        private BiFunction<InetSocketAddress, Consumer<SoeUdpConnection>, SoeUdpConnection> createConnection;

        @Override
        public SoeUdpConnection createOutgoingConnection(final InetSocketAddress address, final Consumer<SoeUdpConnection> connectCallback) {
            if (createConnection == null) return null;

            return createConnection.apply(address, connectCallback);
        }
    }
}
