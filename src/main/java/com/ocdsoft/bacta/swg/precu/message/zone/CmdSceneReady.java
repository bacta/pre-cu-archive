package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class CmdSceneReady extends SwgMessage {

    public CmdSceneReady() {
        super(0x1, 0x43fd1c22);
        
    }
    /**
         01 00 22 1C FD 43 
     */
}
