package com.ocdsoft.bacta.swg.server.game.player.creation;

import bacta.iff.Iff;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.service.data.SharedFileLoader;
import com.ocdsoft.bacta.tre.TreeFile;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 3/28/14.
 */
@Singleton
public class HairStyles implements SharedFileLoader {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<String, HairStyleInfo> hairStyles = new HashMap<>();

    private static final int ID_HAIR = Iff.createChunkId("HAIR");
    private static final int ID_0000 = Iff.createChunkId("0000");
    private static final int ID_PTMP = Iff.createChunkId("PTMP");
    private static final int ID_NAME = Iff.createChunkId("NAME");
    private static final int ID_DEFA = Iff.createChunkId("DEFA");
    private static final int ID_ITMS = Iff.createChunkId("ITMS");

    private final TreeFile treeFile;

    @Inject
    public HairStyles(final TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    public HairStyleInfo getHairStyleInfo(final String playerTemplate) {
        return hairStyles.get(playerTemplate);
    }

    private void load() {
        logger.trace("Loading hair styles.");

        logger.error("Not implemented.");

        //TODO: Replace with new Iff library.

//
//
//        final ChunkReader chunkReader = new ChunkReader("player/default_pc_hairstyles.iff", treeFile.open("player/default_pc_hairstyles.iff"));
//
//        ChunkBufferContext root = chunkReader.openChunk();
//
//        if (root == null || !root.isFormType(ID_HAIR))
//            throw new RuntimeException("Failed to load hair styles. Not a HAIR file.");
//
//        root = chunkReader.nextChunk();
//
//        if (!root.isFormType(TAG_0000))
//            throw new RuntimeException("Failed to load hair styles. Wrong version.");
//
//        while (root.hasMoreChunks(chunkReader.readerIndex())) {
//            ChunkBufferContext context = chunkReader.nextChunk();
//
//            if (context.isFormType(ID_PTMP)) {
//                HairStyleInfo styleInfo = new HairStyleInfo(chunkReader);
//                hairStyles.put(styleInfo.getPlayerTemplate(), styleInfo);
//            }
//        }
//
//        chunkReader.closeChunk(); //Closes TAG_0000
//        chunkReader.closeChunk(); //Closes ID_HAIR

        logger.debug("Loaded {} default hair styles.", hairStyles.size());
    }

    @Override
    public void reload() {
        synchronized (this) {
            hairStyles.clear();
            load();
        }
    }

    public final class HairStyleInfo {
        private final Logger logger = LoggerFactory.getLogger(HairStyleInfo.class);

        @Getter
        private String playerTemplate;
        @Getter
        private String defaultTemplate = "";

        private final Collection<String> templates = new ArrayList<>();

        public boolean containsTemplate(final String templateName) {
            return templates.contains(templateName);
        }

//        public HairStyleInfo(ChunkReader reader) {
//            logger.trace("Parsing hair style info.");
//            ChunkBufferContext root = reader.getCurrentContext();
//
//            while (root.hasMoreChunks(reader.readerIndex())) {
//                ChunkBufferContext context = reader.nextChunk();
//
//                if (context.isChunkId(ID_NAME)) {
//                    playerTemplate = reader.readNullTerminatedAscii();
//
//                    logger.trace("NAME: " + playerTemplate);
//                } else if (context.isChunkId(ID_DEFA)) {
//                    defaultTemplate = reader.readNullTerminatedAscii();
//                    logger.trace("DEFA: " + defaultTemplate);
//                } else if (context.isChunkId(ID_ITMS)) {
//                    int totalBytes = context.getChunkSize();
//
//                    while (context.hasMoreBytes(reader.readerIndex())) {
//                        templates.add(reader.readNullTerminatedAscii());
//                    }
//
//                    logger.trace("ITMS: " + templates.size());
//                }
//            }
//        }
    }
}
