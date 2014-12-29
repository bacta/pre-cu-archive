package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class SelectCharacter extends SwgMessage {
	//priority 0x02
	//opcode 0xB5098D76
	
	public SelectCharacter(long id) {
		super(0x02, 0xB5098D76);
		
		writeLong(id);
	}
}
