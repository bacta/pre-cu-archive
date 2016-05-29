package com.ocdsoft.bacta.swg.shared.chat;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatAvatarId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by crush on 5/20/2016.
 */
@Getter
@AllArgsConstructor
public final class ChatRoomData implements ByteBufferWritable {
    public static final ChatRoomData EMPTY = new ChatRoomData();

    private final int id;
    private final ChatRoomVisibility roomType;
    private final String path;
    private final ChatAvatarId owner;
    private final ChatAvatarId creator;
    private final String title; //unicode
    private final Set<ChatAvatarId> moderators;
    private final Set<ChatAvatarId> invitees;
    private final boolean moderated;

    private ChatRoomData() {
        id = 0;
        roomType = ChatRoomVisibility.PUBLIC;
        path = "";
        owner = ChatAvatarId.EMPTY;
        creator = ChatAvatarId.EMPTY;
        title = "";
        moderators = new HashSet<>(0);
        invitees = new HashSet<>(0);
        moderated = false;
    }

    public ChatRoomData(final ByteBuffer buffer) {
        this.id = buffer.getInt();
        this.roomType = ChatRoomVisibility.from(buffer.getInt());
        this.moderated = BufferUtil.getBoolean(buffer);
        this.path = BufferUtil.getAscii(buffer);
        this.owner = new ChatAvatarId(buffer);
        this.creator = new ChatAvatarId(buffer);
        this.title = BufferUtil.getUnicode(buffer);
        this.moderators = BufferUtil.getHashSet(buffer, ChatAvatarId::new);
        this.invitees = BufferUtil.getHashSet(buffer, ChatAvatarId::new);
    }

    public boolean isPublicRoom() {
        return roomType == ChatRoomVisibility.PUBLIC;
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.put(buffer, id);
        BufferUtil.put(buffer, roomType.value);
        BufferUtil.put(buffer, moderated);
        BufferUtil.put(buffer, path);
        BufferUtil.put(buffer, owner);
        BufferUtil.put(buffer, creator);
        BufferUtil.putUnicode(buffer, title);
        BufferUtil.put(buffer, moderators);
        BufferUtil.put(buffer, invitees);
    }
}
