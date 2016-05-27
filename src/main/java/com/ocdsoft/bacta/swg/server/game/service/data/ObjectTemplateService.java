package com.ocdsoft.bacta.swg.server.game.service.data;

import bacta.iff.Iff;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.intangible.IntangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.object.template.server.*;
import com.ocdsoft.bacta.swg.shared.foundation.CrcString;
import com.ocdsoft.bacta.swg.shared.foundation.DataResourceList;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;
import com.ocdsoft.bacta.swg.shared.template.definition.TemplateDefinition;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by crush on 3/4/14.
 */
@Singleton
public class ObjectTemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectTemplateService.class);

    private final TIntObjectMap<Class<? extends ServerObject>> templateClassMap = new TIntObjectHashMap<>(100);
    private final ObjectTemplateList objectTemplateList;

    @Inject
    public ObjectTemplateService(final ObjectTemplateList objectTemplateList) {

        this.objectTemplateList = objectTemplateList;

        registerTemplates();
        configureTemplateClassMap();
    }

    private void registerTemplates() {
        try {
            //Registering all template definitions with the object template list.
            final Reflections reflections = new Reflections();
            //TODO: Move the name of the register function to the annotation.
            final Set<Class<?>> templateDefinitions = reflections.getTypesAnnotatedWith(TemplateDefinition.class);
            final Iterator<Class<?>> iterator = templateDefinitions.iterator();

            while (iterator.hasNext()) {
                final Class<?> classType = iterator.next();
                final String registerMethodName = classType.getAnnotation(TemplateDefinition.class).value();

                if (ObjectTemplate.class.isAssignableFrom(classType)) {
                    final Method registerMethod = classType.getDeclaredMethod(registerMethodName, DataResourceList.class);
                    registerMethod.setAccessible(true);
                    registerMethod.invoke(null, objectTemplateList);
                }
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    private void configureTemplateClassMap() {
        //TODO: Load with reflection instead.
        templateClassMap.put(ServerObjectTemplate.TAG_SERVEROBJECTTEMPLATE, ServerObject.class);
        templateClassMap.put(ServerTangibleObjectTemplate.TAG_SERVERTANGIBLEOBJECTTEMPLATE, TangibleObject.class);
        templateClassMap.put(ServerCreatureObjectTemplate.TAG_SERVERCREATUREOBJECTTEMPLATE, CreatureObject.class);
        templateClassMap.put(ServerIntangibleObjectTemplate.TAG_SERVERINTANGIBLEOBJECTTEMPLATE, IntangibleObject.class);
        templateClassMap.put(ServerPlayerObjectTemplate.TAG_SERVERPLAYEROBJECTTEMPLATE, PlayerObject.class);
    }

    public <T extends ObjectTemplate> T getObjectTemplate(final String templatePath) {
        return objectTemplateList.fetch(templatePath);
    }

    public <T extends ObjectTemplate> T getObjectTemplate(final CrcString templatePath) {
        return objectTemplateList.fetch(templatePath);
    }

    public <T extends ObjectTemplate> T getObjectTemplate(final int crc) {
        return objectTemplateList.fetch(crc);
    }

    /**
     * This gets the class associated with the provided template type
     * this is used in object player.
     *
     * @param template template to get class object for
     * @return Class object related to the specified type
     */
    @SuppressWarnings("unchecked")
    public <T extends ServerObject> Class<T> getClassForTemplate(final ObjectTemplate template) {
        final Class<T> classType = (Class<T>) templateClassMap.get(template.getId());

        if (classType == null)
            LOGGER.error("Did not find template with class mapping: " + Iff.getChunkName(template.getId()));

        return classType;
    }
}
