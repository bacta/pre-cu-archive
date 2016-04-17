package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class ConnectPlayerResponseMessage extends GameNetworkMessage {
	public ConnectPlayerResponseMessage() {
		super(0x03, 0x6137556F); //ConnectPlayerResponseMessage
		
		writeInt(0);
	}
}
