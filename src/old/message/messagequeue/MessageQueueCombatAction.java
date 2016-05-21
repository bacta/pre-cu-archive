package com.ocdsoft.bacta.swg.precu.message.game.messagequeue;

/**
 * Created by crush on 8/13/2014.
 */
public class MessageQueueCombatAction {
    public class AttackData {
        //NetworkId id
        //NetworkId weapon
        //char endPosture
        //int trailBits
        //int clientEffectId
        //int actionNameCrc
        //bool useLocation
        //Vector3 targetLocation
        //NetworkId targetCell
    }

    public class DefenderData {
        //NetworkId id
        //CombatEngineData::CombatDefense defense;
        //int clientEffectId
        //int hitLocation
        //int damageAmount
    }
}
