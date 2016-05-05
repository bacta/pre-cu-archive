package com.ocdsoft.bacta.swg.precu.message.game.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.localization.StringId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

/**
 * A ProsePackage consists of different parameters that can be substituted into a message. Three of these parameters are
 * defined as {@link ProsePackageParticipant} and encompass a {@link StringId}, NetworkId, or UTF-16 String. A ProsePackage may also set
 * an Integer and Float parameter as well.
 */

@Getter
@AllArgsConstructor
public class ProsePackage implements ByteBufferWritable {

    private final StringId stringId;
    private final ProsePackageParticipant actor;
    private final ProsePackageParticipant target;
    private final ProsePackageParticipant other;
    private final int digitInteger;
    private final float digitFloat;
    private final boolean complexGrammar;

    public ProsePackage(ByteBuffer buffer) {
        this.stringId = new StringId(buffer);
        this.actor = new ProsePackageParticipant(buffer);
        this.target = new ProsePackageParticipant(buffer);
        this.other = new ProsePackageParticipant(buffer);
        this.digitInteger = buffer.getInt();
        this.digitFloat = buffer.getFloat();
        this.complexGrammar = BufferUtil.getBoolean(buffer);
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
}