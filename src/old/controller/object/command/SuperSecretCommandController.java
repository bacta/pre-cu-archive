package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
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
    public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {
        logger.warn("This command not implemented, and a complete mystery");
    }

}
