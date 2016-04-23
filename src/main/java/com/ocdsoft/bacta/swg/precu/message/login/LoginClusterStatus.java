package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class LoginClusterStatus extends GameNetworkMessage {

    private static final short priority = 0x3;
    private static final int messageType = SOECRC32.hashCode(LoginClusterStatus.class.getSimpleName());

    private final Set<ClusterData> clusterDataSet;

    @Inject
    public LoginClusterStatus() {
        super(priority, messageType);
        clusterDataSet = new TreeSet<>();
    }

	public LoginClusterStatus(Set<ClusterEntry> clusterEntrySet) {
        this();
        clusterDataSet.addAll(clusterEntrySet.stream().map(ClusterEntry::getStatusClusterData).collect(Collectors.toList()));
	}

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        int count = buffer.getInt();
        for(int i = 0; i < count; ++i) {
            ClusterData status = new ClusterData();
            status.readFromBuffer(buffer);
            clusterDataSet.add(status);
        }
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

        buffer.putInt(clusterDataSet.size());

        for (ClusterData clusterData : clusterDataSet) {
            clusterData.writeToBuffer(buffer);
        }
    }

    /**
     *
     * // NGE Struct
     struct LoginClusterStatus_ClusterData
     {
     unsigned int m_clusterId;
     std::string m_connectionServerAddress;
     unsigned __int16 m_connectionServerPort;
     unsigned __int16 m_connectionServerPingPort;
     int m_populationOnline;
     LoginClusterStatus_ClusterData::PopulationStatus m_populationOnlineStatus;
     int m_maxCharactersPerAccount;
     int m_timeZone;
     LoginClusterStatus_ClusterData::Status m_status;
     bool m_dontRecommend;
     unsigned int m_onlinePlayerLimit;
     unsigned int m_onlineFreeTrialLimit;
     }
     *
     */

    @Singleton
    public static class ClusterData implements ByteBufferSerializable, Comparable<ClusterData> {

        @Getter
        @Setter
        private int id;

        @Getter
        private String connectionServerAddress;

        @Getter
        private short connectionServerPort;

        @Getter
        private short connectionServerPingPort;

        @Getter
        @Setter
        private int populationOnline;

        @Getter
        @Setter
        private int onlinePlayerLimit;

        @Getter
        private int maxCharactersPerAccount;

        @Getter
        private int timeZone;

        @Getter
        @Setter
        private ServerStatus status; //enum

        private boolean dontRecommend;

        @Inject
        public ClusterData() {
            id = -1;
            connectionServerAddress = "";
            connectionServerPort = -1;
            connectionServerPingPort = -1;
            populationOnline = -1;
            onlinePlayerLimit = -1;
            maxCharactersPerAccount = -1;
            timeZone = -1;
            status = ServerStatus.DOWN;
            dontRecommend = true;
        }

        @Inject
        public ClusterData(BactaConfiguration configuration) {
            id = -1;
            connectionServerAddress = configuration.getString("Bacta/GameServer", "PublicAddress");
            connectionServerPort = (short) configuration.getInt("Bacta/GameServer", "Port");
            connectionServerPingPort = (short) configuration.getInt("Bacta/GameServer", "Ping");
            populationOnline = 0;
            onlinePlayerLimit = configuration.getInt("Bacta/GameServer", "OnlinePlayerLimit");
            maxCharactersPerAccount = configuration.getInt("Bacta/GameServer", "MaxCharsPerAccount");
            timeZone = SoeMessageUtil.getTimeZoneValue();
            status = ServerStatus.DOWN;
            dontRecommend = configuration.getBoolean("Bacta/GameServer", "DontRecommended");
        }

        public ClusterData(Map<String, Object> clusterInfo) {
            id = (int) clusterInfo.get("id");
            connectionServerAddress = (String) clusterInfo.get("connectionServerAddress");
            connectionServerPort = ((Double)clusterInfo.get("connectionServerPort")).shortValue();
            connectionServerPingPort = ((Double)clusterInfo.get("connectionServerPingPort")).shortValue();
            populationOnline = ((Double)clusterInfo.get("populationOnline")).intValue();
            onlinePlayerLimit = ((Double)clusterInfo.get("onlinePlayerLimit")).intValue();
            maxCharactersPerAccount = ((Double)clusterInfo.get("maxCharactersPerAccount")).intValue();
            timeZone = ((Double)clusterInfo.get("timeZone")).intValue();
            status = ServerStatus.DOWN;
            dontRecommend = (boolean) clusterInfo.get("dontRecommend");
        }

        @Override
        public void readFromBuffer(ByteBuffer buffer) {
            id = buffer.getInt();
            connectionServerAddress = BufferUtil.getAscii(buffer);
            connectionServerPort = buffer.getShort();
            connectionServerPingPort = buffer.getShort();
            populationOnline = buffer.getInt();
            onlinePlayerLimit = buffer.getInt();
            maxCharactersPerAccount = buffer.getInt();
            timeZone = buffer.getInt();
            status = ServerStatus.values()[buffer.getInt()];
            dontRecommend = BufferUtil.getBoolean(buffer);
        }

        @Override
        public void writeToBuffer(ByteBuffer buffer) {
            buffer.putInt(id);
            BufferUtil.putAscii(buffer, connectionServerAddress);
            buffer.putShort(connectionServerPort);
            buffer.putShort(connectionServerPingPort);
            buffer.putInt(populationOnline);
            buffer.putInt(onlinePlayerLimit);
            buffer.putInt(maxCharactersPerAccount);
            buffer.putInt(timeZone);
            status.writeToBuffer(buffer);
            BufferUtil.putBoolean(buffer, dontRecommend);
        }

        public boolean isDown() { return status == ServerStatus.DOWN; }
        public boolean isLoading() { return status == ServerStatus.LOADING; }
        public boolean isUp()  { return status == ServerStatus.UP;  }
        public boolean isLocked()  { return status == ServerStatus.LOCKED;  }
        public boolean isRestricted()  { return status == ServerStatus.RESTRICTED;  }
        public boolean isFull()  { return status == ServerStatus.FULL;  }
        public boolean isRecommended()  { return !dontRecommend;  }

        @Override
        public int compareTo(ClusterData o) {
            return connectionServerAddress.compareTo(o.connectionServerAddress);
        }
    }
}
