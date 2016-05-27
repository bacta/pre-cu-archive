package com.ocdsoft.bacta.swg.server.game.data;

import com.esotericsoftware.kryo.Kryo;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.serialize.KryoSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kyle on 8/14/2014.
 */
@Singleton
public final class ChatObjectSerializer extends KryoSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatObjectSerializer.class);

    @Inject
    public ChatObjectSerializer(Injector injector) {
        super(injector);
    }

    @Override
    public void registerTypes(Kryo kryo, Injector injector) {

    }
}
