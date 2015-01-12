package com.ocdsoft.bacta.swg.precu.service.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.swg.shared.tre.TreeFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by crush on 3/19/14.
 */
@Singleton
public class SetupSharedFile {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BactaConfiguration configuration;
    private final TreeFile treeFile;

    @Inject
    public SetupSharedFile(BactaConfiguration configuration, TreeFile treeFile) {
        this.configuration = configuration;
        this.treeFile = treeFile;

        install();
    }

    public void install() {
        try {
            final String rootPath = configuration.getStringWithDefault("Bacta/SharedFiles", "Path", "");
            treeFile.setRootPath(rootPath);

            int subscriptionFeatures = configuration.getIntWithDefault("Station", "subscriptionFeatures", 1);
            int maxSearchPriority = configuration.getIntWithDefault("SharedFile", "maxSearchPriority", 26);

            for (int feature = 0; feature <= subscriptionFeatures; feature++) {
                for (int priority = 0; priority < maxSearchPriority; priority++) {
                    String propertyName = String.format("searchPath_%02d_%d", feature, priority);
                    Collection<String> filePaths = configuration.getStringCollection("SharedFile", propertyName);

                    if (filePaths != null) {
                        for (String filePath : filePaths)
                            treeFile.addSearchPath(rootPath + filePath, priority);
                    }

                    propertyName = String.format("searchTree_%02d_%d", feature, priority);
                    filePaths = configuration.getStringCollection("SharedFile", propertyName);

                    if (filePaths != null) {
                        for (String filePath : filePaths)
                            treeFile.addSearchTree(rootPath + filePath, priority);
                    }

                    propertyName = String.format("searchTOC_%02d_%d", feature, priority);
                    filePaths = configuration.getStringCollection("SharedFile", propertyName);

                    if (filePaths != null) {
                        for (String filePath : filePaths)
                            treeFile.addSearchTOC(rootPath + filePath, priority);
                    }
                }
            }

            treeFile.addSearchAbsolute(configuration.getIntWithDefault("SharedFile", "searchAbsolute", maxSearchPriority + 1));
            treeFile.addSearchCache(configuration.getIntWithDefault("SharedFile", "searchCache", maxSearchPriority + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
