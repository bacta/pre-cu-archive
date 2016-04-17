package com.ocdsoft.bacta.swg.precu.factory;


import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;

/**
 * Created by kyle on 4/10/2016.
 */
public interface ObjControllerMessageFactory {
    public ObjControllerMessage create(Class<? extends ObjControllerMessage> messageClass, ObjControllerMessage parentMessage);
}
