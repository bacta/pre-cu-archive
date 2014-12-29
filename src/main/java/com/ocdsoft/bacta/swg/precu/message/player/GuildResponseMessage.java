package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

/**
 * Created by crush on 8/15/2014.
 */
public class GuildResponseMessage extends SwgMessage {
    public GuildResponseMessage(final long targetId, final String guildName, final String memberTitle) {
        super(0x04, 0x32263F20);

        writeLong(targetId);
        writeAscii(guildName);
        writeAscii(memberTitle);
    }

    public GuildResponseMessage(final long targetId) {
        super(0x04, 0x32263F20);

        writeLong(targetId);
        writeShort(0);
        writeShort(0);
    }
}
