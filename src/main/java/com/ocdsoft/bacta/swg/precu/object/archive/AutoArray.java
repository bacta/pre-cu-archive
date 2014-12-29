package com.ocdsoft.bacta.swg.precu.object.archive;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.util.ByteAppender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crush on 8/14/2014.
 */
public class AutoArray<T> implements AutoVariableBase {
    private List<T> array = new ArrayList<>();

    @Override
    public void pack(SoeByteBuf buffer) {
        try {
            buffer.writeInt(array.size());

            for (T item : array) {
                ByteAppender.append(item, buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unpack(SoeByteBuf buffer) {

    }
}
