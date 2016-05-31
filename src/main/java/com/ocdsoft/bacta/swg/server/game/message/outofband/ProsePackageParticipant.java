package com.ocdsoft.bacta.swg.server.game.message.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public final class ProsePackageParticipant implements ByteBufferWritable {
    public static final ProsePackageParticipant EMPTY = new ProsePackageParticipant();

    private final long id;
    private final StringId stringId;
    private final String unicodeString;

    private ProsePackageParticipant() {
        this.id = 0;
        this.stringId = StringId.INVALID;
        this.unicodeString = "";
    }

    public ProsePackageParticipant(final long objectId, final StringId stringId, final String unicodeString) {
        this.id = objectId;
        this.stringId = stringId;
        this.unicodeString = unicodeString;
    }

    public ProsePackageParticipant(final long objectId, final String unicodeString) {
        this.id = objectId;
        this.stringId = StringId.INVALID;
        this.unicodeString = unicodeString;
    }

    public ProsePackageParticipant(final long objectId, final StringId stringId) {
        this.id = objectId;
        this.stringId = stringId;
        this.unicodeString = "";
    }

    public ProsePackageParticipant(final long objectId) {
        this.id = objectId;
        this.stringId = StringId.INVALID;
        this.unicodeString = "";
    }

    public ProsePackageParticipant(final StringId stringId) {
        this.stringId = stringId;
        this.id = 0;
        this.unicodeString = "";
    }

    public ProsePackageParticipant(final String unicodeString) {
        this.unicodeString = unicodeString;
        this.id = 0;
        this.stringId = StringId.INVALID;
    }

    public ProsePackageParticipant(final ByteBuffer buffer) {
        this.id = buffer.getLong();
        this.stringId = new StringId(buffer);
        this.unicodeString = BufferUtil.getUnicode(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, id);
        BufferUtil.put(buffer, stringId);
        BufferUtil.putUnicode(buffer, unicodeString);
    }
}
