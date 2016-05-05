package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@AllArgsConstructor
@Getter
public final class GameServerStatusResponse extends GameNetworkMessage {

    static {
        priority = 0x2;
        messageType = SOECRC32.hashCode(GameServerStatusResponse.class.getSimpleName());
    }

    private int clusterId;

    public GameServerStatusResponse(ByteBuffer buffer) {
        clusterId = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(clusterId);
    }

}
