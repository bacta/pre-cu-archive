package com.ocdsoft.bacta.swg.server.game.controller.game;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.game.CommoditiesResourceTypeListRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/26/2016.
 */
@MessageHandled(handles = CommoditiesResourceTypeListRequest.class)
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public class CommoditiesResourceTypeListRequestController implements GameNetworkMessageController<CommoditiesResourceTypeListRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommoditiesResourceTypeListRequestController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final CommoditiesResourceTypeListRequest message) throws Exception {

        LOGGER.warn("Not implemented.");

//        Archive::ReadIterator ri = static_cast<const GameNetworkMessage &>(message).getByteStream().begin();
//        GenericValueTypeMessage<std::string> const m(ri);
//
//        static char buffer[128];
//        snprintf(buffer, sizeof(buffer)-1, "%s.%d", GameServer::getInstance().getClusterName().c_str(), CommoditiesMarket::getResourceTypeMapVersionNumber());
//        buffer[sizeof(buffer)-1] = '\0';
//
//        if (m.getValue() != std::string(buffer))
//        {
//            GenericValueTypeMessage<std::pair<std::string, std::map<int, std::set<std::string> > > > const rsp("CommoditiesResourceTypeListResponse", std::make_pair(std::string(buffer), CommoditiesMarket::getResourceTypeMap()));
//            send(rsp, true);
//        }
    }
}