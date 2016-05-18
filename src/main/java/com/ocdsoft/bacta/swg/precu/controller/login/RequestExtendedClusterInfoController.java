package com.ocdsoft.bacta.swg.precu.controller.login;


import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;

import com.ocdsoft.bacta.swg.precu.message.login.RequestExtendedClusterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = RequestExtendedClusterInfo.class, type = ServerType.LOGIN)
@ConnectionRolesAllowed({})
public class RequestExtendedClusterInfoController implements GameNetworkMessageController<RequestExtendedClusterInfo> {

    private static Logger logger = LoggerFactory.getLogger(RequestExtendedClusterInfoController.class.getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection loginConnection, RequestExtendedClusterInfo message) throws Exception {
        logger.warn("This controller is not implemented");
    }
}

