package com.ocdsoft.bacta.swg.server.game.message.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * A ProsePackage consists of different parameters that can be substituted into a message. Three of these parameters are
 * defined as {@link ProsePackageParticipant} and encompass a {@link StringId}, NetworkId, or UTF-16 String. A ProsePackage may also set
 * an Integer and Float parameter as well.
 */

@Getter
public class ProsePackage implements ByteBufferWritable {

    private final StringId stringId;
    private final ProsePackageParticipant actor;
    private final ProsePackageParticipant target;
    private final ProsePackageParticipant other;
    private final int digitInteger;
    private final float digitFloat;
    private final boolean complexGrammar;

    public ProsePackage(final StringId stringId, final ProsePackageParticipant actor) {
        this(stringId, actor, ProsePackageParticipant.EMPTY, ProsePackageParticipant.EMPTY, 0, 0.f, false);
    }

    public ProsePackage(final StringId stringId, final ProsePackageParticipant actor, final int digitInteger) {
        this(stringId, actor, ProsePackageParticipant.EMPTY, ProsePackageParticipant.EMPTY, digitInteger, 0.f, false);
    }

    public ProsePackage(final StringId stringId, final ProsePackageParticipant actor, final float digitFloat) {
        this(stringId, actor, ProsePackageParticipant.EMPTY, ProsePackageParticipant.EMPTY, 0, digitFloat, false);
    }

    public ProsePackage(final StringId stringId, final ProsePackageParticipant actor, final ProsePackageParticipant target) {
        this(stringId, actor, target, ProsePackageParticipant.EMPTY, 0, 0.f, false);
    }

    public ProsePackage(final StringId stringId, final ProsePackageParticipant actor, final ProsePackageParticipant target, final int digitInteger) {
        this(stringId, actor, target, ProsePackageParticipant.EMPTY, digitInteger, 0.f, false);
    }

    public ProsePackage(final StringId stringId, final ProsePackageParticipant actor, final ProsePackageParticipant target, final float digitFloat) {
        this(stringId, actor, target, ProsePackageParticipant.EMPTY, 0, digitFloat, false);
    }

    public ProsePackage(final StringId stringId, final ProsePackageParticipant actor, final ProsePackageParticipant target, final ProsePackageParticipant other) {
        this(stringId, actor, target, other, 0, 0.f, false);
    }

    public ProsePackage(final StringId stringId, final ProsePackageParticipant actor, final ProsePackageParticipant target, final ProsePackageParticipant other, final int digitInteger) {
        this(stringId, actor, target, other, digitInteger, 0.f, false);
    }

    public ProsePackage(final StringId stringId, final ProsePackageParticipant actor, final ProsePackageParticipant target, final ProsePackageParticipant other, final float digitFloat) {
        this(stringId, actor, target, other, 0, digitFloat, false);
    }


    public ProsePackage(final StringId stringId,
                        final ProsePackageParticipant actor,
                        final ProsePackageParticipant target,
                        final ProsePackageParticipant other,
                        final int digitInteger,
                        final float digitFloat,
                        final boolean complexGrammar) {
        this.stringId = stringId;
        this.actor = actor;
        this.target = target;
        this.other = other;
        this.digitInteger = digitInteger;
        this.digitFloat = digitFloat;
        this.complexGrammar = complexGrammar;
    }

    public ProsePackage(final ByteBuffer buffer) {
        this.stringId = new StringId(buffer);
        this.actor = new ProsePackageParticipant(buffer);
        this.target = new ProsePackageParticipant(buffer);
        this.other = new ProsePackageParticipant(buffer);
        this.digitInteger = buffer.getInt();
        this.digitFloat = buffer.getFloat();
        this.complexGrammar = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, stringId);
        BufferUtil.put(buffer, actor);
        BufferUtil.put(buffer, target);
        BufferUtil.put(buffer, other);
        BufferUtil.put(buffer, digitInteger);
        BufferUtil.put(buffer, digitFloat);
        BufferUtil.put(buffer, complexGrammar);
    }
}