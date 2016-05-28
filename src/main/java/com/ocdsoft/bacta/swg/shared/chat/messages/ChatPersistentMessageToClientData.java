package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 */
@Getter
@AllArgsConstructor
public class ChatPersistentMessageToClientData implements ByteBufferWritable {
    private String fromCharacterName;
    private String fromGameCode;
    private String fromServerCode;
    private int id;
    private boolean header;
    private String message; //unicode
    private String subject; //unicode
    private String outOfBand; //unicode
    private byte status;
    private int timeStamp;

    public ChatPersistentMessageToClientData() {
        header = true; //Defaults to true
    }

    public ChatPersistentMessageToClientData(final ByteBuffer buffer) {
        this.fromCharacterName = BufferUtil.getAscii(buffer);
        this.fromGameCode = BufferUtil.getAscii(buffer);
        this.fromServerCode = BufferUtil.getAscii(buffer);
        this.id = buffer.getInt();
        this.header = BufferUtil.getBoolean(buffer);
        this.message = BufferUtil.getUnicode(buffer);
        this.subject = BufferUtil.getUnicode(buffer);
        this.outOfBand = BufferUtil.getUnicode(buffer);
        this.status = buffer.get();
        this.timeStamp = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, fromCharacterName);
        BufferUtil.putAscii(buffer, fromGameCode);
        BufferUtil.putAscii(buffer, fromServerCode);
        BufferUtil.put(buffer, id);
        BufferUtil.put(buffer, header);
        BufferUtil.putUnicode(buffer, message);
        BufferUtil.putUnicode(buffer, subject);
        BufferUtil.putUnicode(buffer, outOfBand);
        BufferUtil.put(buffer, status);
        BufferUtil.put(buffer, timeStamp);
    }
}
