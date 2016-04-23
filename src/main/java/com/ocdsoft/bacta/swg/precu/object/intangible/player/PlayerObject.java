package com.ocdsoft.bacta.swg.precu.object.intangible.player;

import com.ocdsoft.bacta.swg.precu.message.game.outofband.WaypointDataBase;
import com.ocdsoft.bacta.swg.precu.object.MatchMakingId;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.*;
import com.ocdsoft.bacta.swg.precu.object.intangible.IntangibleObject;
import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;


public final class PlayerObject extends IntangibleObject {
    @Override
    public int getOpcode() {
        return 0x504C4159;
    } //'PLAY'

    private String biography = "";

    //PLAY03
    @Getter
    private final AutoDeltaVariable<MatchMakingId> matchMakingCharacterProfileId = new AutoDeltaVariable<>(new MatchMakingId(), sharedPackage);
    private final AutoDeltaVariable<MatchMakingId> matchMakingPersonalProfileId = new AutoDeltaVariable<>(new MatchMakingId(), sharedPackage);
    private final AutoDeltaString skillTitle = new AutoDeltaString("", sharedPackage);
    private final AutoDeltaInt bornDate = new AutoDeltaInt(0, sharedPackage);
    private final AutoDeltaInt playedTime = new AutoDeltaInt(0, sharedPackage);

    //PLAY06
    private final AutoDeltaByte privilegedTitle = new AutoDeltaByte(0, sharedPackageNp);

    //PLAY08
    private final AutoDeltaMap<String, Integer> experienceList = new AutoDeltaMap<>(firstParentAuthClientServerPackage);
    private final AutoDeltaMap<Long, WaypointDataBase> waypointList = new AutoDeltaMap<>(firstParentAuthClientServerPackage);
    private final AutoDeltaInt forcePower = new AutoDeltaInt(0, firstParentAuthClientServerPackage);
    private final AutoDeltaInt forcePowerMax = new AutoDeltaInt(0, firstParentAuthClientServerPackage);
    private final AutoDeltaVector<Byte> fsQuestMask = new AutoDeltaVector<>(firstParentAuthClientServerPackage);
    private final AutoDeltaVector<Byte> fsQuestMaskCompleted = new AutoDeltaVector<>(firstParentAuthClientServerPackage);
    private final AutoDeltaVector<QuestJournalEntry> questJournal = new AutoDeltaVector<>(firstParentAuthClientServerPackage);

    //PLAY09
    private final AutoDeltaVector<String> commands = new AutoDeltaVector<>(firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt experimentationFlag = new AutoDeltaInt(35, firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt craftingStage = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);
    private final AutoDeltaLong craftingStation = new AutoDeltaLong(0L, firstParentAuthClientServerPackageNp);

    //TODO: This should actually be a map.
    private final AutoDeltaVector<DraftSchematicEntry> draftSchematics = new AutoDeltaVector<>(firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt experimentationPoints = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt accomplishmentCounter = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);
    private final AutoDeltaVector<String> friendList = new AutoDeltaVector<>(firstParentAuthClientServerPackageNp);
    private final AutoDeltaVector<String> ignoreList = new AutoDeltaVector<>(firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt spokenLanguage = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt food = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt maxFood = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt drink = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt maxDrink = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt meds = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt maxMeds = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);
    private final AutoDeltaMap<Long, WaypointDataBase> groupWaypoints = new AutoDeltaMap<>(firstParentAuthClientServerPackageNp);
    private final AutoDeltaInt jediState = new AutoDeltaInt(0, firstParentAuthClientServerPackageNp);

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    public final String getBiography() { return biography; }
    public final void setBiography(final String biography) { this.biography = biography; setDirty(true); }

    public final int getBornDate() { return bornDate.get(); }
    public final void setBornDate(int value) { bornDate.set(value); setDirty(true); }

    public final int getPlayedTime() { return playedTime.get(); }
    public final void setPlayedTime(int value) { playedTime.set(value); setDirty(true); }

    public final boolean isLinkDead() { return matchMakingCharacterProfileId.get().isBitSet(MatchMakingId.linkDead); }

    public final void setLinkDead() {
        MatchMakingId matchMakingId = matchMakingCharacterProfileId.get();
        matchMakingId.set(MatchMakingId.linkDead);

        matchMakingCharacterProfileId.set(matchMakingId);
        setDirty(true);
    }

    public final void clearLinkDead() {
        MatchMakingId matchMakingId = matchMakingCharacterProfileId.get();
        matchMakingId.unset(MatchMakingId.linkDead);
        matchMakingCharacterProfileId.set(matchMakingId);
        setDirty(true);
    }
}
