package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/27/2016.
 * <p>
 * Has no members.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class GcwRegionsReq extends GameNetworkMessage {
    public GcwRegionsReq(final ByteBuffer buffer) {
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    }
}