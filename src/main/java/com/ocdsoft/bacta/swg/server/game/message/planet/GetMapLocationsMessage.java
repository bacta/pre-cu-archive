package com.ocdsoft.bacta.swg.server.game.message.planet;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@Priority(0x5)
public final class GetMapLocationsMessage extends GameNetworkMessage {
    private final String planetName;
    private final int cacheVersionStatic;
    private final int cacheVersionDynamic;
    private final int cacheVersionPersist;

    public GetMapLocationsMessage(final ByteBuffer buffer) {
        planetName = BufferUtil.getAscii(buffer);
        cacheVersionStatic = buffer.getInt();
        cacheVersionDynamic = buffer.getInt();
        cacheVersionPersist = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, planetName);
        BufferUtil.put(buffer, cacheVersionStatic);
        BufferUtil.put(buffer, cacheVersionDynamic);
        BufferUtil.put(buffer, cacheVersionPersist);
    }
}
