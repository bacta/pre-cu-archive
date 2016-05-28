package com.ocdsoft.bacta.swg.server.game.commodities;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.Set;

/**
 * Created by crush on 5/28/2016.
 */
@Getter
public final class ResourceTypeMap implements ByteBufferWritable {
    private final TIntObjectMap<Set<String>> internalMap;

    public ResourceTypeMap() {
        this.internalMap = new TIntObjectHashMap<>();
    }

    public ResourceTypeMap(final ByteBuffer buffer) {
        this.internalMap = BufferUtil.getTIntObjectHashMap(buffer,
                buff1 -> BufferUtil.getHashSet(buff1, BufferUtil::getAscii));
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, internalMap,
                (buff1, set) -> BufferUtil.put(buff1, set, BufferUtil::putAscii));
    }
}
