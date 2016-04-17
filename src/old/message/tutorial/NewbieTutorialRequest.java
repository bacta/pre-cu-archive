package com.ocdsoft.bacta.swg.precu.message.tutorial;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

/**
 * Created by crush on 8/15/2014.
 */
public class NewbieTutorialRequest extends GameNetworkMessage {
    public NewbieTutorialRequest(final String request) {
        super(0x02, 0x90DD61AF);

        writeAscii(request);
    }
}
