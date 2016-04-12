package com.ocdsoft.bacta.swg.precu.message.tutorial;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

/**
 * Created by crush on 8/15/2014.
 */
public class NewbieTutorialResponse extends GameNetworkMessage {
    public NewbieTutorialResponse(final String response) {
        super(0x2, 0xCA88FBAD);

        writeAscii(response);
    }
}
