package com.ocdsoft.bacta.swg.precu.message.knowledgebase;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

/**
 * Created by crush on 8/14/2014.
 */
public class AppendCommentResponseMessage extends GameNetworkMessage {
    public AppendCommentResponseMessage(int result, int ticketId) {
        super(0x03, 0xA04A3ECA);

        writeInt(result);
        writeInt(ticketId);
    }
}
