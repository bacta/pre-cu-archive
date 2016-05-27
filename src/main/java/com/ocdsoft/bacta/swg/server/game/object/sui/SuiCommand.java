package com.ocdsoft.bacta.swg.server.game.object.sui;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SuiCommand implements ByteBufferWritable {
    public static final byte SCT_clearDataSource = 0x01;
    public static final byte SCT_addChildWidget = 0x02;
    public static final byte SCT_setProperty = 0x03;
    public static final byte SCT_addDataItem = 0x04;
    public static final byte SCT_subscribeToEvent = 0x05;
    public static final byte SCT_addDataSourceContainer = 0x06;
    public static final byte SCT_clearDataSourceContainer = 0x07;

    private final byte commandType;
    private final List<String> wideParameters = new ArrayList<String>();
    private final List<String> narrowParameters = new ArrayList<String>();

    public SuiCommand(byte commandType) {
        this.commandType = commandType;
    }

    public void addNarrowParameter(String param) {
        narrowParameters.add(param);
    }

    public void addWideParameter(String param) {
        wideParameters.add(param);
    }

    // TODO: this
//    public SuiCommand(ByteBuffer buffer) {
//
//    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.put(commandType);
        buffer.putInt(wideParameters.size());

        for (int i = 0; i < wideParameters.size(); i++) {
            BufferUtil.putUnicode(buffer, wideParameters.get(i));
        }

        buffer.putInt(narrowParameters.size());

        for (int i = 0; i < narrowParameters.size(); i++) {
            BufferUtil.putAscii(buffer, narrowParameters.get(i));
        }
    }
}
