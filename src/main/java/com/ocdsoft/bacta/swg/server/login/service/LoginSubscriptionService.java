package com.ocdsoft.bacta.swg.server.login.service;

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
public final class LoginSubscriptionService implements SubscriptionService {

    private final ClusterService clusterService;

    @Inject
    public LoginSubscriptionService(final ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public void onConnect(final SoeUdpConnection connection) {
        clusterService.addConnection(connection);
    }

    @Override
    public void onDisconnect(final SoeUdpConnection connection) {
        clusterService.removeConnection(connection);
    }

    @Override
    public <T extends GameNetworkMessage & Subscribable> void messageSubscribe(SoeUdpConnection connection, Class<T> messageClass) {

    }

    @Override
    public <T extends GameNetworkMessage & Subscribable> void messageUnsubscribe(SoeUdpConnection connection, Class<T> messageClass) {

    }
}
