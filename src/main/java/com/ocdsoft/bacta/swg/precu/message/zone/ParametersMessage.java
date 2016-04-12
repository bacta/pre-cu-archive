package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class ParametersMessage extends GameNetworkMessage {

	public ParametersMessage() {
		super(0x02, 0x487652DA);
		
		writeInt(0x00000384); //weatherUpdateInterval
	}
	
}
