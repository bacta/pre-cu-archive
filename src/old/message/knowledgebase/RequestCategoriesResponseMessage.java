package com.ocdsoft.bacta.swg.precu.message.knowledgebase;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.archive.AutoArray;
import com.ocdsoft.bacta.swg.precu.object.knowledgebase.CustomerServiceCategory;

/**
 * Created by crush on 8/14/2014.
 */
public class RequestCategoriesResponseMessage extends GameNetworkMessage {
    public RequestCategoriesResponseMessage(int result, AutoArray<CustomerServiceCategory> categories) {
        super(0x06, 0x61148FD4);

        writeInt(result);
        categories.pack(this);
    }
}
