package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.object.BiographyRetrieved;
import com.ocdsoft.bacta.swg.server.game.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.service.container.ContainerService;
import com.ocdsoft.bacta.swg.shared.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id=0x1bad8ffc)
public class RequestbiographyCommandController implements CommandController {
    private final ContainerService containerService;
	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public RequestbiographyCommandController(ContainerService containerService) {
        this.containerService = containerService;
    }

	@Override
	public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {

        PlayerObject playerObject = containerService.getSlottedObject(target, "ghost");

        if (playerObject != null) {
            BiographyRetrieved biographyRetrieved = new BiographyRetrieved(
                    invoker.getNetworkId(),
                    target.getNetworkId(),
                    playerObject.getBiography());

            client.sendMessage(biographyRetrieved);
        }
	}
	
}
