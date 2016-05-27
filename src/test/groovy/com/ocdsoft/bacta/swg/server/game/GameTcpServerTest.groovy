package com.ocdsoft.bacta.swg.server.game

import com.ocdsoft.bacta.swg.server.login.GameServerDisconnectedException
import com.ocdsoft.bacta.swg.server.login.GameTcpClient
import spock.lang.Specification

/**
 * Created by kyle on 5/21/2016.
 */
class GameTcpServerTest extends Specification {

    def "TestServerCommunicate"() {

        setup:
        def gameTcpServer = new GameTcpServer(9000)
        def gameTcpClient = new GameTcpClient("localhost", 9000)

        when:
        def serverThread = new Thread(gameTcpServer)
        serverThread.start();

        def timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            void run() {
                gameTcpServer.stop()
            }
        }, 2000);

        gameTcpClient.call()

        then:
        thrown(GameServerDisconnectedException)
    }

    def "TestGameServerShutdownGracefully"() {

        setup:
        def gameTcpServer = new GameTcpServer(9000)
        def gameTcpClient = new GameTcpClient("localhost", 9000)

        when:
        def serverThread = new Thread(gameTcpServer)
        serverThread.start();

        def timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            void run() {
                gameTcpServer.stop()
            }
        }, 2000);

        gameTcpClient.call()

        then:
        thrown(GameServerDisconnectedException)
    }

    def "TestGameServerDisappear"() {

        setup:
        def gameTcpServer = new GameTcpServer(9000)
        def gameTcpClient = new GameTcpClient("localhost", 9000)

        when:
        def serverThread = new Thread(gameTcpServer)
        serverThread.start();

        def timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            void run() {
                gameTcpServer.stop()
            }
        }, 2000);

        gameTcpClient.call()

        then:
        thrown(GameServerDisconnectedException)
    }

}
