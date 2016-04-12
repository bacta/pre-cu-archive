package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.Vector;
import com.ocdsoft.bacta.swg.precu.object.intangible.player.PlayerObject;

/**
 * Created by crush on 8/11/2014.
 */
public class CharacterSheetResponseMessage extends GameNetworkMessage {
    public CharacterSheetResponseMessage(PlayerObject playerObject) {
        super(0x0D, 0x9B3A17C4);

        writeInt(playerObject.getBornDate());
        writeInt(playerObject.getPlayedTime());

        Vector.zero.writeToBuffer(this);
        writeAscii("");
        Vector.zero.writeToBuffer(this);
        writeAscii("");
        Vector.zero.writeToBuffer(this);
        writeAscii("");
        writeUnicode("crush");
        writeInt(20);
        writeInt(15);
        writeInt(40);
    }
}
