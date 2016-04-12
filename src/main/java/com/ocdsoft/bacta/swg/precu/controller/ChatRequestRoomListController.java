package com.ocdsoft.bacta.swg.precu.controller;

import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.chat.ChatRequestRoomList;
import com.ocdsoft.bacta.swg.shared.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(ChatRequestRoomList.class)
public class ChatRequestRoomListController implements GameNetworkMessageController<ChatRequestRoomList> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handleIncoming(SoeUdpConnection connection, ChatRequestRoomList message) throws Exception {
        throw new NotImplementedException();
    }
}
