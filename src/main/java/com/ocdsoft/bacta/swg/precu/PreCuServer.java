package com.ocdsoft.bacta.swg.precu;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ocdsoft.bacta.soe.io.udp.game.GameServer;
import com.ocdsoft.bacta.soe.io.udp.login.LoginServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kburkhardt on 12/29/14.
 */
public final class PreCuServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreCuServer.class);

    public static void main(String[] args) {

        LOGGER.info("Starting Pre-CU Server");

        System.setProperty("bacta.serverPath", "");//"pre-cu" + System.getProperty("file.separator"));

        Injector injector = Guice.createInjector(new PreCuModule(), new LoginModule());
        LoginServer loginServer = injector.getInstance(LoginServer.class);
        Thread loginThread = new Thread(loginServer);
        loginThread.start();

        //injector = Guice.createInjector(new PreCuModule(), new ChatModule());
        //injector.getInstance(ChatServer.class);

        injector = Guice.createInjector(new PreCuModule(), new GameModule(), new PingModule());
        GameServer gameServer = injector.getInstance(GameServer.class);
        Thread gameThread = new Thread(gameServer);
        gameThread.start();

    }
}
