package com.ocdsoft.bacta.swg.precu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ocdsoft.bacta.soe.io.udp.chat.ChatServer;
import com.ocdsoft.bacta.soe.io.udp.game.GameServer;
import com.ocdsoft.bacta.soe.io.udp.login.LoginServer;

/**
 * Created by kburkhardt on 12/29/14.
 */
public final class PreCuServer {

    public static void main(String[] args) {

        System.setProperty("bacta.serverPath", "pre-cu");

        Injector injector = Guice.createInjector(new PreCuModule(), new LoginModule());
        LoginServer loginServer = injector.getInstance(LoginServer.class);
        Thread loginThread = new Thread(loginServer);
        loginThread.start();

        //injector = Guice.createInjector(new PreCuModule(), new ChatModule());
        //injector.getInstance(ChatServer.class);

        //injector = Guice.createInjector(new PreCuModule(), new GameModule(), new PingModule());
        //injector.getInstance(GameServer.class);


    }
}
