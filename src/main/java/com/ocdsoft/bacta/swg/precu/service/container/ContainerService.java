package com.ocdsoft.bacta.swg.precu.service.container;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.swg.precu.message.scene.UpdateContainmentMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.service.object.SceneObjectService;
import com.ocdsoft.bacta.swg.shared.container.Container;
import com.ocdsoft.bacta.swg.shared.container.ContainerErrorCode;
import com.ocdsoft.bacta.swg.shared.container.SlottedContainer;
import com.ocdsoft.bacta.swg.shared.container.VolumeContainer;
import com.ocdsoft.bacta.swg.shared.object.template.SharedObjectTemplate;
import com.ocdsoft.bacta.swg.shared.slot.*;
import gnu.trove.list.TIntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by crush on 8/25/2014.
 */
@Singleton
public class ContainerService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final SlotIdManager slotIdManager;
    private final SlotDescriptorList slotDescriptorList;
    private final ArrangementDescriptorList arrangementDescriptorList;
    private final boolean containerLoopChecking;
    private final int containerMaxDepth;

    @Inject
    private SceneObjectService sceneObjectService;

    private Field containerField;

    @Inject
    public ContainerService(SlotIdManager slotIdManager,
                            SlotDescriptorList slotDescriptorList,
                            ArrangementDescriptorList arrangementDescriptorList,
                            BactaConfiguration configuration) {
        this.slotIdManager = slotIdManager;
        this.slotDescriptorList = slotDescriptorList;
        this.arrangementDescriptorList = arrangementDescriptorList;

        this.containerLoopChecking = configuration.getBooleanLastWithDefault(
                "SharedObject",
                "containerLoopChecking",
                true);

        this.containerMaxDepth = configuration.getIntWithDefault(
                "SharedObject",
                "containerMaxDepth",
                9);

        //Setup the container field so that it can easily be found.
        try {
            this.containerField = SceneObject.class.getDeclaredField("container");
            this.containerField.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            throw new AssertionError("Container service cannot function because it was unable to find the container field.");
        }
    }

    public void createObjectContainer(SceneObject object) {
        try {
            SharedObjectTemplate shot = (SharedObjectTemplate) object.getObjectTemplate();

            switch (shot.getContainerType()) {
                case 1:
                case 5:
                    SlotDescriptor slotDescriptor = shot.getSlotDescriptor();

                    if (slotDescriptor != null) {
                        TIntList slots = slotDescriptor.getSlots();

                        containerField.set(object, new SlottedContainer(object, slots));
                        logger.trace("Creating a slotted container for object <{}> with template <{}> and <{}> slots.",
                                object.getNetworkId(),
                                shot.getName(),
                                slots.size());
                    }
                    break;
                case 2:
                case 3:
                case 4:
                    int volumeLimit = shot.getContainerVolumeLimit();
                    containerField.set(object, new VolumeContainer(object, volumeLimit));
                    logger.trace("Creating a volume container for object <{}> with template >{}> and a volume limit of <{}>.",
                            object.getNetworkId(),
                            shot.getName(),
                            volumeLimit);
                    break;
                case 0:
                    logger.trace("Object <{}> with template <{}> doesn't have a container because its container type is 0.",
                            object.getNetworkId(),
                            shot.getName());
                    break;
                default:
                    logger.error("Invalid container type <{}> specified for object <{}> with template <{}>.",
                            shot.getContainerType(),
                            object.getNetworkId(),
                            shot.getName());
                    break;
            }
        } catch (IllegalAccessException ex) {
            logger.error("Unable to access the container field for object <{}>.",
                    object.getNetworkId());
        }
    }

    /**
     * Attempts to get an object from a slotted container by slot name.
     * @param container The object that contains the slotted item.
     * @param slotName The name of the slot to get the object from.
     * @param <T> The type to cast the object to.
     * @return Returns null if the container isn't slotted, or doesn't contain an object in the specified slot.
     */
    @SuppressWarnings("unchecked")
    public <T> T getSlottedObject(SceneObject container, final String slotName) {
        try {
            Container actualContainer = (Container) containerField.get(container);

            if (actualContainer == null || !actualContainer.isSlottedContainer())
                return null;

            SlottedContainer slottedContainer = (SlottedContainer) actualContainer;

            int slotId = slotIdManager.findSlotId(slotName);

            if (SlotIdManager.isInvalidSlot(slotId) || !slottedContainer.containsSlot(slotId))
                return null;

            return (T) slottedContainer.getObjectInSlot(slotId);
        } catch (IllegalAccessException ex) {
            logger.error("Unable to access the container field for object <{}>.",
                    container.getNetworkId());
            return null;
        }
    }

    public final int mayAdd(SceneObject container, SceneObject item) {
        try {
            //Are the item and the container the same object?
            if (item == container)
                return ContainerErrorCode.AddSelf;

            Container actualContainer = (Container) containerField.get(container);

            //Check if the container contains the item somewhere in the object graph.
            //Check if the item contains the container somewhere in the object graph.

            return ContainerErrorCode.Success;
        } catch (IllegalAccessException ex) {
            logger.error("Unable to access the container field for object <{}>.",
                    container.getNetworkId());
            return ContainerErrorCode.Unknown;
        }
    }

    public final int checkDepth(SceneObject object) {
        SceneObject parent;
        int depth = 0;

        while ((parent = sceneObjectService.get(object.getContainedBy())) != null)
            ++depth;

        return depth;
    }

    public final int removeFromContainer(SceneObject item) {
        SceneObject container = sceneObjectService.get(item.getContainedBy());

        if (container == null)
            return ContainerErrorCode.NoContainer;

        try {
            Container actualContainer = (Container) containerField.get(container);
            actualContainer.remove(item);

            item.setContainedBy(0);
            item.setCurrentArrangement(-1); //Not slotted in any arrangement

            return ContainerErrorCode.Success;
        } catch (IllegalAccessException ex) {
            logger.error("Unable to access the container field for object <{}>.",
                    container.getNetworkId());
            return ContainerErrorCode.Unknown;
        }
    }

    public final int transferItemToContainer(SceneObject container, SceneObject item) {
        try {
            Container<SceneObject> actualContainer = (Container) containerField.get(container);

            logger.debug("Transferring object <{}> with template <{}> to container of item <{}> with template <{}>.",
                    item.getNetworkId(),
                    item.getObjectTemplate().getName(),
                    container.getNetworkId(),
                    container.getObjectTemplate().getName());

            //Make sure we can add the item to the container.
            int error = mayAdd(container, item);

            if (error != ContainerErrorCode.Success)
                return error;

            //Remove it from its current container
            error = removeFromContainer(item);

            if (actualContainer.isSlottedContainer())
                error = transferItemToSlottedContainer((SlottedContainer) actualContainer, item);

            if (actualContainer.isVolumeContainer())
                error = transferItemToVolumeContainer((VolumeContainer) actualContainer, item);

            if (error == ContainerErrorCode.Success) {
                logger.debug("Sending update containment message");

                UpdateContainmentMessage updateContainmentMessage = new UpdateContainmentMessage(item);
                container.broadcastMessage(updateContainmentMessage);
            }

            return error;
        } catch (IllegalAccessException ex) {
            logger.error("Unable to access the container field for object <{}>.",
                    container.getNetworkId());
            return ContainerErrorCode.Unknown;
        }
    }

    private final int transferItemToSlottedContainer(SlottedContainer<SceneObject> container, SceneObject item) {
        SharedObjectTemplate shot = (SharedObjectTemplate) item.getObjectTemplate();

        if (shot != null) {
            ArrangementDescriptor arrangementDescriptor = arrangementDescriptorList.fetch(shot.getArrangementDescriptorFilename());
            int arrangementIndex = -1;

            for (int i = 0; i < arrangementDescriptor.getArrangementCount(); i++) {
                TIntList arrangement = arrangementDescriptor.getArrangement(i);

                for (int j = 0; j < arrangement.size(); j++) {
                    int slotId = arrangement.get(j);

                    if (container.isSlotEmpty(slotId)) {
                        logger.trace("Slotting item <{}> with template <{}> into slot <{}:{}>.",
                                item.getNetworkId(),
                                item.getObjectTemplate().getName(),
                                slotIdManager.getSlotName(slotId),
                                slotId);
                        arrangementIndex = i;
                        item.setContainedBy(container.getOwner().getNetworkId());
                        item.setCurrentArrangement(arrangementIndex);
                        container.add(slotId, item);
                        break;
                    }
                }

                if (arrangementIndex != -1)
                    break;
            }
        }

        return ContainerErrorCode.Success;
    }

    private final int transferItemToVolumeContainer(VolumeContainer container, SceneObject item) {

        //TODO Check volume limits and stuff.

        item.setCurrentArrangement(-1);
        container.add(item);

        return ContainerErrorCode.Success;
    }

    public final boolean transferItemToWorld(SceneObject item) {
        return false;
    }

    public final int getTotalVolumeLimitedByParents(VolumeContainer volumeContainer) {
        int totalVolume = volumeContainer.getTotalVolume();

        /*
        if (totalVolume > 0) {
            if (volumeContainer.getOwner() != null) {
                SceneObject parent = sceneObjectService.get(volumeContainer.getOwner().getParent());

                if (parent != null) {
                    Container container = parent.getContainer();

                    if (container != null && container.isVolumeContainer()) {
                        VolumeContainer parentContainer = (VolumeContainer) container;

                        int parentTotalVolume = parentContainer.getTotalVolume();

                        if (parentTotalVolume <= 0) {

                        } else {
                            int remainingInParent = parentTotalVolume - parentContainer.getCurrentVolume();
                        }
                    }
                }
            }
        }*/

        return totalVolume;
    }
}
