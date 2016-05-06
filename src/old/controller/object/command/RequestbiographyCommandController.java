package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.game.object.BiographyRetrieved;
import com.ocdsoft.bacta.swg.precu.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
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
	public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {

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
