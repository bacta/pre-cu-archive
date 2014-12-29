package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class HeartbeatMessage extends SwgMessage {

	public HeartbeatMessage() {
		super(0x1, 0xA16CF9AF);
		
	}
}
