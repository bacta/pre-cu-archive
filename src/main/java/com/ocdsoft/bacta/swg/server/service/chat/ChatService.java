package com.ocdsoft.bacta.swg.server.service.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.object.ServerObject;
import com.ocdsoft.bacta.swg.shared.localization.StringId;

/**
 * Created by crush on 4/28/2016.
 */
@Singleton
public class ChatService {

    //private final List<SoeUdpConnection> chatServers;

    @Inject
    public ChatService() {

    }

    public void sendSystemMessageSimple(final ServerObject player,
                                        final StringId stringId,
                                        final ServerObject target) {

    }
}
