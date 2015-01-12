package com.ocdsoft.bacta.swg.precu.chat;

import com.ocdsoft.bacta.soe.object.chat.ChatAvatarId;

/**
 * Created by crush on 7/24/2014.
 */
public interface ChatServerAgentFactory {
    ChatServerAgent create(ChatAvatarId avatarId, ChatServerAgentHandler handler);
}
