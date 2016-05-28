package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.server.game.commodities.ResourceTypeMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/28/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class CommoditiesResourceTypeListResponse extends GameNetworkMessage {
    private final String resourceTypeMapVersionNumber;
    private final ResourceTypeMap resourceTypeMap;

    public CommoditiesResourceTypeListResponse(final ByteBuffer buffer) {
        this.resourceTypeMapVersionNumber = BufferUtil.getAscii(buffer);
        this.resourceTypeMap = new ResourceTypeMap(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, resourceTypeMapVersionNumber);
        BufferUtil.put(buffer, resourceTypeMap);
    }
}