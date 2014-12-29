package com.ocdsoft.bacta.swg.precu.object.tangible;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.object.SceneObjectSerializer;
import com.ocdsoft.bacta.swg.server.game.service.data.ObjectTemplateService;
import com.ocdsoft.network.service.object.ObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kburkhardt on 8/22/14.
 */
public class TangibleObjectSerializer<T extends TangibleObject> extends SceneObjectSerializer<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public TangibleObjectSerializer(ObjectService objectService,
                                    ObjectTemplateService objectTemplateService) {
        super(objectService, objectTemplateService);
    }

    @Override
    public void write(Kryo kryo, Output output, T object) {
        super.write(kryo, output, object);

        //output.writeString(object.zone.getTerrainName());
//        output.writeFloat(object.complexity.get());
//        kryo.writeObject(output, object.nameStringId.get());
//        kryo.writeObject(output, object.objectName.get());
//        output.writeInt(object.volume.get());
//        output.writeString(object.appearanceData.get());
//
//        Set<Integer> components = object.components.get();
//        output.writeInt(components.size());
//        for(int value : components) {
//            output.writeInt(value);
//        }


    }

    @Override
    public T read(Kryo kryo, Input input, Class<T> type) {
        T newObject = super.read(kryo, input, type);

        //newObject.zoneName = input.readString();
        //newObject.complexity = kryo.readObject(input, AutoDeltaFloat.class);

//        newObject.complexity.set(input.readFloat());
//        newObject.nameStringId.set(kryo.readObject(input, StringId.class));
//        newObject.objectName.set(kryo.readObject(input, UnicodeString.class));
//        newObject.volume.set(input.readInt());
//        newObject.appearanceData.set(input.readString());
//
//        int setSize = input.readInt();
//        Set<Integer> newSet = new TreeSet<>();
//        for(int i = 0; i < setSize; ++i) {
//            newObject.components.insert(input.readInt());
//        }

        return newObject;
    }
}
