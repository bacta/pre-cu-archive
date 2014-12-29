package com.ocdsoft.bacta.swg.precu.message.outofband;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import com.ocdsoft.bacta.swg.shared.localization.StringId;

public final class ProsePackageParticipant implements SoeByteBufSerializable {
    public static final ProsePackageParticipant empty = new ProsePackageParticipant();

    private final long id;
    private final StringId stringId;
    private final String unicodeString;

    private ProsePackageParticipant() {
        this.id = 0;
        this.stringId = StringId.empty;
        this.unicodeString = "";
    }

    public ProsePackageParticipant(long objectId) {
        this.id = objectId;
        this.stringId = StringId.empty;
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
        this.stringId = StringId.empty;
    }

    public ProsePackageParticipant(SoeByteBuf message) {
        this.id = message.readLong();
        this.stringId = new StringId(message);
        this.unicodeString = message.readUnicode();
    }

    @Override
    public void writeToBuffer(SoeByteBuf message) {
        message.writeLong(id);
        stringId.writeToBuffer(message);
        message.writeUnicode(unicodeString);
    }
}
