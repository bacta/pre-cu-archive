package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class SelectCharacter extends GameNetworkMessage {
	//priority 0x02
	//opcode 0xB5098D76
	
	public SelectCharacter(long id) {
		super(0x02, 0xB5098D76);
		
		writeLong(id);
	}
}
