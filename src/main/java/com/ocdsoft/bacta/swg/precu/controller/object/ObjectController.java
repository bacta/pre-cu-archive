package com.ocdsoft.bacta.swg.precu.controller.object;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.network.controller.Controller;

public interface ObjectController extends Controller {
    public void handleIncoming(final GameClient client, final SoeByteBuf message, final TangibleObject invoker);
}
