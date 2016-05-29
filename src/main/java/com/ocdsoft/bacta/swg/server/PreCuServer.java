package com.ocdsoft.bacta.swg.server;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ocdsoft.bacta.swg.server.chat.ChatServer;
import com.ocdsoft.bacta.swg.server.game.GameServer;
import com.ocdsoft.bacta.swg.server.login.LoginServer;
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
            LOGGER.info("Starting LoginServer");
            Injector injector = Guice.createInjector(new PreCuModule(), new LoginModule());
            LoginServer loginServer = injector.getInstance(LoginServer.class);
            Thread loginThread = new Thread(loginServer);
            loginThread.start();
        }

        //injector = Guice.createInjector(new PreCuModule(), new ChatModule());
        //injector.getInstance(ChatServer.class);

        if(argSet.contains("game")) {
            LOGGER.info("Starting GameServer");
            Injector injector = Guice.createInjector(new PreCuModule(), new GameModule(), new PingModule());
            GameServer gameServer = injector.getInstance(GameServer.class);
            Thread gameThread = new Thread(gameServer);
            gameThread.start();
        }

        if (argSet.contains("chat")) {
            LOGGER.info("Starting ChatServer");
            Injector injector = Guice.createInjector(new PreCuModule(), new ChatModule());
            ChatServer chatServer = injector.getInstance(ChatServer.class);
            Thread chatThread = new Thread(chatServer);
            chatThread.start();
        }

    }
}
