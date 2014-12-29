package com.ocdsoft.bacta.swg.precu.message.knowledgebase;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

/**
 * Created by crush on 8/14/2014.
 */
public class AppendCommentResponseMessage extends SwgMessage {
    public AppendCommentResponseMessage(int result, int ticketId) {
        super(0x03, 0xA04A3ECA);

        writeInt(result);
        writeInt(ticketId);
    }
}
