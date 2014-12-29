package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.network.controller.Controller;

public interface CommandController extends Controller {
	public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params);
}
