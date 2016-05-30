package com.ocdsoft.bacta.swg.server.game.group;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.universe.group.GroupObject;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import gnu.trove.TCollections;
import gnu.trove.map.TLongLongMap;
import gnu.trove.map.hash.TLongLongHashMap;

/**
 * Created by crush on 5/28/2016.
 */
@Singleton
public class GroupService {
    //%s is the cluster name, and %d is the group id.
    private static final String chatRoomFormat = "swg.%s.group.%d.GroupChat";

    private final TLongLongMap leaderMap;

    private final ServerObjectService serverObjectService;

    @Inject
    public GroupService(final ServerObjectService serverObjectService) {
        this.serverObjectService = serverObjectService;
        this.leaderMap = TCollections.synchronizedMap(new TLongLongHashMap());
    }

    public void createAllGroupChatRooms() {
        leaderMap.forEachValue((groupId) -> {
            final ServerObject serverObject = serverObjectService.get(groupId);

            if (serverObject != null) {
                final GroupObject groupObject = serverObject.asGroupObject();

                if (groupObject != null)
                    createGroupChatRoom(groupObject);
            }

            return true;
        });
    }

    public void createGroupChatRoom(final GroupObject groupObject) {

    }

    public void notifyChatRoomCreated(final String roomPath) {
    }

}
