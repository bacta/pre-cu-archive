package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.CollectionServerFirstListRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/26/2016.
 */
@MessageHandled(handles = CollectionServerFirstListRequest.class)
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public class CollectionServerFirstListRequestController implements GameNetworkMessageController<CollectionServerFirstListRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionServerFirstListRequestController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final CollectionServerFirstListRequest message) throws Exception {
        LOGGER.warn("Not implemented");

//        Archive::ReadIterator ri = static_cast<const GameNetworkMessage &>(message).getByteStream().begin();
//        GenericValueTypeMessage<std::string> const m(ri);
//
//        PlanetObject const * const planetObject = ServerUniverse::getInstance().getTatooinePlanet();
//        if (planetObject)
//        {
//            static char buffer[128];
//            snprintf(buffer, sizeof(buffer)-1, "%s.%d", GameServer::getInstance().getClusterName().c_str(), planetObject->getCollectionServerFirstUpdateNumber());
//            buffer[sizeof(buffer)-1] = '\0';
//
//            if (m.getValue() != std::string(buffer))
//            {
//                GenericValueTypeMessage<std::pair<std::string, std::set<std::pair<std::pair<int32, std::string>, std::pair<NetworkId, Unicode::String> > > > > const rsp("CollectionServerFirstListResponse", std::make_pair(std::string(buffer), planetObject->getCollectionServerFirst()));
//                send(rsp, true);
//            }
//        }
    }
}