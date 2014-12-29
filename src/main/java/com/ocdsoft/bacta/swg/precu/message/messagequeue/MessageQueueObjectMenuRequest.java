package com.ocdsoft.bacta.swg.precu.message.messagequeue;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by crush on 9/5/2014.
 */
public class MessageQueueObjectMenuRequest extends MessageQueueData {
    @Getter private Collection<ObjectMenuRequestData> data;
    @Getter private long requestorId;
    @Getter private long targetId;
    private byte sequence;

    public MessageQueueObjectMenuRequest() { }

    public MessageQueueObjectMenuRequest(long targetId, long requestorId, Collection<ObjectMenuRequestData> data, byte sequence) {
        this.targetId = targetId;
        this.requestorId = requestorId;
        this.data = data;
        this.sequence = sequence;
    }

    public void pack(SoeByteBuf buffer) {
        buffer.writeLong(targetId);
        buffer.writeLong(requestorId);

        buffer.writeInt(data.size());

        for (ObjectMenuRequestData item : data)
            item.writeToBuffer(buffer);

        buffer.writeByte(sequence);
    }

    public void unpack(SoeByteBuf buffer) {
        targetId = buffer.readLong();
        requestorId = buffer.readLong();

        int size = buffer.readInt();

        data = new ArrayList<>(size);

        for (int i = 0; i < size; i++)
            data.add(new ObjectMenuRequestData(buffer));

        sequence = buffer.readByte();
    }

    public static final class ObjectMenuRequestData implements SoeByteBufSerializable {
        @Getter private byte id;
        @Getter private byte parent;
        @Getter private byte menuItemType;
        @Getter private byte flags;
        @Getter private String label;

        public ObjectMenuRequestData(byte id, byte parent, byte menuItemType, byte flags, String label) {
            this.id = id;
            this.parent = parent;
            this.menuItemType = menuItemType;
            this.flags = flags;
            this.label = label;
        }

        public ObjectMenuRequestData(SoeByteBuf buffer) {
            id = buffer.readByte();
            parent = buffer.readByte();
            menuItemType = buffer.readByte();
            flags = buffer.readByte();
            label = buffer.readUnicode();
        }

        @Override
        public void writeToBuffer(SoeByteBuf buffer) {
            buffer.writeByte(id);
            buffer.writeByte(parent);
            buffer.writeByte(menuItemType);
            buffer.writeByte(flags);
            buffer.writeUnicode(label);
        }
    }
}
