package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ChatOnGetIgnoreList extends SwgMessage {
	public ChatOnGetIgnoreList(long creoid) {
		super(0x03, 0xF8C275B0);
		
		writeLong(creoid);
		
		//list of ChatAvatarId
		writeInt(0);
	}
}
