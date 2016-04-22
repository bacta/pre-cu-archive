package com.ocdsoft.bacta.swg.precu.object.login;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.Setter;
import org.joda.time.DateTimeZone;

import java.nio.ByteBuffer;

/**
 * Created by kburkhardt on 1/18/15.
 *
 struct LoginEnumCluster_ClusterData
 {
     unsigned int m_clusterId;
     std::string m_clusterName;
     int m_timeZone;
 };
 */
public class ClusterData implements ByteBufferWritable, Comparable<ClusterData> {

    @Setter
    private int id;
    
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
