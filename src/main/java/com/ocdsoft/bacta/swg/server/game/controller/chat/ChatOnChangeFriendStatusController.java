package com.ocdsoft.bacta.swg.server.game.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameClientMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.chat.GameChatService;
import com.ocdsoft.bacta.swg.server.game.message.outofband.ProsePackage;
import com.ocdsoft.bacta.swg.server.game.message.outofband.ProsePackageBuilder;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.server.game.player.PlayerObjectService;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import com.ocdsoft.bacta.swg.shared.chat.ChatStringIds;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatAvatarId;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatOnChangeFriendStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/30/2016.
 */
@MessageHandled(handles = ChatOnChangeFriendStatus.class, type = ServerType.GAME)
@ConnectionRolesAllowed(ConnectionRole.WHITELISTED)
public final class ChatOnChangeFriendStatusController implements GameClientMessageController<ChatOnChangeFriendStatus> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatOnChangeFriendStatusController.class);

    private static final ProsePackageBuilder FRIEND_ADDED = ProsePackageBuilder.with(ChatStringIds.FRIEND_ADDED);
    private static final ProsePackageBuilder FRIEND_REMOVED = ProsePackageBuilder.with(ChatStringIds.FRIEND_REMOVED);
    private static final ProsePackageBuilder FRIEND_DUPLICATE = ProsePackageBuilder.with(ChatStringIds.FRIEND_DUPLICATE);
    private static final ProsePackageBuilder FRIEND_NOT_FOUND = ProsePackageBuilder.with(ChatStringIds.FRIEND_NOT_FOUND);

    private final PlayerObjectService playerObjectService;
    private final ServerObjectService serverObjectService;
    private final GameChatService chatService;

    @Inject
    public ChatOnChangeFriendStatusController(final PlayerObjectService playerObjectService,
                                              final ServerObjectService serverObjectService,
                                              final GameChatService chatService) {
        this.playerObjectService = playerObjectService;
        this.serverObjectService = serverObjectService;
        this.chatService = chatService;
    }

    @Override
    public void handleIncoming(final long[] distributionList, final boolean reliable, final ChatOnChangeFriendStatus message, final SoeUdpConnection connection) throws Exception {
        for (final long networkId : distributionList) {
            final ServerObject serverObject = serverObjectService.get(networkId);

            if (serverObject == null)
                continue;

            final ChatAvatarId serverAvatar = chatService.getServerAvatar();

            final String friendName = message.getFriendId()
                    .getNameWithNecessaryPrefix(serverAvatar.getGameCode(), serverAvatar.getCluster());

            switch (message.getResultCode()) {
                case SUCCESS:
                    final PlayerObject playerObject = playerObjectService.getPlayerObject(serverObject);

                    if (playerObject != null) {
                        if (message.isAdd()) {
                            final ProsePackage prosePackage = FRIEND_ADDED.target(friendName).build();
                            chatService.sendSystemMessage(serverObject, prosePackage);
                        } else {
                            final ProsePackage prosePackage = FRIEND_REMOVED.target(friendName).build();
                            chatService.sendSystemMessage(serverObject, prosePackage);
                        }

                        playerObjectService.requestFriendList(playerObject);
                        break;
                    }

                case FRIEND_NOT_FOUND:
                case DST_AVATAR_DOESNT_EXIST: {
                    final ProsePackage prosePackage = FRIEND_NOT_FOUND.target(friendName).build();
                    chatService.sendSystemMessage(serverObject, prosePackage);
                    break;
                }

                case DUPLICATE_FRIEND: {
                    final ProsePackage prosePackage = FRIEND_DUPLICATE.target(friendName).build();
                    chatService.sendSystemMessage(serverObject, prosePackage);
                    break;
                }
                default:
                    LOGGER.warn("Unhandled result code returned: {}", message.getResultCode());
                    break;
            }
        }
    }
}
