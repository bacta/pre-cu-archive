package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/26/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class SetCombatSpamRangeFilter extends GameNetworkMessage {
    private final int combatSpamRangeFilter;

    public SetCombatSpamRangeFilter(final ByteBuffer buffer) {
        this.combatSpamRangeFilter = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putInt(this.combatSpamRangeFilter);
    }
}