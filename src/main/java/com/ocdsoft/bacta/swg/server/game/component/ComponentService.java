package com.ocdsoft.bacta.swg.server.game.component;

import com.google.inject.Singleton;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 9/5/2014.
 */
@Singleton
public class ComponentService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private TIntObjectMap<ExtensibleComponent> objectComponents = new TIntObjectHashMap<>();
}
