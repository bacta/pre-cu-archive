package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@AllArgsConstructor
@Getter
public final class GameServerStatus extends GameNetworkMessage {

    static {
        priority = 0x4;
        messageType = SOECRC32.hashCode(GameServerStatus.class.getSimpleName());
    }

    private final ClusterEntry clusterEntry;

    public GameServerStatus(final ByteBuffer buffer) {
        this.clusterEntry = new ClusterEntry(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        clusterEntry.writeToBuffer(buffer);
    }

}
