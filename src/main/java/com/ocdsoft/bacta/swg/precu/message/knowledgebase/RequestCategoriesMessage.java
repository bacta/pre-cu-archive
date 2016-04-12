package com.ocdsoft.bacta.swg.precu.message.knowledgebase;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

//C->S
public class RequestCategoriesMessage extends GameNetworkMessage {

    public RequestCategoriesMessage(final String language) {
        super(0x2, 0xf898e25f);
        writeAscii(language);
    }
}
