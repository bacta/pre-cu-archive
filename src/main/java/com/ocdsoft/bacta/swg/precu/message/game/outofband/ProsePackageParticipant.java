package com.ocdsoft.bacta.swg.precu.message.game.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.localization.StringId;

import java.nio.ByteBuffer;

public final class ProsePackageParticipant implements ByteBufferWritable {
    public static final ProsePackageParticipant empty = new ProsePackageParticipant();

    private final long id;
    private final StringId stringId;
    private final String unicodeString;

    private ProsePackageParticipant() {
        this.id = 0;
        this.stringId = StringId.INVALID;
        this.unicodeString = "";
    }

    public ProsePackageParticipant(long objectId) {
        this.id = objectId;
        this.stringId = StringId.INVALID;
        this.unicodeString = "";
    }

    public ProsePackageParticipant(StringId stringId) {
        this.stringId = stringId;
        this.id = 0;
        this.unicodeString = "";
    }

    public ProsePackageParticipant(String unicodeString) {
        this.unicodeString = unicodeString;
        this.id = 0;
        this.stringId = StringId.INVALID;
    }

    public ProsePackageParticipant(ByteBuffer buffer) {
        this.id = buffer.getLong();
        this.stringId = new StringId(buffer);
        this.unicodeString = BufferUtil.getUnicode(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(id);
        stringId.writeToBuffer(buffer);
        BufferUtil.putUnicode(buffer, unicodeString);
    }
}
