package com.ocdsoft.bacta.swg.server.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kburkhardt on 2/14/14.
 */

@Singleton
public final class PingServer implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(PingServer.class);

    private final PingTransceiver pingTransceiver;

    @Inject
    public PingServer(final PingTransceiver pingTransceiver) {
        this.pingTransceiver = pingTransceiver;
    }

    @Override
    public void run() {
        LOGGER.info("Ping Server is starting");
        pingTransceiver.run();
        LOGGER.info("Stopping ping server");
    }
}
