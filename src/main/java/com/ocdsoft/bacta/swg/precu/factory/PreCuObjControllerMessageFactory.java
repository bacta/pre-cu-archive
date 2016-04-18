package com.ocdsoft.bacta.swg.precu.factory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.soe.factory.ObjControllerMessageFactory;
import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;

/**
 * Created by kyle on 4/10/2016.
 */
public class PreCuObjControllerMessageFactory implements ObjControllerMessageFactory<ObjControllerMessage> {

    private final Injector injector;

    @Inject
    public PreCuObjControllerMessageFactory(final Injector injector) {
        this.injector = injector;
    }

    @Override
    public ObjControllerMessage create(Class<? extends ObjControllerMessage> messageClass, ObjControllerMessage parentMessage) {
        ObjControllerMessage message = injector.getInstance(messageClass);
        message.readFromBuffer(parentMessage.getBuffer());
        return message;
    }
}
