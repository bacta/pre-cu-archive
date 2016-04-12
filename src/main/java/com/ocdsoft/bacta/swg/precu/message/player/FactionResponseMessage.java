package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.intangible.player.PlayerObject;

/**
 * Created by crush on 8/19/2014.
 */
public class FactionResponseMessage extends GameNetworkMessage {
    public FactionResponseMessage(PlayerObject playerObject) {
        super(0x07, 0x5DD53957);

        //int factionRebelValue;
        //int factionImperialValue;
        //int factionCriminalValue;
        //string[] npcFactionNameList
        //float[] npcFactionValueList;
    }
}
