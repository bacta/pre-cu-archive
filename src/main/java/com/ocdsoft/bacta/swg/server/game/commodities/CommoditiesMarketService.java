package com.ocdsoft.bacta.swg.server.game.commodities;

import com.google.inject.Singleton;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by crush on 5/28/2016.
 */
@Singleton
public class CommoditiesMarketService {

    private final AtomicInteger itemTypeMapVersionNumber;
    private final AtomicInteger resourceTypeMapVersionNumber;

    @Getter
    private final ItemTypeMap itemTypeMap;

    @Getter
    private final ResourceTypeMap resourceTypeMap;

    public CommoditiesMarketService() {
        itemTypeMap = new ItemTypeMap();
        resourceTypeMap = new ResourceTypeMap();

        this.itemTypeMapVersionNumber = new AtomicInteger(0);
        this.resourceTypeMapVersionNumber = new AtomicInteger(0);
    }

    public int getItemTypeMapVersionNumber() {
        return itemTypeMapVersionNumber.get();
    }

    public int getResourceTypeMapVersionNumber() {
        return resourceTypeMapVersionNumber.get();
    }
}
