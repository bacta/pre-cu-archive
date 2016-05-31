package com.ocdsoft.bacta.swg.server.game.message.outofband;

import com.ocdsoft.bacta.swg.shared.localization.StringId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Created by crush on 5/30/2016.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProsePackageBuilder {
    private final StringId stringId;
    private final ProsePackageParticipant actor;
    private final ProsePackageParticipant target;
    private final ProsePackageParticipant other;
    private final int digitInteger;
    private final float digitFloat;
    private final boolean complexGrammar;

    private ProsePackageBuilder(final StringId stringId) {
        this.actor = ProsePackageParticipant.EMPTY;
        this.target = ProsePackageParticipant.EMPTY;
        this.other = ProsePackageParticipant.EMPTY;
        this.stringId = stringId;
        this.digitInteger = 0;
        this.digitFloat = 0.f;
        this.complexGrammar = false;
    }

    public static ProsePackageBuilder with(final StringId stringId) {
        return new ProsePackageBuilder(stringId);
    }

    public ProsePackageBuilder actor(final long actorNetworkId) {
        final ProsePackageParticipant actor = new ProsePackageParticipant(
                actorNetworkId,
                this.actor.getStringId(),
                this.actor.getUnicodeString());

        return new ProsePackageBuilder(this.stringId, actor, this.target, this.other, this.digitInteger, this.digitFloat, this.complexGrammar);
    }

    public ProsePackageBuilder actor(final String actorName) {
        final ProsePackageParticipant actor = new ProsePackageParticipant(
                this.actor.getId(),
                this.actor.getStringId(),
                actorName);

        return new ProsePackageBuilder(this.stringId, actor, this.target, this.other, this.digitInteger, this.digitFloat, this.complexGrammar);
    }

    public ProsePackageBuilder actor(final StringId actorStringId) {
        final ProsePackageParticipant actor = new ProsePackageParticipant(
                this.actor.getId(),
                actorStringId,
                this.actor.getUnicodeString());

        return new ProsePackageBuilder(this.stringId, actor, this.target, this.other, this.digitInteger, this.digitFloat, this.complexGrammar);
    }

    public ProsePackageBuilder target(final long targetNetworkId) {
        final ProsePackageParticipant target = new ProsePackageParticipant(
                targetNetworkId,
                this.target.getStringId(),
                this.target.getUnicodeString());

        return new ProsePackageBuilder(this.stringId, this.actor, target, this.other, this.digitInteger, this.digitFloat, this.complexGrammar);
    }

    public ProsePackageBuilder target(final String targetName) {
        final ProsePackageParticipant target = new ProsePackageParticipant(
                this.target.getId(),
                this.target.getStringId(),
                targetName);

        return new ProsePackageBuilder(this.stringId, this.actor, target, this.other, this.digitInteger, this.digitFloat, this.complexGrammar);
    }

    public ProsePackageBuilder target(final StringId targetStringId) {
        final ProsePackageParticipant target = new ProsePackageParticipant(
                this.target.getId(),
                targetStringId,
                this.target.getUnicodeString());

        return new ProsePackageBuilder(this.stringId, this.actor, target, this.other, this.digitInteger, this.digitFloat, this.complexGrammar);
    }

    public ProsePackageBuilder other(final long otherNetworkId) {
        final ProsePackageParticipant other = new ProsePackageParticipant(
                otherNetworkId,
                this.other.getStringId(),
                this.other.getUnicodeString());

        return new ProsePackageBuilder(this.stringId, this.actor, this.target, other, this.digitInteger, this.digitFloat, this.complexGrammar);
    }

    public ProsePackageBuilder other(final String otherName) {
        final ProsePackageParticipant other = new ProsePackageParticipant(
                this.other.getId(),
                this.other.getStringId(),
                otherName);

        return new ProsePackageBuilder(this.stringId, this.actor, this.target, other, this.digitInteger, this.digitFloat, this.complexGrammar);
    }

    public ProsePackageBuilder other(final StringId otherStringId) {
        final ProsePackageParticipant other = new ProsePackageParticipant(
                this.other.getId(),
                otherStringId,
                this.other.getUnicodeString());

        return new ProsePackageBuilder(this.stringId, this.actor, this.target, other, this.digitInteger, this.digitFloat, this.complexGrammar);
    }

    public ProsePackageBuilder value(final int value) {
        return new ProsePackageBuilder(this.stringId, this.actor, this.target, other, value, this.digitFloat, this.complexGrammar);
    }

    public ProsePackageBuilder value(final float value) {
        return new ProsePackageBuilder(this.stringId, this.actor, this.target, other, this.digitInteger, value, this.complexGrammar);
    }

    public ProsePackageBuilder usingComplexGrammar() {
        return new ProsePackageBuilder(this.stringId, this.actor, this.target, other, this.digitInteger, this.digitFloat, true);
    }

    public ProsePackage build() {
        return new ProsePackage(stringId, actor, target, other, digitInteger, digitFloat, complexGrammar);
    }
}
