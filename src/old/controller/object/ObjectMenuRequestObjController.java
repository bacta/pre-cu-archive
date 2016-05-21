package com.ocdsoft.bacta.swg.precu.controller.game.object;

import com.ocdsoft.bacta.swg.annotations.ObjController;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.game.messagequeue.MessageQueueObjectMenuRequest;
import com.ocdsoft.bacta.swg.precu.message.game.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ObjController(id = 0x146)
public class ObjectMenuRequestObjController implements ObjectController {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message, TangibleObject invoker) {
        MessageQueueObjectMenuRequest request = new MessageQueueObjectMenuRequest();
        request.unpack(message);

        //TODO: Get the ObjectMenuComponent from the template of the target object and apply it.

        ObjControllerMessage objc = new ObjControllerMessage(0x0B, 0x147, invoker.getNetworkId(), 0);
        request.pack(objc);

        client.sendMessage(objc);
    }
}
