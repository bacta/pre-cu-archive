package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.SuiCreatePageMessage;
import com.ocdsoft.bacta.swg.server.game.message.outofband.OutOfBandPackager;
import com.ocdsoft.bacta.swg.server.game.message.outofband.ProsePackage;
import com.ocdsoft.bacta.swg.server.game.message.outofband.ProsePackageParticipant;
import com.ocdsoft.bacta.swg.server.game.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.server.game.object.sui.SuiPageData;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.service.container.ContainerService;
import com.ocdsoft.bacta.swg.shared.annotations.Command;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id=0xfc3d1cb2)
public class BurstrunCommandController implements CommandController {
    @Inject
    private ContainerService containerService;

	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {
		
		logger.warn("This command not implemented properly!");

        ProsePackage prosePackage = new ProsePackage(new StringId("guild", "sponsor_verify_prompt"));
        prosePackage.setActor(new ProsePackageParticipant(invoker.getNetworkId()));
        prosePackage.setTarget(new ProsePackageParticipant("cRush's Crushers"));
        String oob = OutOfBandPackager.pack(prosePackage, -1);

        StringBuilder value = new StringBuilder();
        value.append(""); //normalText - You can send a custom message here along with the OOB packages.
        value.append((char)0);
        value.append(oob);

        SuiPageData sui = new SuiPageData("Script.messageBox", invoker.getNetworkId(), 1000.f);
        sui.setProperty("Prompt.lblPrompt", "Text", value.toString());

        SuiCreatePageMessage msg = new SuiCreatePageMessage(sui);
        //client.sendMessage(msg);


        PlayerObject playerObject = containerService.getSlottedObject(invoker, "ghost");

        if (playerObject != null) {
            logger.debug("Setting link dead");
            playerObject.setLinkDead();
        }
	}
	
}
