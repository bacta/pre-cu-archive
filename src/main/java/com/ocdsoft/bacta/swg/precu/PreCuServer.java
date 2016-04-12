package com.ocdsoft.bacta.swg.precu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ocdsoft.bacta.soe.io.udp.game.GameServer;

/**
 * Created by kburkhardt on 12/29/14.
 */
public final class PreCuServer {

    public static void main(String[] args) {

        for(String module : args) {

        }

        Injector injector = Guice.createInjector(new PreCuModule());
        injector.getInstance(GameServer.class);
    }
}
