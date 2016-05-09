package com.ocdsoft.bacta.swg.precu.service.object;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.data.GameDatabaseConnector;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.engine.service.objectfactory.NetworkObjectFactory;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.archive.OnDirtyCallbackBase;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.precu.service.data.ObjectTemplateService;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Kyle on 3/24/14.
 */
@Singleton
public final class ServerObjectService implements ObjectService<ServerObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerObjectService.class);

    private final TLongObjectMap<ServerObject> internalMap = new TLongObjectHashMap<>();

    private final Set<ServerObject> dirtyList = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private final NetworkObjectFactory networkObjectFactory;
    private final DeltaNetworkDispatcher deltaDispatcher;
    private final GameDatabaseConnector databaseConnector;
    private final ObjectTemplateService objectTemplateService;
    private final int deltaUpdateInterval;

    @Inject
    public ServerObjectService(final BactaConfiguration configuration,
                               final NetworkObjectFactory networkObjectFactory,
                               final GameDatabaseConnector databaseConnector,
                               final ObjectTemplateService objectTemplateService) {

        this.networkObjectFactory = networkObjectFactory;
        this.deltaUpdateInterval = configuration.getIntWithDefault("Bacta/GameServer", "DeltaUpdateInterval", 50);
        this.databaseConnector = databaseConnector;
        this.objectTemplateService = objectTemplateService;
        this.deltaDispatcher = new DeltaNetworkDispatcher();

        new Thread(deltaDispatcher).start();
    }

    @Override
    public <T extends ServerObject> T createObject(final long creator, final String templatePath) {
        final ServerObjectTemplate template = objectTemplateService.getObjectTemplate(templatePath);
        final Class<T> objectClass = objectTemplateService.getClassForTemplate(template);
        final T newObject = networkObjectFactory.createNetworkObject(objectClass);

        newObject.setOnDirtyCallback(new ServerObjectServiceOnDirtyCallback(newObject));

        internalMap.put(newObject.getNetworkId(), newObject);
        databaseConnector.createNetworkObject(newObject);

        return newObject;
    }

    @Override
    public <T extends ServerObject> T get(long key) {
        T object = (T) internalMap.get(key);

        if(object == null) {
            object = databaseConnector.getNetworkObject(key);
            if(object != null) {
                //ObjectTemplate template = objectTemplateService.getObjectTemplate(object.getTemplatePath());
                //object.setTemplate(template); //TODO: Fix this!!!!!!
                internalMap.put(key, object);
            }
        }

        return object;
    }

    @Override
    public <T extends ServerObject> T get(ServerObject requester, long key) {
        //TODO: Reimplement permissions.
        return get(key);
    }

    @Override
    public <T extends ServerObject> void updateObject(T object) {
        databaseConnector.updateNetworkObject(object);
    }


    // Executor?
    private class DeltaNetworkDispatcher implements Runnable {

        protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

        @Override
        public void run() {

            long nextIteration = 0;

            while(true) {
                try {
                    long currentTime = System.currentTimeMillis();

                    if (nextIteration > currentTime) {
                        Thread.sleep(nextIteration - currentTime);
                    }

                    for (ServerObject object : dirtyList) {
                        if (object.isInitialized())
                            object.sendDeltas();

                        object.clearDeltas();
                    }

                    dirtyList.clear();

                    nextIteration = currentTime + deltaUpdateInterval;

                } catch(Exception e) {
                    logger.error("UNKNOWN", e);
                }
            }
        }
    }

    private final class ServerObjectServiceOnDirtyCallback implements OnDirtyCallbackBase {
        private final ServerObject serverObject;

        public ServerObjectServiceOnDirtyCallback(final ServerObject serverObject) {
            this.serverObject = serverObject;
        }

        @Override
        public void onDirty() {
            dirtyList.add(serverObject);
        }

    }
}
