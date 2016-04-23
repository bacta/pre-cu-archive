package com.ocdsoft.bacta.swg.precu.message.game.messagequeue;

import java.util.List;

/**
 * Created by crush on 8/13/2014.
 */
public class MessageQueueCraftExperiment {
    public class ExperimentInfo {
        private final int attributeIndex;
        private final int experimentPoints;

        public ExperimentInfo(final int attributeIndex, final int experimentPoints) {
            this.attributeIndex = attributeIndex;
            this.experimentPoints = experimentPoints;
        }
    }

    List<ExperimentInfo> experiments;

    int totalPoints;
    byte sequenceId;
    int coreLevel;
}
