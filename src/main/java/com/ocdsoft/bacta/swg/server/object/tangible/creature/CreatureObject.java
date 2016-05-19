package com.ocdsoft.bacta.swg.server.object.tangible.creature;


import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.archive.delta.*;
import com.ocdsoft.bacta.swg.archive.delta.map.AutoDeltaIntObjectMap;
import com.ocdsoft.bacta.swg.archive.delta.map.AutoDeltaStringIntMap;
import com.ocdsoft.bacta.swg.archive.delta.map.AutoDeltaStringObjectMap;
import com.ocdsoft.bacta.swg.archive.delta.packedmap.AutoDeltaPackedBuffMap;
import com.ocdsoft.bacta.swg.archive.delta.set.AutoDeltaStringSet;
import com.ocdsoft.bacta.swg.archive.delta.vector.AutoDeltaFloatVector;
import com.ocdsoft.bacta.swg.archive.delta.vector.AutoDeltaIntVector;
import com.ocdsoft.bacta.swg.archive.delta.vector.AutoDeltaObjectVector;
import com.ocdsoft.bacta.swg.server.controller.game.UpdatePostureMessage;
import com.ocdsoft.bacta.swg.server.event.ObservableGameEvent;
import com.ocdsoft.bacta.swg.server.message.game.object.PostureMessage;
import com.ocdsoft.bacta.swg.server.object.buff.Buff;
import com.ocdsoft.bacta.swg.server.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.server.object.template.shared.SharedCreatureObjectTemplate;
import com.ocdsoft.bacta.swg.server.object.template.shared.SharedCreatureObjectTemplate.MovementTypes;
import com.ocdsoft.bacta.swg.server.object.universe.group.GroupInviter;
import com.ocdsoft.bacta.swg.server.object.universe.group.GroupMissionCriticalObject;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;
import gnu.trove.list.TIntList;

public class CreatureObject extends TangibleObject {
    private final AutoDeltaIntVector unmodifiedMaxAttributes;
    private final AutoDeltaStringSet skills;
    private final AutoDeltaByte posture;
    private final AutoDeltaByte rank;
    private final AutoDeltaLong masterId;
    private final AutoDeltaFloat scaleFactor;
    private final AutoDeltaLong states;
    private final AutoDeltaIntVector attributeWounds;
    private final AutoDeltaFloat accelPercent;
    private final AutoDeltaFloat accelScale;
    private final AutoDeltaStringObjectMap<SkillModEntry> modMap;
    private final AutoDeltaFloat movementPercent;
    private final AutoDeltaFloat movementScale;
    private final AutoDeltaLong performanceListenTarget;
    private final AutoDeltaFloat runSpeed;
    private final AutoDeltaFloat slopeModAngle;
    private final AutoDeltaFloat slopeModPercent;
    private final AutoDeltaFloat turnScale;
    private final AutoDeltaFloat walkSpeed;
    private final AutoDeltaFloat waterModPercent;
    private final AutoDeltaObjectVector<GroupMissionCriticalObject> groupMissionCriticalObjectList;
    private final AutoDeltaShort level;
    private final AutoDeltaString animatingSkillData;
    private final AutoDeltaString animationMood;
    private final AutoDeltaLong currentWeapon;
    private final AutoDeltaLong group;
    private final AutoDeltaVariable<GroupInviter> groupInviter;
    private final AutoDeltaInt guildId;
    private final AutoDeltaLong lookAtTarget;
    private final AutoDeltaByte moodId;
    private final AutoDeltaInt performanceStartTime;
    private final AutoDeltaInt performanceType;
    private final AutoDeltaIntVector attributes;
    private final AutoDeltaIntVector maxAttributes;
    private final AutoDeltaIntVector totalAttributes; //The current attributes, with all mods applied.
    private final AutoDeltaIntVector totalMaxAttributes; //The max attributes, with all mods applied.
    private final AutoDeltaIntVector attribBonus;
    private final AutoDeltaInt shockWounds;
    private final AutoDeltaObjectVector<WearableEntry> wearableData;
    private final AutoDeltaString alternateAppearanceSharedObjectTemplateName;
    private final AutoDeltaBoolean coverVisibility;
    private final AutoDeltaIntObjectMap<Buff.PackedBuff> buffs;
    private final AutoDeltaPackedBuffMap persistedBuffs;
    private final AutoDeltaInt hologramType;
    private final AutoDeltaBoolean clientUsesAnimationLocomotion;
    private final AutoDeltaByte difficulty;
    private final AutoDeltaBoolean visibleOnMapAndRadar;
    private final AutoDeltaStringIntMap commands; //game commands a creature may execute.
    private final AutoDeltaBoolean isBeast;
    private final AutoDeltaBoolean forceShowHam;
    private final AutoDeltaObjectVector<WearableEntry> wearableAppearanceData; //Vector for our appearance items.
    private final AutoDeltaLong decoyOrigin; //The OID of the player whome we copied for this decoy creature.
    private final AutoDeltaInt totalLevelXp;
    private final AutoDeltaInt levelHealthGranted;
    private final AutoDeltaLong inviterForPendingGroup;
    private final AutoDeltaIntVector timedMod;
    private final AutoDeltaFloatVector timedModDuration;
    private final AutoDeltaIntVector timedModUpdateTime;
    private final AutoDeltaLong intendedTarget;
    private final AutoDeltaByte mood;


    @Inject
    public CreatureObject(final ObjectTemplateList objectTempalteList,
                          final SlotIdManager slotIdManager,
                          final ServerObjectTemplate template) {
        super(objectTempalteList, slotIdManager, template);

        unmodifiedMaxAttributes = new AutoDeltaIntVector();
        skills = new AutoDeltaStringSet();
        posture = new AutoDeltaByte(CreaturePosture.UPRIGHT);
        rank = new AutoDeltaByte((byte) 0);
        masterId = new AutoDeltaLong();
        scaleFactor = new AutoDeltaFloat(1.0F);
        shockWounds = new AutoDeltaInt(0);
        states = new AutoDeltaLong(0L);
        attributeWounds = new AutoDeltaIntVector();
        accelPercent = new AutoDeltaFloat(1.0F);
        accelScale = new AutoDeltaFloat(1.0F);
        attribBonus = new AutoDeltaIntVector(CreatureAttribute.SIZE);
        modMap = new AutoDeltaStringObjectMap<>(SkillModEntry::new);
        movementPercent = new AutoDeltaFloat(1.0F);
        movementScale = new AutoDeltaFloat(1.0F);
        performanceListenTarget = new AutoDeltaLong();
        runSpeed = new AutoDeltaFloat(((SharedCreatureObjectTemplate) getSharedTemplate()).getSpeed(MovementTypes.MT_run));
        slopeModAngle = new AutoDeltaFloat((float) (((SharedCreatureObjectTemplate) getSharedTemplate()).getSlopeModAngle() * Math.PI / 180));
        slopeModPercent = new AutoDeltaFloat(1.0F);
        turnScale = new AutoDeltaFloat(1.0F);
        walkSpeed = new AutoDeltaFloat(((SharedCreatureObjectTemplate) getSharedTemplate()).getSpeed(MovementTypes.MT_walk));
        waterModPercent = new AutoDeltaFloat(((SharedCreatureObjectTemplate) getSharedTemplate()).getWaterModPercent());
        groupMissionCriticalObjectList = new AutoDeltaObjectVector<>(GroupMissionCriticalObject::new);
        level = new AutoDeltaShort((short) -1);
        animatingSkillData = new AutoDeltaString();
        animationMood = new AutoDeltaString("neutral");
        currentWeapon = new AutoDeltaLong();
        group = new AutoDeltaLong();
        groupInviter = new AutoDeltaVariable<>(GroupInviter::new);
        guildId = new AutoDeltaInt(0);
        lookAtTarget = new AutoDeltaLong();
        moodId = new AutoDeltaByte();
        performanceStartTime = new AutoDeltaInt();
        performanceType = new AutoDeltaInt();
        attributes = new AutoDeltaIntVector(CreatureAttribute.SIZE);
        maxAttributes = new AutoDeltaIntVector(CreatureAttribute.SIZE);
        wearableData = new AutoDeltaObjectVector<>(WearableEntry::new);
        alternateAppearanceSharedObjectTemplateName = new AutoDeltaString();
        coverVisibility = new AutoDeltaBoolean(true);

        totalAttributes = new AutoDeltaIntVector();
        totalMaxAttributes = new AutoDeltaIntVector();
        buffs = new AutoDeltaIntObjectMap<>(Buff.PackedBuff::new);
        persistedBuffs = new AutoDeltaPackedBuffMap();
        hologramType = new AutoDeltaInt();
        clientUsesAnimationLocomotion = new AutoDeltaBoolean();
        difficulty = new AutoDeltaByte();
        visibleOnMapAndRadar = new AutoDeltaBoolean();
        commands = new AutoDeltaStringIntMap();
        isBeast = new AutoDeltaBoolean();
        forceShowHam = new AutoDeltaBoolean();
        wearableAppearanceData = new AutoDeltaObjectVector<>(WearableEntry::new);
        decoyOrigin = new AutoDeltaLong();
        totalLevelXp = new AutoDeltaInt();
        levelHealthGranted = new AutoDeltaInt();
        inviterForPendingGroup = new AutoDeltaLong();
        timedMod = new AutoDeltaIntVector();
        timedModDuration = new AutoDeltaFloatVector();
        timedModUpdateTime = new AutoDeltaIntVector();
        intendedTarget = new AutoDeltaLong();
        mood = new AutoDeltaByte();
    }

    private void addMembersToPackages() {
        authClientServerPackage.addVariable(maxAttributes);
        authClientServerPackage.addVariable(skills);

        authClientServerPackageNp.addVariable(accelPercent);
        authClientServerPackageNp.addVariable(accelScale);
        authClientServerPackageNp.addVariable(attribBonus);
        authClientServerPackageNp.addVariable(modMap);
        authClientServerPackageNp.addVariable(movementPercent);
        authClientServerPackageNp.addVariable(movementScale);
        authClientServerPackageNp.addVariable(performanceListenTarget);
        authClientServerPackageNp.addVariable(runSpeed);
        authClientServerPackageNp.addVariable(slopeModAngle);
        authClientServerPackageNp.addVariable(slopeModPercent);
        authClientServerPackageNp.addVariable(turnScale);
        authClientServerPackageNp.addVariable(walkSpeed);
        authClientServerPackageNp.addVariable(waterModPercent);
        authClientServerPackageNp.addVariable(groupMissionCriticalObjectList);
        authClientServerPackageNp.addVariable(commands);
        authClientServerPackageNp.addVariable(totalLevelXp);

        sharedPackage.addVariable(posture);
        sharedPackage.addVariable(rank);
        sharedPackage.addVariable(masterId);
        sharedPackage.addVariable(scaleFactor);
        sharedPackage.addVariable(shockWounds);
        sharedPackage.addVariable(states);

        sharedPackageNp.addVariable(level);
        sharedPackageNp.addVariable(levelHealthGranted);
        sharedPackageNp.addVariable(animatingSkillData);
        sharedPackageNp.addVariable(animationMood);
        sharedPackageNp.addVariable(currentWeapon);
        sharedPackageNp.addVariable(group);
        sharedPackageNp.addVariable(groupInviter);
        sharedPackageNp.addVariable(inviterForPendingGroup);
        sharedPackageNp.addVariable(guildId);
        sharedPackageNp.addVariable(lookAtTarget);
        sharedPackageNp.addVariable(intendedTarget);
        sharedPackageNp.addVariable(mood);
        sharedPackageNp.addVariable(performanceStartTime);
        sharedPackageNp.addVariable(performanceType);
        sharedPackageNp.addVariable(totalAttributes);
        sharedPackageNp.addVariable(totalMaxAttributes);
        sharedPackageNp.addVariable(wearableData);
        sharedPackageNp.addVariable(alternateAppearanceSharedObjectTemplateName);
        sharedPackageNp.addVariable(timedMod);
        sharedPackageNp.addVariable(timedModDuration);
        sharedPackageNp.addVariable(timedModUpdateTime);
        sharedPackageNp.addVariable(coverVisibility);
        sharedPackageNp.addVariable(buffs);
        sharedPackageNp.addVariable(clientUsesAnimationLocomotion);
        sharedPackageNp.addVariable(difficulty);
        sharedPackageNp.addVariable(hologramType);
        sharedPackageNp.addVariable(visibleOnMapAndRadar);
        sharedPackageNp.addVariable(isBeast);
        sharedPackageNp.addVariable(forceShowHam);
        sharedPackageNp.addVariable(wearableAppearanceData);
        sharedPackageNp.addVariable(decoyOrigin);
    }

    public long getMasterId() {
        return masterId.get();
    }

    public final void setPosture(byte posture) {

        if (this.posture.get() == posture) {
            return;
        }

        this.posture.set(posture);

        PostureMessage postureMessage = new PostureMessage(this, posture);
        broadcastMessage(postureMessage);

        notifyObservers(ObservableGameEvent.POSTURE_CHANGE);
    }

    public final byte getPosture() {
        return this.posture.get();
    }

    public final void addSkill(final String skill) {
        skills.insert(skill);
        setDirty(true);
    }

    public final void removeSkill(final String skill) {
        skills.erase(skill);
        setDirty(true);
    }

    public final boolean hasSkill(final String skill) {
        return skills.contains(skill);
    }

    public final void setLookAtTarget(long lookAtTarget) {
        this.lookAtTarget.set(lookAtTarget);
        setDirty(true);
    }

    public final long getLookAtTarget() {
        return lookAtTarget.get();
    }

    //hamBase
    public final int getHealthBase() {
        return unmodifiedMaxAttributes.get(CreatureAttribute.HEALTH);
    }

    public final int getStrengthBase() {
        return unmodifiedMaxAttributes.get(CreatureAttribute.STRENGTH);
    }

    public final int getConstitutionBase() {
        return unmodifiedMaxAttributes.get(CreatureAttribute.CONSTITUTION);
    }

    public final int getActionBase() {
        return unmodifiedMaxAttributes.get(CreatureAttribute.ACTION);
    }

    public final int getQuicknessBase() {
        return unmodifiedMaxAttributes.get(CreatureAttribute.QUICKNESS);
    }

    public final int getStaminaBase() {
        return unmodifiedMaxAttributes.get(CreatureAttribute.STAMINA);
    }

    public final int getMindBase() {
        return unmodifiedMaxAttributes.get(CreatureAttribute.MIND);
    }

    public final int getFocusBase() {
        return unmodifiedMaxAttributes.get(CreatureAttribute.FOCUS);
    }

    public final int getWillpowerBase() {
        return unmodifiedMaxAttributes.get(CreatureAttribute.WILLPOWER);
    }

    public final void setUnmodifiedMaxAttributes(TIntList values) {
        unmodifiedMaxAttributes.set(values);
        setDirty(true);
    }

    public final void setHealthBase(int value) {
        unmodifiedMaxAttributes.set(CreatureAttribute.HEALTH, value);
        setDirty(true);
    }

    public final void setStrengthBase(int value) {
        unmodifiedMaxAttributes.set(CreatureAttribute.STRENGTH, value);
        setDirty(true);
    }

    public final void setConstitutionBase(int value) {
        unmodifiedMaxAttributes.set(CreatureAttribute.CONSTITUTION, value);
        setDirty(true);
    }

    public final void setActionBase(int value) {
        unmodifiedMaxAttributes.set(CreatureAttribute.ACTION, value);
        setDirty(true);
    }

    public final void setQuicknessBase(int value) {
        unmodifiedMaxAttributes.set(CreatureAttribute.QUICKNESS, value);
        setDirty(true);
    }

    public final void setStaminaBase(int value) {
        unmodifiedMaxAttributes.set(CreatureAttribute.STAMINA, value);
        setDirty(true);
    }

    public final void setMindBase(int value) {
        unmodifiedMaxAttributes.set(CreatureAttribute.MIND, value);
        setDirty(true);
    }

    public final void setFocusBase(int value) {
        unmodifiedMaxAttributes.set(CreatureAttribute.FOCUS, value);
        setDirty(true);
    }

    public final void setWillpowerBase(int value) {
        unmodifiedMaxAttributes.set(CreatureAttribute.WILLPOWER, value);
        setDirty(true);
    }

    //Attribute wounds
    public final int getHealthWounds() {
        return attributeWounds.get(CreatureAttribute.HEALTH);
    }

    public final int getStrengthWounds() {
        return attributeWounds.get(CreatureAttribute.STRENGTH);
    }

    public final int getConstitutionWounds() {
        return attributeWounds.get(CreatureAttribute.CONSTITUTION);
    }

    public final int getActionWounds() {
        return attributeWounds.get(CreatureAttribute.ACTION);
    }

    public final int getQuicknessWounds() {
        return attributeWounds.get(CreatureAttribute.QUICKNESS);
    }

    public final int getStaminaWounds() {
        return attributeWounds.get(CreatureAttribute.STAMINA);
    }

    public final int getMindWounds() {
        return attributeWounds.get(CreatureAttribute.MIND);
    }

    public final int getFocusWounds() {
        return attributeWounds.get(CreatureAttribute.FOCUS);
    }

    public final int getWillpowerWounds() {
        return attributeWounds.get(CreatureAttribute.WILLPOWER);
    }

    public final void setHealthWounds(int value) {
        attributeWounds.set(CreatureAttribute.HEALTH, value);
        setDirty(true);
    }

    public final void setStrengthWounds(int value) {
        attributeWounds.set(CreatureAttribute.STRENGTH, value);
        setDirty(true);
    }

    public final void setConstitutionWounds(int value) {
        attributeWounds.set(CreatureAttribute.CONSTITUTION, value);
        setDirty(true);
    }

    public final void setActionWounds(int value) {
        attributeWounds.set(CreatureAttribute.ACTION, value);
        setDirty(true);
    }

    public final void setQuicknessWounds(int value) {
        attributeWounds.set(CreatureAttribute.QUICKNESS, value);
        setDirty(true);
    }

    public final void setStaminaWounds(int value) {
        attributeWounds.set(CreatureAttribute.STAMINA, value);
        setDirty(true);
    }

    public final void setMindWounds(int value) {
        attributeWounds.set(CreatureAttribute.MIND, value);
        setDirty(true);
    }

    public final void setFocusWounds(int value) {
        attributeWounds.set(CreatureAttribute.FOCUS, value);
        setDirty(true);
    }

    public final void setWillpowerWounds(int value) {
        attributeWounds.set(CreatureAttribute.WILLPOWER, value);
        setDirty(true);
    }

    //hamEncumbrance
    public final int getHealthEncumbrance() {
        return attribBonus.get(CreatureAttribute.HEALTH);
    }

    public final int getStrengthEncumbrance() {
        return attribBonus.get(CreatureAttribute.STRENGTH);
    }

    public final int getConstitutionEncumbrance() {
        return attribBonus.get(CreatureAttribute.CONSTITUTION);
    }

    public final int getActionEncumbrance() {
        return attribBonus.get(CreatureAttribute.ACTION);
    }

    public final int getQuicknessEncumbrance() {
        return attribBonus.get(CreatureAttribute.QUICKNESS);
    }

    public final int getStaminaEncumbrance() {
        return attribBonus.get(CreatureAttribute.STAMINA);
    }

    public final int getMindEncumbrance() {
        return attribBonus.get(CreatureAttribute.MIND);
    }

    public final int getFocusEncumbrance() {
        return attribBonus.get(CreatureAttribute.FOCUS);
    }

    public final int getWillpowerEncumbrance() {
        return attribBonus.get(CreatureAttribute.WILLPOWER);
    }

    public final void setHealthEncumbrance(int value) {
        attribBonus.set(CreatureAttribute.HEALTH, value);
        setDirty(true);
    }

    public final void setStrengthEncumbrance(int value) {
        attribBonus.set(CreatureAttribute.STRENGTH, value);
        setDirty(true);
    }

    public final void setConstitutionEncumbrance(int value) {
        attribBonus.set(CreatureAttribute.CONSTITUTION, value);
        setDirty(true);
    }

    public final void setActionEncumbrance(int value) {
        attribBonus.set(CreatureAttribute.ACTION, value);
        setDirty(true);
    }

    public final void setQuicknessEncumbrance(int value) {
        attribBonus.set(CreatureAttribute.QUICKNESS, value);
        setDirty(true);
    }

    public final void setStaminaEncumbrance(int value) {
        attribBonus.set(CreatureAttribute.STAMINA, value);
        setDirty(true);
    }

    public final void setMindEncumbrance(int value) {
        attribBonus.set(CreatureAttribute.MIND, value);
        setDirty(true);
    }

    public final void setFocusEncumbrance(int value) {
        attribBonus.set(CreatureAttribute.FOCUS, value);
        setDirty(true);
    }

    public final void setWillpowerEncumbrance(int value) {
        attribBonus.set(CreatureAttribute.WILLPOWER, value);
        setDirty(true);
    }

    //ham
    public final int getHealth() {
        return attributes.get(CreatureAttribute.HEALTH);
    }

    public final int getStrength() {
        return attributes.get(CreatureAttribute.STRENGTH);
    }

    public final int getConstitution() {
        return attributes.get(CreatureAttribute.CONSTITUTION);
    }

    public final int getAction() {
        return attributes.get(CreatureAttribute.ACTION);
    }

    public final int getQuickness() {
        return attributes.get(CreatureAttribute.QUICKNESS);
    }

    public final int getStamina() {
        return attributes.get(CreatureAttribute.STAMINA);
    }

    public final int getMind() {
        return attributes.get(CreatureAttribute.MIND);
    }

    public final int getFocus() {
        return attributes.get(CreatureAttribute.FOCUS);
    }

    public final int getWillpower() {
        return attributes.get(CreatureAttribute.WILLPOWER);
    }

    public final void setAttributes(TIntList value) {
        attributes.set(value);
        setDirty(true);
    }

    public final void setHealth(int value) {
        attributes.set(CreatureAttribute.HEALTH, value);
        setDirty(true);
    }

    public final void setStrength(int value) {
        attributes.set(CreatureAttribute.STRENGTH, value);
        setDirty(true);
    }

    public final void setConstitution(int value) {
        attributes.set(CreatureAttribute.CONSTITUTION, value);
        setDirty(true);
    }

    public final void setAction(int value) {
        attributes.set(CreatureAttribute.ACTION, value);
        setDirty(true);
    }

    public final void setQuickness(int value) {
        attributes.set(CreatureAttribute.QUICKNESS, value);
        setDirty(true);
    }

    public final void setStamina(int value) {
        attributes.set(CreatureAttribute.STAMINA, value);
        setDirty(true);
    }

    public final void setMind(int value) {
        attributes.set(CreatureAttribute.MIND, value);
        setDirty(true);
    }

    public final void setFocus(int value) {
        attributes.set(CreatureAttribute.FOCUS, value);
        setDirty(true);
    }

    public final void setWillpower(int value) {
        attributes.set(CreatureAttribute.WILLPOWER, value);
        setDirty(true);
    }

    //hamMax
    public final int getHealthMax() {
        return maxAttributes.get(CreatureAttribute.HEALTH);
    }

    public final int getStrengthMax() {
        return maxAttributes.get(CreatureAttribute.STRENGTH);
    }

    public final int getConstitutionMax() {
        return maxAttributes.get(CreatureAttribute.CONSTITUTION);
    }

    public final int getActionMax() {
        return maxAttributes.get(CreatureAttribute.ACTION);
    }

    public final int getQuicknessMax() {
        return maxAttributes.get(CreatureAttribute.QUICKNESS);
    }

    public final int getStaminaMax() {
        return maxAttributes.get(CreatureAttribute.STAMINA);
    }

    public final int getMindMax() {
        return maxAttributes.get(CreatureAttribute.MIND);
    }

    public final int getFocusMax() {
        return maxAttributes.get(CreatureAttribute.FOCUS);
    }

    public final int getWillpowerMax() {
        return maxAttributes.get(CreatureAttribute.WILLPOWER);
    }

    public final void setMaxAttributes(TIntList value) {
        maxAttributes.set(value);
        setDirty(true);
    }

    public final void setHealthMax(int value) {
        maxAttributes.set(CreatureAttribute.HEALTH, value);
        setDirty(true);
    }

    public final void setStrengthMax(int value) {
        maxAttributes.set(CreatureAttribute.STRENGTH, value);
        setDirty(true);
    }

    public final void setConstitutionMax(int value) {
        maxAttributes.set(CreatureAttribute.CONSTITUTION, value);
        setDirty(true);
    }

    public final void setActionMax(int value) {
        maxAttributes.set(CreatureAttribute.ACTION, value);
        setDirty(true);
    }

    public final void setQuicknessMax(int value) {
        maxAttributes.set(CreatureAttribute.QUICKNESS, value);
        setDirty(true);
    }

    public final void setStaminaMax(int value) {
        maxAttributes.set(CreatureAttribute.STAMINA, value);
        setDirty(true);
    }

    public final void setMindMax(int value) {
        maxAttributes.set(CreatureAttribute.MIND, value);
        setDirty(true);
    }

    public final void setFocusMax(int value) {
        maxAttributes.set(CreatureAttribute.FOCUS, value);
        setDirty(true);
    }

    public final void setWillpowerMax(int value) {
        maxAttributes.set(CreatureAttribute.WILLPOWER, value);
        setDirty(true);
    }

    public final float getRunSpeed() {
        return runSpeed.get();
    }

    public final float setWalkSpeed() {
        return walkSpeed.get();
    }

    public final float setSlopeModAngle() {
        return slopeModAngle.get();
    }

    public final float setSlopeModPercent() {
        return slopeModPercent.get();
    }

    public final float setWaterModPercent() {
        return waterModPercent.get();
    }

    public final float getScaleFactor() {
        return scaleFactor.get();
    }

    public final void setRunSpeed(float speed) {
        runSpeed.set(speed);
        setDirty(true);
    }

    public final void setWalkSpeed(float speed) {
        walkSpeed.set(speed);
        setDirty(true);
    }

    public final void setSlopeModAngle(float angle) {
        slopeModAngle.set(angle);
        setDirty(true);
    }

    public final void setSlopeModPercent(float percent) {
        slopeModPercent.set(percent);
        setDirty(true);
    }

    public final void setWaterModPercent(float percent) {
        waterModPercent.set(percent);
        setDirty(true);
    }

    public final void setScaleFactor(float value) {
        scaleFactor.set(value);
        setDirty(true);
    }

    public final boolean isIncapacitated() {
        final byte posture = getPosture();
        return (posture == CreaturePosture.INCAPACITATED /*&& !getState(CreatureState.FEIGN_DEATH)*/)
                || posture == CreaturePosture.DEAD;
    }

    public final boolean isDead() {
        return getPosture() == CreaturePosture.DEAD;
    }

    public boolean isDisabled() {
        //Check if its a vehicle type or sub type, then super.isDisabled()
        return (isIncapacitated() || isDead());
    }

    @Override
    protected void sendObjectSpecificBaselinesToClient(final SoeUdpConnection client) {
        super.sendObjectSpecificBaselinesToClient(client);

//        final Property property = getProperty(SlowDownProperty.getClassPropertyId());
//
//        if (property != null) {
//            final SlowDownProperty slowDownProperty = (SlowDownProperty)property;
//
//            final SlowDownEffectMessage message = new SlowDownEffectMessage();
//            client.sendMessage(message);
//        }

        final UpdatePostureMessage updatePostureMessage = new UpdatePostureMessage(getNetworkId(), getPosture());
        client.sendMessage(updatePostureMessage);
    }

    /**
     * Checks if the provided object is an instanceof CreatureObject, and then casts it. Otherwise returns null.
     *
     * @param object The object to cast to CreatureObject.
     * @return If object is a CreatureObject, then casts it to CreatureObject. Otherwise, returns null.
     */
    public static CreatureObject asCreatureObject(final GameObject object) {
        if (object instanceof CreatureObject)
            return (CreatureObject) object;

        return null;
    }
}
