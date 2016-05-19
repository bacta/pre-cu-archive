package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.SuiCreatePageMessage;
import com.ocdsoft.bacta.swg.precu.message.game.outofband.OutOfBandPackager;
import com.ocdsoft.bacta.swg.precu.message.game.outofband.ProsePackage;
import com.ocdsoft.bacta.swg.precu.message.game.outofband.ProsePackageParticipant;
import com.ocdsoft.bacta.swg.precu.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.precu.object.sui.SuiPageData;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id=0xfc3d1cb2)
public class BurstrunCommandController implements CommandController {
    @Inject
    private ContainerService containerService;

	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {
		
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
