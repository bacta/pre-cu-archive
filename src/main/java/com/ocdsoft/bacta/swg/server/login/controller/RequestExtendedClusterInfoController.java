package com.ocdsoft.bacta.swg.server.login.controller;


import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.login.message.LoginClusterStatusEx;
import com.ocdsoft.bacta.swg.server.login.message.RequestExtendedClusterInfo;
import com.ocdsoft.bacta.swg.server.login.object.ClusterServer;
import com.ocdsoft.bacta.swg.server.login.service.ClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

@MessageHandled(handles = RequestExtendedClusterInfo.class, type = ServerType.LOGIN)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class RequestExtendedClusterInfoController implements GameNetworkMessageController<RequestExtendedClusterInfo> {

    private static Logger LOGGER = LoggerFactory.getLogger(RequestExtendedClusterInfoController.class.getSimpleName());

    private final ClusterService clusterService;

    @Inject
    public RequestExtendedClusterInfoController(final ClusterService clusterService) {
        this.clusterService = clusterService;
    }


    @Override
    public void handleIncoming(SoeUdpConnection loginConnection, RequestExtendedClusterInfo message) throws Exception {
        LoginClusterStatusEx loginClusterStatusEx = new LoginClusterStatusEx(
                clusterService.getClusterEntries().stream()
                .map(ClusterServer::getExtendedClusterData)
                .collect(Collectors.toSet())
        );
        loginConnection.sendMessage(loginClusterStatusEx);
    }
}

