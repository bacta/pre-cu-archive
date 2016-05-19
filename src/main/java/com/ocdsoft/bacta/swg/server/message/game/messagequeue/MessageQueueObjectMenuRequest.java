package com.ocdsoft.bacta.swg.server.message.game.messagequeue;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by crush on 9/5/2014.
 */
@AllArgsConstructor
public class MessageQueueObjectMenuRequest extends MessageQueueData {
    @Getter private Collection<ObjectMenuRequestData> data;
    @Getter private long requestorId;
    @Getter private long targetId;
    private byte sequence;

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
            ObjectMenuRequestData objectMenuRequestData = new ObjectMenuRequestData(buffer);
            data.add(objectMenuRequestData);
        }

        sequence = buffer.get();
    }

    @Getter
    @AllArgsConstructor
    public static final class ObjectMenuRequestData implements ByteBufferWritable {
        private final byte id;
        private final byte parent;
        private final byte menuItemType;
        private final byte flags;
        private final String label;

        public ObjectMenuRequestData(ByteBuffer buffer) {
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
