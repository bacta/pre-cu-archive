package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@AllArgsConstructor
@Getter
@Priority(0x4)
public final class GameServerStatusResponse extends GameNetworkMessage {

    private int clusterId;

    public GameServerStatusResponse(ByteBuffer buffer) {
        clusterId = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(clusterId);
    }

}
