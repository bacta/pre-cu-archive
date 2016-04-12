package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class CmdSceneReady extends GameNetworkMessage {

    public CmdSceneReady() {
        super(0x1, 0x43fd1c22);
        
    }
    /**
         01 00 22 1C FD 43 
     */
}
