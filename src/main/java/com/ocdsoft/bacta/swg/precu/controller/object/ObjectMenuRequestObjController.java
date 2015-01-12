package com.ocdsoft.bacta.swg.precu.controller.object;

import com.ocdsoft.bacta.swg.annotations.ObjController;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.messagequeue.MessageQueueObjectMenuRequest;
import com.ocdsoft.bacta.swg.server.game.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ObjController(id = 0x146)
public class ObjectMenuRequestObjController implements ObjectController {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message, TangibleObject invoker) {
        MessageQueueObjectMenuRequest request = new MessageQueueObjectMenuRequest();
        request.unpack(message);

        //TODO: Get the ObjectMenuComponent from the template of the target object and apply it.

        ObjControllerMessage objc = new ObjControllerMessage(0x0B, 0x147, invoker.getNetworkId(), 0);
        request.pack(objc);

        client.sendMessage(objc);
    }
}
