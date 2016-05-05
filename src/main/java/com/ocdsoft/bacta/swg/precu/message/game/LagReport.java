package com.ocdsoft.bacta.swg.precu.message.game;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
      02 00 85 2F ED C5 80 03 00 00 00 00 00 00 

  */
@Getter
@AllArgsConstructor
public class LagReport extends GameNetworkMessage {

    static {
        priority = 0x2;
        messageType = SOECRC32.hashCode(LagReport.class.getSimpleName());
    }

    private final int value1;
    private final int value2;

    public LagReport(ByteBuffer buffer) {
        this.value1 = buffer.getInt();
        this.value2 = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(value1);
        buffer.putInt(value2);
    }
}
