package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ChatOnConnectAvatar extends SwgMessage {
    public ChatOnConnectAvatar() {
        super(0x01, 0xD72FE9BE);  //ChatOnConnectAvatar

        //empty struct. just tells the client the avatar connected to the chat server.
    }
}
