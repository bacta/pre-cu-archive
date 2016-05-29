package com.ocdsoft.bacta.swg.server.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.SubscriptionService;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Subscribable;

/**
 * Created by kyle on 5/26/2016.
 */
@Singleton
public class GameSubscriptionService implements SubscriptionService {

    @Override
    public void onConnect(final SoeUdpConnection connection) {

    }

    @Override
    public void onDisconnect(final SoeUdpConnection connection) {

    }

    @Override
    public <T extends GameNetworkMessage & Subscribable> void messageSubscribe(final SoeUdpConnection connection, Class<T> messageClass) {

    }

    @Override
    public <T extends GameNetworkMessage & Subscribable> void messageUnsubscribe(final SoeUdpConnection connection, Class<T> messageClass) {

    }
}
