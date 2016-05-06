package com.ocdsoft.bacta.swg.precu.service.container;

import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.shared.container.ContainerTransferSession;

/**
 * Created by crush on 5/4/2016.
 */
public class PreCuContainerTransferSession extends ContainerTransferSession<ServerObject> {
    public PreCuContainerTransferSession(final ServerObject destination, final ServerObject transferer, final ServerObject item) {
        super(destination, transferer, item);
    }
}
