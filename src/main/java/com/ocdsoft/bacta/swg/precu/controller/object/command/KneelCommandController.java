package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreaturePosture;
import com.ocdsoft.bacta.swg.shared.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id = 0x1b48b26)
public class KneelCommandController implements CommandController {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public KneelCommandController() {

    }

    @Override
    public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {

        CreatureObject creature = client.getCharacter();
        creature.setPosture(CreaturePosture.CROUCHED);

    }

}
