package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTimeZone;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.TreeSet;

public class LoginEnumCluster extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(LoginEnumCluster.class.getSimpleName());

    private final Set<ClusterData> clusterDataSet;
    private int maxCharactersPerAccount;

    @Inject
    public LoginEnumCluster() {
        super(priority, messageType);

        clusterDataSet = new TreeSet<>();
    }

	public LoginEnumCluster(Set<ClusterEntry> clusterEntrySet, int maxCharactersPerAccount) {
        this();

        clusterDataSet.addAll(clusterEntrySet.stream().map(ClusterEntry::getClusterData).collect(java.util.stream.Collectors.toList()));
        this.maxCharactersPerAccount = maxCharactersPerAccount;
	}

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        for(int i = 0; i < buffer.getInt(); ++i) {
            ClusterData data = new ClusterData();
            data.readFromBuffer(buffer);
            clusterDataSet.add(data);
        }

        maxCharactersPerAccount = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

        buffer.putInt(clusterDataSet.size());
        for (ClusterData data : clusterDataSet) {
            data.writeToBuffer(buffer);
        }
        buffer.putInt(maxCharactersPerAccount);
    }

    @Getter
    public static class ClusterData implements ByteBufferSerializable, Comparable<ClusterData> {

        @Setter
        private int id;

        private String name;
        private int timezone;  // Offset from GMT in seconds

        @Inject
        public ClusterData() {
            this.id = -1;
            this.name = "";
            this.timezone = DateTimeZone.getDefault().getOffset(null) / 1000;
        }

        public ClusterData(final int id, final String name) {
            this.id = id;
            this.name = name;
            this.timezone = DateTimeZone.getDefault().getOffset(null) / 1000;
        }

        @Override
        public void readFromBuffer(ByteBuffer buffer) {
            id = buffer.getInt();
            name = BufferUtil.getAscii(buffer);
            timezone = buffer.getInt();
        }

        @Override
        public void writeToBuffer(ByteBuffer buffer) {
            buffer.putInt(id);
            BufferUtil.putAscii(buffer, name);
            buffer.putInt(timezone);
        }

        @Override
        public int compareTo(ClusterData o) {
            return name.compareTo(o.name);
        }
    }
}
