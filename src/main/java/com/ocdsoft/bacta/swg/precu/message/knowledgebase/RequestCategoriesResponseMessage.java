package com.ocdsoft.bacta.swg.precu.message.knowledgebase;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.archive.AutoArray;
import com.ocdsoft.bacta.swg.server.game.object.knowledgebase.CustomerServiceCategory;

/**
 * Created by crush on 8/14/2014.
 */
public class RequestCategoriesResponseMessage extends SwgMessage {
    public RequestCategoriesResponseMessage(int result, AutoArray<CustomerServiceCategory> categories) {
        super(0x06, 0x61148FD4);

        writeInt(result);
        categories.pack(this);
    }
}
