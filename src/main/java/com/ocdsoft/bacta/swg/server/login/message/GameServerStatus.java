package com.ocdsoft.bacta.swg.server.login.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.message.Subscribable;
import com.ocdsoft.bacta.swg.server.login.object.ClusterServer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@AllArgsConstructor
@Getter
@Priority(0x4)
public final class GameServerStatus extends GameNetworkMessage implements Subscribable {

    private final ClusterServer clusterServer;

    public GameServerStatus(final ByteBuffer buffer) {
        this.clusterServer = new ClusterServer(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        clusterServer.writeToBuffer(buffer);
    }

}
