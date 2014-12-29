package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class GuildRequestMessage extends SwgMessage {

    public GuildRequestMessage(long targetId) {
        super(0x2, 0x81eb4ef7);

        writeLong(targetId);
    }
}
