package com.ocdsoft.bacta.swg.precu.object.tangible;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.ocdsoft.bacta.swg.server.game.message.scene.UpdateTransformMessage;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import com.ocdsoft.bacta.swg.server.game.object.archive.delta.*;
import com.ocdsoft.bacta.swg.server.game.zone.Zone;
import lombok.Getter;
import lombok.Setter;
import org.magnos.steer.SteerSubject;
import org.magnos.steer.vec.Vec3;

import java.util.Set;

public class TangibleObject extends SceneObject implements SteerSubject<Vec3> {
    public static final class Conditions {
        public final static int onOff = 0x1;
        public final static int vendor = 0x2;
        public final static int insured = 0x4;
        public final static int conversable = 0x8;
        public final static int hibernating = 0x10;
        public final static int magicItem = 0x20;
        public final static int aggressive = 0x40;
        public final static int wantSawAttackTrigger = 0x80;
        public final static int invulnerable = 0x100;
        public final static int disabled = 0x200;
        public final static int uninsurable = 0x400;
        public final static int interesting = 0x800;
        public final static int mount = 0x1000;
        public final static int crafted = 0x2000;
        public final static int wingsOpened = 0x4000;
        public final static int spaceInteresting = 0x8000;
        public final static int docking = 0x10000;
        public final static int destroying = 0x20000;
        public final static int commable = 0x40000;
        public final static int dockable = 0x80000;
        public final static int eject = 0x100000;
        public final static int inspectable = 0x200000;
        public final static int transferable = 0x400000;
        public final static int inflightTutorial = 0x800000;
        public final static int spaceCombatMusic = 0x1000000;
        public final static int encounterLocked = 0x2000000;
        public final static int spawnedCreature = 0x4000000;
        public final static int holidayInteresting = 0x8000000;
        public final static int locked = 0x10000000;
    }

    @Override
    public int getOpcode() {
        return 0x54414E4F;
    } //'TANO'

    public static transient ImmutableSet<TangibleObject> NO_NEAR_OBJECTS = ImmutableSet.copyOf(new TangibleObject[0]);

    private transient ImmutableSet<TangibleObject> nearObjects = NO_NEAR_OBJECTS;
    @Setter @Getter protected transient Zone zone = null;
    @Getter protected String zoneName = "";
    @Getter @Setter private transient int movementCounter = 0;
    @Getter @Setter private transient boolean inert = true;

    //TangibleObjectMessage03
    private final AutoDeltaString appearanceData;
    private final AutoDeltaSet<Integer> components;
    private final AutoDeltaInt condition;
    private final AutoDeltaInt count;
    private final AutoDeltaInt damageTaken;
    private final AutoDeltaInt maxHitPoints;
    private final AutoDeltaBoolean visible;

    //TangibleObjectMessage06
    private final AutoDeltaVector<Long> defenderList; //accessList?

    //TangibleObjectMessage07
    private final AutoDeltaLong craftingSessionManufacturingSchematic;
    private final AutoDeltaLong craftingSessionPrototype;

    protected TangibleObject() {
        appearanceData = new AutoDeltaString("", sharedPackage);
        components = new AutoDeltaSet<>(sharedPackage);
        condition = new AutoDeltaInt(0, sharedPackage);
        count = new AutoDeltaInt(0, sharedPackage);
        damageTaken = new AutoDeltaInt(0, sharedPackage);
        maxHitPoints = new AutoDeltaInt(0, sharedPackage);
        visible = new AutoDeltaBoolean(false, sharedPackage);

        defenderList = new AutoDeltaVector<Long>(sharedPackageNp);

        craftingSessionManufacturingSchematic = new AutoDeltaLong(0L, uiPackage);
        craftingSessionPrototype = new AutoDeltaLong(0L, uiPackage);

        position = new Vec3( 0, 0, 0 );
    }



    public TangibleObject[] getNearObjects() {
        return nearObjects.toArray(new TangibleObject[nearObjects.size()]);
    }

    @Override
    public long getSpatialGroups() {
        return 1;
    }

    @Override
    public long getSpatialCollisionGroups() {
        return 1;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public void attach(Object attachment) {

    }

    @Override
    public <T> T attachment() {
        return null;
    }

    @Override
    public <T> T attachment(Class<T> type) {
        return null;
    }

    @Override
    public Vec3 getTarget(SteerSubject subject) {
        return null;
    }

    @Override
    public Vec3 getDirection() {
        return Vec3.UP;
    }

    @Override
    public Vec3 getVelocity() {
        return Vec3.UP;
    }

    @Override
    public float getVelocityMax() {
        return 10;
    }

    @Override
    public Vec3 getAcceleration() {
        return Vec3.UP;
    }

    @Override
    public float getAccelerationMax() {
        return 10;
    }

    @Override
    public float getDistanceAndNormal(Vec3 origin, Vec3 lookahead, Vec3 outNormal) {
        return 0;
    }

    @Override
    public Vec3 getPosition(Vec3 out) {
        return null;
    }

    @Override
    public float getRadius() {
        return 0;
    }

    @Override
    public final void setPosition(float x, float z, float y) {
        setPosition(x, z, y, true);
    }

    public String getAppearanceData() { return appearanceData.get(); }
    public void setAppearanceData(final String appearanceData) { this.appearanceData.set(appearanceData); setDirty(true); }

    @Override
    public final void setPosition(float x, float z, float y, boolean updateZone) {
        super.setPosition(x, z, y);

        if (updateZone) {
            updateZone();
        }

        broadcastMessage(new UpdateTransformMessage(this), false);
    }

    public void updateZone() {
        ImmutableSet<TangibleObject> newNearObjects = getUpdatedNearObjects();

        Set<TangibleObject> newObjects = Sets.difference(newNearObjects, nearObjects);
        Set<TangibleObject> expiredObjects = Sets.difference(nearObjects, newNearObjects);

        nearObjects = newNearObjects;

        // Notify Appear
        for (TangibleObject tano : newObjects) {
            addInRangeObject(tano);
        }

        // Notify Disappear
        for (TangibleObject tano : expiredObjects) {
            removeInRangeObject(tano);
        }
    }

    public void clearZone() {
        zone = null;
        nearObjects = NO_NEAR_OBJECTS;
    }

    private ImmutableSet<TangibleObject> getUpdatedNearObjects() {
        if(zone == null) {
            return NO_NEAR_OBJECTS;
        }

        UpdateTransformCallback updateTransformCallback = new UpdateTransformCallback(this);
        zone.contains(position, 160.f, Integer.MAX_VALUE, 1, updateTransformCallback);

        return updateTransformCallback.getNearObjects();
    }

    public void updateNearObjects() {
        nearObjects = getUpdatedNearObjects();
    }

    public void addInRangeObject(TangibleObject tano) {

        if(tano.getClient() != null && listeners.add(tano.getClient())) {

            if (client != null) {
                tano.sendTo(client);
            }
            tano.addInRangeObject(this);
        }

    }

    public void removeInRangeObject(TangibleObject tano) {

        if(tano.getClient() != null && listeners.remove(tano.getClient())) {
            if (client != null) {
                tano.sendDestroyTo(client);
            }
            tano.removeInRangeObject(this);
        }

    }

    public final void setCondition(int condition) {
        int currentCondition = this.condition.get();
        this.condition.set(currentCondition | condition);
    }
}
