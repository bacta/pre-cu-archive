package com.ocdsoft.bacta.swg.precu.object;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.service.data.ObjectTemplateService;
import com.ocdsoft.bacta.swg.shared.object.template.ObjectTemplate;
import com.ocdsoft.network.data.serialization.NetworkObjectSerializer;
import com.ocdsoft.network.service.object.ObjectService;
import org.magnos.steer.vec.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Quat4f;

/**
 * Created by kburkhardt on 8/22/14.
 */
public class SceneObjectSerializer<T extends SceneObject> extends NetworkObjectSerializer<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ObjectTemplateService objectTemplateService;

    @Inject
    public SceneObjectSerializer(ObjectService objectService,
                                 ObjectTemplateService objectTemplateService) {
        super(objectService);

        this.objectTemplateService = objectTemplateService;
    }

    @Override
    public void write(Kryo kryo, Output output, T object) {
        super.write(kryo, output, object);

        output.writeString(object.getObjectTemplate().getName());
        output.writeLong(object.getContainedBy());
        kryo.writeObject(output, object.getOrientation());
        kryo.writeObject(output, object.getPosition());
    }

    @Override
    public T read(Kryo kryo, Input input, Class<T> type) {
        T newObject = super.read(kryo, input, type);

        ObjectTemplate objectTemplate = objectTemplateService.getObjectTemplate(input.readString());
        newObject.setObjectTemplate(objectTemplate);
        newObject.setContainedBy(input.readLong());
        newObject.setOrientation(kryo.readObject(input, Quat4f.class));
        newObject.setPosition(kryo.readObject(input, Vec3.class));

        return newObject;
    }
}
