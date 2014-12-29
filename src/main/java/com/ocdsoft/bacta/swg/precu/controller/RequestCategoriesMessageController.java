package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.knowledgebase.RequestCategoriesMessage;
import com.ocdsoft.bacta.swg.server.game.message.knowledgebase.RequestCategoriesResponseMessage;
import com.ocdsoft.bacta.swg.server.game.object.archive.AutoArray;
import com.ocdsoft.bacta.swg.server.game.object.knowledgebase.CustomerServiceCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.GAME, handles = RequestCategoriesMessage.class)
public class RequestCategoriesMessageController implements SwgMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public RequestCategoriesMessageController() {

    }

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {

        AutoArray<CustomerServiceCategory> categories = new AutoArray<>();

        RequestCategoriesResponseMessage response = new RequestCategoriesResponseMessage(1, categories);
        client.sendMessage(response);

    }
}
