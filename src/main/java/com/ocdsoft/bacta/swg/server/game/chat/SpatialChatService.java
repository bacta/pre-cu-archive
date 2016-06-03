package com.ocdsoft.bacta.swg.server.game.chat;

import bacta.iff.Iff;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.ObjControllerBuilder;
import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueSpatialChat;
import com.ocdsoft.bacta.swg.server.game.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.tre.TreeFile;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TIntShortMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TIntShortHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by crush on 6/2/2016.
 */
@Singleton
public class SpatialChatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpatialChatService.class);

    private static final int TAG_SPCT = Tag.convertStringToTag("SPCT");
    private static final int TAG_TYPS = Tag.convertStringToTag("TYPS");
    private static final int TAG_VOLT = Tag.convertStringToTag("VOLT");
    private static final int TAG_PRVT = Tag.convertStringToTag("PRVT");
    private static final int TAG_SKCT = Tag.convertStringToTag("SKCT");
    private static final int TAG_VOLS = Tag.convertStringToTag("VOLS");
    private static final int TAG_PRIV = Tag.convertStringToTag("PRIV");
    private static final int TAG_SKCS = Tag.convertStringToTag("SKCS");

    private static final String SPATIAL_TYPES_FILENAME = "chat/spatial_chat_types.iff";

    private final TIntObjectMap<String> idToNameMap;
    private final TObjectIntMap<String> nameToIdMap;
    private final TIntShortMap idToVolumeMap;
    private final TIntSet privateIds;
    private final TIntObjectMap<List<String>> skillsProviding;
    private final List<String> defaultSkills;
    private final AtomicInteger defaultVolume;
    private final int defaultChatType = 1;

    private final TreeFile treeFile;
    private final ServerObjectService serverObjectService;

    @Inject
    public SpatialChatService(final TreeFile treeFile,
                              final ServerObjectService serverObjectService) {
        this.treeFile = treeFile;
        this.serverObjectService = serverObjectService;

        this.idToNameMap = new TIntObjectHashMap<>();
        this.nameToIdMap = new TObjectIntHashMap<>();
        this.idToVolumeMap = new TIntShortHashMap();
        this.privateIds = new TIntHashSet();
        this.skillsProviding = new TIntObjectHashMap<>();
        this.defaultSkills = ImmutableList.of();
        this.defaultVolume = new AtomicInteger(1024);
    }

    public void speakText(final CreatureObject speaker, final long targetId, final short chatType, final short moodType, short flags, final int language, final String message, final String outOfBand) {
        final short volume = getVolume(chatType);

        if (isPrivate(chatType))
            flags |= MessageQueueSpatialChat.Flags.PRIVATE;

        final MessageQueueSpatialChat spatialChat = new MessageQueueSpatialChat(
                speaker.getNetworkId(),
                targetId,
                message,
                volume,
                chatType,
                moodType,
                flags,
                (byte) language,
                outOfBand,
                "");

        speakText(spatialChat);
    }

    public void speakText(final MessageQueueSpatialChat spatialChat) {
        //TODO: Implement script hooks.

        final float distance = (float) spatialChat.getVolume();
        final int flags = spatialChat.getFlags();
        final boolean isPrivate = (flags & MessageQueueSpatialChat.Flags.PRIVATE) != 0;
        final boolean isTargetOnly = (flags & MessageQueueSpatialChat.Flags.TARGET_ONLY) != 0;
        final boolean isTargetGroupOnly = (flags & MessageQueueSpatialChat.Flags.TARGET_GROUP_ONLY) != 0;
        final boolean isTargetAndSourceGroup = (flags & MessageQueueSpatialChat.Flags.TARGET_AND_SOURCE_GROUP) != 0;

        final long chatTargetId = spatialChat.getTargetId();
        final long chatSourceId = spatialChat.getSourceId();

        final ServerObject chatTarget = serverObjectService.get(chatTargetId);
        final ServerObject chatSource = serverObjectService.get(chatSourceId);

        //TODO: Iterate all players in range of distance.
        final ServerObject target = chatSource; //Temporary until we get the loop - you can only talk to yourself.
        final long targetId = target.getNetworkId();

        if ((flags & MessageQueueSpatialChat.Flags.SKIP_TARGET) != 0 && targetId == chatTargetId)
            return; //change this to continue when we get a for loop

        if ((flags & MessageQueueSpatialChat.Flags.SKIP_SOURCE) != 0 && targetId == chatSourceId)
            return; //change this to continue when we get a for loop

        if (isTargetAndSourceGroup) {
            //Check if the target is in the same group as the chat target or chat source.
            //If not, then skip this target.
        } else if (isTargetGroupOnly) {
            //Check if the target is in the same group as the chat target.
            //If not, then skip this target.
        } else if (isTargetOnly && targetId != chatTargetId) {
            return; //change to continue
        }

        if (isPrivate) {
            if (targetId == chatTargetId || chatSourceId == chatTargetId) {
                if (chatSource.isPlayerControlled() || targetId != chatSourceId)
                    hearText(target, chatSource, spatialChat);
            } else {
                final MessageQueueSpatialChat noTextMessage = new MessageQueueSpatialChat(
                        spatialChat.getSourceId(),
                        spatialChat.getTargetId(),
                        "",
                        spatialChat.getVolume(),
                        spatialChat.getChatType(),
                        spatialChat.getMoodType(),
                        spatialChat.getFlags(),
                        spatialChat.getLanguage(),
                        "",
                        "");
                hearText(target, chatSource, noTextMessage);
            }
        } else {
            hearText(target, chatSource, spatialChat);
        }

        LOGGER.debug("Sending spatial to people in range {}", distance);
    }

    public void hearText(final ServerObject target, final ServerObject source, final MessageQueueSpatialChat spatialChat) {
        final SoeUdpConnection targetConnection = target.getConnection();

        if (targetConnection != null) {
            final ObjControllerMessage msg = ObjControllerBuilder.newBuilder().send().reliable().authClient()
                    .build(target.getNetworkId(), GameControllerMessageType.SPATIAL_CHAT_RECEIVE, spatialChat);
            targetConnection.sendMessage(msg);
        }
    }

    public int getChatTypeByName(final String name) {
        return nameToIdMap.containsKey(name) ? nameToIdMap.get(name) : 0;
    }

    public String getChatNameByType(final int type) {
        return idToNameMap.containsKey(type) ? idToNameMap.get(type) : null;
    }

    public boolean isPrivate(final int type) {
        return privateIds.contains(type);
    }

    public short getVolume(final int type) {
        return idToVolumeMap.containsKey(type) ? idToVolumeMap.get(type) : (short) defaultVolume.get();
    }

    public List<String> getSkillsProviding(final int type) {
        return skillsProviding.containsKey(type)
                ? ImmutableList.copyOf(skillsProviding.get(type))
                : defaultSkills;
    }

    public void loadSpatialChatTypes(final String filename) {
        final byte[] bytes = treeFile.open(filename);

        if (bytes == null) {
            LOGGER.error("Could not open up spatial chat types file: {}", filename);
            return;
        }

        final Iff iff = new Iff(filename, bytes);

        iff.enterForm(TAG_SPCT);
        {
            iff.enterForm(Tag.TAG_0000);
            {
                loadTypes(iff, filename);
                loadVolumes(iff, filename);
                loadPrivates(iff, filename);
                loadSkillChatTypes(iff, filename);
            }
            iff.exitForm(Tag.TAG_0000);
        }
        iff.exitForm(TAG_SPCT);
    }

    private void loadTypes(final Iff iff, final String filename) {
        iff.enterChunk(TAG_TYPS);
        {
            int count = 0;
            while (iff.getChunkLengthLeft() > 0) {
                final String name = iff.readString().toLowerCase();
                ++count;
                idToNameMap.put(count, name);

                if (nameToIdMap.containsKey(name))
                    LOGGER.error("File {} contains duplicate chat type {}", filename, name);

                nameToIdMap.put(name, count);
            }
        }
        iff.exitChunk(TAG_TYPS);
    }

    private void loadVolumes(final Iff iff, final String filename) {
        iff.enterForm(TAG_VOLS);
        {
            while (iff.enterChunk(TAG_VOLT, true)) {
                final String name = iff.readString().toLowerCase();
                final short volume = iff.readShort();

                if (name.isEmpty()) {
                    this.defaultVolume.set(volume);
                } else {
                    final int id = getChatTypeByName(name);

                    if (id != 0) {
                        idToVolumeMap.put(id, volume);
                    } else {
                        LOGGER.warn("File {} invalid chat type for {} for volume.", filename, name);
                    }
                }

                iff.exitChunk(TAG_VOLT);
            }
        }
        iff.exitForm(TAG_VOLS);
    }

    private void loadPrivates(final Iff iff, final String filename) {
        iff.enterForm(TAG_PRIV);
        {
            while (iff.enterChunk(TAG_PRVT, true)) {
                final String name = iff.readString().toLowerCase();
                final int id = getChatTypeByName(name);

                if (id != 0) {
                    privateIds.add(id);
                } else {
                    LOGGER.warn("File {} invalid chat type {} for private", filename, name);
                }

                iff.exitChunk(TAG_PRVT);
            }
        }
        iff.exitForm(TAG_PRIV);
    }

    private void loadSkillChatTypes(final Iff iff, final String filename) {
        iff.enterForm(TAG_SKCS);
        {
            while (iff.enterChunk(TAG_SKCT, true)) {
                final String typeName = iff.readString().toLowerCase();
                final String skillName = iff.readString();
                final int id = getChatTypeByName(typeName);

                if (id != 0) {
                    final List<String> skills;
                    if (!skillsProviding.containsKey(id)) {
                        skills = new ArrayList<>();
                        skillsProviding.put(id, skills);
                    } else {
                        skills = skillsProviding.get(id);
                    }

                    skills.add(skillName);
                } else {
                    LOGGER.warn("File {} invalid chat type {} for skill", filename, typeName);
                }

                iff.exitChunk(TAG_SKCT);
            }
        }
        iff.exitForm(TAG_SKCS);
    }
}
