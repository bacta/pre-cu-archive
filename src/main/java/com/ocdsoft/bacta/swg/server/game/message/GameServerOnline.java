package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.message.Subscribable;
import com.ocdsoft.bacta.swg.server.login.object.ClusterServer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 5/21/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public class GameServerOnline extends GameNetworkMessage implements Subscribable {

    private final ClusterServer clusterServer;

    public GameServerOnline(final ByteBuffer buffer) {
        clusterServer = new ClusterServer(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        clusterServer.writeToBuffer(buffer);
    }
}