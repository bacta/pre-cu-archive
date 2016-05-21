package com.ocdsoft.bacta.swg.precu.message.game.object;

import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;


public class Emote extends ObjControllerMessage {

   public Emote(TangibleObject receiver, TangibleObject sender, long targetId, int emoteId, boolean showText) {
        super(0x0B, 0x12E, receiver.getNetworkId(), 0);

        writeLong(sender.getNetworkId()); //EmoterId
        writeLong(targetId); //TargetId
        writeInt(emoteId); //EmoteId

        writeByte(showText ? 3 : 1);
    }
}
