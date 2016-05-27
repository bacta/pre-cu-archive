package com.ocdsoft.bacta.swg.server.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.engine.network.io.tcp.TcpServer;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.SoeTransceiver;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;
import com.ocdsoft.bacta.swg.server.game.message.GameServerOnline;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * GameServer is the main class which starts the services running
 */
public final class GameServer implements Runnable, Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    /**
     * Various metadata about the game server
     */
    private final GameServerState serverState;

    /**
     * The ConnectionServerAgent communicates the status of this GameServer to the connection server
     */
    private final OutgoingConnectionService outgoingConnectionService;

    /**
     * The transceiver receives and transmits all the messages
     */
    private final SoeTransceiver transceiver;

    /**
     * Ping server helps the client gauge its latency
     */
    private final PingServer pingServer;

    private final BactaConfiguration configuration;

    private final Timer timer;

    @Inject
    public GameServer(final GameServerState serverState,
                      final SoeTransceiver transceiver,
                      final PingServer pingServer,
                      final OutgoingConnectionService outgoingConnectionService,
                      final BactaConfiguration configuration) throws UnknownHostException {

        this.serverState = serverState;
        this.transceiver = transceiver;
        this.pingServer = pingServer;
        this.outgoingConnectionService = outgoingConnectionService;
        this.configuration = configuration;
        timer = new Timer();

        // One might consider this a hack, but it allows us to use scope in referencing the method desired without making it public
        ((GameOutgoingConnectionService)outgoingConnectionService).createConnection = transceiver::createOutgoingConnection;
    }

    /**
     * Main method that starts both the
     */
    @Override
    public void run() {
        LOGGER.info("Starting");
        try {

            Thread pingThread = new Thread(pingServer);
            pingThread.start();

            serverState.setServerStatus(ServerStatus.UP);

            startTCPServer();
            startLoginUpdate();

            LOGGER.info("Game Server is running");
            // Blocks until stopped
            transceiver.run();
            serverState.setServerStatus(ServerStatus.DOWN);

        } catch (Exception e) {
            LOGGER.error("Error starting game transceiver", e);
            serverState.setServerStatus(ServerStatus.DOWN);
        }
    }

    private void startTCPServer() {
        TcpServer gameTcpServer = new TcpServer(
                new ChannelInitializer<SocketChannel>() { // (4)
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addFirst(new IdleStateHandler(0, 25, 0));
                        ch.pipeline().addLast(new GameTcpServerHandler());
                    }
                },
                configuration.getInt("Bacta/GameServer", "TCPPort")
        );
        gameTcpServer.addObserver(this);

        gameTcpServer.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        TcpServer.Status status = (TcpServer.Status) arg;
        if(status == TcpServer.Status.CONNECTED) {

        } else if ( status == TcpServer.Status.DISCONNECTED) {

        }
    }

    private void startLoginUpdate() throws IOException {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    updateLoginServer();
                } catch (UnknownHostException e) {
                    LOGGER.error("Unknown Host", e);
                }
            }
        }, 100, 30000);

    }

    private void updateLoginServer() throws UnknownHostException {

        final String addressString = configuration.getString("Bacta/LoginServer", "BindIp");
        final InetAddress address;
        if(addressString.equalsIgnoreCase("localhost")) {
            address = InetAddress.getLocalHost();
        } else {
            address = InetAddress.getByName(addressString);
        }
        final int port = configuration.getInt("Bacta/LoginServer", "Port");

        InetSocketAddress remoteAddress = new InetSocketAddress(address, port);

        final SoeUdpConnection connection = outgoingConnectionService.createOutgoingConnection(remoteAddress, this::onConnect);
        connection.connect();
    }

    private void onConnect(SoeUdpConnection connection) {
        GameServerOnline gameServerOnline = new GameServerOnline(serverState.getClusterServer());
        connection.sendMessage(gameServerOnline);
    }

    public void stop() {
        transceiver.stop();
    }


    /**
     * GameOutgoingConnectionService uses a function reference to the {@link SoeTransceiver#createOutgoingConnection(InetSocketAddress, Consumer)}
     * method to provide various services with a consistent manner in which to initialize outgoing communication
     */
    @Singleton
    final static public class GameOutgoingConnectionService implements OutgoingConnectionService {

        private BiFunction<InetSocketAddress, Consumer<SoeUdpConnection>, SoeUdpConnection> createConnection;

        @Override
        public SoeUdpConnection createOutgoingConnection(final InetSocketAddress address, final Consumer<SoeUdpConnection> connectCallback) {
            if(createConnection == null) return null;
            
            return createConnection.apply(address, connectCallback);
        }
    }
}