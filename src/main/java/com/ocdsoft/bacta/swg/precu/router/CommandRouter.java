package com.ocdsoft.bacta.swg.precu.router;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.network.router.Router;

@Singleton
public interface CommandRouter extends Router {
    void routeCommand(int opcode, GameClient client, SoeByteBuf message, TangibleObject invoker);
}
