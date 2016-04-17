package com.ocdsoft.bacta.swg.precu.object.intangible.player;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;

import java.nio.ByteBuffer;

public class QuestJournalEntry implements ByteBufferSerializable {

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.put((byte) 0);
        buffer.putInt(0);
        buffer.putLong(0);
        buffer.putShort((short) 0);
        buffer.putShort((short) 0);
        buffer.put((byte) 0);
        buffer.putInt(0);
    }
}
