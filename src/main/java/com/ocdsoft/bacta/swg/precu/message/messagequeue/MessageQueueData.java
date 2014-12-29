package com.ocdsoft.bacta.swg.precu.message.messagequeue;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;

/**
 * Created by crush on 9/5/2014.
 */
public abstract class MessageQueueData {
    public abstract void pack(SoeByteBuf buffer);
    public abstract void unpack(SoeByteBuf buffer);
}
