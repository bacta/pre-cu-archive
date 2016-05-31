package com.ocdsoft.bacta.swg.server.game.message.outofband;

import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class OutOfBandPackager {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutOfBandPackager.class);

    public static final String pack(final ProsePackage prosePackage, final int position) {
        final ByteBuffer buffer = ByteBuffer.allocate(512).order(ByteOrder.LITTLE_ENDIAN); //Might need a byte buffer that can grow?
        return internalPack(buffer, prosePackage, position);
    }

    private static final String internalPack(final ByteBuffer buffer, final ProsePackage prosePackage, final int position) {
        try {
            buffer.putShort((short) 0); //Update this to 1 if we append a padding byte.
            buffer.put(OutOfBandType.prosePackage);
            buffer.putInt(position);
            prosePackage.writeToBuffer(buffer);

            int size = buffer.position(); //subtract the header info

            //If the size of the buffer is odd, we need to append a skip remainder char.
            if ((size & 1) == 1) {
                buffer.putShort(0, (short) 1);
                buffer.put((byte) 0); //Append a padding byte if the size is odd.
                ++size; //Increase the size by one.
            }

            buffer.rewind();

            final byte[] bytes = new byte[size];
            buffer.get(bytes);

            return new String(bytes, CharsetUtil.UTF_16LE);
        } catch (final BufferOverflowException ex) {
            LOGGER.debug("Resizing buffer because it overflowed");
            final ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2).order(ByteOrder.LITTLE_ENDIAN);
            return internalPack(newBuffer, prosePackage, position);
        }
    }
}
