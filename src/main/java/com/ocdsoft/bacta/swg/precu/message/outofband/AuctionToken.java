package com.ocdsoft.bacta.swg.precu.message.outofband;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;

public final class AuctionToken implements SoeByteBufSerializable {
    private final String sharedTemplateName;
    private final String customizationData;

    public AuctionToken(final String sharedTemplateName, final String customizationData) {
        this.sharedTemplateName = sharedTemplateName;
        this.customizationData = customizationData;
    }

    public AuctionToken(SoeByteBuf message) {
        this.sharedTemplateName = message.readAscii();
        this.customizationData = message.readAscii();
    }

    @Override
    public void writeToBuffer(SoeByteBuf buffer) {
        buffer.writeAscii(sharedTemplateName);
        buffer.writeAscii(customizationData);
    }
}
