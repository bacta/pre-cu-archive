package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/26/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class SetJediSlotInfo extends GameNetworkMessage {
    private final boolean unoccupiedJediSlot;
    private final boolean jediSlotCharacter;

    public SetJediSlotInfo(final ByteBuffer buffer) {
        unoccupiedJediSlot = BufferUtil.getBoolean(buffer);
        jediSlotCharacter = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, unoccupiedJediSlot);
        BufferUtil.put(buffer, jediSlotCharacter);
    }
}