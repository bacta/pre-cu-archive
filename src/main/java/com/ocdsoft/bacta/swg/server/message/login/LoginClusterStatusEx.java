package com.ocdsoft.bacta.swg.server.message.login;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crush on 5/22/2016.
 * <p>
 * Provides extended cluster data (branch; changelist info)
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class LoginClusterStatusEx extends GameNetworkMessage {
    private final List<ClusterData> data;

    public LoginClusterStatusEx(final ByteBuffer buffer) {
        final int size = buffer.getInt();
        data = new ArrayList<>(size);

        for (int i = 0; i < size; ++i)
            data.add(new ClusterData(buffer));
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        final int size = data.size();
        BufferUtil.put(buffer, size);

        for (int i = 0; i < size; ++i)
            BufferUtil.put(buffer, data.get(i));
    }


    @AllArgsConstructor
    public static final class ClusterData implements ByteBufferWritable {
        public final int clusterId;
        public final String branch;
        public final String networkVersion;
        public final int version;
        public final int reserved1;
        public final int reserved2;
        public final int reserved3;
        public final int reserved4;

        public ClusterData(final ByteBuffer buffer) {
            this.clusterId = buffer.getInt();
            this.branch = BufferUtil.getAscii(buffer);
            this.networkVersion = BufferUtil.getAscii(buffer);
            this.version = buffer.getInt();
            this.reserved1 = buffer.getInt();
            this.reserved2 = buffer.getInt();
            this.reserved3 = buffer.getInt();
            this.reserved4 = buffer.getInt();
        }

        @Override
        public void writeToBuffer(final ByteBuffer buffer) {
            BufferUtil.put(buffer, clusterId);
            BufferUtil.put(buffer, branch);
            BufferUtil.put(buffer, networkVersion);
            BufferUtil.put(buffer, version);
            BufferUtil.put(buffer, reserved1);
            BufferUtil.put(buffer, reserved2);
            BufferUtil.put(buffer, reserved3);
            BufferUtil.put(buffer, reserved4);
        }
    }
}