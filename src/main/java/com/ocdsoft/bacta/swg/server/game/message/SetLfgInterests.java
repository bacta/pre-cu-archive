package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.shared.foundation.BitArray;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/27/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class SetLfgInterests extends GameNetworkMessage {
    private final BitArray interests;

    public SetLfgInterests(final ByteBuffer buffer) {
        this.interests = new BitArray(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, interests);
    }
}