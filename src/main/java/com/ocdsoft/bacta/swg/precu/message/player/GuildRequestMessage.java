package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class GuildRequestMessage extends GameNetworkMessage {

    public GuildRequestMessage(long targetId) {
        super(0x2, 0x81eb4ef7);

        writeLong(targetId);
    }
}
