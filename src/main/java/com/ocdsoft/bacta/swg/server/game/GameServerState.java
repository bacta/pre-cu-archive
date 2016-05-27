package com.ocdsoft.bacta.swg.server.game;

import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.swg.server.login.object.ClusterServer;

/**
 * Created by kyle on 4/11/2016.
 */

public interface GameServerState extends ServerState {
    int getId();
    ClusterServer getClusterServer();
    void setOnlineUsers(int onlineUsers);
}
