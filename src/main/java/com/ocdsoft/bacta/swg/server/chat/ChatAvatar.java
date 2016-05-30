package com.ocdsoft.bacta.swg.server.chat;

import com.ocdsoft.bacta.swg.shared.chat.messages.ChatAvatarId;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by crush on 5/29/2016.
 * <p>
 * This is the persistent state of a Chat Avatar.
 */
public final class ChatAvatar {
    @Getter
    @Setter
    private ChatAvatarId avatarId;
    @Getter
    @Setter
    private long networkId;

    private final Set<ChatAvatarId> friendList;
    private final Set<ChatAvatarId> ignoreList;

    public ChatAvatar(final long networkId, final ChatAvatarId avatarId) {
        this.avatarId = avatarId;
        this.networkId = networkId;

        this.friendList = new HashSet<>(10);
        this.ignoreList = new HashSet<>(10);
    }

    public Set<ChatAvatarId> getFriendList() {
        return Collections.unmodifiableSet(friendList);
    }

    public Set<ChatAvatarId> getIgnoreList() {
        return Collections.unmodifiableSet(ignoreList);
    }
}
