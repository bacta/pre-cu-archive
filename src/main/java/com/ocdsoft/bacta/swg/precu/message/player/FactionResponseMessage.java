package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.intangible.player.PlayerObject;

/**
 * Created by crush on 8/19/2014.
 */
public class FactionResponseMessage extends SwgMessage {
    public FactionResponseMessage(PlayerObject playerObject) {
        super(0x07, 0x5DD53957);

        //int factionRebelValue;
        //int factionImperialValue;
        //int factionCriminalValue;
        //string[] npcFactionNameList
        //float[] npcFactionValueList;
    }
}
