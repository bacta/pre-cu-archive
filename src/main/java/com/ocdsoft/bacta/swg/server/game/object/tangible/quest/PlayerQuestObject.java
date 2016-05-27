package com.ocdsoft.bacta.swg.server.game.object.tangible.quest;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerPlayerQuestObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 5/8/2016.
 */
public class PlayerQuestObject extends TangibleObject {
    @Inject
    public PlayerQuestObject(final ObjectTemplateList objectTemplateList,
                             final SlotIdManager slotIdManager,
                             final ServerPlayerQuestObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);

    }
}
