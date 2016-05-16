package com.ocdsoft.bacta.swg.archive;

import com.ocdsoft.bacta.swg.util.ByteAppender;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crush on 8/14/2014.
 */
public class AutoArray<T> implements AutoVariableBase {
    private List<T> array = new ArrayList<>();

    @Override
    public void pack(ByteBuffer buffer) {
        try {
            buffer.putInt(array.size());

            for (T item : array) {
                ByteAppender.append(item, buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unpack(ByteBuffer buffer) {

    }
}
