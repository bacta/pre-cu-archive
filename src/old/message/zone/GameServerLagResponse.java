package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class GameServerLagResponse extends GameNetworkMessage {

	public GameServerLagResponse() {
		super(0x01, 0x789A4E0A);

	}
}
