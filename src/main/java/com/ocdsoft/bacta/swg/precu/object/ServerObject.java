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
import com.ocdsoft.bacta.swg.shared.container.Container;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.utility.Transform;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public abstract class ServerObject extends GameObject implements Subject<ObservableGameEvent> {

    private static final transient Logger LOGGER = LoggerFactory.getLogger(ServerObject.class);

    public int getOpcode() {
        return 0x53434E4F;
    } //'SCNO'

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

    @Getter
    @Setter //TODO: Remove setter access. Require reflection to gain access.
    private transient ObjectTemplate objectTemplate;

    @Getter protected transient final AutoDeltaByteStream authoritativeClientServerPackage = new AutoDeltaByteStream(this); //1
    @Getter protected transient final AutoDeltaByteStream authoritativeClientServerPackageNp = new AutoDeltaByteStream(this); //4
    @Getter protected transient final AutoDeltaByteStream firstParentAuthClientServerPackage = new AutoDeltaByteStream(this); //8
    @Getter protected transient final AutoDeltaByteStream firstParentAuthClientServerPackageNp = new AutoDeltaByteStream(this); //9
    @Getter protected transient final AutoDeltaByteStream sharedPackage = new AutoDeltaByteStream(this); //3
    @Getter protected transient final AutoDeltaByteStream sharedPackageNp = new AutoDeltaByteStream(this); //6
    @Getter protected transient final AutoDeltaByteStream uiPackage = new AutoDeltaByteStream(this); //7

    private final AutoDeltaInt balanceBank;
    private final AutoDeltaInt balanceCash;

    private final AutoDeltaFloat complexity;
    private final AutoDeltaVariable<StringId> nameStringId;
    private final AutoDeltaVariable<UnicodeString> objectName;
    private final AutoDeltaInt volume;

    private final AutoDeltaInt authServerProcessId;

    public final int getBalanceBank() { return balanceBank.get(); }
    public final void setBalanceBank(int value) { balanceBank.set(value); setDirty(true); }

    public final int getBalanceCash() { return balanceCash.get(); }
    public final void setBalanceCash(int value) { balanceCash.set(value); setDirty(true); }

    public final float getComplexity() { return complexity.get(); }
    public final void setComplexity(float value) { complexity.set(value); setDirty(true); }

    public final StringId getNameStringId() { return nameStringId.get(); }
    public final void setNameStringId(StringId value) { nameStringId.set(value); setDirty(true); }

    public final UnicodeString getObjectName() { return objectName.get(); }
    public final void setObjectName(UnicodeString value) { objectName.set(value); setDirty(true); }

    public final int getVolume() { return volume.get(); }
    public final void setVolume(int value) { volume.set(value); setDirty(true); }

    public final int getAuthServerProcessId() { return authServerProcessId.get(); }
    public final void setAuthServerProcessId(int value) { authServerProcessId.set(value); setDirty(true); }

    protected final transient ObservableEventRegistry<ObservableGameEvent> eventRegistry;

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

    protected final void sendTo(SoeUdpConnection theirConnection) {
        LOGGER.trace("Sending baselines to {}.", getNetworkId());

        if (theirConnection == null) return;

        SceneCreateObjectByCrc msg = new SceneCreateObjectByCrc(this);
        theirConnection.sendMessage(msg);

        UpdateContainmentMessage link = new UpdateContainmentMessage(this);
        theirConnection.sendMessage(link);

        sendBaselinesTo(theirConnection);

        if (container != null) {
            final Iterator<GameObject> containerIterator = container.iterator();

            while (containerIterator.hasNext()) {
                //TODO: Remove this if block when refactored to have contents list on Container.
                //At that point, there should never be a null object in the list...

                //TODO: we aren't sending contained objects now...
                //if (containedObject != null)
                //containedObject.sendTo(theirConnection);
            }
        }

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

        if(getConnection() == null || theirConnection == null) return;

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
            if(!sendSelf && theirConnection == getConnection()) {
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
}
