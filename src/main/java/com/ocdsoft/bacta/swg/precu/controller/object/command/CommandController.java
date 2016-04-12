package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.ocdsoft.bacta.engine.network.controller.Controller;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;

public interface CommandController extends Controller {
	public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params);
}
