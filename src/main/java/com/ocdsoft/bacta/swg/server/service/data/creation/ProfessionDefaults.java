package com.ocdsoft.bacta.swg.server.service.data.creation;

import bacta.iff.Iff;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.service.data.SharedFileLoader;
import com.ocdsoft.bacta.tre.TreeFile;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 3/29/14.
 */
@Singleton
public final class ProfessionDefaults implements SharedFileLoader {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<String, ProfessionInfo> professionDefaults = new HashMap<>();

    private static final int ID_PFDT = Iff.createChunkId("PFDT");
    private static final int ID_0000 = Iff.createChunkId("0000");
    private static final int ID_DATA = Iff.createChunkId("DATA");

    private final TreeFile treeFile;

    @Inject
    public ProfessionDefaults(final TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    public ProfessionInfo getProfessionInfo(final String profession) {
        return professionDefaults.get(profession);
    }

    private void load() {
        logger.trace("Loading default professions.");

        logger.error("Not implemented.");

        //TODO: Replace with new Iff.
//
//        final ChunkReader chunkReader = new ChunkReader("player/profession_defaults.iff", treeFile.open("player/profession_defaults.iff"));
//
//        ChunkBufferContext root = chunkReader.openChunk();
//
//        if (root == null || !root.isFormType(ID_PFDT))
//            throw new RuntimeException("Failed to load default professions. Not a PFDT file.");
//
//        root = chunkReader.nextChunk();
//
//        if (!root.isFormType(TAG_0000))
//            throw new RuntimeException("Failed to load default professions. Wrong version.");
//
//        while (root.hasMoreChunks(chunkReader.readerIndex())) {
//            ChunkBufferContext context = chunkReader.nextChunk();
//
//            if (context.isChunkId(ID_DATA)) {
//                final String profession = chunkReader.readNullTerminatedAscii();
//                final String professionFile = chunkReader.readNullTerminatedAscii();
//
//                logger.trace("Loading profession: " + profession);
//
//                final ChunkReader professionReader = new ChunkReader(professionFile, treeFile.open(professionFile));
//
//                professionDefaults.put(profession, new ProfessionInfo(professionReader));
//            }
//        }
//
//        chunkReader.closeChunk(); //Closes TAG_0000
//        chunkReader.closeChunk(); //Closes ID_HAIR

        logger.debug(String.format("Loaded %d default professions.",
                professionDefaults.size()));
    }

    public void reload() {
        synchronized (this) {
            professionDefaults.clear();
            load();
        }
    }


    public static final class ProfessionInfo {
        private static final Logger logger = LoggerFactory.getLogger(ProfessionInfo.class);

        private static final int ID_PRFI = Iff.createChunkId("PRFI");
        private static final int ID_0000 = Iff.createChunkId("0000");
        private static final int ID_SKLS = Iff.createChunkId("SKLS");
        private static final int ID_SKIL = Iff.createChunkId("SKIL");
        private static final int ID_PTMP = Iff.createChunkId("PTMP");
        private static final int ID_NAME = Iff.createChunkId("NAME");
        private static final int ID_ITEM = Iff.createChunkId("ITEM");

        @Getter
        private final Collection<String> skillsList = new ArrayList<>();
        @Getter private final Map<String, Collection<String>> itemTemplates = new HashMap<>();

        public final Collection<String> getItemTemplates(final String playerTemplate) {
            return itemTemplates.get(playerTemplate);
        }
//
//        public ProfessionInfo(ChunkReader reader) {
//            ChunkBufferContext root = reader.nextChunk();
//
//            if (!root.isFormType(ID_PRFI))
//                return;
//
//            root = reader.nextChunk();
//
//            if (!root.isFormType(TAG_0000))
//                return;
//
//            logger.trace("Parsing profession info");
//
//            while (root.hasMoreChunks(reader.readerIndex())) {
//                ChunkBufferContext context = reader.nextChunk();
//
//                if (context.isFormType(ID_SKLS)) {
//                    parseSkills(reader);
//                } else if (context.isFormType(ID_PTMP)) {
//                    parseItemTemplates(reader);
//                }
//            }
//        }
//
//        private final void parseSkills(ChunkReader reader) {
//            final ChunkBufferContext root = reader.getCurrentContext();
//
//            while (root.hasMoreChunks(reader.readerIndex())) {
//                ChunkBufferContext context = reader.nextChunk();
//
//                if (context.isChunkId(ID_SKIL)) {
//                    final String skill = reader.readNullTerminatedAscii();
//                    logger.trace("Parsing skill: " + skill);
//                    skillsList.add(skill);
//                }
//            }
//        }
//
//        private final void parseItemTemplates(ChunkReader reader) {
//            logger.trace("Parsing item templates");
//
//            final ChunkBufferContext root = reader.getCurrentContext();
//            String playerTemplate = null;
//            Collection<String> itemTemplates = new ArrayList<>();
//
//            while (root.hasMoreChunks(reader.readerIndex())) {
//                ChunkBufferContext context = reader.nextChunk();
//
//                if (context.isChunkId(ID_NAME)) {
//                    playerTemplate = reader.readNullTerminatedAscii();
//                } else if (context.isChunkId(ID_ITEM)) {
//                    final int unknownInt = reader.readInt();
//                    final String sharedTemplate = reader.readNullTerminatedAscii();
//                    final String serverTemplate = reader.readNullTerminatedAscii();
//                    logger.trace(String.format("unknownInt: %d; sharedTemplate: %s; serverTemplate: %s",
//                            unknownInt,
//                            sharedTemplate,
//                            serverTemplate));
//
//                    itemTemplates.add(sharedTemplate); //TODO: Fix this...
//                }
//            }
//
//
//            if (playerTemplate == null)
//                return;
//
//            this.itemTemplates.put(playerTemplate, itemTemplates);
//        }
    }
}
