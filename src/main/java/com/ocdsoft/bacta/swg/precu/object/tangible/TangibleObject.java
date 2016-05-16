package com.ocdsoft.bacta.swg.precu.object.tangible;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaBoolean;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaString;
import com.ocdsoft.bacta.swg.archive.delta.set.AutoDeltaIntSet;
import com.ocdsoft.bacta.swg.archive.delta.set.AutoDeltaLongSet;
import com.ocdsoft.bacta.swg.precu.message.game.scene.UpdateTransformMessage;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.UpdateTransformCallback;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerTangibleObjectTemplate;
import com.ocdsoft.bacta.swg.precu.zone.Zone;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;
import lombok.Getter;
import lombok.Setter;
import org.magnos.steer.SteerSubject;
import org.magnos.steer.vec.Vec3;

import java.util.Set;

public class TangibleObject extends ServerObject implements SteerSubject<Vec3> {
    @Override
    public int getObjectType() {
        return 0x54414E4F;
    } //'TANO'

    public static transient ImmutableSet<TangibleObject> NO_NEAR_OBJECTS = ImmutableSet.copyOf(new TangibleObject[0]);

    private transient ImmutableSet<TangibleObject> nearObjects = NO_NEAR_OBJECTS;
    @Setter
    @Getter
    protected transient Zone zone = null;
    @Getter
    protected String zoneName = "";
    @Getter
    @Setter
    private transient int movementCounter = 0;
    @Getter
    @Setter
    private transient boolean inert = true;

    private String customAppearnce;
    //private LocationData locationTargets
    //private long ownerId;
    //private List<PvpEnemy> pvpEnemies;

    private final AutoDeltaInt pvpFaction;
    private final AutoDeltaInt pvpType;
    private final AutoDeltaString appearanceData;
    private final AutoDeltaIntSet components;
    private final AutoDeltaInt condition;
    private final AutoDeltaInt count;
    private final AutoDeltaInt damageTaken;
    private final AutoDeltaInt maxHitPoints;
    private final AutoDeltaBoolean visible;
    private final AutoDeltaBoolean inCombat;
    private final AutoDeltaLongSet passiveRevealPlayerCharacter;
    private final AutoDeltaInt mapColorOverride;
    private final AutoDeltaLongSet accessList;
    private final AutoDeltaIntSet guildAccessList;
    //private final AutoDeltaMap effectsMap;

    @Inject
    public TangibleObject(final ObjectTemplateList objectTemplateList,
                          final SlotIdManager slotIdManager,
                          final ServerTangibleObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template, false);

        pvpFaction = new AutoDeltaInt();
        pvpType = new AutoDeltaInt();
        appearanceData = new AutoDeltaString("");
        components = new AutoDeltaIntSet();
        condition = new AutoDeltaInt(template.getCondition());
        count = new AutoDeltaInt(template.getCount());
        damageTaken = new AutoDeltaInt();
        maxHitPoints = new AutoDeltaInt(template.getMaxHitPoints());
        visible = new AutoDeltaBoolean(true);
        inCombat = new AutoDeltaBoolean();
        passiveRevealPlayerCharacter = new AutoDeltaLongSet();
        mapColorOverride = new AutoDeltaInt();
        accessList = new AutoDeltaLongSet();
        guildAccessList = new AutoDeltaIntSet();
        //effectsMap = new AutoDeltaMap<>(sharedPackageNp); //How the F do we do this!?

        addMembersToPackages();
    }

    private void addMembersToPackages() {
        sharedPackage.addVariable(pvpFaction);
        sharedPackage.addVariable(pvpType);
        sharedPackage.addVariable(appearanceData);
        sharedPackage.addVariable(components);
        sharedPackage.addVariable(condition);
        sharedPackage.addVariable(count);
        sharedPackage.addVariable(damageTaken);
        sharedPackage.addVariable(maxHitPoints);
        sharedPackage.addVariable(visible);
        sharedPackage.addVariable(inCombat);

        sharedPackageNp.addVariable(passiveRevealPlayerCharacter);
        sharedPackageNp.addVariable(mapColorOverride);
        sharedPackageNp.addVariable(accessList);
        sharedPackageNp.addVariable(guildAccessList);
        //sharedPackageNp.addVariable(effectsMap);
    }


    public TangibleObject[] getNearObjects() {
        return nearObjects.toArray(new TangibleObject[nearObjects.size()]);
    }

    public String getAppearanceData() {
        return appearanceData.get();
    }

    public void setAppearanceData(final String appearanceData) {
        this.appearanceData.set(appearanceData);
        setDirty(true);
    }

    public final void setPosition(final Transform transform, boolean updateZone) {
        super.setTransform(transform);

        if (updateZone) {
            updateZone();
        }

        final int sequenceNumber = 0; //This value comes from the MessageQueueDataTransform packet.
        final byte speed = 2; //This value comes from the MessageQueueDataTransform packet.
        final byte lookAtYaw = 1; //This value comes from the MessageQueueDataTransform packet.
        final boolean useLookAtYaw = true; //This value comes from the MessageQueueDataTransform packet.

        //TODO: We should move this to the handler for the MessageQueueDataTransform packet...
        broadcastMessage(new UpdateTransformMessage(this, sequenceNumber, transform, speed, lookAtYaw, useLookAtYaw), false);
    }

    public void updateZone() {
        final ImmutableSet<TangibleObject> newNearObjects = getUpdatedNearObjects();

        final Set<TangibleObject> newObjects = Sets.difference(newNearObjects, nearObjects);
        final Set<TangibleObject> expiredObjects = Sets.difference(nearObjects, newNearObjects);

        nearObjects = newNearObjects;

        // Notify Appear
        for (final TangibleObject tano : newObjects) {
            addInRangeObject(tano);
        }

        // Notify Disappear
        for (final TangibleObject tano : expiredObjects) {
            removeInRangeObject(tano);
        }
    }

    public void clearZone() {
        zone = null;
        nearObjects = NO_NEAR_OBJECTS;
    }

    private ImmutableSet<TangibleObject> getUpdatedNearObjects() {
        if (zone == null) {
            return NO_NEAR_OBJECTS;
        }

        UpdateTransformCallback updateTransformCallback = new UpdateTransformCallback(this);
        zone.contains(transform.getPositionInParent(), 160.f, Integer.MAX_VALUE, 1, updateTransformCallback);

        return updateTransformCallback.getNearObjects();
    }

    public void updateNearObjects() {
        nearObjects = getUpdatedNearObjects();
    }

    public void addInRangeObject(TangibleObject tano) {

        if (tano.getConnection() != null && listeners.add(tano.getConnection())) {

            if (getConnection() != null) {
                tano.sendTo(getConnection());
            }
            tano.addInRangeObject(this);
        }

    }

    public void removeInRangeObject(TangibleObject tano) {

        if (tano.getConnection() != null && listeners.remove(tano.getConnection())) {
            if (getConnection() != null) {
                tano.sendDestroyTo(getConnection());
            }
            tano.removeInRangeObject(this);
        }

    }

    public final void setCondition(int condition) {
        int currentCondition = this.condition.get();
        this.condition.set(currentCondition | condition);
    }

    @Override
    public Vec3 getPosition() {
        return null;
    }

    @Override
    public Vec3 getPosition(Vec3 vec3) {
        return null;
    }

    @Override
    public float getRadius() {
        return 0;
    }

    @Override
    public float getDistanceAndNormal(Vec3 vec3, Vec3 v1, Vec3 v2) {
        return 0;
    }

    @Override
    public long getSpatialGroups() {
        return 0;
    }

    @Override
    public long getSpatialCollisionGroups() {
        return 0;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public void attach(Object o) {

    }

    @Override
    public <T> T attachment() {
        return null;
    }

    @Override
    public <T> T attachment(Class<T> aClass) {
        return null;
    }

    @Override
    public Vec3 getDirection() {
        return null;
    }

    @Override
    public Vec3 getVelocity() {
        return null;
    }

    @Override
    public float getMaximumVelocity() {
        return 0;
    }

    @Override
    public Vec3 getAcceleration() {
        return null;
    }

    @Override
    public float getMaximumAcceleration() {
        return 0;
    }

    @Override
    public Vec3 getTarget(SteerSubject<Vec3> steerSubject) {
        return null;
    }


    public static TangibleObject asTangibleObject(final GameObject object) {
        if (object instanceof TangibleObject)
            return (TangibleObject) object;

        return null;
    }
}
