package com.ocdsoft.bacta.swg.precu.message.game.object;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class ObjectMenuSelectMessage extends GameNetworkMessage {
    private long playerId;
    private byte selectedItemId;

    public ObjectMenuSelectMessage(long playerId, byte selectedItemId) {
        super(0x03, 0x7CA18726);

        this.playerId = playerId;
        this.selectedItemId = selectedItemId;

        writeLong(playerId);
        writeByte(selectedItemId);
    }

    public ObjectMenuSelectMessage(SoeByteBuf buffer) {
        super(0x03, 0x7CA18726);

        playerId = buffer.readLong();
        selectedItemId = buffer.readByte();
    }
}
