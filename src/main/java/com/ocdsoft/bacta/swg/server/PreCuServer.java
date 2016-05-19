package com.ocdsoft.bacta.swg.server;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ocdsoft.bacta.soe.io.udp.game.GameServer;
import com.ocdsoft.bacta.soe.io.udp.login.LoginServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kburkhardt on 12/29/14.
 */
public final class PreCuServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreCuServer.class);

    public static void main(String[] args) {

        Set<String> argSet = new HashSet<>();
        for(String arg : args) {
            argSet.add(arg.toLowerCase());
        }

        System.setProperty("bacta.serverPath", "pre-cu" + System.getProperty("file.separator"));

        if(argSet.contains("login")) {
            LOGGER.info("Starting Pre-CU LoginServer");
            Injector injector = Guice.createInjector(new PreCuModule(), new LoginModule());
            LoginServer loginServer = injector.getInstance(LoginServer.class);
            Thread loginThread = new Thread(loginServer);
            loginThread.start();
        }

        //injector = Guice.createInjector(new PreCuModule(), new ChatModule());
        //injector.getInstance(ChatServer.class);

        if(argSet.contains("game")) {
            LOGGER.info("Starting Pre-CU GameServer");
            Injector injector = Guice.createInjector(new GameModule(), new PreCuModule(), new PingModule());
            GameServer gameServer = injector.getInstance(GameServer.class);
            Thread gameThread = new Thread(gameServer);
            gameThread.start();
        }

    }
}
