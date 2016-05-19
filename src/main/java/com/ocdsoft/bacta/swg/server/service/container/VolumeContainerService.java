package com.ocdsoft.bacta.swg.server.service.container;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.ReflectionUtil;
import com.ocdsoft.bacta.swg.shared.container.ContainedByProperty;
import com.ocdsoft.bacta.swg.shared.container.ContainerResult;
import com.ocdsoft.bacta.swg.shared.container.VolumeContainer;
import com.ocdsoft.bacta.swg.shared.container.VolumeContainmentProperty;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by crush on 5/3/2016.
 */
public class VolumeContainerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VolumeContainerService.class);

    private static final Field currentVolumeField = ReflectionUtil.getFieldOrNull(VolumeContainer.class, "currentVolume");
    private static final Field totalVolumeField = ReflectionUtil.getFieldOrNull(VolumeContainer.class, "totalVolumeField");

    private final ContainerService containerService;

    @Inject
    public VolumeContainerService(final ContainerService containerService) {
        this.containerService = containerService;
    }

    public boolean add(final VolumeContainer container, final GameObject item, final ContainerResult containerResult) {
        return add(container, item, containerResult, false);
    }

    public boolean add(final VolumeContainer container, final GameObject item, final ContainerResult containerResult, boolean allowOverloaded) {
        return false;
    }

    public boolean checkVolume(final VolumeContainer container, final int addedVolume) {
        return false;
    }

    public boolean mayAdd(final VolumeContainer volumeContainer, final GameObject item, final ContainerResult containerResult) {
        return false;
    }

    public boolean remove(final VolumeContainer container, final GameObject item, final ContainerResult containerResult) {
        return false;
    }

    private boolean checkVolume(final VolumeContainmentProperty property) {
        return false;
    }

    private boolean internalRemove(final VolumeContainer volumeContainer, final GameObject item, final VolumeContainmentProperty property) {
        return false;
    }

    private void insertNewItem(final VolumeContainer volumeContainer, final GameObject item, final VolumeContainmentProperty property) {
        final VolumeContainmentProperty prop = property != null ? property : item.getProperty(VolumeContainmentProperty.getClassPropertyId());

        if (prop == null) {
            LOGGER.warn("Item {} has no volume property.", item.getNetworkId());
            return;
        }

        final int totalVolume = ReflectionUtil.getFieldValue(totalVolumeField, volumeContainer);
        final int currentVolume = ReflectionUtil.getFieldValue(currentVolumeField, volumeContainer);

        if (totalVolume != VolumeContainer.NO_VOLUME_LIMIT)
            ReflectionUtil.setFieldValue(currentVolumeField, volumeContainer, currentVolume + prop.getVolume());
    }

    private void childVolumeChanged(final VolumeContainer volumeContainer, final int volume, final boolean updateParent) {
        final int totalVolume = ReflectionUtil.getFieldValue(totalVolumeField, volumeContainer);
        final int currentVolume = ReflectionUtil.getFieldValue(currentVolumeField, volumeContainer);

        if (totalVolume != VolumeContainer.NO_VOLUME_LIMIT)
            ReflectionUtil.setFieldValue(currentVolumeField, volumeContainer, currentVolume + volume);

        if (updateParent) {
            final VolumeContainer parent = getVolumeContainerParent(volumeContainer);

            if (parent != null)
                childVolumeChanged(parent, volume, true);
        }
    }

    private VolumeContainer getVolumeContainerParent(final VolumeContainer self) {
        final GameObject owner = self.getOwner();
        final ContainedByProperty containedByProperty = owner.getContainedByProperty();

        if (containedByProperty != null) {
            final GameObject parent = containedByProperty.getContainedBy();

            if (parent != null)
                return parent.getVolumeContainerProperty();
        }

        return null;
    }

}
