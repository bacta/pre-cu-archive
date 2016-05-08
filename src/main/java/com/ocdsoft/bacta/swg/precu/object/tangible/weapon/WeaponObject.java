package com.ocdsoft.bacta.swg.precu.object.tangible.weapon;

import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaFloat;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerObjectTemplate.DamageType;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerWeaponObjectTemplate;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerWeaponObjectTemplate.AttackType;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerWeaponObjectTemplate.WeaponType;

/**
 * Created by crush on 9/4/2014.
 */
public class WeaponObject extends TangibleObject {
    private int minDamage;
    private int maxDamage;
    private final AutoDeltaFloat attackSpeed;//shared
    private float woundChance;
    private int attackCost;
    private float damageRadius;
    private boolean isDefaultWeapon;
    private final AutoDeltaInt accuracy;//shared
    private final AutoDeltaFloat minRange;//shared
    private final AutoDeltaFloat maxRange;//shared
    private final AutoDeltaInt damageType;//shared
    private final AutoDeltaInt elementalType;//shared
    private final AutoDeltaInt elementalValue; //shared
    private final AutoDeltaInt weaponType; //shared_np

    public WeaponObject(final ServerWeaponObjectTemplate template) {
        super(template);

        attackSpeed = new AutoDeltaFloat();
        accuracy = new AutoDeltaInt();
        minRange = new AutoDeltaFloat();
        maxRange = new AutoDeltaFloat();
        damageType = new AutoDeltaInt((int) template.getDamageType().value);
        elementalType = new AutoDeltaInt();
        elementalValue = new AutoDeltaInt();
        weaponType = new AutoDeltaInt((int) template.getWeaponType().value);

        sharedPackage.addVariable(attackSpeed);
        sharedPackage.addVariable(accuracy);
        sharedPackage.addVariable(minRange);
        sharedPackage.addVariable(maxRange);
        sharedPackage.addVariable(damageType);
        sharedPackage.addVariable(elementalType);
        sharedPackage.addVariable(elementalValue);
        sharedPackageNp.addVariable(weaponType);
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public float getAttackSpeed() {
        return this.attackSpeed.get();
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed.set(attackSpeed);
    }

    public float getWoundChance() {
        return this.woundChance;
    }

    public void setWoundChance(float woundChance) {
        this.woundChance = woundChance;
    }

    public int getAttackCost() {
        return this.attackCost;
    }

    public void setAttackCost(int attackCost) {
        this.attackCost = attackCost;
    }

    public float getDamageRadius() {
        return this.damageRadius;
    }

    public void setDamageRadius(float damageRadius) {
        this.damageRadius = damageRadius;
    }

    public void setAsDefaultWeapon(boolean isDefault) {
        this.isDefaultWeapon = isDefault;
    }

    public int getAccuracy() {
        return this.accuracy.get();
    }

    public void setAccuracy(int accuracy) {
        this.accuracy.set(accuracy);
    }

    public float getMinRange() {
        return this.minRange.get();
    }

    public void setMinRange(float minRange) {
        this.minRange.set(minRange);
    }

    public float getMaxRange() {
        return this.maxRange.get();
    }

    public void setMaxRange(float maxRange) {
        this.maxRange.set(maxRange);
    }

    public DamageType getDamageType() {
        return DamageType.from(this.damageType.get());
    }

    public void setDamageType(int damageType) {
        this.damageType.set(damageType);
    }

    public void setDamageType(DamageType damageType) {
        this.damageType.set((int) damageType.value);
    }

    public DamageType getElementalType() {
        return DamageType.from(this.elementalType.get());
    }

    public void setElementalType(int elementalType) {
        this.elementalType.set(elementalType);
    }

    public void setElementalType(DamageType elementalType) {
        this.elementalType.set((int) elementalType.value);
    }

    public int getElementalValue() {
        return this.elementalValue.get();
    }

    public void setElementalValue(int value) {
        this.elementalValue.set(value);
    }

    public WeaponType getWeaponType() {
        return WeaponType.from(this.weaponType.get());
    }

    public void setWeaponType(int weaponType) {
        this.weaponType.set(weaponType);
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType.set((int) weaponType.value);
    }

    public AttackType getAttackType() {
        final ServerWeaponObjectTemplate template = (ServerWeaponObjectTemplate) getObjectTemplate();
        return template.getAttackType();
    }

    public float getAudibleRange() {
        final ServerWeaponObjectTemplate template = (ServerWeaponObjectTemplate) getObjectTemplate();
        return template.getAudibleRange();
    }

    public int getMinDamage() {
        throw new UnsupportedOperationException("Implement this method on WeaponService because it requires services.");
    }

    public int getMaxDamage() {
        throw new UnsupportedOperationException("Implement this method on WeaponService because it requires services.");
    }
//
//    public boolean canDestroy() {
//        return isDefaultWeapon ? false : super.canDestroy();
//    }
//
//    public boolean onContainerAboutToTransfer(final ServerObject destination, final ServerObject transferer) {
//        return isDefaultWeapon ? false : super.onContainerAboutToTransfer(destination, transferer);
//    }
}