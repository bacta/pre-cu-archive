package com.ocdsoft.bacta.swg.server.controller.login;


import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.message.login.LoginClusterStatusEx;
import com.ocdsoft.bacta.swg.server.message.login.RequestExtendedClusterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@MessageHandled(handles = RequestExtendedClusterInfo.class, type = ServerType.LOGIN)
@ConnectionRolesAllowed({})
public class RequestExtendedClusterInfoController implements GameNetworkMessageController<RequestExtendedClusterInfo> {
    private static Logger logger = LoggerFactory.getLogger(RequestExtendedClusterInfoController.class.getSimpleName());

    @Override
    public void handleIncoming(final SoeUdpConnection loginConnection, final RequestExtendedClusterInfo message) throws Exception {

        loginConnection.sendMessage(new LoginClusterStatusEx(
                Collections.singletonList(new LoginClusterStatusEx.ClusterData(
                        2,
                        "develop",
                        "9001",
                        1000000,
                        1,
                        2,
                        3,
                        4))));
    }
}

