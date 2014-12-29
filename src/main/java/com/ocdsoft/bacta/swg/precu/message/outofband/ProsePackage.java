package com.ocdsoft.bacta.swg.precu.message.outofband;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import lombok.Getter;
import lombok.Setter;

/**
 * A ProsePackage consists of different parameters that can be substituted into a message. Three of these parameters are
 * defined as {@link ProsePackageParticipant} and encompass a {@link StringId}, NetworkId, or UTF-16 String. A ProsePackage may also set
 * an Integer and Float parameter as well.
 */
public class ProsePackage implements SoeByteBufSerializable {
    @Getter
    private final StringId stringId;
    @Setter
    private ProsePackageParticipant actor = ProsePackageParticipant.empty;
    @Setter
    private ProsePackageParticipant target = ProsePackageParticipant.empty;
    @Setter
    private ProsePackageParticipant other = ProsePackageParticipant.empty;
    @Setter
    private int digitInteger;
    @Setter
    private float digitFloat;
    @Setter
    private boolean complexGrammar;

    public ProsePackage(StringId stringId) {
        this.stringId = stringId;
    }

    public ProsePackage(SoeByteBuf message) {
        this.stringId = new StringId(message);
        this.actor = new ProsePackageParticipant(message);
        this.target = new ProsePackageParticipant(message);
        this.other = new ProsePackageParticipant(message);
        this.digitInteger = message.readInt();
        this.digitFloat = message.readFloat();
        this.complexGrammar = message.readBoolean();
    }

    @Override
    public void writeToBuffer(SoeByteBuf buffer) {
        stringId.writeToBuffer(buffer);
        actor.writeToBuffer(buffer);
        target.writeToBuffer(buffer);
        other.writeToBuffer(buffer);
        buffer.writeInt(digitInteger);
        buffer.writeFloat(digitFloat);
        buffer.writeBoolean(complexGrammar);
    }
}