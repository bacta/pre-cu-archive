package com.ocdsoft.bacta.swg.server.login.object;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.io.udp.GameNetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.login.message.LoginClusterStatus;
import com.ocdsoft.bacta.swg.server.login.message.LoginEnumCluster;
import lombok.AllArgsConstructor;
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
    private final ExtendedClusterData extendedClusterData;

    @Inject
    public ClusterServer(final BactaConfiguration configuration,
                         final GameNetworkConfiguration networkConfiguration,
                         final GameServerState gameServerState) {

        id = networkConfiguration.getClusterId();
        remoteAddress = new InetSocketAddress(
                networkConfiguration.getPublicAddress(),
                networkConfiguration.getUdpPort()
                );
        tcpPort = configuration.getInt("Bacta/GameServer", "TcpPort");
        serverKey = configuration.getString("Bacta/GameServer", "ServerKey");
        name = configuration.getString("Bacta/GameServer", "ServerName");

        statusClusterData = new LoginClusterStatus.ClusterData(configuration, networkConfiguration);
        clusterData = new LoginEnumCluster.ClusterData(id, name);

        extendedClusterData = new ExtendedClusterData(gameServerState);
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
                ((Double)clusterInfo.get("UdpPort")).intValue()
        );
        tcpPort = ((Double)clusterInfo.get("TcpPort")).intValue();
        serverKey = (String) clusterInfo.get("secret");
        name = (String) clusterInfo.get("name");

        Map<String, Object> status = (Map<String, Object>) clusterInfo.get("statusClusterData");
        status.put("id", id);

        statusClusterData = new LoginClusterStatus.ClusterData(status);
        clusterData = new LoginEnumCluster.ClusterData(id, name);
        extendedClusterData = new ExtendedClusterData(
                id,
                (String) clusterInfo.get("branch"),
                (String) clusterInfo.get("networkVersion"),
                ((Double)clusterInfo.get("version")).intValue(),
                ((Double)clusterInfo.get("reserved1")).intValue(),
                ((Double)clusterInfo.get("reserved2")).intValue(),
                ((Double)clusterInfo.get("reserved3")).intValue(),
                ((Double)clusterInfo.get("reserved4")).intValue()
        );
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
        extendedClusterData = new ExtendedClusterData(buffer);
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
        extendedClusterData.writeToBuffer(buffer);
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


    @Getter
    @AllArgsConstructor
    public static class ExtendedClusterData implements ByteBufferWritable {

        private final int clusterId;
        private final String branch;
        private final String networkVersion;
        private final int version;
        private final int reserved1;
        private final int reserved2;
        private final int reserved3;
        private final int reserved4;

        ExtendedClusterData(final GameServerState gameServerState) {
            this.clusterId = gameServerState.getClusterId();
            this.branch = gameServerState.getBranch();
            this.networkVersion = gameServerState.getNetworkVersion();
            this.version = gameServerState.getVersion();
            this.reserved1 = 0;
            this.reserved2 = 0;
            this.reserved3 = 0;
            this.reserved4 = 0;
        }

        public ExtendedClusterData(ByteBuffer buffer) {
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
        public void writeToBuffer(ByteBuffer buffer) {
            buffer.putInt(clusterId);
            BufferUtil.putAscii(buffer, branch);
            BufferUtil.putAscii(buffer, networkVersion);
            buffer.putInt(version);
            buffer.putInt(reserved1);
            buffer.putInt(reserved2);
            buffer.putInt(reserved3);
            buffer.putInt(reserved4);
        }
    }
}
