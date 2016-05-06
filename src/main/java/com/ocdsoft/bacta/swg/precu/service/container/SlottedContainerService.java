package com.ocdsoft.bacta.swg.precu.service.container;

import com.ocdsoft.bacta.engine.utils.ReflectionUtil;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.shared.container.*;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import gnu.trove.list.TIntList;
import gnu.trove.map.TIntIntMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by crush on 5/3/2016.
 */
public class SlottedContainerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlottedContainerService.class);

    private static final Field slotMapField = ReflectionUtil.getFieldOrNull(SlottedContainer.class, "slotMap");

    private final ContainerService containerService;
    private final SlotIdManager slotIdManager;

    public SlottedContainerService(final ContainerService containerService,
                                   final SlotIdManager slotIdManager) {
        this.containerService = containerService;
        this.slotIdManager = slotIdManager;
    }

    public boolean isContentItemObservedWith(final SlottedContainer container,
                                             final GameObject item) {
        //-- Rule 1: if base container claims that the item is visible with the container,
        //   we stick with that.  This prevents us from changing any existing behavior at
        //   the time this code is written.
        final boolean observedWithBaseContainer = containerService.isContentItemObservedWith(container, item);

        if (observedWithBaseContainer)
            return true;

        //-- Rule 2: if the item is in this container, check if any of the current arrangement's
        //   slots have the observeWithParent attribute set.  If so, return true, if not, return false.
        final ContainedByProperty containedByProperty = item.getContainedByProperty();

        if (containedByProperty == null)
            return false;

        final SlottedContainmentProperty slottedContainmentProperty = item.getProperty(SlottedContainmentProperty.getClassPropertyId());

        if (slottedContainmentProperty == null)
            return false;

        int arrangementIndex = slottedContainmentProperty.getCurrentArrangement();

        // Note: when checking if item is in container, we must also check
        // that contained item's arrangement is set to a valid arrangement.
        // This function can be called during container transfers prior to
        // the arrangementIndex being set.  When this occurs, handle this
        // case as if the item is not in the container because it's not really
        // there in its entirety yet.

        final GameObject containedByObject = containedByProperty.getContainedBy();
        final boolean isInThisContainer = containedByObject == container.getOwner() && arrangementIndex >= 0;

        if (isInThisContainer) {
            final TIntList slots = slottedContainmentProperty.getSlotArrangement(arrangementIndex);

            for (int i = 0; i < slots.size(); ++i) {
                final boolean observeWithParent = slotIdManager.getSlotObserveWithParent(slots.get(i));

                if (observeWithParent)
                    return true;
            }

            return false;
        }

        //-- Rule 3: if the item is not in this container, determine which arrangement it would
        //   use if it went in this slot.  If no arrangement is valid, return false.  If an arrangement
        //   is valid, check each slot in the arrangement.  If any slot has observeWithParent set true,
        //   return true; otherwise, return false.
        final ContainerTransferSession<ServerObject> session = new PreCuContainerTransferSession(null, null, null);

        arrangementIndex = getFirstUnoccupiedArrangement(container, item, session);

        if (arrangementIndex == -1 || session.getErrorCode() != ContainerErrorCode.SUCCESS)
            return false;

        final TIntList slots = slottedContainmentProperty.getSlotArrangement(arrangementIndex);

        for (int i = 0; i < slots.size(); ++i) {
            final boolean observeWithParent = slotIdManager.getSlotObserveWithParent(slots.get(i));

            if (observeWithParent)
                return true;
        }

        return false;
    }

    public boolean isContentItemExposedWith(final SlottedContainer container,
                                            final GameObject item) {
        //-- Rule 1: if base container claims that the item is visible with the container,
        //   we stick with that.  This prevents us from changing any existing behavior at
        //   the time this code is written.
        final boolean exposedWithBaseContainer = containerService.isContentItemExposedWith(container, item);

        if (exposedWithBaseContainer)
            return true;

        //-- Rule 2: if the item is in this container, check if any of the current arrangement's
        //   slots have the exposeWithParent attribute set.  If so, return true, if not, return false.
        final ContainedByProperty containedByProperty = item.getContainedByProperty();

        if (containedByProperty == null)
            return false;

        final SlottedContainmentProperty slottedContainmentProperty = item.getProperty(SlottedContainmentProperty.getClassPropertyId());

        if (slottedContainmentProperty == null)
            return false;

        int arrangementIndex = slottedContainmentProperty.getCurrentArrangement();

        // Note: when checking if item is in container, we must also check
        // that contained item's arrangement is set to a valid arrangement.
        // This function can be called during container transfers prior to
        // the arrangementIndex being set.  When this occurs, handle this
        // case as if the item is not in the container because it's not really
        // there in its entirety yet.

        final GameObject containedByObject = containedByProperty.getContainedBy();
        final boolean isInThisContainer = (containedByObject == container.getOwner()) && (arrangementIndex >= 0);

        if (isInThisContainer) {
            final TIntList slots = slottedContainmentProperty.getSlotArrangement(arrangementIndex);

            for (int i = 0; i < slots.size(); ++i) {
                final boolean exposeWithParent = slotIdManager.getSlotExposeWithParent(slots.get(i));
                if (exposeWithParent)
                    return true;
            }

            return false;
        }

        //-- Rule 3: if the item is not in this container, determine which arrangement it would
        //   use if it went in this slot.  If no arrangement is valid, return false.  If an arrangement
        //   is valid, check each slot in the arrangement.  If any slot has exposeWithParent set true,
        //   return true; otherwise, return false.
        final ContainerTransferSession<ServerObject> session = new PreCuContainerTransferSession(null, null, null);

        arrangementIndex = getFirstUnoccupiedArrangement(container, item, session);

        if (arrangementIndex == -1 || (session.getErrorCode() != ContainerErrorCode.SUCCESS))
            return false;

        final TIntList slots = slottedContainmentProperty.getSlotArrangement(arrangementIndex);

        for (int i = 0; i < slots.size(); ++i) {
            final boolean exposeWithParent = slotIdManager.getSlotExposeWithParent(slots.get(i));
            if (exposeWithParent)
                return true;
        }

        return false;
    }

    public boolean canContentsBeObservedWith(final SlottedContainer container) {
        // Content items can be observed with this container if any of our slots are marked for observe with parent.
        final TIntIntMap slotMap = ReflectionUtil.getFieldValue(slotMapField, container);

        for (int slotId : slotMap.keys()) {
            if (slotIdManager.getSlotObserveWithParent(slotId))
                return true;
        }

        return false;
    }

    public boolean add(final SlottedContainer container,
                       final GameObject item,
                       final int arrangementIndex,
                       final ContainerTransferSession<ServerObject> session) {
        return false;
    }

    public boolean addToSlot(final SlottedContainer container,
                             final GameObject item,
                             final int slotId,
                             final ContainerTransferSession<ServerObject> session) {
        return false;
    }

    public GameObject getObjectInSlot(final SlottedContainer container,
                                      final int slotId,
                                      final ContainerTransferSession<ServerObject> session) {
        return null;
    }

    public List<GameObject> getObjectsForCombatBone(final SlottedContainer container,
                                                    final int bone) {
        return null;
    }

    public int getFirstUnoccupiedArrangement(final SlottedContainer container,
                                             final GameObject item,
                                             final ContainerTransferSession<ServerObject> session) {
        return -1;
    }

    public TIntList getValidArrangements(final SlottedContainer container,
                                         final GameObject item,
                                         final ContainerTransferSession<ServerObject> session) {
        return getValidArrangements(container, item, session, false, false);
    }

    public TIntList getValidArrangements(final SlottedContainer container,
                                         final GameObject item,
                                         final ContainerTransferSession<ServerObject> session,
                                         final boolean returnOnFirst) {
        return getValidArrangements(container, item, session, returnOnFirst, false);
    }

    public TIntList getValidArrangements(final SlottedContainer container,
                                         final GameObject item,
                                         final ContainerTransferSession<ServerObject> session,
                                         final boolean returnOnFirst,
                                         final boolean unoccupiedArrangementsOnly) {
        return null;
    }

    public boolean hasSlot(final SlottedContainer container, final int slotId) {
        return false;
    }

    public boolean isSlotEmpty(final SlottedContainer container, final int slotId, final ContainerTransferSession<ServerObject> session) {
        return false;
    }

    public boolean mayAdd(final SlottedContainer container, final GameObject item, final ContainerTransferSession<ServerObject> session) {
        return false;
    }

    public boolean mayAdd(final SlottedContainer container, final GameObject item, final int arrangementIndex, final ContainerTransferSession<ServerObject> session) {
        return false;
    }

    public boolean mayAddToSlot(final SlottedContainer container, final GameObject item, final int slotId, final ContainerTransferSession<ServerObject> session) {
        return false;
    }

    public boolean remove(final SlottedContainer container, final GameObject item, final ContainerTransferSession<ServerObject> session) {
        return false;
    }

    public boolean remove(final SlottedContainer container, final int position, final ContainerTransferSession<ServerObject> session) {
        return false;
    }

    public void removeItemFromSlotOnly(final SlottedContainer container, final GameObject item) {

    }

    public TIntList getSlotIdList(final SlottedContainer container) {
        return null;
    }

    public int findFirstSlotIdForObject(final SlottedContainer container, final GameObject item) {
        return -1;
    }

}
