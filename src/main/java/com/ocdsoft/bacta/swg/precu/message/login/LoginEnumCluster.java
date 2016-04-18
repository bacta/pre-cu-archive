package com.ocdsoft.bacta.swg.precu.message.login;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.object.cluster.ClusterData;
import com.ocdsoft.bacta.swg.precu.object.cluster.ClusterEntry;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.TreeSet;

public class LoginEnumCluster extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(LoginEnumCluster.class.getSimpleName());

    private final Set<ClusterData> clusterDataSet;
    private final int maxCharactersPerAccount;

	public LoginEnumCluster(Set<ClusterEntry> clusterEntrySet, int maxCharactersPerAccount) {
        super(priority, messageType);

        clusterDataSet = new TreeSet<>();
        clusterDataSet.addAll(clusterEntrySet.stream().map(entry -> entry.getClusterData()).collect(java.util.stream.Collectors.toList()));

        this.maxCharactersPerAccount = maxCharactersPerAccount;
	}

    public LoginEnumCluster(ByteBuffer buffer) {
        super(priority, messageType);

        clusterDataSet = new TreeSet<>();
        int count = buffer.getInt();
        for(int i = 0; i < count; ++i) {
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

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }
}
