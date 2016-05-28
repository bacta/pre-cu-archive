package com.ocdsoft.bacta.swg.server.chat.service;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.MessageSubscriptionService;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Subscribable;

/**
 * Created by crush on 5/27/2016.
 */
@Singleton
public class ChatMessageSubscriptionService implements MessageSubscriptionService {
    @Override
    public void onConnect(SoeUdpConnection connection) {

    }

    @Override
    public void onDisconnect(SoeUdpConnection connection) {

    }

    @Override
    public <T extends GameNetworkMessage & Subscribable> void subscribe(SoeUdpConnection connection, Class<T> messageClass) {

    }

    @Override
    public <T extends GameNetworkMessage & Subscribable> void unsubscribe(SoeUdpConnection connection, Class<T> messageClass) {

    }
}
