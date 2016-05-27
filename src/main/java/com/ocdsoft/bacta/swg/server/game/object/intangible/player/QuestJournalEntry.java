package com.ocdsoft.bacta.swg.server.game.object.intangible.player;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@AllArgsConstructor
@Getter
public class QuestJournalEntry implements ByteBufferWritable {

    public QuestJournalEntry(ByteBuffer buffer) {

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
