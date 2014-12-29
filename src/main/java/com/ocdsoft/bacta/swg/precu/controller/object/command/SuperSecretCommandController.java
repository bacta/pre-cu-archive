package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Far too much research has been mad to get a real name
 * all has failed
 */
@Command(id = 0x35c28e6f)
public class SuperSecretCommandController implements CommandController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {
        logger.warn("This command not implemented, and a complete mystery");
    }

}
