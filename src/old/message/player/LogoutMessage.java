package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

/**
 * Created by Kyle on 3/23/14.
 */
public class LogoutMessage extends GameNetworkMessage {

    public LogoutMessage() {
        super(0x1, 0x42FD19DD);
    }
}
