package com.ocdsoft.bacta.swg.server.game.message.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 5/28/2016.
 */
@Getter
@Priority(0x01)
@AllArgsConstructor
public class LogoutMessage extends GameNetworkMessage {
    public LogoutMessage(final ByteBuffer buffer) {
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    }
}