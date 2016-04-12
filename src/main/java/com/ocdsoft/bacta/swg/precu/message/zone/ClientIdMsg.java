package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class ClientIdMsg extends GameNetworkMessage {
	
	private int gameBitsToClear;
	private int dataLen;
	private int sessionKey;
	private int accountId;
	
	public ClientIdMsg(byte[] token, String clientVersion) {
		super(0x03, 0xd5899226);
		
		writeInt(0);
		writeInt(token.length);
		writeBytes(token);
		writeAscii(clientVersion);
	}
}
