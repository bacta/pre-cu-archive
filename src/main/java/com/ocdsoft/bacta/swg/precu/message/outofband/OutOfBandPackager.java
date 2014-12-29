package com.ocdsoft.bacta.swg.precu.message.outofband;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OutOfBandPackager {
    private static final Logger logger = LoggerFactory.getLogger(OutOfBandPackager.class);

    public static final String pack(ProsePackage prosePackage, int position) {
        SoeByteBuf buffer = new SoeByteBuf();
        buffer.writeShort(0); //Update this to 1 if we append a padding byte.
        buffer.writeByte(OutOfBandType.prosePackage);
        buffer.writeInt(position);
        prosePackage.writeToBuffer(buffer);

        int size = buffer.readableBytes(); //subtract the header info

        if ((size & 1) == 1) {
            buffer.setShort(0, 1);
            buffer.writeByte(0); //Append a padding byte if the size is odd.
        }

        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.getBytes(0, bytes);

        return new String(bytes, CharsetUtil.UTF_16LE);
    }
}
