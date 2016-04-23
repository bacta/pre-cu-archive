package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterStatus;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class LoginClusterStatus extends GameNetworkMessage {

    private static final short priority = 0x3;
    private static final int messageType = SOECRC32.hashCode(LoginClusterStatus.class.getSimpleName());

    private final Set<ClusterStatus> clusterStatusSet;

    @Inject
    public LoginClusterStatus() {
        super(priority, messageType);
        clusterStatusSet = new TreeSet<>();
    }

	public LoginClusterStatus(Set<ClusterEntry> clusterEntrySet) {
        this();
        clusterStatusSet.addAll(clusterEntrySet.stream().map(ClusterEntry::getClusterStatus).collect(Collectors.toList()));
	}

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        int count = buffer.getInt();
        for(int i = 0; i < count; ++i) {
            ClusterStatus status = new ClusterStatus(buffer);
            clusterStatusSet.add(status);
        }
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

        buffer.putInt(clusterStatusSet.size());

        for (ClusterStatus clusterStatus: clusterStatusSet) {
            clusterStatus.writeToBuffer(buffer);
        }
    }
}
