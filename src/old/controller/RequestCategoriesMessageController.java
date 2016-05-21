package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.knowledgebase.RequestCategoriesMessage;
import com.ocdsoft.bacta.swg.precu.message.knowledgebase.RequestCategoriesResponseMessage;
import com.ocdsoft.bacta.swg.archive.AutoArray;
import com.ocdsoft.bacta.swg.precu.object.knowledgebase.CustomerServiceCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(server = ServerType.GAME, handles = RequestCategoriesMessage.class)
public class RequestCategoriesMessageController implements GameNetworkMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public RequestCategoriesMessageController() {

    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message) {

        AutoArray<CustomerServiceCategory> categories = new AutoArray<>();

        RequestCategoriesResponseMessage response = new RequestCategoriesResponseMessage(1, categories);
        client.sendMessage(response);

    }
}
