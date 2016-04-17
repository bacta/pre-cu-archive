package com.ocdsoft.bacta.swg.precu.message.outofband;

import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public final class OutOfBandPackager {
    private static final Logger logger = LoggerFactory.getLogger(OutOfBandPackager.class);

    public static final String pack(ProsePackage prosePackage, int position) {

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.putShort((short) 0); //Update this to 1 if we append a padding byte.
        buffer.put(OutOfBandType.prosePackage);
        buffer.putInt(position);
        prosePackage.writeToBuffer(buffer);

        int size = buffer.position(); //subtract the header info

        if ((size & 1) == 1) {
            buffer.putShort(0, (short) 1);
            buffer.put((byte) 0); //Append a padding byte if the size is odd.
        }

        byte[] bytes = new byte[buffer.position()];
        buffer.put(bytes);

        return new String(bytes, CharsetUtil.UTF_16LE);
    }
}
