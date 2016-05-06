package com.ocdsoft.bacta.swg.precu.service.container;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.localization.StringId;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.service.chat.ChatService;
import com.ocdsoft.bacta.swg.precu.service.rewards.VeteranRewardService;
import com.ocdsoft.bacta.swg.shared.container.ContainerErrorCode;
import com.ocdsoft.bacta.swg.shared.container.ContainerTransferSession;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by crush on 4/28/2016.
 * <p>
 * All container transfers should go through this service rather than the containers methods themselves.
 */
@Singleton
public final class ContainerTransferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerTransferService.class);

    private final ChatService chatService;
    private final SlotIdManager slotIdManager;
    private final VeteranRewardService veteranRewardService;

    @Inject
    public ContainerTransferService(final ChatService chatService,
                                    final SlotIdManager slotIdManager,
                                    final VeteranRewardService veteranRewardService) {
        this.chatService = chatService;
        this.slotIdManager = slotIdManager;
        this.veteranRewardService = veteranRewardService;
    }

    public boolean canTransferTo(final ContainerTransferSession<ServerObject> session) {
        return false;
    }

    public boolean canTransferToSlot(final ContainerTransferSession<ServerObject> session, final int slotId) {
        return false;
    }

    public boolean transferItemToGeneralContainer(final ContainerTransferSession<ServerObject> session) {
        return transferItemToGeneralContainer(session, false);
    }

    public boolean transferItemToGeneralContainer(final ContainerTransferSession<ServerObject> session, final boolean allowOverloaded) {
        return false;
    }

    public boolean transferItemToSlottedContainer(final ContainerTransferSession<ServerObject> session, final int arrangementIndex) {
        return false;
    }

    public boolean transferItemToSlottedContainerSlotId(final ContainerTransferSession<ServerObject> session, final int slotId) {
        return false;
    }

    public boolean transferItemToUnknownContainer(final ContainerTransferSession<ServerObject> session, final int arrangementIndex, final Transform transform) {
        return false;
    }

    public boolean transferItemToCell(final ContainerTransferSession<ServerObject> session) {
        return false;
    }

    public boolean transferItemToCell(final ContainerTransferSession<ServerObject> session, final Transform transform) {
        return false;
    }

    public boolean transferItemToWorld(final ContainerTransferSession<ServerObject> session, final Transform transform) {
        return false;
    }

    public boolean canPlayerManipulateSlot(final int slotId) {
        return false;
    }

    public void sendContainerMessageToClient(final ServerObject player, final ContainerErrorCode errorCode) {
        sendContainerMessageToClient(player, errorCode, null);
    }

    public void sendContainerMessageToClient(final ServerObject player, final ContainerErrorCode errorCode, final ServerObject target) {
        final boolean sendMessage = errorCode != ContainerErrorCode.BLOCKED_BY_SCRIPT && errorCode != ContainerErrorCode.SILENT_ERROR;

        if (sendMessage || (player.getConnection() != null && player.getConnection().isGod())) {
            final String message;

            if (target != null && (!target.getAssignedObjectName().isEmpty() || target.getNameStringId().isValid()))
                message = String.format("container%02d_prose", errorCode.value);
            else
                message = String.format("container%02d", errorCode.value);

            final StringId code = new StringId("container_error_message", message);

            chatService.sendSystemMessageSimple(player, code, target);
        }
    }
}