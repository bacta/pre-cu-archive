package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;

/**
 * Created by crush on 8/13/2014.
 */
public class MatchMakingId implements ByteBufferSerializable {
    public static final int lookingForGroup = 0x0;
    public static final int helper = 0x1;
    public static final int rolePlay = 0x2;
    public static final int faction = 0x3;
    public static final int species = 0x4;
    public static final int title = 0x5;
    public static final int friend = 0x6;
    public static final int awayFromKeyBoard = 0x7;
    public static final int linkDead = 0x8;
    public static final int displayingFactionRank = 0x9;
    public static final int displayLocationInSearchResults = 0xA;
    public static final int outOfCharacter = 0xB;
    public static final int searchableByCtsSourceGalaxy = 0xC;
    public static final int lookingForWork = 0xD;
    public static final int maxLoadedBits = 0x7E;
    public static final int anonymous = 0x7F;

    private BitSet bitSet = new BitSet(128);

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(4);
        buffer.put(Arrays.copyOf(bitSet.toByteArray(), 16));
    }

    public void flip(int index) {
        bitSet.flip(index);
    }

    public void set(int index) {
        bitSet.set(index);
    }

    public void unset(int index) {
        bitSet.clear(index);
    }

    public boolean isBitSet(int index) {
        return bitSet.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchMakingId that = (MatchMakingId) o;

        if (bitSet != null ? !bitSet.equals(that.bitSet) : that.bitSet != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return bitSet != null ? bitSet.hashCode() : 0;
    }
}
