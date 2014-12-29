package com.ocdsoft.bacta.swg.precu.message.object;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import io.netty.buffer.ByteBuf;

public class ObjControllerMessage extends SwgMessage {
    //int flags
    //int message
    //networkid networkId
    //float value

	public ObjControllerMessage(int flags, int message, long networkId, float value) {
		super(0x05, 0x80CE5E46);
		
		writeInt(flags);
		writeInt(message);
		writeLong(networkId);
		writeFloat(value);
	}

    public ObjControllerMessage(ByteBuf copyBuf) {
        super(copyBuf);
    }


    public void setReceiver(long receiver) {
        setLong(14, receiver);
    }
}
