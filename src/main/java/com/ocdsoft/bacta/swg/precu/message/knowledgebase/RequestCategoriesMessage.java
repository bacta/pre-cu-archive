package com.ocdsoft.bacta.swg.precu.message.knowledgebase;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

//C->S
public class RequestCategoriesMessage extends SwgMessage {

    public RequestCategoriesMessage(final String language) {
        super(0x2, 0xf898e25f);
        writeAscii(language);
    }
}
