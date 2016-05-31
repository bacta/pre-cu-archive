package com.ocdsoft.bacta.swg.server.game.controller.object;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueData;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;

/**
 * Created by crush on 5/29/2016.
 */
public interface MessageQueueController<T extends MessageQueueData> {
    void handleIncoming(SoeUdpConnection connection, ServerObject actor, int flags, float value, T data);
}
