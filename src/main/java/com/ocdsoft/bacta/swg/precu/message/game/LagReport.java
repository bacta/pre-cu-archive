package com.ocdsoft.bacta.swg.precu.message.game;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
      02 00 85 2F ED C5 80 03 00 00 00 00 00 00 

  */
@Getter
public class LagReport extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(LagReport.class.getSimpleName()); // 0xc5ed2f85

    private int value1;
    private int value2;

    @Inject
    public LagReport() {
        super(priority, messageType);

        this.value1 = -1;
        this.value2 = -1;
    }

    public LagReport(final int value1, final int value2) {
        super(priority, messageType);

        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        this.value1 = buffer.getInt();
        this.value2 = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(value1);
        buffer.putInt(value2);
    }
}
