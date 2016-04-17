package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class SuiUpdatePageMessage extends GameNetworkMessage {
	public SuiUpdatePageMessage() {
		super(0x09, 0x5F3342F6);
	}

	@Override
	public void readFromBuffer(ByteBuffer buffer) {

	}

	@Override
	public void writeToBuffer(ByteBuffer buffer) {
		writeInt(1);
		writeAscii("Script.inputBox");
		writeInt(1);

		writeByte(0x03);
		writeInt(1);
		writeUnicode("Test");
		writeInt(2);
		writeAscii("Prompt.lblPrompt");
		writeAscii("Text");

		writeLong(0);
		writeFloat(0);
		writeLong(0);
	}
}
