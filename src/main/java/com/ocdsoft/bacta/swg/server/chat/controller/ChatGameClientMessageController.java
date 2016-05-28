package com.ocdsoft.bacta.swg.server.chat.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.dispatch.GameClientMessageDispatcher;
import com.ocdsoft.bacta.soe.message.GameClientMessage;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.serialize.GameNetworkMessageSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/27/2016.
 */
@MessageHandled(handles = GameClientMessage.class, type = ServerType.CHAT)
@ConnectionRolesAllowed(value = ConnectionRole.WHITELISTED)
public final class ChatGameClientMessageController implements GameNetworkMessageController<GameClientMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatGameClientMessageController.class);

    private final GameNetworkMessageSerializer serializer;
    private final GameClientMessageDispatcher dispatcher;

    @Inject
    public ChatGameClientMessageController(final GameNetworkMessageSerializer serializer,
                                           final GameClientMessageDispatcher dispatcher) {
        this.serializer = serializer;
        this.dispatcher = dispatcher;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final GameClientMessage message) throws Exception {
        final long[] distributionList = message.getDistributionList();
        final boolean reliable = message.isReliable();

        final ByteBuffer internalMessage = message.getInternalMessage();

        final short priority = internalMessage.getShort();
        final int messageType = internalMessage.getInt();

        final GameNetworkMessage gnm = serializer.readFromBuffer(messageType, internalMessage);

        //Just pass it to the dispatcher.

        //Now we can either dispatch it to another controller, or we can do something else with it.
        dispatcher.dispatch(distributionList, reliable, messageType, gnm, connection);
    }
}