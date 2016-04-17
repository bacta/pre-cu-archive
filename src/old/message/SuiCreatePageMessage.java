package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.sui.SuiPageData;

import java.nio.ByteBuffer;

public class SuiCreatePageMessage extends GameNetworkMessage {
	public SuiCreatePageMessage(SuiPageData pageData) {
		super(0x02, 0xD44B7259);
		
		pageData.writeToBuffer(this);
	}

	@Override
	public void readFromBuffer(ByteBuffer buffer) {

	}

	@Override
	public void writeToBuffer(ByteBuffer buffer) {

	}
}
