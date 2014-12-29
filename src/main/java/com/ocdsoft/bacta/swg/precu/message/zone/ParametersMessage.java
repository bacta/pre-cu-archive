package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ParametersMessage extends SwgMessage {

	public ParametersMessage() {
		super(0x02, 0x487652DA);
		
		writeInt(0x00000384); //weatherUpdateInterval
	}
	
}
