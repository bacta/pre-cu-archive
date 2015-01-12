package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.annotations.Command;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.service.container.ContainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id=0xfbe911e4)
public class SetBiographyCommandController implements CommandController {
    private final ContainerService containerService;
	private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public SetBiographyCommandController(ContainerService containerService) {
        this.containerService = containerService;
    }

	@Override
	public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {
        PlayerObject ghost = containerService.getSlottedObject(target, "ghost");

        if (ghost != null)
            ghost.setBiography(params);
	}
	
}
