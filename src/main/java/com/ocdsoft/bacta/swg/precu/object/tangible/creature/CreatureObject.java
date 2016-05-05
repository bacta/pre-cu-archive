package com.ocdsoft.bacta.swg.precu.object.tangible.creature;


import com.ocdsoft.bacta.swg.precu.event.ObservableGameEvent;
import com.ocdsoft.bacta.swg.precu.message.game.object.PostureMessage;
import com.ocdsoft.bacta.swg.precu.object.GroupInviter;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.*;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Collection;

public final class CreatureObject extends TangibleObject {
    @Override
    public int getOpcode() {
        return 0x4352454F;
    } //'CREO'

    //CreatureObjectMessage01
    private final AutoDeltaVector<Integer> unmodifiedMaxAttributes;
    private final AutoDeltaSet<String> skills;

    //CreatureObjectMessage03
    private final AutoDeltaByte posture;
    private final AutoDeltaByte rank;
    private final AutoDeltaLong masterId;
    private final AutoDeltaFloat scaleFactor;
    private final AutoDeltaInt shockWounds;
    private final AutoDeltaLong states;
    private final AutoDeltaVector<Integer> attributeWounds;

    //CreatureObjectMessage04
    private final AutoDeltaFloat accelPercent;
    private final AutoDeltaFloat accelScale;
    private final AutoDeltaVector<Integer> attribBonus;
    private final AutoDeltaMap<String, SkillModEntry> modMap;
    private final AutoDeltaFloat movementPercent;
    private final AutoDeltaFloat movementScale;
    private final AutoDeltaLong performanceListenTarget;
    private final AutoDeltaFloat runSpeed;
    private final AutoDeltaFloat slopeModAngle;
    private final AutoDeltaFloat slopeModPercent;
    private final AutoDeltaFloat turnScale;
    private final AutoDeltaFloat walkSpeed;
    private final AutoDeltaFloat waterModPercent;
    private final AutoDeltaVector<GroupMissionCriticalObject> groupMissionCriticalObjectList;

    //CreatureObjectMessage06
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
    private final AutoDeltaVector<Integer> attributes;
    private final AutoDeltaVector<Integer> maxAttributes;
    private final AutoDeltaVector<WearableEntry> wearableData;
    private final AutoDeltaString alternateAppearanceSharedObjectTemplateName;
    private final AutoDeltaBoolean coverVisibility;

    public CreatureObject() {
        unmodifiedMaxAttributes = new AutoDeltaVector<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0), authoritativeClientServerPackage);
        skills = new AutoDeltaSet<>(authoritativeClientServerPackage);

        //CreatureObjectMessage03
        posture = new AutoDeltaByte(0, sharedPackage);
        rank = new AutoDeltaByte(0, sharedPackage);
        masterId = new AutoDeltaLong(0L, sharedPackage);
        scaleFactor = new AutoDeltaFloat(1.0F, sharedPackage);
        shockWounds = new AutoDeltaInt(0, sharedPackage);
        states = new AutoDeltaLong(0L, sharedPackage);
        attributeWounds = new AutoDeltaVector<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0), sharedPackage);

        //CreatureObjectMessage04
        accelPercent = new AutoDeltaFloat(1.0F, authoritativeClientServerPackageNp);
        accelScale = new AutoDeltaFloat(1.0F, authoritativeClientServerPackageNp);
        attribBonus = new AutoDeltaVector<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0), authoritativeClientServerPackageNp);
        modMap = new AutoDeltaMap<>(authoritativeClientServerPackageNp);
        movementPercent = new AutoDeltaFloat(1.0F, authoritativeClientServerPackageNp);
        movementScale = new AutoDeltaFloat(1.0F, authoritativeClientServerPackageNp);
        performanceListenTarget = new AutoDeltaLong(0L, authoritativeClientServerPackageNp);
        runSpeed = new AutoDeltaFloat(5.0F, authoritativeClientServerPackageNp);
        slopeModAngle = new AutoDeltaFloat(1.0F, authoritativeClientServerPackageNp);
        slopeModPercent = new AutoDeltaFloat(1.0F, authoritativeClientServerPackageNp);
        turnScale = new AutoDeltaFloat(1.0F, authoritativeClientServerPackageNp);
        walkSpeed = new AutoDeltaFloat(1.5F, authoritativeClientServerPackageNp);
        waterModPercent = new AutoDeltaFloat(1.0F, authoritativeClientServerPackageNp);
        groupMissionCriticalObjectList = new AutoDeltaVector<>(authoritativeClientServerPackageNp);

        //CreatureObjectMessage06
        level = new AutoDeltaShort(0, sharedPackageNp);
        animatingSkillData = new AutoDeltaString("", sharedPackageNp);
        animationMood = new AutoDeltaString("", sharedPackageNp);
        currentWeapon = new AutoDeltaLong(0L, sharedPackageNp);
        group = new AutoDeltaLong(0L, sharedPackageNp);
        groupInviter = new AutoDeltaVariable<GroupInviter>(new GroupInviter(0, 0), sharedPackageNp);
        guildId = new AutoDeltaInt(0, sharedPackageNp);
        lookAtTarget = new AutoDeltaLong(0L, sharedPackageNp);
        moodId = new AutoDeltaByte(0, sharedPackageNp);
        performanceStartTime = new AutoDeltaInt(0, sharedPackageNp);
        performanceType = new AutoDeltaInt(0, sharedPackageNp);
        attributes = new AutoDeltaVector<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0), sharedPackageNp);
        maxAttributes = new AutoDeltaVector<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0), sharedPackageNp);
        wearableData = new AutoDeltaVector<>(sharedPackageNp);
        alternateAppearanceSharedObjectTemplateName = new AutoDeltaString("", sharedPackageNp);
        coverVisibility = new AutoDeltaBoolean(true, sharedPackageNp);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    public final void setPosture(byte posture) {

        if(this.posture.get() == posture) {
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

    public final void addSkill(final String skill) { skills.insert(skill); setDirty(true); }
    public final void removeSkill(final String skill) { skills.erase(skill); setDirty(true); }
    public final boolean hasSkill(final String skill) { return skills.contains(skill); }

    public final void setLookAtTarget(long lookAtTarget) {
        this.lookAtTarget.set(lookAtTarget);
        setDirty(true);
    }

    public final long getLookAtTarget() { return lookAtTarget.get(); }

    //hamBase
    public final int getHealthBase() { return unmodifiedMaxAttributes.get(CreatureAttribute.Health); }
    public final int getStrengthBase() { return unmodifiedMaxAttributes.get(CreatureAttribute.Strength); }
    public final int getConstitutionBase() { return unmodifiedMaxAttributes.get(CreatureAttribute.Constitution); }
    public final int getActionBase() { return unmodifiedMaxAttributes.get(CreatureAttribute.Action); }
    public final int getQuicknessBase() { return unmodifiedMaxAttributes.get(CreatureAttribute.Quickness); }
    public final int getStaminaBase() { return unmodifiedMaxAttributes.get(CreatureAttribute.Stamina); }
    public final int getMindBase() { return unmodifiedMaxAttributes.get(CreatureAttribute.Mind); }
    public final int getFocusBase() { return unmodifiedMaxAttributes.get(CreatureAttribute.Focus); }
    public final int getWillpowerBase() { return unmodifiedMaxAttributes.get(CreatureAttribute.Willpower); }

    public final void setUnmodifiedMaxAttributes(Collection<Integer> values) { unmodifiedMaxAttributes.set(values); setDirty(true); }
    public final void setHealthBase(int value) { unmodifiedMaxAttributes.set(CreatureAttribute.Health, value); setDirty(true); }
    public final void setStrengthBase(int value) { unmodifiedMaxAttributes.set(CreatureAttribute.Strength, value); setDirty(true); }
    public final void setConstitutionBase(int value) { unmodifiedMaxAttributes.set(CreatureAttribute.Constitution, value); setDirty(true); }
    public final void setActionBase(int value) { unmodifiedMaxAttributes.set(CreatureAttribute.Action, value); setDirty(true); }
    public final void setQuicknessBase(int value) { unmodifiedMaxAttributes.set(CreatureAttribute.Quickness, value); setDirty(true); }
    public final void setStaminaBase(int value) { unmodifiedMaxAttributes.set(CreatureAttribute.Stamina, value); setDirty(true); }
    public final void setMindBase(int value) { unmodifiedMaxAttributes.set(CreatureAttribute.Mind, value); setDirty(true); }
    public final void setFocusBase(int value) { unmodifiedMaxAttributes.set(CreatureAttribute.Focus, value); setDirty(true); }
    public final void setWillpowerBase(int value) { unmodifiedMaxAttributes.set(CreatureAttribute.Willpower, value); setDirty(true); }

    //Attribute wounds
    public final int getHealthWounds() { return attributeWounds.get(CreatureAttribute.Health); }
    public final int getStrengthWounds() { return attributeWounds.get(CreatureAttribute.Strength); }
    public final int getConstitutionWounds() { return attributeWounds.get(CreatureAttribute.Constitution); }
    public final int getActionWounds() { return attributeWounds.get(CreatureAttribute.Action); }
    public final int getQuicknessWounds() { return attributeWounds.get(CreatureAttribute.Quickness); }
    public final int getStaminaWounds() { return attributeWounds.get(CreatureAttribute.Stamina); }
    public final int getMindWounds() { return attributeWounds.get(CreatureAttribute.Mind); }
    public final int getFocusWounds() { return attributeWounds.get(CreatureAttribute.Focus); }
    public final int getWillpowerWounds() { return attributeWounds.get(CreatureAttribute.Willpower); }

    public final void setHealthWounds(int value) { attributeWounds.set(CreatureAttribute.Health, value); setDirty(true); }
    public final void setStrengthWounds(int value) { attributeWounds.set(CreatureAttribute.Strength, value); setDirty(true); }
    public final void setConstitutionWounds(int value) { attributeWounds.set(CreatureAttribute.Constitution, value); setDirty(true); }
    public final void setActionWounds(int value) { attributeWounds.set(CreatureAttribute.Action, value); setDirty(true); }
    public final void setQuicknessWounds(int value) { attributeWounds.set(CreatureAttribute.Quickness, value); setDirty(true); }
    public final void setStaminaWounds(int value) { attributeWounds.set(CreatureAttribute.Stamina, value); setDirty(true); }
    public final void setMindWounds(int value) { attributeWounds.set(CreatureAttribute.Mind, value); setDirty(true); }
    public final void setFocusWounds(int value) { attributeWounds.set(CreatureAttribute.Focus, value); setDirty(true); }
    public final void setWillpowerWounds(int value) { attributeWounds.set(CreatureAttribute.Willpower, value); setDirty(true); }

    //hamEncumbrance
    public final int getHealthEncumbrance() { return attribBonus.get(CreatureAttribute.Health); }
    public final int getStrengthEncumbrance() { return attribBonus.get(CreatureAttribute.Strength); }
    public final int getConstitutionEncumbrance() { return attribBonus.get(CreatureAttribute.Constitution); }
    public final int getActionEncumbrance() { return attribBonus.get(CreatureAttribute.Action); }
    public final int getQuicknessEncumbrance() { return attribBonus.get(CreatureAttribute.Quickness); }
    public final int getStaminaEncumbrance() { return attribBonus.get(CreatureAttribute.Stamina); }
    public final int getMindEncumbrance() { return attribBonus.get(CreatureAttribute.Mind); }
    public final int getFocusEncumbrance() { return attribBonus.get(CreatureAttribute.Focus); }
    public final int getWillpowerEncumbrance() { return attribBonus.get(CreatureAttribute.Willpower); }

    public final void setHealthEncumbrance(int value) { attribBonus.set(CreatureAttribute.Health, value); setDirty(true); }
    public final void setStrengthEncumbrance(int value) { attribBonus.set(CreatureAttribute.Strength, value); setDirty(true); }
    public final void setConstitutionEncumbrance(int value) { attribBonus.set(CreatureAttribute.Constitution, value); setDirty(true); }
    public final void setActionEncumbrance(int value) { attribBonus.set(CreatureAttribute.Action, value); setDirty(true); }
    public final void setQuicknessEncumbrance(int value) { attribBonus.set(CreatureAttribute.Quickness, value); setDirty(true); }
    public final void setStaminaEncumbrance(int value) { attribBonus.set(CreatureAttribute.Stamina, value); setDirty(true); }
    public final void setMindEncumbrance(int value) { attribBonus.set(CreatureAttribute.Mind, value); setDirty(true); }
    public final void setFocusEncumbrance(int value) { attribBonus.set(CreatureAttribute.Focus, value); setDirty(true); }
    public final void setWillpowerEncumbrance(int value) { attribBonus.set(CreatureAttribute.Willpower, value); setDirty(true); }

    //ham
    public final int getHealth() { return attributes.get(CreatureAttribute.Health); }
    public final int getStrength() { return attributes.get(CreatureAttribute.Strength); }
    public final int getConstitution() { return attributes.get(CreatureAttribute.Constitution); }
    public final int getAction() { return attributes.get(CreatureAttribute.Action); }
    public final int getQuickness() { return attributes.get(CreatureAttribute.Quickness); }
    public final int getStamina() { return attributes.get(CreatureAttribute.Stamina); }
    public final int getMind() { return attributes.get(CreatureAttribute.Mind); }
    public final int getFocus() { return attributes.get(CreatureAttribute.Focus); }
    public final int getWillpower() { return attributes.get(CreatureAttribute.Willpower); }

    public final void setAttributes(Collection<Integer> value) { attributes.set(value); setDirty(true); }
    public final void setHealth(int value) { attributes.set(CreatureAttribute.Health, value); setDirty(true); }
    public final void setStrength(int value) { attributes.set(CreatureAttribute.Strength, value); setDirty(true); }
    public final void setConstitution(int value) { attributes.set(CreatureAttribute.Constitution, value); setDirty(true); }
    public final void setAction(int value) { attributes.set(CreatureAttribute.Action, value); setDirty(true); }
    public final void setQuickness(int value) { attributes.set(CreatureAttribute.Quickness, value); setDirty(true); }
    public final void setStamina(int value) { attributes.set(CreatureAttribute.Stamina, value); setDirty(true); }
    public final void setMind(int value) { attributes.set(CreatureAttribute.Mind, value); setDirty(true); }
    public final void setFocus(int value) { attributes.set(CreatureAttribute.Focus, value); setDirty(true); }
    public final void setWillpower(int value) { attributes.set(CreatureAttribute.Willpower, value); setDirty(true); }

    //hamMax
    public final int getHealthMax() { return maxAttributes.get(CreatureAttribute.Health); }
    public final int getStrengthMax() { return maxAttributes.get(CreatureAttribute.Strength); }
    public final int getConstitutionMax() { return maxAttributes.get(CreatureAttribute.Constitution); }
    public final int getActionMax() { return maxAttributes.get(CreatureAttribute.Action); }
    public final int getQuicknessMax() { return maxAttributes.get(CreatureAttribute.Quickness); }
    public final int getStaminaMax() { return maxAttributes.get(CreatureAttribute.Stamina); }
    public final int getMindMax() { return maxAttributes.get(CreatureAttribute.Mind); }
    public final int getFocusMax() { return maxAttributes.get(CreatureAttribute.Focus); }
    public final int getWillpowerMax() { return maxAttributes.get(CreatureAttribute.Willpower); }

    public final void setMaxAttributes(Collection<Integer> value) { maxAttributes.set(value); setDirty(true); }
    public final void setHealthMax(int value) { maxAttributes.set(CreatureAttribute.Health, value); setDirty(true); }
    public final void setStrengthMax(int value) { maxAttributes.set(CreatureAttribute.Strength, value); setDirty(true); }
    public final void setConstitutionMax(int value) { maxAttributes.set(CreatureAttribute.Constitution, value); setDirty(true); }
    public final void setActionMax(int value) { maxAttributes.set(CreatureAttribute.Action, value); setDirty(true); }
    public final void setQuicknessMax(int value) { maxAttributes.set(CreatureAttribute.Quickness, value); setDirty(true); }
    public final void setStaminaMax(int value) { maxAttributes.set(CreatureAttribute.Stamina, value); setDirty(true); }
    public final void setMindMax(int value) { maxAttributes.set(CreatureAttribute.Mind, value); setDirty(true); }
    public final void setFocusMax(int value) { maxAttributes.set(CreatureAttribute.Focus, value); setDirty(true); }
    public final void setWillpowerMax(int value) { maxAttributes.set(CreatureAttribute.Willpower, value); setDirty(true); }

    public final float getRunSpeed() { return runSpeed.get(); }
    public final float setWalkSpeed() { return walkSpeed.get(); }
    public final float setSlopeModAngle() { return slopeModAngle.get(); }
    public final float setSlopeModPercent() { return slopeModPercent.get(); }
    public final float setWaterModPercent() { return waterModPercent.get(); }
    public final float getScaleFactor() { return scaleFactor.get(); }

    public final void setRunSpeed(float speed) { runSpeed.set(speed); setDirty(true); }
    public final void setWalkSpeed(float speed) { walkSpeed.set(speed); setDirty(true); }
    public final void setSlopeModAngle(float angle) { slopeModAngle.set(angle); setDirty(true); }
    public final void setSlopeModPercent(float percent) { slopeModPercent.set(percent); setDirty(true); }
    public final void setWaterModPercent(float percent) { waterModPercent.set(percent); setDirty(true); }
    public final void setScaleFactor(float value) { scaleFactor.set(value); setDirty(true); }
}
