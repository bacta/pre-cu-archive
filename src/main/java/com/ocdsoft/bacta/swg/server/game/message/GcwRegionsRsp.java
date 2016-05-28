package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/27/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class GcwRegionsRsp extends GameNetworkMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcwRegionsRsp.class);
    //Map<RegionPlanet, Map<GcwScoreCategory->CategoryName, Pair<Pair<RegionCenterX, RegionCenterZ>, RegionRadius>>>>

    public GcwRegionsRsp(final ByteBuffer buffer) {
        LOGGER.info("Deserializing GcwRegionsRsp");

        final int keyCount = buffer.getInt();

        for (int i = 0; i < keyCount; ++i) {
            final String regionPlanet = BufferUtil.getAscii(buffer);

            final int subKeyCount = buffer.getInt();

            for (int j = 0; j < subKeyCount; ++j) {
                final String categoryName = BufferUtil.getAscii(buffer);
                final float regionCenterX = buffer.getFloat();
                final float regionCenterZ = buffer.getFloat();
                final float regionRadius = buffer.getFloat();
            }
        }
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        LOGGER.warn("Not implemented");
        buffer.putInt(0);
    }
}