package com.ocdsoft.bacta.swg.precu.object.intangible.player;

import com.ocdsoft.bacta.swg.precu.message.game.outofband.WaypointDataBase;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.*;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.map.AutoDeltaLongObjectMap;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.map.AutoDeltaStringIntMap;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.vector.AutoDeltaByteVector;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.vector.AutoDeltaObjectVector;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.vector.AutoDeltaStringVector;
import com.ocdsoft.bacta.swg.precu.object.intangible.IntangibleObject;
import com.ocdsoft.bacta.swg.precu.object.matchmaking.MatchMakingId;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CraftingStage;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerPlayerObjectTemplate;


public final class PlayerObject extends IntangibleObject {
    private String biography = "";

    private final AutoDeltaVariable<MatchMakingId> matchMakingCharacterProfileId;
    private final AutoDeltaVariable<MatchMakingId> matchMakingPersonalProfileId;
    private final AutoDeltaString skillTitle;
    private final AutoDeltaInt bornDate;
    private final AutoDeltaInt playedTime;

    private final AutoDeltaByte privilegedTitle;

    private final AutoDeltaStringIntMap experienceList;
    private final AutoDeltaLongObjectMap<WaypointDataBase> waypointList;
    private final AutoDeltaInt forcePower;
    private final AutoDeltaInt forcePowerMax;
    private final AutoDeltaByteVector fsQuestMask;
    private final AutoDeltaByteVector fsQuestMaskCompleted;
    private final AutoDeltaObjectVector<QuestJournalEntry> questJournal;

    private final AutoDeltaStringVector commands;
    private final AutoDeltaInt experimentationFlag;
    private final AutoDeltaInt craftingStage;
    private final AutoDeltaLong craftingStation;
    private final AutoDeltaObjectVector<DraftSchematicEntry> draftSchematics;
    private final AutoDeltaInt experimentationPoints;
    private final AutoDeltaInt accomplishmentCounter;
    private final AutoDeltaStringVector friendList;
    private final AutoDeltaStringVector ignoreList;
    private final AutoDeltaInt spokenLanguage;
    private final AutoDeltaInt food;
    private final AutoDeltaInt maxFood;
    private final AutoDeltaInt drink;
    private final AutoDeltaInt maxDrink;
    private final AutoDeltaInt meds;
    private final AutoDeltaInt maxMeds;
    private final AutoDeltaLongObjectMap<WaypointDataBase> groupWaypoints;
    private final AutoDeltaInt jediState;


    public PlayerObject(final ServerPlayerObjectTemplate template) {
        super(template);

        matchMakingCharacterProfileId = new AutoDeltaVariable<>(MatchMakingId::new);
        matchMakingPersonalProfileId = new AutoDeltaVariable<>(MatchMakingId::new);
        skillTitle = new AutoDeltaString("");
        bornDate = new AutoDeltaInt();
        playedTime = new AutoDeltaInt();

        privilegedTitle = new AutoDeltaByte(PlayerDataPriviledgedTitle.NORMAL_PLAYER);

        experienceList = new AutoDeltaStringIntMap();
        waypointList = new AutoDeltaLongObjectMap<>(WaypointDataBase::new);
        forcePower = new AutoDeltaInt(0);
        forcePowerMax = new AutoDeltaInt();
        fsQuestMask = new AutoDeltaByteVector();
        fsQuestMaskCompleted = new AutoDeltaByteVector();
        questJournal = new AutoDeltaObjectVector<>(QuestJournalEntry::new);

        commands = new AutoDeltaStringVector();
        experimentationFlag = new AutoDeltaInt(35);
        craftingStage = new AutoDeltaInt(CraftingStage.NONE);
        craftingStation = new AutoDeltaLong();
        draftSchematics = new AutoDeltaObjectVector<>(DraftSchematicEntry::new);
        experimentationPoints = new AutoDeltaInt(8);
        accomplishmentCounter = new AutoDeltaInt(0);
        friendList = new AutoDeltaStringVector();
        ignoreList = new AutoDeltaStringVector();
        spokenLanguage = new AutoDeltaInt(0);
        food = new AutoDeltaInt(0);
        maxFood = new AutoDeltaInt(100);
        drink = new AutoDeltaInt(0);
        maxDrink = new AutoDeltaInt(100);
        meds = new AutoDeltaInt(0);
        maxMeds = new AutoDeltaInt(100);
        groupWaypoints = new AutoDeltaLongObjectMap<>(WaypointDataBase::new);
        jediState = new AutoDeltaInt(0);

        sharedPackage.addVariable(matchMakingCharacterProfileId);
        sharedPackage.addVariable(matchMakingPersonalProfileId);
        sharedPackage.addVariable(skillTitle);
        sharedPackage.addVariable(bornDate);
        sharedPackage.addVariable(playedTime);

        sharedPackageNp.addVariable(privilegedTitle);

        firstParentAuthClientServerPackage.addVariable(experienceList);
        firstParentAuthClientServerPackage.addVariable(waypointList);
        firstParentAuthClientServerPackage.addVariable(forcePower);
        firstParentAuthClientServerPackage.addVariable(forcePowerMax);
        firstParentAuthClientServerPackage.addVariable(fsQuestMask);
        firstParentAuthClientServerPackage.addVariable(fsQuestMaskCompleted);
        firstParentAuthClientServerPackage.addVariable(questJournal);

        firstParentAuthClientServerPackageNp.addVariable(commands);
        firstParentAuthClientServerPackageNp.addVariable(experimentationFlag);
        firstParentAuthClientServerPackageNp.addVariable(craftingStage);
        firstParentAuthClientServerPackageNp.addVariable(craftingStation);
        firstParentAuthClientServerPackageNp.addVariable(draftSchematics);
        firstParentAuthClientServerPackageNp.addVariable(experimentationPoints);
        firstParentAuthClientServerPackageNp.addVariable(accomplishmentCounter);
        firstParentAuthClientServerPackageNp.addVariable(friendList);
        firstParentAuthClientServerPackageNp.addVariable(ignoreList);
        firstParentAuthClientServerPackageNp.addVariable(spokenLanguage);
        firstParentAuthClientServerPackageNp.addVariable(food);
        firstParentAuthClientServerPackageNp.addVariable(maxFood);
        firstParentAuthClientServerPackageNp.addVariable(drink);
        firstParentAuthClientServerPackageNp.addVariable(maxDrink);
        firstParentAuthClientServerPackageNp.addVariable(meds);
        firstParentAuthClientServerPackageNp.addVariable(maxMeds);
        firstParentAuthClientServerPackageNp.addVariable(groupWaypoints);
        firstParentAuthClientServerPackageNp.addVariable(jediState);

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
