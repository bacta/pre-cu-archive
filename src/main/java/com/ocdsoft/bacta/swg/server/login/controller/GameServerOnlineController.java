package com.ocdsoft.bacta.swg.server.login.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.controller.SubscriptionHandlerController;
import com.ocdsoft.bacta.swg.server.login.service.ClusterService;
import com.ocdsoft.bacta.swg.server.game.message.GameServerOnline;

import java.util.Set;

/**
 * Created by kburkhardt on 1/31/15.
 */
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
@MessageHandled(handles = GameServerOnline.class, type = ServerType.LOGIN)
public class GameServerOnlineController extends SubscriptionHandlerController<GameServerOnline> {

    private final ClusterService clusterService;

    @Inject
    public GameServerOnlineController(final ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public void handleIncomingInternal(SoeUdpConnection connection, GameServerOnline message) throws Exception {
        clusterService.notifyGameServerOnline(message.getClusterServer());
    }

    @Override
    protected void notifySubscribers(Set<SoeUdpConnection> subscribers, GameServerOnline message) {

    }

}
