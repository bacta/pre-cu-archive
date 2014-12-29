package com.ocdsoft.bacta.swg.precu.object.sui;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;

import java.util.ArrayList;
import java.util.List;

public class SuiCommand implements SoeByteBufSerializable {
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

    @Override
    public void writeToBuffer(SoeByteBuf message) {
        message.writeByte(commandType);
        message.writeInt(wideParameters.size());

        for (int i = 0; i < wideParameters.size(); i++)
            message.writeUnicode(wideParameters.get(i));

        message.writeInt(narrowParameters.size());

        for (int i = 0; i < narrowParameters.size(); i++)
            message.writeAscii(narrowParameters.get(i));
    }
}
