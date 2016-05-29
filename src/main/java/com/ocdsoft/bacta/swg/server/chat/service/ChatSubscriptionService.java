package com.ocdsoft.bacta.swg.server.chat.service;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.SubscriptionService;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Subscribable;

/**
 * Created by crush on 5/27/2016.
 */
@Singleton
public class ChatSubscriptionService implements SubscriptionService {
    @Override
    public void onConnect(SoeUdpConnection connection) {

    }

    @Override
    public void onDisconnect(SoeUdpConnection connection) {

    }

    @Override
    public <T extends GameNetworkMessage & Subscribable> void messageSubscribe(SoeUdpConnection connection, Class<T> messageClass) {

    }

    @Override
    public <T extends GameNetworkMessage & Subscribable> void messageUnsubscribe(SoeUdpConnection connection, Class<T> messageClass) {

    }
}
