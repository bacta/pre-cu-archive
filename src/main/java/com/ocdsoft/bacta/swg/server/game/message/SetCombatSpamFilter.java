package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.shared.combat.CombatSpamFilterType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/26/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class SetCombatSpamFilter extends GameNetworkMessage {
    private final CombatSpamFilterType combatSpamFilterType;

    public SetCombatSpamFilter(final ByteBuffer buffer) {
        combatSpamFilterType = CombatSpamFilterType.from(buffer.getInt());
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putInt(combatSpamFilterType.value);
    }
}