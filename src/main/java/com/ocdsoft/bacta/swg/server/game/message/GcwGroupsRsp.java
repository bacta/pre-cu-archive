package com.ocdsoft.bacta.swg.server.game.message;

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
public final class GcwGroupsRsp extends GameNetworkMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcwGroupsRsp.class);
    //Map<gcwGroup, Map<categoryName, categoryScoreWieght>>

    public GcwGroupsRsp(final ByteBuffer buffer) {
        LOGGER.warn("Not implemented");
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        LOGGER.warn("Not implemented");
        buffer.putInt(0);
    }
}