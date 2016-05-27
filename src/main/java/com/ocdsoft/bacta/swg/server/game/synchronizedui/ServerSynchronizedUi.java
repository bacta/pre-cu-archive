package com.ocdsoft.bacta.swg.server.game.synchronizedui;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaByteStream;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaVariableBase;
import com.ocdsoft.bacta.swg.server.game.message.scene.BaselinesMessage;
import com.ocdsoft.bacta.swg.server.game.message.scene.DeltasMessage;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by crush on 5/7/2016.
 */
public class ServerSynchronizedUi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSynchronizedUi.class);

    private final List<ServerObject> clientObjects;
    private final ServerObject owner;
    private final AutoDeltaByteStream uiPackage;

    public ServerSynchronizedUi(final ServerObject owner) {
        this.clientObjects = new ArrayList<>();
        this.owner = owner;

        this.uiPackage = new AutoDeltaByteStream();
    }

    public int getNumClients() {
        return clientObjects.size();
    }

    public List<ServerObject> getClients() {
        return Collections.unmodifiableList(clientObjects);
    }

    public void addClientObject(final ServerObject object) {
        final SoeUdpConnection client = object.getConnection();

        if (client == null) {
            LOGGER.warn("Trying to add a client object {} that has no connection.", object.getNetworkId());
            return;
        }

        if (clientObjects.contains(object)) {
            LOGGER.warn("Trying to add a client {} object that is already contained.", object.getNetworkId());
        } else {
            clientObjects.add(object);
        }

        //TODO: We can't do this because the connection object has no concept of sync ui.
        //client.addSynchronizedUi(this);
        sendBaselinesToClient(client);
    }

    public void removeClientObject(final ServerObject object) {
        if (!clientObjects.remove(object)) {
            LOGGER.warn("Removing a client that doesn't exist on this ui.");
            return;
        }

        final SoeUdpConnection client = object.getConnection();

        //TODO: We can't do this because the connection object has no concept of sync ui.
        //if (client != null)
        //    client.removeSynchronizedUi(this);
    }

    public void removeAllClientObjects() {
        clientObjects.clear(); //Shouldn't we clear the connection's reference to this ui?
    }

    public void applyBaselines(final ByteBuffer source) {
        uiPackage.unpack(source);
    }

    public void applyDeltas(final ByteBuffer source) {
        uiPackage.unpackDeltas(source);
    }

    public void sendDeltas() {
        if (uiPackage.getItemCount() > 0) {
            final DeltasMessage uiDeltas = new DeltasMessage(owner, uiPackage, DeltasMessage.DELTAS_UI);

            for (final ServerObject object : clientObjects) {
                final SoeUdpConnection connection = object.getConnection();

                if (connection != null)
                    connection.sendMessage(uiDeltas);
            }
        }
    }

    protected void addToUiPackage(final AutoDeltaVariableBase source) {
        uiPackage.addVariable(source);
    }

    private void sendBaselinesToClient(final SoeUdpConnection client) {
        final BaselinesMessage uiBaseline = new BaselinesMessage(owner, uiPackage, BaselinesMessage.BASELINES_UI);
        client.sendMessage(uiBaseline);
    }
}
