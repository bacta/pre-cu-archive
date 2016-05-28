package com.ocdsoft.bacta.swg.server.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by crush on 5/27/2016.
 */
@Getter
@Singleton
public class ChatServerConfiguration {
    private static final String SECTION = "Bacta/ChatServer";

    private final Set<String> createRooms;
    /**
     * The name of the cluster for the ChatServer. Defaults to chat.
     */
    private final String clusterName;
    /**
     * The name of the game. Defaults to SWG.
     */
    private final String gameCode;

    /**
     * The name of the system avatar. Defaults to SYSTEM.
     */
    private final String systemAvatarName;

    @Inject
    public ChatServerConfiguration(final BactaConfiguration configuration) {
        //Get all the rooms that should be created.
        final Collection<String> createRoomEntries = configuration.getStringCollection(SECTION, "CreateRoom");

        if (createRoomEntries != null) {
            createRooms = new HashSet<>(createRoomEntries.size());
            createRooms.addAll(createRoomEntries);
        } else {
            createRooms = new HashSet<>(0);
        }

        clusterName = configuration.getStringWithDefault(SECTION, "clusterName", "chat");
        gameCode = configuration.getStringWithDefault(SECTION, "SystemAvatar.Game", "SWG");
        systemAvatarName = configuration.getStringWithDefault(SECTION, "SystemAvatar.Name", "SYSTEM");
    }


}
