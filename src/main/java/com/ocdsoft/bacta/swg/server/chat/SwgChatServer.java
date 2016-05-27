package com.ocdsoft.bacta.swg.server.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;

/**
 * Created by crush on 5/23/2016.
 */
@Singleton
public class SwgChatServer {
    private final OutgoingConnectionService outgoingConnectionService;

    @Inject
    public SwgChatServer(final OutgoingConnectionService outgoingConnectionService) {
        this.outgoingConnectionService = outgoingConnectionService;
    }
}
