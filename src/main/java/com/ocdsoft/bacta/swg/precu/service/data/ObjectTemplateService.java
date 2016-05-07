package com.ocdsoft.bacta.swg.precu.service.data;

import bacta.iff.Iff;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.lang.NotImplementedException;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
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

    private final TIntObjectMap<Class<? extends ServerObject>> templateClassMap = new TIntObjectHashMap<>();
    private final ObjectTemplateList objectTemplateList;

    @Inject
    public ObjectTemplateService(final ObjectTemplateList objectTemplateList) {

        this.objectTemplateList = objectTemplateList;

        load();
    }

    private void load() {
        try {
            //Registering all template definitions with the object template list.

            final Reflections reflections = new Reflections();
            //TODO: Move the name of the register function to the annotation.
            final Set<Class<?>> templateDefinitions = reflections.getTypesAnnotatedWith(TemplateDefinition.class);
            final Iterator<Class<?>> iterator = templateDefinitions.iterator();

            while (iterator.hasNext()) {
                final Class<?> classType = iterator.next();

                if (ObjectTemplate.class.isAssignableFrom(classType)) {
                    final Method registerMethod = classType.getDeclaredMethod("registerTemplateConstructors", DataResourceList.class);
                    registerMethod.setAccessible(true);
                    registerMethod.invoke(null, objectTemplateList);
                }
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
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
     * this is used in object creation.
     *
     * @param template template to get class object for
     * @return Class object related to the specified type
     * @throws NotImplementedException when template id is not mapped to a class
     */
    @SuppressWarnings("unchecked")
    public <T extends ServerObject> Class<T> getClassForTemplate(final ObjectTemplate template) {
        final Class<T> classType = (Class<T>) templateClassMap.get(template.getId());

        if (classType == null) {
            LOGGER.error("Template with class mapping: " + Iff.getChunkName(template.getId()));
            throw new NotImplementedException();
        }

        return classType;
    }
}
