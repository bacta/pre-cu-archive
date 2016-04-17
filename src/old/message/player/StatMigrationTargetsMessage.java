package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;

/**
 * Created by crush on 8/18/2014.
 */
public class StatMigrationTargetsMessage extends GameNetworkMessage {
    public StatMigrationTargetsMessage(CreatureObject creatureObject) {
        super(0x09, 0xEFAC38C4);

        //Get template. Find ranges for each attribute

        //Limit for each attribute
        writeInt(1000);
        writeInt(1000);
        writeInt(1000);
        writeInt(1000);
        writeInt(1000);
        writeInt(1000);
        writeInt(1000);
        writeInt(1000);
        writeInt(1000);

        //Total attribute limit...shouldn't exceed all attributes limits combined.
        writeInt(5000);
    }
}
