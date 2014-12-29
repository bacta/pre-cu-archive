package com.ocdsoft.bacta.swg.precu.object.intangible.player;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;

public class QuestJournalEntry implements SoeByteBufSerializable {

    @Override
    public void writeToBuffer(SoeByteBuf message) {
        message.writeByte(0);
        message.writeInt(0);
        message.writeLong(0);
        message.writeShort(0);
        message.writeShort(0);
        message.writeByte(0);
        message.writeInt(0);
    }

}
