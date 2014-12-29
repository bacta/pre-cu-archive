package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

/**
 * A request for the server to send the client a list of all available chat channels.
 * @direction c->s
 */
public class ChatRequestRoomList extends SwgMessage {
	public ChatRequestRoomList() {
		super(0x01, 0x4C3D2CFA);  //ChatRequestRoomList
	}
}
