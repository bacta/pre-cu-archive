package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class GameServerLagResponse extends SwgMessage {

	public GameServerLagResponse() {
		super(0x01, 0x789A4E0A);

	}
}
