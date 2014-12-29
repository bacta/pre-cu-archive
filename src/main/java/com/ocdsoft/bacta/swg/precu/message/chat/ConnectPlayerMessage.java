package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ConnectPlayerMessage extends SwgMessage {
	public ConnectPlayerMessage() {
		super(0x02, 0x6137556F);

		writeInt(0);
	}
}
