package com.ocdsoft.bacta.swg.precu.chat;

import com.ocdsoft.bacta.swg.network.soe.lang.UnicodeString;
import com.ocdsoft.bacta.swg.network.soe.object.chat.ChatAvatarId;

import java.util.List;

/**
 * Created by crush on 8/13/2014.
 */
public class ChatRoomData {
    private int id;
    private int roomType;
    private String path;
    private ChatAvatarId owner;
    private ChatAvatarId creator;
    private UnicodeString title;
    private List<ChatAvatarId> moderators;
    private List<ChatAvatarId> invitees;
    private boolean moderated;
}
