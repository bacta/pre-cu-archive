package com.ocdsoft.bacta.swg.precu.message.object;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.soe.controller.ObjController;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class ObjControllerMessage extends GameNetworkMessage {
    private int flag;
    private int messageType;
    private long receiver;
    private int tickCount;

    private ByteBuffer buffer;

    public ObjControllerMessage() {
        super(0, 0);
    }

	public ObjControllerMessage(final int flag,
                                final int messageType,
                                final long receiver,
                                final int tickCount) {

		super(0x05, 0x80CE5E46);

		this.flag = flag;
        this.messageType = messageType;
        this.receiver = receiver;
        this.tickCount = tickCount;
	}

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

	@Override
	public void readFromBuffer(ByteBuffer buffer) {
        buffer.putInt(flag);
        buffer.putInt(messageType);
        buffer.putLong(receiver);
        buffer.putInt(tickCount);

        this.buffer = buffer;
	}

	@Override
	public void writeToBuffer(ByteBuffer buffer) {
        this.flag = buffer.getInt();
        this.messageType = buffer.getInt();
        this.receiver = buffer.getLong();
        this.tickCount = buffer.getInt();
	}
}
