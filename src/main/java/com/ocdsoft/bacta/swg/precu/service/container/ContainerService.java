package com.ocdsoft.bacta.swg.precu.service.container;

import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.utils.ReflectionUtil;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.shared.container.ContainedByProperty;
import com.ocdsoft.bacta.swg.shared.container.Container;
import com.ocdsoft.bacta.swg.shared.container.ContainerErrorCode;
import com.ocdsoft.bacta.swg.shared.container.ContainerTransferSession;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crush on 5/3/2016.
 * <p>
 * Used for making transfers with a Container object directly. Please go through the
 * {@link ContainerTransferService} if making transfers.
 */
public class ContainerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerService.class);

    static final Field changedField = ReflectionUtil.getFieldOrNull(Container.class, "changed");
    static final Field contentsField = ReflectionUtil.getFieldOrNull(Container.class, "contents");

    private final boolean loopChecking;
    private final int maxDepth;

    public ContainerService(final BactaConfiguration configuration) {
        loopChecking = configuration.getBooleanWithDefault("SharedObject", "containerLoopChecking", true);
        maxDepth = configuration.getIntWithDefault("SharedObject", "containerMaxDepth", 9);
    }

    public boolean isContentItemObservedWith(final Container container, final GameObject item) {
        return false;
    }

    public boolean isContentItemExposedWith(final Container container, final GameObject item) {
        return false;
    }

    public boolean canContentsBeObservedWith(final Container container) {
        return false;
    }

    public boolean mayAdd(final Container container, final GameObject item, final ContainerTransferSession<ServerObject> session) {
        session.setErrorCode(ContainerErrorCode.SUCCESS);

        if (item == container.getOwner()) {
            session.setErrorCode(ContainerErrorCode.ADD_SELF);
            return false;
        }

        if (loopChecking) {
            final Container containerToCheck = item.getContainerProperty();

            if (containerToCheck != null) {
                ContainedByProperty cbIter = container.getOwner().getContainedByProperty();

                if (cbIter != null) {
                    GameObject iterObject = cbIter.getContainedBy();

                    if (iterObject != null) {
                        final List<GameObject> checkList = new ArrayList<>(10);
                        checkList.add(container.getOwner());

                        for (int count = 0; iterObject != null; ++count) {
                            if (count > maxDepth) {
                                LOGGER.warn("Too deep a container hierarchy.");
                                session.setErrorCode(ContainerErrorCode.TOO_DEEP);
                                return false;
                            }

                            checkList.add(iterObject);
                            final GameObject obj = cbIter.getContainedBy();
                            iterObject = null;

                            if (obj != null) {
                                cbIter = obj.getContainedByProperty();

                                if (cbIter != null)
                                    iterObject = cbIter.getContainedBy();
                            }
                        }

                        if (checkList.contains(item)) {
                            LOGGER.warn("Adding item {} to {} would have introduced a container loop.",
                                    item.getNetworkId(),
                                    container.getOwner().getNetworkId());
                            session.setErrorCode(ContainerErrorCode.ALREADY_IN);
                            return false;
                        }
                    }
                }
            }
        }

        if (checkDepth(container) > maxDepth) {
            session.setErrorCode(ContainerErrorCode.TOO_DEEP);
            return false;
        }

        return true;
    }

    public boolean remove(final Container container, final GameObject item, final ContainerTransferSession<ServerObject> session) {
        session.setErrorCode(ContainerErrorCode.UNKNOWN);

        boolean returnValue = false;

        ContainedByProperty property = item.getContainedByProperty();

        if (property != null) {
            if (property.getContainedBy() != container.getOwner()) {
                LOGGER.warn("Cannot remove an item [{}] from container [{}] whose containedBy says it isn't in this container.",
                        item.getNetworkId(),
                        container.getOwner().getNetworkId());
                session.setErrorCode(ContainerErrorCode.NOT_FOUND);
                return false;
            }

            final List<GameObject> contents = ReflectionUtil.getFieldValue(contentsField, container);
            returnValue = contents.remove(item);

            if (returnValue) {
                session.setErrorCode(ContainerErrorCode.SUCCESS);
                property.setContainedBy(null);
            }
        }

        return returnValue;
    }

    public boolean remove(final Container container, final int position, final ContainerTransferSession<ServerObject> session) {
        session.setErrorCode(ContainerErrorCode.SUCCESS);

        final List<GameObject> contents = ReflectionUtil.getFieldValue(contentsField, container);
        final GameObject obj = contents.get(position);

        if (obj != null) {
            ContainedByProperty property = obj.getContainedByProperty();

            if (property != null) {
                if (property.getContainedBy() != container.getOwner()) {
                    LOGGER.warn("Cannot remove an item [{}] from container [{}] whose containedBy says it isn't in this container.",
                            obj.getNetworkId(),
                            container.getOwner().getNetworkId());
                    session.setErrorCode(ContainerErrorCode.NOT_FOUND);
                    return false;
                }
                property.setContainedBy(null);
            }

            contents.remove(position);
            return true;
        }

        session.setErrorCode(ContainerErrorCode.UNKNOWN);
        return false;
    }

    protected int addToContents(final Container container, final GameObject item, final ContainerTransferSession<ServerObject> session) {
        session.setErrorCode(ContainerErrorCode.SUCCESS);

        if (!mayAdd(container, item, session))
            return -1;

        ContainedByProperty containedItem = item.getContainedByProperty();

        assert containedItem != null : "Cannot add an item with no containedByProperty to a container.";

        if (containedItem.getContainedBy() != null && containedItem.getContainedBy() != container.getOwner()) {
            LOGGER.warn("Cannot add an item [{}] to a container [{}] when it is already contained!",
                    item.getNetworkId(),
                    container.getOwner().getNetworkId());
            session.setErrorCode(ContainerErrorCode.ALREADY_IN);
            return -1;
        }

        if (loopChecking) {
            final Container containerToCheck = item.getContainerProperty();

            if (containerToCheck != null) {
                ContainedByProperty cbIter = container.getOwner().getContainedByProperty();

                if (cbIter != null) {
                    GameObject iterObject = cbIter.getContainedBy();

                    if (iterObject != null) {
                        final List<GameObject> checkList = new ArrayList<>(10);
                        checkList.add(container.getOwner());

                        for (int count = 0; iterObject != null; ++count) {
                            if (count > maxDepth) {
                                LOGGER.warn("Too deep a container hierarchy.");
                                session.setErrorCode(ContainerErrorCode.TOO_DEEP);
                                return -1;
                            }

                            checkList.add(iterObject);
                            final GameObject obj = cbIter.getContainedBy();
                            iterObject = null;

                            if (obj != null) {
                                cbIter = obj.getContainedByProperty();

                                if (cbIter != null)
                                    iterObject = cbIter.getContainedBy();
                            }
                        }

                        if (checkList.contains(item)) {
                            LOGGER.warn("Adding item {} to {} would have introduced a container loop.",
                                    item.getNetworkId(),
                                    container.getOwner().getNetworkId());
                            session.setErrorCode(ContainerErrorCode.ALREADY_IN);
                            return -1;
                        }
                    }
                }
            }
        }

        if (checkDepth(container) > maxDepth) {
            session.setErrorCode(ContainerErrorCode.TOO_DEEP);
            return -1;
        }

        if (isItemContained(container, item, session)) {
            LOGGER.warn("Cannot add an item {} to a container {} when it is already in it! This shouldn't happen because the item's contained by property says it is not in this container, but it is in the container's internal list.",
                    item.getNetworkId(),
                    container.getOwner().getNetworkId());
            session.setErrorCode(ContainerErrorCode.ALREADY_IN);
            return -1;
        }

        containedItem.setContainedBy(container.getOwner());

        final List<GameObject> contents = ReflectionUtil.getFieldValue(contentsField, container);
        contents.add(item);

        return contents.size();
    }

    protected int find(final Container container, final GameObject item, final ContainerTransferSession<ServerObject> session) {
        session.setErrorCode(ContainerErrorCode.SUCCESS);

        final List<GameObject> contents = ReflectionUtil.getFieldValue(contentsField, container);
        int index = contents.indexOf(item);

        if (index == -1)
            session.setErrorCode(ContainerErrorCode.NOT_FOUND);

        return index;
    }

    protected GameObject getContents(final Container container, final int position) {
        final List<GameObject> contents = ReflectionUtil.getFieldValue(contentsField, container);
        return contents.get(position);
    }

    private boolean isItemContained(final Container container, final GameObject item, final ContainerTransferSession<ServerObject> session) {
        return find(container, item, session) != -1;
    }

    public static int checkDepth(final Container container) {
        int count = 0;
        ContainedByProperty parentContainedByProperty = container.getOwner().getContainedByProperty();

        if (parentContainedByProperty == null)
            return count;

        GameObject parentObject = parentContainedByProperty.getContainedBy();

        if (parentObject == null)
            return count;

        while (parentObject != null) {
            ++count;
            parentContainedByProperty = parentObject.getContainedByProperty();

            if (parentContainedByProperty == null)
                return count;

            parentObject = parentContainedByProperty.getContainedBy();
        }

        return count;
    }
}
