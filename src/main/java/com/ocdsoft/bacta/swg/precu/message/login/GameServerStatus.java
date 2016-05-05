package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@AllArgsConstructor
@Getter
@Priority(0x4)
public final class GameServerStatus extends GameNetworkMessage {

    private final ClusterEntry clusterEntry;

    public GameServerStatus(final ByteBuffer buffer) {
        this.clusterEntry = new ClusterEntry(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        clusterEntry.writeToBuffer(buffer);
    }

}
