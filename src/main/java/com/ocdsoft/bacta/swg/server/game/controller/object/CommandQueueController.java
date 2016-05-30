package com.ocdsoft.bacta.swg.server.game.controller.object;

import com.ocdsoft.bacta.engine.network.controller.Controller;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;

public interface CommandQueueController extends Controller {
    void handleCommand(SoeUdpConnection connection, ServerObject actor, ServerObject target, String params);
}
