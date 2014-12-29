package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.sui.SuiPageData;

public class SuiCreatePageMessage extends SwgMessage {
	public SuiCreatePageMessage(SuiPageData pageData) {
		super(0x02, 0xD44B7259);
		
		pageData.writeToBuffer(this);
	}
}
