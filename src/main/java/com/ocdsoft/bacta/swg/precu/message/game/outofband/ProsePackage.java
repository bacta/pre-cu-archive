package com.ocdsoft.bacta.swg.precu.message.game.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.localization.StringId;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

/**
 * A ProsePackage consists of different parameters that can be substituted into a message. Three of these parameters are
 * defined as {@link ProsePackageParticipant} and encompass a {@link StringId}, NetworkId, or UTF-16 String. A ProsePackage may also set
 * an Integer and Float parameter as well.
 */
public class ProsePackage implements ByteBufferSerializable {
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

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        stringId.writeToBuffer(buffer);
        actor.writeToBuffer(buffer);
        target.writeToBuffer(buffer);
        other.writeToBuffer(buffer);
        buffer.putInt(digitInteger);
        buffer.putFloat(digitFloat);
        BufferUtil.putBoolean(buffer, complexGrammar);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        this.stringId.readFromBuffer(buffer);
        this.actor.readFromBuffer(buffer);
        this.target.readFromBuffer(buffer);
        this.other.readFromBuffer(buffer);
        this.digitInteger = buffer.getInt();
        this.digitFloat = buffer.getFloat();
        this.complexGrammar = BufferUtil.getBoolean(buffer);
    }
}