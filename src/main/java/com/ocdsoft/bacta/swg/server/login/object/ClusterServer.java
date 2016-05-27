package com.ocdsoft.bacta.swg.server.login.object;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.server.login.message.LoginClusterStatus;
import com.ocdsoft.bacta.swg.server.login.message.LoginEnumCluster;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

@Getter
public class ClusterServer implements ByteBufferWritable, Comparable<ClusterServer> {

    private final int id;
    private final InetSocketAddress remoteAddress;
    private final int tcpPort;
    private final String serverKey;
    private final String name;
    private final LoginClusterStatus.ClusterData statusClusterData;
    private final LoginEnumCluster.ClusterData clusterData;

    @Inject
    public ClusterServer(final BactaConfiguration configuration) {
        id = configuration.getInt("Bacta/GameServer", "ServerID");
        remoteAddress = new InetSocketAddress(
                configuration.getString("Bacta/GameServer", "PublicAddress"),
                configuration.getInt("Bacta/GameServer", "Port")
                );
        tcpPort = configuration.getInt("Bacta/GameServer", "TCPPort");
        serverKey = configuration.getString("Bacta/GameServer", "ServerKey");
        name = configuration.getString("Bacta/GameServer", "ServerName");

        statusClusterData = new LoginClusterStatus.ClusterData(configuration);
        clusterData = new LoginEnumCluster.ClusterData(id, name);
    }

    /**
     * This constructor is called when the cluster object is
     * deserialized from the database
     * @param clusterInfo Map of values
     */
    public ClusterServer(Map<String, Object> clusterInfo) {

        id = ((Double)clusterInfo.get("id")).intValue();
        remoteAddress = new InetSocketAddress(
                (String) clusterInfo.get("PublicAddress"),
                ((Double)clusterInfo.get("Port")).intValue()
        );
        tcpPort = ((Double)clusterInfo.get("TCPPort")).intValue();
        serverKey = (String) clusterInfo.get("secret");
        name = (String) clusterInfo.get("name");

        Map<String, Object> status = (Map<String, Object>) clusterInfo.get("statusClusterData");
        status.put("id", id);

        statusClusterData = new LoginClusterStatus.ClusterData(status);
        clusterData = new LoginEnumCluster.ClusterData(id, name);
    }

    public ClusterServer(ByteBuffer buffer) {
        id = buffer.getInt();
        remoteAddress = new InetSocketAddress(
                BufferUtil.getAscii(buffer),
                buffer.getInt()
        );
        tcpPort = buffer.getInt();
        serverKey = BufferUtil.getAscii(buffer);
        name = BufferUtil.getAscii(buffer);

        statusClusterData = new LoginClusterStatus.ClusterData(buffer);
        clusterData = new LoginEnumCluster.ClusterData(buffer);
    }


    @Override
    public int compareTo(ClusterServer o) {
        return o.getName().compareTo(getName());
    }


    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(id);
        BufferUtil.putAscii(buffer, remoteAddress.getHostString());
        buffer.putInt(remoteAddress.getPort());
        buffer.putInt(tcpPort);
        BufferUtil.putAscii(buffer, serverKey);
        BufferUtil.putAscii(buffer, name);

        statusClusterData.writeToBuffer(buffer);
        clusterData.writeToBuffer(buffer);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ClusterServer that = (ClusterServer) o;

        return id == that.id &&
                serverKey.equals(that.serverKey);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (serverKey != null ? serverKey.hashCode() : 0);
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
