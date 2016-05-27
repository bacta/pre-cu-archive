package com.ocdsoft.bacta.swg.server.game

import com.ocdsoft.bacta.engine.network.client.TcpClient
import com.ocdsoft.bacta.engine.network.io.tcp.TcpServer
import com.ocdsoft.bacta.swg.server.login.GameClientTcpHandler
import com.ocdsoft.bacta.swg.server.login.GameServerDisconnectedException
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.timeout.IdleStateHandler
import spock.lang.Specification

/**
 * Created by kyle on 5/21/2016.
 */
class GameTcpServerTest extends Specification {

    def "TestServerCommunicate"() {

        setup:
        def gameTcpServer = new TcpServer(
                new ChannelInitializer<SocketChannel>() { // (4)
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addFirst(new IdleStateHandler(0, 25, 0));
                        ch.pipeline().addLast(new GameTcpServerHandler());
                    }
                },
                9000
        );

        final InetSocketAddress tcpAddress = new InetSocketAddress("localhost", 9000);
        def gameTcpClient = new TcpClient(tcpAddress, new ChannelInitializer< SocketChannel >() { // (4)
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addFirst(new IdleStateHandler(0, 25, 0));
                ch.pipeline().addLast(new GameClientTcpHandler());
            }
        });

        when:
        gameTcpServer.start();

        def timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            void run() {
                gameTcpServer.stop()
            }
        }, 2000);

        gameTcpClient.start()

        while (gameTcpClient.isConnected()) {
            Thread.sleep(1000);
        }


        then:
        noExceptionThrown()
        !gameTcpClient.isConnected()
        !gameTcpServer.isConnected()

    }


}
