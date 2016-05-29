package com.ocdsoft.bacta.swg.server.game.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.dispatch.GameClientMessageDispatcher;
import com.ocdsoft.bacta.soe.message.GameClientMessage;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.serialize.GameNetworkMessageSerializer;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/27/2016.
 *
 * Handles incoming GameClientMessages. If a controller exists for the message, then it will handle it there.
 *
 * It will always forward the message directly to the client regardless.
 */
@MessageHandled(handles = GameClientMessage.class)
@ConnectionRolesAllowed(value = ConnectionRole.WHITELISTED)
public final class GameGameClientMessageController implements GameNetworkMessageController<GameClientMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameGameClientMessageController.class);

    private final GameNetworkMessageSerializer serializer;
    private final GameClientMessageDispatcher dispatcher;
    private final ServerObjectService serverObjectService;

    @Inject
    public GameGameClientMessageController(final GameNetworkMessageSerializer serializer,
                                           final GameClientMessageDispatcher dispatcher,
                                           final ServerObjectService serverObjectService) {
        this.serializer = serializer;
        this.dispatcher = dispatcher;
        this.serverObjectService = serverObjectService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final GameClientMessage message) throws Exception {
        final long[] distributionList = message.getDistributionList();
        final boolean reliable = message.isReliable();

        final ByteBuffer internalMessage = message.getInternalMessage();

        final short priority = internalMessage.getShort();
        final int messageType = internalMessage.getInt();

        final GameNetworkMessage gnm = serializer.readFromBuffer(messageType, internalMessage);

        LOGGER.debug("Received message type {} for networkId {}",
                gnm.getClass().getSimpleName(),
                distributionList[0]);

        //We want to forward every GameClientMessage back to the client.
        //This was only observed in ConnectionServer/ChatServerConnection and might not be true if others
        //are also sending GameClientMessages to ConnectionServer (aka GameServer).
        for (int i = 0; i < distributionList.length; ++i) {
            final long networkId = distributionList[i];
            final ServerObject serverObject = serverObjectService.get(networkId);

            if (serverObject != null) {
                final SoeUdpConnection client = serverObject.getConnection();

                if (client != null) {
                    LOGGER.debug("Sending message type {} to client {}:{}",
                            gnm.getClass().getSimpleName(),
                            client.getRemoteAddress().getHostName(),
                            client.getRemoteAddress().getPort());

                    client.sendMessage(gnm);
                }
            }
        }

        //Now we can either dispatch it to another controller, or we can do something else with it.
        dispatcher.dispatch(distributionList, reliable, messageType, gnm, connection);
    }
}