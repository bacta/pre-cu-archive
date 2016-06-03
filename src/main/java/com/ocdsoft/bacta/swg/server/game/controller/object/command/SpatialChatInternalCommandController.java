package com.ocdsoft.bacta.swg.server.game.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.chat.GameChatService;
import com.ocdsoft.bacta.swg.server.game.chat.SpatialChatService;
import com.ocdsoft.bacta.swg.server.game.controller.object.CommandQueueController;
import com.ocdsoft.bacta.swg.server.game.controller.object.QueuesCommand;
import com.ocdsoft.bacta.swg.server.game.language.GameLanguageService;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueSpatialChat;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.shared.util.CommandParamsIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/1/2016.
 */
@QueuesCommand("spatialChatInternal")
public final class SpatialChatInternalCommandController implements CommandQueueController {
    private static Logger LOGGER = LoggerFactory.getLogger(SpatialChatInternalCommandController.class);

    private final GameChatService chatService;
    private final GameLanguageService gameLanguageService;
    private final SpatialChatService spatialChatService;

    @Inject
    public SpatialChatInternalCommandController(final GameChatService chatService,
                                                final GameLanguageService gameLanguageService,
                                                final SpatialChatService spatialChatService) {
        this.chatService = chatService;
        this.gameLanguageService = gameLanguageService;
        this.spatialChatService = spatialChatService;
    }

    @Override
    public void handleCommand(final SoeUdpConnection connection, final ServerObject actor, final ServerObject target, final String params) {
        final CommandParamsIterator iterator = new CommandParamsIterator(params);

        final long targetNetworkId = iterator.getLong();
        final int chatType = iterator.getInteger();
        final int moodType = iterator.getInteger();
        int flags = iterator.getInteger();
        int languageId = iterator.getInteger();

        if (!gameLanguageService.isLanguageValid(languageId))
            languageId = gameLanguageService.getBasicLanguageId();

        //TODO: Handle color codes in the message.
        final String message = iterator.getRemainingStringToNull();
        final String outOfBand = iterator.getRemainingStringTrimmed();

        final boolean isPrivate = spatialChatService.isPrivate(chatType);

        if (isPrivate)
            flags |= MessageQueueSpatialChat.Flags.PRIVATE;

        if (chatType != -1 && moodType != -1 && flags != -1) {
            final CreatureObject creatureActor = actor.asCreatureObject();
            short volume = spatialChatService.getVolume(chatType);

            //speak cs_spaceSpeechMultiple times when piloting a ship.
            //if (creatureActor != null && creatureActor.getPilotedShip())
            //volume *= cs_spaceSpeechMultiple;

            boolean allowToSpeak = true;
            boolean squelched = false;

            //final PlayerObject playerObject =

            if (allowToSpeak) {
                spatialChatService.speakText(new MessageQueueSpatialChat(
                        actor.getNetworkId(),
                        targetNetworkId,
                        message,
                        volume,
                        (short) chatType,
                        (short) moodType,
                        flags,
                        (byte) languageId,
                        outOfBand,
                        ""));
                //
            } //else if (!squelched && getChatspamNotifyPlayerWhenLimitedIntervalSeconds() > 0) {
            //send ChatSpamLimited for x seconds message.
            //}
        }

//        actor.speakText(new MessageQueueSpatialChat(
//                actor.getNetworkId(),
//                target != null ? target.getNetworkId() : 0,
//                message,
//                volume,
//                (short)chatType,
//                (short)moodType,
//                flags,
//                language,
//                outOfBand));

        //target.hearText(final ServerObject source, final MessageQueueSpatialChat spatialChat, int chatMessageIndex);
    }
}