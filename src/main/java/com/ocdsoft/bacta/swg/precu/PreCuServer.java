package com.ocdsoft.bacta.swg.precu;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by kburkhardt on 12/29/14.
 */
public final class PreCuServer {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new PreCuModule());

    }
}
