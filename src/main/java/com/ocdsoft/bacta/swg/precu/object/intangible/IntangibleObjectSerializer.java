package com.ocdsoft.bacta.swg.precu.object.intangible;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.precu.object.SceneObjectSerializer;
import com.ocdsoft.bacta.swg.precu.service.data.ObjectTemplateService;
import com.ocdsoft.network.service.object.ObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kburkhardt on 8/22/14.
 */
public class IntangibleObjectSerializer<T extends IntangibleObject> extends SceneObjectSerializer<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public IntangibleObjectSerializer(ObjectService objectService,
                                      ObjectTemplateService objectTemplateService) {
        super(objectService, objectTemplateService);
    }

    @Override
    public void write(Kryo kryo, Output output, T object) {
        super.write(kryo, output, object);

        //        output.writeFloat(complexity.get());
//        kryo.writeObject(output, nameStringId.get());
//        kryo.writeObject(output, objectName.get());
//        output.writeInt(volume.get());
//        output.writeString(appearanceData.get());

    }

    @Override
    public T read(Kryo kryo, Input input, Class<T> type) {
        T newObject = super.read(kryo, input, type);

//        complexity.set(input.readFloat());
//        nameStringId.set(kryo.readObject(input, StringId.class));
//        objectName.set(kryo.readObject(input, UnicodeString.class));
//        volume.set(input.readInt());
//        appearanceData.set(input.readString());

        return newObject;
    }
}
