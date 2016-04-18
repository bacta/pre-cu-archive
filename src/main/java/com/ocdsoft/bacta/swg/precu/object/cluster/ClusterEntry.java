package com.ocdsoft.bacta.swg.precu.object.cluster;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.object.ClusterEntryItem;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.Map;

public class ClusterEntry implements ClusterEntryItem, ByteBufferWritable, Comparable<ClusterEntry> {

    @Getter
    private int id;

    @Getter
    private final String secret;

    @Getter
    private final String name;

    @Getter
    private final ClusterStatus clusterStatus;

    @Getter
    private final ClusterData clusterData;

    /**
     * This constructor is used in Cluster Service to load constructors from
     */
    @Inject
    public ClusterEntry() {
        id = 0;
        secret = "";
        name = "";

        clusterStatus = null;
        clusterData = null;
    }
    
    @Override
    public void setId(int id) {
        this.id = id;
        clusterStatus.setId(id);
        clusterData.setId(id);
    }


    public ClusterEntry(BactaConfiguration configuration) {
        id = -1;
        secret = configuration.getString("Bacta/GameServer", "Secret");
        name = configuration.getString("Bacta/GameServer", "Name");

        clusterStatus = new ClusterStatus(configuration);
        clusterData = new ClusterData(id, name);
    }

    public ClusterEntry(ByteBuffer buffer) {

        id = buffer.getInt();
        secret = BufferUtil.getAscii(buffer);
        name = BufferUtil.getAscii(buffer);

        clusterStatus = new ClusterStatus(buffer);
        clusterData = new ClusterData(buffer);
    }

    /**
     * This constructor is called when the cluster object is
     * deserialized from the database
     * @param clusterInfo Map of values
     */
    public ClusterEntry(Map<String, Object> clusterInfo) {

        id = ((Double)clusterInfo.get("id")).intValue();
        secret = (String) clusterInfo.get("secret");
        name = (String) clusterInfo.get("name");

        Map<String, Object> status = (Map<String, Object>) clusterInfo.get("clusterStatus");
        status.put("id", id);

        clusterStatus = new ClusterStatus(status);
        clusterData = new ClusterData(id, name);
    }

    @Override
    public int compareTo(ClusterEntry o) {
        return o.getName().compareTo(getName());
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(id);
        BufferUtil.putAscii(buffer, secret);
        BufferUtil.putAscii(buffer, name);

        clusterStatus.writeToBuffer(buffer);
        clusterData.writeToBuffer(buffer);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ClusterEntry that = (ClusterEntry) o;

        return secret.equals(that.secret);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (secret != null ? secret.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (clusterStatus != null ? clusterStatus.hashCode() : 0);
        result = 31 * result + (clusterData != null ? clusterData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClusterEntry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + clusterStatus.getStatus() + '\'' +
                ", timezone='" + SoeMessageUtil.getTimeZoneOffsetFromValue(clusterStatus.getTimeZone()) + '\'' +
                '}';
    }
}
