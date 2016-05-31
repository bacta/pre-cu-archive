package com.ocdsoft.bacta.swg.server.game.player;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.chat.GameChatService;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.service.container.SlottedContainerService;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatAvatarId;
import com.ocdsoft.bacta.swg.shared.container.ContainerResult;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.container.SlottedContainer;
import com.ocdsoft.bacta.swg.shared.foundation.ConstCharCrcLowerString;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/30/2016.
 */
@Singleton
public final class PlayerObjectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerObjectService.class);

    private static final ConstCharCrcLowerString SLOT_GHOST = new ConstCharCrcLowerString("ghost");

    private final SlotIdManager slotIdManager;
    private final GameChatService chatService;
    private final SlottedContainerService slottedContainerService;
    private final ServerObjectService serverObjectService;

    private final int playerSlotId;

    @Inject
    public PlayerObjectService(final SlotIdManager slotIdManager,
                               final GameChatService chatService,
                               final SlottedContainerService slottedContainerService,
                               final ServerObjectService serverObjectService) {
        this.slotIdManager = slotIdManager;
        this.chatService = chatService;
        this.slottedContainerService = slottedContainerService;
        this.serverObjectService = serverObjectService;
        this.playerSlotId = slotIdManager.findSlotId(SLOT_GHOST);
    }

    /**
     * Gets the {@link PlayerObject} from a {@link CreatureObject} belonging to the given networkId.
     *
     * @param creatureObjectNetworkId The NetworkId of the CreatureObject on which the PlayerObject is slotted.
     * @return The PlayerObject if it exists. Otherwise, null.
     */
    public PlayerObject getPlayerObject(final long creatureObjectNetworkId) {
        final ServerObject serverObject = serverObjectService.get(creatureObjectNetworkId);
        return serverObject != null ? getPlayerObject(serverObject) : null;
    }

    public PlayerObject getPlayerObject(final ServerObject serverObject) {
        final CreatureObject creatureObject = serverObject.asCreatureObject();
        return creatureObject != null ? getPlayerObject(creatureObject) : null;
    }

    /**
     * Get the {@link PlayerObject} from a {@link CreatureObject}.
     *
     * @param creatureObject The creature that has the PlayerObject slotted.
     * @return The PlayerObject if it exists. Otherwise, null.
     */
    public PlayerObject getPlayerObject(final CreatureObject creatureObject) {
        if (creatureObject != null) {
            final SlottedContainer slottedContainer = creatureObject.getSlottedContainerProperty();

            if (slottedContainer != null) {
                final ContainerResult containerResult = new ContainerResult();
                final GameObject slottedObject = slottedContainerService.getObjectInSlot(slottedContainer, playerSlotId, containerResult);

                if (slottedObject != null) {
                    final ServerObject serverSlottedObject = slottedObject.asServerObject();

                    if (serverSlottedObject != null)
                        return serverSlottedObject.asPlayerObject();
                }
            }
        }

        return null;
    }

    public void requestFriendList(final PlayerObject playerObject) {
        final CreatureObject creatureObject = serverObjectService.get(playerObject.getContainedBy());

        if (creatureObject != null) {
            final ChatAvatarId avatarId = chatService.constructChatAvatarId(creatureObject);
            chatService.getFriendList(avatarId.getFullName());
        }
    }
}
