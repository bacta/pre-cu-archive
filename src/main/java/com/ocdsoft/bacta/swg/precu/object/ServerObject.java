package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.engine.lang.ObservableEventRegistry;
import com.ocdsoft.bacta.engine.lang.Observer;
import com.ocdsoft.bacta.engine.lang.Subject;
import com.ocdsoft.bacta.engine.lang.UnicodeString;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.localization.StringId;
import com.ocdsoft.bacta.swg.precu.event.ObservableGameEvent;
import com.ocdsoft.bacta.swg.precu.message.game.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.message.game.scene.*;
import com.ocdsoft.bacta.swg.precu.object.archive.OnDirtyCallbackBase;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaByteStream;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaFloat;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaVariable;
import com.ocdsoft.bacta.swg.precu.object.cell.CellObject;
import com.ocdsoft.bacta.swg.precu.object.template.shared.SharedObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.ContainedByProperty;
import com.ocdsoft.bacta.swg.shared.container.Container;
import com.ocdsoft.bacta.swg.shared.foundation.ConstCharCrcLowerString;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public abstract class ServerObject extends GameObject implements Subject<ObservableGameEvent> {

    private static final transient Logger LOGGER = LoggerFactory.getLogger(ServerObject.class);

    private static final ConstCharCrcLowerString TEMPLATE_NAME = new ConstCharCrcLowerString("object/object/base/shared_object_default.iff");

    /**
     * Template to use if no shared template is given.
     */
    private static SharedObjectTemplate defaultSharedTemplate;

    @Getter
    private transient boolean initialized = false;

    @Getter
    @Setter
    private long containedBy = 0;

    @Getter
    protected Transform transform = new Transform();

    protected transient Container container;

    @Getter
    @Setter
    protected int currentArrangement = -1;

    @Getter
    @Setter
    protected transient SoeUdpConnection connection;

    protected transient final Set<SoeUdpConnection> listeners;

    private transient int localFlags;

    @Getter
    protected transient final AutoDeltaByteStream authoritativeClientServerPackage = new AutoDeltaByteStream(this); //1
    @Getter
    protected transient final AutoDeltaByteStream authoritativeClientServerPackageNp = new AutoDeltaByteStream(this); //4
    @Getter
    protected transient final AutoDeltaByteStream firstParentAuthClientServerPackage = new AutoDeltaByteStream(this); //8
    @Getter
    protected transient final AutoDeltaByteStream firstParentAuthClientServerPackageNp = new AutoDeltaByteStream(this); //9
    @Getter
    protected transient final AutoDeltaByteStream sharedPackage = new AutoDeltaByteStream(this); //3
    @Getter
    protected transient final AutoDeltaByteStream sharedPackageNp = new AutoDeltaByteStream(this); //6
    @Getter
    protected transient final AutoDeltaByteStream uiPackage = new AutoDeltaByteStream(this); //7

    /**
     * Volume this object takes up in its container.
     */
    private final AutoDeltaInt volume;
    /**
     * Difficulty crafting or manufacturing this object.
     */
    private final AutoDeltaFloat complexity;
    /**
     * Name for this object. For players, this is their character name. Overrides the nameStringId.
     */
    private final AutoDeltaVariable<UnicodeString> objectName;
    /**
     * Name for this object. It is overridden by the existence of {@link ServerObject#objectName}.
     */
    private final AutoDeltaVariable<StringId> nameStringId;
    /**
     * Description for this object.
     */
    //private final AutoDeltaVariable<StringId> descriptionStringId;
    /**
     * How much money this object has in its bank account.
     */
    private final AutoDeltaInt balanceBank;
    /**
     * How much money this object has in cash.
     */
    private final AutoDeltaInt balanceCash;
    /**
     * The process id on the auth server.
     */
    private final AutoDeltaInt authServerProcessId;

    private int gameObjectType;

    /**
     * Template shared between client and server.
     */
    private SharedObjectTemplate sharedTemplate;

    protected ServerObject() {
        listeners = Collections.synchronizedSet(new HashSet<>());
        connection = null;

        balanceBank = new AutoDeltaInt(0, authoritativeClientServerPackage);
        balanceCash = new AutoDeltaInt(0, authoritativeClientServerPackage);

        complexity = new AutoDeltaFloat(1.0f, sharedPackage);
        nameStringId = new AutoDeltaVariable<>(StringId.INVALID, sharedPackage);
        objectName = new AutoDeltaVariable<>(UnicodeString.EMPTY, sharedPackage);
        volume = new AutoDeltaInt(0, sharedPackage);

        authServerProcessId = new AutoDeltaInt(0, sharedPackageNp);

        eventRegistry = new ObservableEventRegistry();
    }

    public final boolean getLocalFlag(final int flag) {
        return (localFlags & (1 << flag)) != 0;
    }

    public final void setLocalFlag(final int flag, final boolean enabled) {
        if (enabled)
            localFlags |= (1 << flag);
        else
            localFlags &= ~(1 << flag);
    }

    public final int getTotalMoney() {
        return getBalanceCash() + getBalanceBank();
    }

    public final SharedObjectTemplate getSharedTemplate() {
        return sharedTemplate != null ? sharedTemplate : getDefaultSharedTemplate();
    }

    public final void setSharedTemplate(final SharedObjectTemplate template) {
        this.sharedTemplate = template;
    }

    public final int getBalanceBank() {
        return balanceBank.get();
    }

    public final void setBalanceBank(int value) {
        balanceBank.set(value);
        setDirty(true);
    }

    public final int getBalanceCash() {
        return balanceCash.get();
    }

    public final void setBalanceCash(int value) {
        balanceCash.set(value);
        setDirty(true);
    }

    public final float getComplexity() {
        return complexity.get();
    }

    public final void setComplexity(float value) {
        complexity.set(value);
        setDirty(true);
    }

    public final StringId getNameStringId() {
        return nameStringId.get();
    }

    public final void setNameStringId(StringId value) {
        nameStringId.set(value);
        setDirty(true);
    }

    public final UnicodeString getAssignedObjectName() {
        return objectName.get();
    }

    public final void setAssignedObjectName(final UnicodeString value) {
        objectName.set(value);
        setDirty(true);
    }

    public final int getVolume() {
        return volume.get();
    }

    public final void setVolume(int value) {
        volume.set(value);
        setDirty(true);
    }

    public final int getAuthServerProcessId() {
        return authServerProcessId.get();
    }

    public final void setAuthServerProcessId(int value) {
        authServerProcessId.set(value);
        setDirty(true);
    }

    protected final transient ObservableEventRegistry<ObservableGameEvent> eventRegistry;

    protected final void sendTo(SoeUdpConnection theirConnection) {
        LOGGER.trace("Sending baselines to {}.", getNetworkId());

        if (theirConnection == null) return;

        SceneCreateObjectByCrc msg = new SceneCreateObjectByCrc(this);
        theirConnection.sendMessage(msg);

        UpdateContainmentMessage link = new UpdateContainmentMessage(this);
        theirConnection.sendMessage(link);

        sendBaselinesTo(theirConnection);
//
//        if (container != null) {
//            final Iterator<GameObject> containerIterator = container.iterator();
//
//            while (containerIterator.hasNext()) {
//                //TODO: Remove this if block when refactored to have contents list on Container.
//                //At that point, there should never be a null object in the list...
//
//                //TODO: we aren't sending contained objects now...
//                //if (containedObject != null)
//                //containedObject.sendTo(theirConnection);
//            }
//        }

        SceneEndBaselines close = new SceneEndBaselines(this.getNetworkId());
        theirConnection.sendMessage(close);
    }

    public final void sendDestroyTo(SoeUdpConnection theirConnection) {
        if (theirConnection != null) {
            // TODO: hyperspace boolean
            SceneDestroyObject msg = new SceneDestroyObject(this.getNetworkId(), false);
            theirConnection.sendMessage(msg);
        }
    }

    public final void setInitialized() {
        clearDeltas(); //Clear any deltas that might've been set before initialization.

        initialized = true;
    }

    private final void sendBaselinesTo(SoeUdpConnection theirConnection) {

        if (getConnection() == null || theirConnection == null) return;

        if (getConnection().equals(theirConnection)) {
            theirConnection.sendMessage(new BaselinesMessage(this, authoritativeClientServerPackage, 1)); //BaselineTypes.ClientServer)); //1
        }

        theirConnection.sendMessage(new BaselinesMessage(this, sharedPackage, 3)); //BaselineTypes.Shared)); //3

        if (getConnection().equals(theirConnection)) {
            theirConnection.sendMessage(new BaselinesMessage(this, authoritativeClientServerPackageNp, 4)); //BaselineTypes.ClientServerNp)); //4
        }

        theirConnection.sendMessage(new BaselinesMessage(this, sharedPackageNp, 6)); //BaselineTypes.SharedNp)); //6

        if (getConnection().equals(theirConnection)) {
            theirConnection.sendMessage(new BaselinesMessage(this, uiPackage, 7)); //BaselineTypes.Ui)); //7
            theirConnection.sendMessage(new BaselinesMessage(this, firstParentAuthClientServerPackage, 8)); //BaselineTypes.FirstParentClientServer)); //8
            theirConnection.sendMessage(new BaselinesMessage(this, firstParentAuthClientServerPackageNp, 9)); //BaselineTypes.FirstParentClientServerNp)); //9
        }
    }

    public final void sendDeltas() {
        if (authoritativeClientServerPackage.isDirty())
            broadcastMessage(new DeltasMessage(this, authoritativeClientServerPackage, 1));

        if (sharedPackage.isDirty())
            getConnection().sendMessage(new DeltasMessage(this, sharedPackage, 3));

        if (authoritativeClientServerPackageNp.isDirty())
            getConnection().sendMessage(new DeltasMessage(this, authoritativeClientServerPackageNp, 4));

        if (sharedPackageNp.isDirty())
            broadcastMessage(new DeltasMessage(this, sharedPackageNp, 6));

        if (uiPackage.isDirty())
            getConnection().sendMessage(new DeltasMessage(this, uiPackage, 7));

        if (firstParentAuthClientServerPackage.isDirty())
            getConnection().sendMessage(new DeltasMessage(this, firstParentAuthClientServerPackage, 8));

        if (firstParentAuthClientServerPackageNp.isDirty())
            getConnection().sendMessage(new DeltasMessage(this, firstParentAuthClientServerPackageNp, 9));

        clearDeltas();
    }

    public final void clearDeltas() {
        authoritativeClientServerPackage.clearDeltas();
        authoritativeClientServerPackageNp.clearDeltas();
        sharedPackage.clearDeltas();
        sharedPackageNp.clearDeltas();
        uiPackage.clearDeltas();
        firstParentAuthClientServerPackage.clearDeltas();
        firstParentAuthClientServerPackageNp.clearDeltas();
    }

    public void setTransform(final Transform transform) {
        this.transform = transform;
        dirty = true;
    }

    public void setOnDirtyCallback(OnDirtyCallbackBase onDirtyCallback) {
        sharedPackage.addOnDirtyCallback(onDirtyCallback);
        sharedPackageNp.addOnDirtyCallback(onDirtyCallback);
        authoritativeClientServerPackage.addOnDirtyCallback(onDirtyCallback);
        authoritativeClientServerPackageNp.addOnDirtyCallback(onDirtyCallback);
        firstParentAuthClientServerPackage.addOnDirtyCallback(onDirtyCallback);
        firstParentAuthClientServerPackageNp.addOnDirtyCallback(onDirtyCallback);
        uiPackage.addOnDirtyCallback(onDirtyCallback);
    }

    public final void broadcastMessage(GameNetworkMessage message) {
        broadcastMessage(message, true);
    }

    public final void broadcastMessage(GameNetworkMessage message, boolean sendSelf) {

        for (SoeUdpConnection theirConnection : listeners) {
            if (!sendSelf && theirConnection == getConnection()) {
                continue;
            }
            theirConnection.sendMessage(message);
            LOGGER.debug("Broadcasting message {} to {}", message.getClass().getSimpleName(), theirConnection.getCurrentCharName());
            System.out.println(SoeMessageUtil.bytesToHex(message));
        }
    }

    public final void broadcastMessage(ObjControllerMessage message) {
        broadcastMessage(message, false);
    }

    public final void broadcastMessage(ObjControllerMessage message, boolean changeReceiver) {
        for (SoeUdpConnection theirConnection : listeners) {

            if(changeReceiver) {
                message.setReceiver(theirConnection.getCurrentNetworkId());
            }
            theirConnection.sendMessage(message);

            LOGGER.debug("Broadcasting obj controller to {}", theirConnection.getCurrentCharName());
            System.out.println(SoeMessageUtil.bytesToHex(message));
        }
    }

    @Override
    public final void register(Observer obj, ObservableGameEvent event) {
        eventRegistry.register(obj, event);
    }

    @Override
    public final void unregister(Observer obj, ObservableGameEvent event) {
        eventRegistry.unregister(obj, event);
    }

    @Override
    public void notifyObservers(ObservableGameEvent event) {
        eventRegistry.notifyObservers(event);
    }

    @Override
    public Object getUpdate(Observer obj) {
        return null;
    }

    //TODO: Might need a safer way of doing this.
    public static void setDefaultSharedTemplate(final SharedObjectTemplate template) {
        defaultSharedTemplate = template;
    }

    /**
     * Returns a shared template if none was given for this object.
     *
     * @return The shared template
     */
    protected static final SharedObjectTemplate getDefaultSharedTemplate() {
        if (defaultSharedTemplate != null)
            return defaultSharedTemplate;

        throw new IllegalStateException("The default SharedObjectTemplate for ServerObject was not set. See ServerObject.setDefaultSharedTemplate.");
    }


    protected final void setInitialized(final boolean initialized) {
        setLocalFlag(LocalObjectFlags.INITIALIZED, initialized);
    }

    protected final void setBeingDestroyed(final boolean beingDestroyed) {
        setLocalFlag(LocalObjectFlags.BEING_DESTROYED, beingDestroyed);

        //if (beingDestroyed && isAuthoritative() && getScriptObject())
        //    getScriptObject().setOwnerDestroyed();

        //Stop listening to broadcast messages.
        //if (beingDestroyed && isAuthoritative())
        //    stopListeningToAllBroadcastMessages();
    }

    protected final void setPlacing(final boolean placing) {
        setLocalFlag(LocalObjectFlags.PLACING, placing);
    }

    protected final void setUnloading(final boolean unloading) {
        setLocalFlag(LocalObjectFlags.UNLOADING, unloading);
    }

    protected final void setGoingToConclude(final boolean goingToConclude) {
        setLocalFlag(LocalObjectFlags.GOING_TO_CONCLUDE, goingToConclude);
    }

    protected final void setInEndBaselines(final boolean inEndBaselines) {
        setLocalFlag(LocalObjectFlags.IN_END_BASELINES, inEndBaselines);
    }

    protected final void setNeedsPobFixup(final boolean needsPobFixup) {
        setLocalFlag(LocalObjectFlags.NEEDS_POB_FIX, needsPobFixup);
    }

    public final boolean isInitialized() {
        return getLocalFlag(LocalObjectFlags.INITIALIZED);
    }

    public final boolean isPlacing() {
        return getLocalFlag(LocalObjectFlags.PLACING);
    }

    public final boolean isUnloading() {
        return getLocalFlag(LocalObjectFlags.UNLOADING);
    }

    public final boolean isBeingDestroyed() {
        return getLocalFlag(LocalObjectFlags.BEING_DESTROYED);
    }

    public final boolean isGoingToConclude() {
        return getLocalFlag(LocalObjectFlags.GOING_TO_CONCLUDE);
    }

    public final boolean isInEndBaselines() {
        return getLocalFlag(LocalObjectFlags.IN_END_BASELINES);
    }

    public final boolean isNeedingPobFixup() {
        return getLocalFlag(LocalObjectFlags.NEEDS_POB_FIX);
    }

    public float getLocationReservationRadius() {
        return getSharedTemplate() != null ? getSharedTemplate().getLocationReservationRadius() : 0.f;
    }

    public void setTransformChanged(final boolean changed) {
        setLocalFlag(LocalObjectFlags.TRANSFORM_CHANGED, changed);

        if (changed) {
            //addObjectToConcludeList();

            //if (isAuthoritative() && isPersisted())
            //PositionUpdateTracker.positionChanged(this);
        }
    }

    public boolean getTransformChanged() {
        return getLocalFlag(LocalObjectFlags.TRANSFORM_CHANGED);
    }


    /**
     * Determines if this object is contained by another object.
     *
     * @param container       The object that might contain this one.
     * @param includeContents If true, return true if one of the container's contents contains us.
     * @return True if we are contained by the container (or its contents).
     */
    public boolean isContainedBy(final ServerObject container, final boolean includeContents) {
        final ContainedByProperty containedByProperty = getContainedByProperty();
        final GameObject test = containedByProperty != null ? containedByProperty.getContainedBy() : null;

        if (test == container)
            return true;

        if (test != null && test != this && includeContents)
            ((ServerObject) test).isContainedBy(container, true);

        return false;
    }

    public boolean canDropInWorld() {
        return false;
    }

    public void onContainerTransferComplete(final ServerObject oldContainer, final ServerObject newContainer) {
        setTransformChanged(true);

        if ((oldContainer != null && oldContainer instanceof CellObject)
                || (newContainer != null && newContainer instanceof CellObject)) {
            //positionUpdateTracker::flushPositionUpdate(this);
        }

        //TODO: Finish this logic?
    }


    public static class LocalObjectFlags {
        public static final int INITIALIZED = 0;
        public static final int BEING_DESTROYED = 1;
        public static final int PLACING = 2;
        public static final int TRANSFORM_CHANGED = 3;
        public static final int UNLOADING = 4;
        public static final int GOING_TO_CONCLUDE = 5;
        public static final int IN_END_BASELINES = 6;
        public static final int AUTO_DELTA_CHANGED = 7;
        public static final int SEND_TO_CLIENT = 8;
        public static final int HYPERSPACE_ON_CREATE = 9;
        public static final int HYPERSPACE_ON_DESTRUCT = 10;
        public static final int DIRTY_OBJECT_MENU_SENT = 11;
        public static final int DIRTY_ATTRIBUTES_SENT = 12;
        public static final int NEEDS_POB_FIX = 13;
    }
}
