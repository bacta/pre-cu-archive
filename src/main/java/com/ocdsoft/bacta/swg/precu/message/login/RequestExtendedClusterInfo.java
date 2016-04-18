package com.ocdsoft.bacta.swg.precu.message.login;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

public class RequestExtendedClusterInfo extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(RequestExtendedClusterInfo.class.getSimpleName());


    public RequestExtendedClusterInfo() {
        super(priority, messageType);
        
    }

    public RequestExtendedClusterInfo(ByteBuffer buffer) {
        super(priority, messageType);
    
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
    
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }
    /**
         02 00 05 ED 33 8E 00 00 00 00 

     */
}
