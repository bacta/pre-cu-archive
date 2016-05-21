package com.ocdsoft.bacta.swg.server.message.login;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
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
