package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreaturePosture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id = 0xa8a25c79)
public class StandCommandController implements CommandController {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {
        CreatureObject creature = (CreatureObject) invoker;
        creature.setPosture(CreaturePosture.UPRIGHT);

        creature.addSkill("combat_marksman_novice");
    }

}
