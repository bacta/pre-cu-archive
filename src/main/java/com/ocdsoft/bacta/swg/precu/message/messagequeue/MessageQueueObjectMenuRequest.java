package com.ocdsoft.bacta.swg.precu.message.messagequeue;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.Getter;

import java.nio.Buffer;
import java.nio.ByteBuffer;
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

    public void pack(ByteBuffer buffer) {
        buffer.putLong(targetId);
        buffer.putLong(requestorId);

        buffer.putInt(data.size());

        for (ObjectMenuRequestData item : data) {
            item.writeToBuffer(buffer);
        }

        buffer.put(sequence);
    }

    public void unpack(ByteBuffer buffer) {
        targetId = buffer.getLong();
        requestorId = buffer.getLong();

        int size = buffer.getInt();

        data = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            ObjectMenuRequestData objectMenuRequestData = new ObjectMenuRequestData();
            objectMenuRequestData.readFromBuffer(buffer);

            data.add(objectMenuRequestData);
        }

        sequence = buffer.get();
    }

    public static final class ObjectMenuRequestData implements ByteBufferSerializable {
        @Getter private byte id;
        @Getter private byte parent;
        @Getter private byte menuItemType;
        @Getter private byte flags;
        @Getter private String label;

        protected ObjectMenuRequestData() {}

        public ObjectMenuRequestData(byte id, byte parent, byte menuItemType, byte flags, String label) {
            this.id = id;
            this.parent = parent;
            this.menuItemType = menuItemType;
            this.flags = flags;
            this.label = label;
        }

        @Override
        public void readFromBuffer(ByteBuffer buffer) {
            id = buffer.get();
            parent = buffer.get();
            menuItemType = buffer.get();
            flags = buffer.get();
            label = BufferUtil.getUnicode(buffer);
        }

        @Override
        public void writeToBuffer(ByteBuffer buffer) {
            buffer.put(id);
            buffer.put(parent);
            buffer.put(menuItemType);
            buffer.put(flags);
            BufferUtil.putUnicode(buffer, label);
        }
    }
}
