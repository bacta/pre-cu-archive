package com.ocdsoft.bacta.swg.server.game.object.tangible.factory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.object.NetworkIdGenerator;
import com.ocdsoft.bacta.engine.object.NetworkObject;
import com.ocdsoft.bacta.engine.service.objectfactory.NetworkObjectFactory;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kburkhardt on 2/24/14.
 */
@Singleton
public class GuiceNetworkObjectFactory implements NetworkObjectFactory<ServerObjectTemplate> {

    private final ObjectTemplateList objectTemplateList;
    private final SlotIdManager slotIdManager;
    private final NetworkIdGenerator idGenerator;
    private final Map<Class, Constructor> constructorMap;

    @Inject
    public GuiceNetworkObjectFactory(final ObjectTemplateList objectTemplateList,
                                     final SlotIdManager slotIdManager,
                                     final NetworkIdGenerator idGenerator) {
        this.objectTemplateList = objectTemplateList;
        this.slotIdManager = slotIdManager;
        this.idGenerator = idGenerator;
        constructorMap = new HashMap<>();
    }

    @Override
    public <T extends NetworkObject> T createNetworkObject(Class<T> clazz, ServerObjectTemplate template) {

        Constructor<T> constructor = constructorMap.get(clazz);
        if(constructor == null) {
            try {
                constructor = clazz.getConstructor(ObjectTemplateList.class, SlotIdManager.class, ServerObjectTemplate.class);
                constructorMap.put(clazz, constructor);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        T newObject = null;
        try {
            newObject = constructor.newInstance(objectTemplateList, slotIdManager, template);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        newObject.setNetworkId(idGenerator.next() );
        return newObject;
    }
}
