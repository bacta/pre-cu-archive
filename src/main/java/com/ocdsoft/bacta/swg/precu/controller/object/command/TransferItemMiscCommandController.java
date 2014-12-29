package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.service.container.ContainerService;
import com.ocdsoft.bacta.swg.shared.annotations.Command;
import com.ocdsoft.network.service.object.ObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;

@Command(id=0x82f75977)
public class TransferItemMiscCommandController implements CommandController {
	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final ContainerService containerService;
    private final ObjectService<SceneObject> objectService;

    @Inject
    public TransferItemMiscCommandController(ContainerService containerService,
                                             ObjectService<SceneObject> objectService) {
        this.containerService = containerService;
        this.objectService = objectService;
    }

	@Override
	public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {
        try {
            StringTokenizer tokenizer = new StringTokenizer(params);

            long containerId = Long.parseLong(tokenizer.nextToken());
            int arrangementIndex = Integer.parseInt(tokenizer.nextToken());
            float x = Float.parseFloat(tokenizer.nextToken());
            float y = Float.parseFloat(tokenizer.nextToken());
            float z = Float.parseFloat(tokenizer.nextToken());

            SceneObject container = objectService.get(containerId);

            containerService.transferItemToContainer(container, target);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
}
