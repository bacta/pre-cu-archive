package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.server.game.commodities.ItemTypeMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/28/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class CommoditiesItemTypeListResponse extends GameNetworkMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommoditiesItemTypeListResponse.class);

    private final String itemTypeMapVersionNumber;
    private final ItemTypeMap itemTypeMap;

    public CommoditiesItemTypeListResponse(final ByteBuffer buffer) {
        this.itemTypeMapVersionNumber = BufferUtil.getAscii(buffer);
        this.itemTypeMap = new ItemTypeMap(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, itemTypeMapVersionNumber);
        BufferUtil.put(buffer, itemTypeMap);
    }
}