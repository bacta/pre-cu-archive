package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;

/**
 * Created by crush on 8/18/2014.
 */
public class PlayerMoneyResponse extends SwgMessage {
    public PlayerMoneyResponse(int balanceCash, int balanceBank) {
        super(0x03, 0x367E737E);

        writeInt(balanceCash);
        writeInt(balanceBank);
    }

    public PlayerMoneyResponse(CreatureObject creatureObject) {
        this(creatureObject.getBalanceCash(), creatureObject.getBalanceBank());
    }
}
