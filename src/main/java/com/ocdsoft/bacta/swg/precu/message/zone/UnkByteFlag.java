package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class UnkByteFlag extends SwgMessage {

	public UnkByteFlag() {
		super(0x02, 0x7102B15F);
		
		writeByte(1);
	}
	
}
