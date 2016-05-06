package com.ocdsoft.bacta.swg.precu.object.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.object.ClusterEntryItem;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.precu.message.login.LoginClusterStatus;
import com.ocdsoft.bacta.swg.precu.message.login.LoginEnumCluster;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ClusterEntry implements ClusterEntryItem, ByteBufferWritable, Comparable<ClusterEntry> {

    private final int id;
    private final String secret;
    private final String name;
    private final LoginClusterStatus.ClusterData statusClusterData;
    private final LoginEnumCluster.ClusterData clusterData;

    @Inject
    public ClusterEntry(BactaConfiguration configuration) {
        id = configuration.getInt("Bacta/GameServer", "ServerID");
        secret = configuration.getString("Bacta/GameServer", "Secret");
        name = configuration.getString("Bacta/GameServer", "Name");

        statusClusterData = new LoginClusterStatus.ClusterData(configuration);
        clusterData = new LoginEnumCluster.ClusterData(id, name);
    }

    public ClusterEntry(ByteBuffer buffer) {
        id = buffer.getInt();
        secret = BufferUtil.getAscii(buffer);
        name = BufferUtil.getAscii(buffer);

        statusClusterData = new LoginClusterStatus.ClusterData(buffer);
        clusterData = new LoginEnumCluster.ClusterData(buffer);
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

        Map<String, Object> status = (Map<String, Object>) clusterInfo.get("statusClusterData");
        status.put("id", id);

        statusClusterData = new LoginClusterStatus.ClusterData(status);
        clusterData = new LoginEnumCluster.ClusterData(id, name);
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

        statusClusterData.writeToBuffer(buffer);
        clusterData.writeToBuffer(buffer);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ClusterEntry that = (ClusterEntry) o;

        return id == that.id &&
                secret.equals(that.secret);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (secret != null ? secret.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (clusterData != null ? clusterData.hashCode() : 0);
        result = 31 * result + (clusterData != null ? clusterData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClusterEntry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + statusClusterData.getStatus() + '\'' +
                ", timezone='" + SoeMessageUtil.getTimeZoneOffsetFromValue(clusterData.getTimezone()) + '\'' +
                '}';
    }


}
