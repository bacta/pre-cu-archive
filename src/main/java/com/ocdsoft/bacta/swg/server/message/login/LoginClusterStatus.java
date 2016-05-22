package com.ocdsoft.bacta.swg.server.message.login;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.server.object.login.ClusterEntry;
import com.ocdsoft.bacta.swg.server.object.login.PopulationStatus;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Priority(0x3)
public class LoginClusterStatus extends GameNetworkMessage {

    private final Set<ClusterData> clusterDataSet;

    private LoginClusterStatus() {
        clusterDataSet = new TreeSet<>();
    }

	public LoginClusterStatus(Set<ClusterEntry> clusterEntrySet) {
        this();
        clusterDataSet.addAll(clusterEntrySet.stream().map(ClusterEntry::getStatusClusterData).collect(Collectors.toList()));
	}

    public LoginClusterStatus(ByteBuffer buffer) {
        this();
        int count = buffer.getInt();
        for(int i = 0; i < count; ++i) {
            ClusterData status = new ClusterData(buffer);
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

    @Singleton
    @Getter
    public static class ClusterData implements ByteBufferWritable, Comparable<ClusterData> {

        private int id;
        private String connectionServerAddress;
        private short connectionServerPort;
        private short connectionServerPingPort;
        @Setter
        private int populationOnline;
        @Setter
        private PopulationStatus populationOnlineStatus;
        private int maxCharactersPerAccount;
        private int timeZone;
        @Setter
        private ServerStatus status; //enum
        private boolean dontRecommend;
        @Setter
        private int onlinePlayerLimit;
        @Setter
        private int onlineFreeTrialLimit;

        public ClusterData(final ByteBuffer buffer) {
            id = buffer.getInt();
            connectionServerAddress = BufferUtil.getAscii(buffer);
            connectionServerPort = buffer.getShort();
            connectionServerPingPort = buffer.getShort();
            populationOnline = buffer.getInt();
            populationOnlineStatus = PopulationStatus.values()[buffer.getInt()];
            maxCharactersPerAccount = buffer.getInt();
            timeZone = buffer.getInt();
            status = ServerStatus.values()[buffer.getInt()];
            dontRecommend = BufferUtil.getBoolean(buffer);
            onlinePlayerLimit = buffer.getInt();
            onlineFreeTrialLimit = buffer.getInt();
        }

        public ClusterData(final BactaConfiguration configuration) {
            id = configuration.getInt("Bacta/GameServer", "ServerID");
            connectionServerAddress = configuration.getString("Bacta/GameServer", "PublicAddress");
            connectionServerPort = (short) configuration.getInt("Bacta/GameServer", "Port");
            connectionServerPingPort = (short) configuration.getInt("Bacta/GameServer", "Ping");
            populationOnline = 0;
            populationOnlineStatus = PopulationStatus.PS_very_light;
            maxCharactersPerAccount = configuration.getInt("Bacta/GameServer", "MaxCharsPerAccount");
            timeZone = SoeMessageUtil.getTimeZoneValue();
            status = ServerStatus.DOWN;
            dontRecommend = configuration.getBoolean("Bacta/GameServer", "DontRecommended");
            onlinePlayerLimit = configuration.getInt("Bacta/GameServer", "OnlinePlayerLimit");
            onlineFreeTrialLimit = configuration.getInt("Bacta/GameServer", "OnlineFreeTrialLimit");
        }

        public ClusterData(Map<String, Object> clusterInfo) {
            id = (int) clusterInfo.get("id");
            connectionServerAddress = (String) clusterInfo.get("connectionServerAddress");
            connectionServerPort = ((Double)clusterInfo.get("connectionServerPort")).shortValue();
            connectionServerPingPort = ((Double)clusterInfo.get("connectionServerPingPort")).shortValue();
            populationOnline = ((Double)clusterInfo.get("populationOnline")).intValue();
            populationOnlineStatus = PopulationStatus.PS_very_light;
            maxCharactersPerAccount = ((Double)clusterInfo.get("maxCharactersPerAccount")).intValue();
            timeZone = ((Double)clusterInfo.get("timeZone")).intValue();
            status = ServerStatus.DOWN;
            dontRecommend = (boolean) clusterInfo.get("dontRecommend");
            onlinePlayerLimit = ((Double)clusterInfo.get("onlinePlayerLimit")).intValue();
            onlineFreeTrialLimit = ((Double)clusterInfo.get("onlineFreeTrialLimit")).intValue();
        }

        @Override
        public void writeToBuffer(ByteBuffer buffer) {
            buffer.putInt(id);
            BufferUtil.putAscii(buffer, connectionServerAddress);
            buffer.putShort(connectionServerPort);
            buffer.putShort(connectionServerPingPort);
            buffer.putInt(populationOnline);
            populationOnlineStatus.writeToBuffer(buffer);
            buffer.putInt(maxCharactersPerAccount);
            buffer.putInt(timeZone);
            status.writeToBuffer(buffer);
            BufferUtil.putBoolean(buffer, dontRecommend);
            buffer.putInt(onlinePlayerLimit);
            buffer.putInt(onlineFreeTrialLimit);
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
