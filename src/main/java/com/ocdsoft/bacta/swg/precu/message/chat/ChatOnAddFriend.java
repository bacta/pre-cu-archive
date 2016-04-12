package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class ChatOnAddFriend extends GameNetworkMessage {
	public ChatOnAddFriend() {
		super(0x03, 0x2B2A0D94);
		
		writeLong(0); //unknown?
	}
}
