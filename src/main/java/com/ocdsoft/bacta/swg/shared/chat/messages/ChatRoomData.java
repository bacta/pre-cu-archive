package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crush on 5/20/2016.
 */
@Getter
public class ChatRoomData implements ByteBufferWritable {
    private int id;
    private int roomType;
    private String path;
    private ChatAvatarId owner;
    private ChatAvatarId creator;
    private String title; //unicode
    private List<ChatAvatarId> moderators;
    private List<ChatAvatarId> invitees;
    private byte moderated;

    public ChatRoomData() {

    }

    public ChatRoomData(final ByteBuffer buffer) {
        this.id = buffer.getInt();
        this.roomType = buffer.getInt();
        this.moderated = buffer.get();
        this.path = BufferUtil.getAscii(buffer);
        this.owner = new ChatAvatarId(buffer);
        this.creator = new ChatAvatarId(buffer);
        this.title = BufferUtil.getUnicode(buffer);

        int i;

        int count = buffer.getInt();
        moderators = new ArrayList<>(count);

        for (i = 0; i < count; ++i)
            moderators.add(new ChatAvatarId(buffer));

        count = buffer.getInt();
        invitees = new ArrayList<>(count);

        for (i = 0; i < count; ++i)
            invitees.add(new ChatAvatarId(buffer));
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.put(buffer, id);
        BufferUtil.put(buffer, roomType);
        BufferUtil.put(buffer, moderated);
        BufferUtil.put(buffer, path);
        BufferUtil.put(buffer, owner);
        BufferUtil.put(buffer, creator);
        BufferUtil.putUnicode(buffer, title);

        int i;
        int count = moderators == null ? 0 : moderators.size();

        BufferUtil.put(buffer, count);

        for (i = 0; i < count; ++i)
            BufferUtil.put(buffer, moderators.get(i));

        count = invitees == null ? 0 : invitees.size();

        BufferUtil.put(buffer, count);

        for (i = 0; i < count; ++i)
            BufferUtil.put(buffer, invitees.get(i));
    }
}
