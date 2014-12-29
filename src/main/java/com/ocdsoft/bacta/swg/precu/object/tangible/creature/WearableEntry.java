package com.ocdsoft.bacta.swg.precu.object.tangible.creature;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import lombok.Getter;

public class WearableEntry implements SoeByteBufSerializable {
    @Getter
    private String customizationString;
    @Getter
    private Integer containmentType;
    @Getter
    private Long objectId;
    @Getter
    private Integer templateCrc;

    @Override
    public void writeToBuffer(SoeByteBuf message) {
        message.writeAscii(customizationString);
        message.writeInt(containmentType);
        message.writeLong(objectId);
        message.writeInt(templateCrc);
    }
}
