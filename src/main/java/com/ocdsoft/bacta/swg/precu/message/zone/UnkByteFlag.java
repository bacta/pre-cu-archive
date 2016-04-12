package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class UnkByteFlag extends GameNetworkMessage {

	public UnkByteFlag() {
		super(0x02, 0x7102B15F);
		
		writeByte(1);
	}
	
}
