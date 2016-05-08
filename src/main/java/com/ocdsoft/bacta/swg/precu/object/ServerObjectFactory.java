package com.ocdsoft.bacta.swg.precu.object;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.precu.container.IntangibleVolumeContainer;
import com.ocdsoft.bacta.swg.precu.container.TangibleVolumeContainer;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.precu.object.template.shared.SharedObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.*;
import com.ocdsoft.bacta.swg.shared.portal.PortalProperty;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/6/2016.
 */
@Singleton
public final class ServerObjectFactory implements PreCuObjectFactory<ServerObject, ServerObjectTemplate> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerObjectFactory.class);

    private final ObjectTemplateList objectTemplateList;
    private final SlotIdManager slotIdManager;

    @Inject
    public ServerObjectFactory(final ObjectTemplateList objectTemplateList,
                               final SlotIdManager slotIdManager) {
        this.objectTemplateList = objectTemplateList;
        this.slotIdManager = slotIdManager;
    }

    @Override
    public ServerObject create(final ServerObjectTemplate template) {
        throw new UnsupportedOperationException("Cannot create a ServerObject because it is an abstract base class.");
    }

    @Override
    public void initialize(final ServerObject object, final ServerObjectTemplate template) {
        final String sharedTemplateName = template.getSharedTemplate();
        object.sharedTemplate = objectTemplateList.fetch(sharedTemplateName);

        if (object.sharedTemplate == null && !sharedTemplateName.isEmpty()) {
            LOGGER.warn("Template {} has an invalid shared template {}. We will use the default shared template for now.",
                    template.getResourceName(),
                    sharedTemplateName);
        }

        if (object.getSharedTemplate() != null) {
            //object.nameStringId.set(object.getSharedTemplate().getObjectName());
            //object.descriptionStringId.set(object.getSharedTemplate().getDetailedDescription());
        }

        final ContainedByProperty containedBy = new ContainedByProperty(object, null);
        object.addProperty(containedBy);

        final SlottedContainmentProperty slottedProperty = new SlottedContainmentProperty(object, slotIdManager);
        object.addProperty(slottedProperty);

        if (object.getSharedTemplate() != null) {
            final ArrangementDescriptor arrangementDescriptor = object.getSharedTemplate().getArrangementDescriptor();

            if (arrangementDescriptor != null) {
                final int arrangementCount = arrangementDescriptor.getArrangementCount();

                for (int i = 0; i < arrangementCount; ++i)
                    slottedProperty.addArrangement(arrangementDescriptor.getArrangement(i));
            }
        }

        if (object.getSharedTemplate() != null) {
            final SharedObjectTemplate.ContainerType containerType = object.getSharedTemplate().getContainerType();

            switch (containerType) {
                case CT_none:
                    break;

                case CT_slotted:
                case CT_ridable: {
                    final SlotDescriptor slotDescriptor = object.getSharedTemplate().getSlotDescriptor();

                    if (slotDescriptor != null) {
                        final SlottedContainer slottedContainer = new SlottedContainer(object, slotDescriptor.getSlots());
                        object.addProperty(slottedContainer);
                    }
                    break;
                }
                case CT_volume: {
                    int maxVolume = object.getSharedTemplate().getContainerVolumeLimit();

                    if (maxVolume <= 0)
                        maxVolume = VolumeContainer.NO_VOLUME_LIMIT;

                    final VolumeContainer volumeContainer = new TangibleVolumeContainer(object, maxVolume);
                    object.addProperty(volumeContainer);
                    break;
                }
                case CT_volumeIntangible: {
                    int maxVolume = object.getSharedTemplate().getContainerVolumeLimit();

                    if (maxVolume <= 0)
                        maxVolume = VolumeContainer.NO_VOLUME_LIMIT;

                    final VolumeContainer volumeContainer = new IntangibleVolumeContainer(object, maxVolume);
                    object.addProperty(volumeContainer);
                    break;
                }
                case CT_volumeGeneric: {
                    int maxVolume = object.getSharedTemplate().getContainerVolumeLimit();

                    if (maxVolume <= 0)
                        maxVolume = VolumeContainer.NO_VOLUME_LIMIT;

                    final VolumeContainer volumeContainer = new VolumeContainer(object, maxVolume);
                    object.addProperty(volumeContainer);
                    break;
                }
                default:
                    LOGGER.warn("Invalid container type specified.");
                    break;
            }

            object.setLocalFlag(ServerObject.LocalObjectFlags.SEND_TO_CLIENT, object.getSharedTemplate().getSendToClient());
            object.gameObjectType = (int) object.getSharedTemplate().getGameObjectType().value;
        }

        int vol = template.getVolume();
        final VolumeContainmentProperty volumeProperty = new VolumeContainmentProperty(object, vol < 1 ? 1 : vol);
        object.addProperty(volumeProperty);

        if (object.getSharedTemplate() != null) {
            final String portalLayoutFileName = object.getSharedTemplate().getPortalLayoutFilename();

            if (!portalLayoutFileName.isEmpty()) {
                final PortalProperty portalProperty = new PortalProperty(object, portalLayoutFileName);
                object.addProperty(portalProperty);
            }
        }

        //triggerVolumeInfo.setOnChange(this, &ServerObject::updateTriggerVolumes);

        //addMembersToPackages();
    }
}
