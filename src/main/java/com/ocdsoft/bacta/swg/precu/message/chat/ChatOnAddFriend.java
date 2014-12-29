package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ChatOnAddFriend extends SwgMessage {
	public ChatOnAddFriend() {
		super(0x03, 0x2B2A0D94);
		
		writeLong(0); //unknown?
	}
}
