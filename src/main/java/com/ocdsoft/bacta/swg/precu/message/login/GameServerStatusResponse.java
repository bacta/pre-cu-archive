package com.ocdsoft.bacta.swg.precu.message.login;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

public class GameServerStatusResponse extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(GameServerStatusResponse.class.getSimpleName());

    @Getter
    private int clusterId;

	public GameServerStatusResponse(final int clusterId) {
        super(priority, messageType);

        this.clusterId = clusterId;
	}

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        clusterId = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(clusterId);
    }
}
