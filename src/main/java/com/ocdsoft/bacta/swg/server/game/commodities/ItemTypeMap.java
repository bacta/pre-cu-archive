package com.ocdsoft.bacta.swg.server.game.commodities;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/28/2016.
 */
@Getter
@Singleton
public final class ItemTypeMap implements ByteBufferWritable {
    private final TIntObjectMap<TIntObjectMap<ObjectTypeNameEntry>> internalMap;

    public ItemTypeMap() {
        this.internalMap = new TIntObjectHashMap<>();
    }

    public ItemTypeMap(final ByteBuffer buffer) {
        this.internalMap = BufferUtil.getTIntObjectHashMap(buffer,
                buff1 -> BufferUtil.getTIntObjectHashMap(buff1, ObjectTypeNameEntry::new));
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, internalMap, BufferUtil::put);
    }

    @Getter
    @AllArgsConstructor
    public static final class ObjectTypeNameEntry implements ByteBufferWritable {
        private final int gameObjectType;
        private final StringId objectName;

        public ObjectTypeNameEntry(final ByteBuffer buffer) {
            this.gameObjectType = buffer.getInt();
            this.objectName = new StringId(buffer);
        }

        @Override
        public void writeToBuffer(final ByteBuffer buffer) {
            BufferUtil.put(buffer, gameObjectType);
            BufferUtil.put(buffer, objectName);
        }
    }
}
