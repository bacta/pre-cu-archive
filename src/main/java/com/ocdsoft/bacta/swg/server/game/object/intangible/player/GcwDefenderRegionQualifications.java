package com.ocdsoft.bacta.swg.server.game.object.intangible.player;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/9/2016.
 */
@Getter
@AllArgsConstructor
public final class GcwDefenderRegionQualifications implements ByteBufferWritable {
    private final String gcwRegionName;
    private final boolean qualifyForRegionBonus;
    private final boolean qualifyForRegionDefenderTitle;

    public GcwDefenderRegionQualifications() {
        gcwRegionName = "";
        qualifyForRegionBonus = false;
        qualifyForRegionDefenderTitle = false;
    }

    public GcwDefenderRegionQualifications(final ByteBuffer buffer) {
        this.gcwRegionName = BufferUtil.getAscii(buffer);
        this.qualifyForRegionBonus = BufferUtil.getBoolean(buffer);
        this.qualifyForRegionDefenderTitle = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, gcwRegionName);
        BufferUtil.put(buffer, qualifyForRegionBonus);
        BufferUtil.put(buffer, qualifyForRegionDefenderTitle);
    }
}
