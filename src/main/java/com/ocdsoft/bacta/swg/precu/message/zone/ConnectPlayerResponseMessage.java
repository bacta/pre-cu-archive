package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ConnectPlayerResponseMessage extends SwgMessage {
	public ConnectPlayerResponseMessage() {
		super(0x03, 0x6137556F); //ConnectPlayerResponseMessage
		
		writeInt(0);
	}
}
