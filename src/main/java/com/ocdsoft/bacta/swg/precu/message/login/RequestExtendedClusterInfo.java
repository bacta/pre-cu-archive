package com.ocdsoft.bacta.swg.precu.message.login;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

@Priority(0x2)
public class RequestExtendedClusterInfo extends GameNetworkMessage {

    public RequestExtendedClusterInfo(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
    
    }
    /**
         02 00 05 ED 33 8E 00 00 00 00 

     */
}
