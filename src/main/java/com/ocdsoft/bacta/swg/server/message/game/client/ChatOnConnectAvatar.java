package com.ocdsoft.bacta.swg.server.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 5/19/2016.
 */
@AllArgsConstructor
public class ChatOnConnectAvatar extends GameNetworkMessage {

    public ChatOnConnectAvatar(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {

    }
}
