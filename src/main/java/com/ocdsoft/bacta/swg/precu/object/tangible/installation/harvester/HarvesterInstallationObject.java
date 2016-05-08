package com.ocdsoft.bacta.swg.precu.object.tangible.installation.harvester;

import com.ocdsoft.bacta.swg.precu.object.tangible.installation.InstallationObject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerHarvesterInstallationObjectTemplate;

/**
 * Created by crush on 5/7/2016.
 */
public class HarvesterInstallationObject extends InstallationObject {

    private float installedEfficiency;
    private long resourceType;
    private int maxExtractionRate;
    private float currentExtractionRate;
    private int maxHopperAmount;
    private long hopperResource;
    private float hopperAmount;

    public HarvesterInstallationObject(final ServerHarvesterInstallationObjectTemplate template) {
        super(template);
    }
}
