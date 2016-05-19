package com.ocdsoft.bacta.swg.server.message.game.messagequeue;


import java.nio.ByteBuffer;

/**
 * Created by crush on 9/5/2014.
 */
public abstract class MessageQueueData {
    public abstract void pack(ByteBuffer buffer);
    public abstract void unpack(ByteBuffer buffer);
}
