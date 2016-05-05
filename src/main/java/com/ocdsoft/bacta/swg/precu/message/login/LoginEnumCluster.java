package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTimeZone;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.TreeSet;

@Priority(0x2)
public class LoginEnumCluster extends GameNetworkMessage {

    private final Set<ClusterData> clusterDataSet;
    private int maxCharactersPerAccount;

    @Inject
    public LoginEnumCluster() {
        clusterDataSet = new TreeSet<>();
    }

	public LoginEnumCluster(Set<ClusterEntry> clusterEntrySet, int maxCharactersPerAccount) {
        this();

        clusterDataSet.addAll(clusterEntrySet.stream().map(ClusterEntry::getClusterData).collect(java.util.stream.Collectors.toList()));
        this.maxCharactersPerAccount = maxCharactersPerAccount;
	}

    public LoginEnumCluster(ByteBuffer buffer) {
        this();
        for(int i = 0; i < buffer.getInt(); ++i) {
            ClusterData data = new ClusterData(buffer);
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
    public static class ClusterData implements ByteBufferWritable, Comparable<ClusterData> {

        private final int id;
        private final String name;
        private final int timezone;  // Offset from GMT in seconds

        public ClusterData(final int id, final String name) {
            this.id = id;
            this.name = name;
            this.timezone = DateTimeZone.getDefault().getOffset(null) / 1000;
        }

        public ClusterData(ByteBuffer buffer) {
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
