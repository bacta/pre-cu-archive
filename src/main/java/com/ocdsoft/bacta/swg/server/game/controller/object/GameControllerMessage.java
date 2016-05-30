package com.ocdsoft.bacta.swg.server.game.controller.object;

import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by crush on 5/29/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GameControllerMessage {
    GameControllerMessageType[] value();
}
