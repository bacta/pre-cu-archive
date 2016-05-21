package com.ocdsoft.bacta.swg.precu.message.zone;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;
import org.magnos.steer.vec.Vec3;

public class CmdStartSceneMessage extends GameNetworkMessage {

	public CmdStartSceneMessage(CreatureObject player, String terrain, long galacticTime) {
		super(0x8, 0x3AE6DFAE);
		
		writeByte(0);  // Ignore Layout Files
		writeLong(player.getNetworkId());
		
		writeAscii(terrain);
		
		Vec3 vec = player.getPosition();
		
		writeFloat(vec.x); // x
		writeFloat(vec.z); // z
		writeFloat(vec.y); // y
		
		writeAscii(player.getObjectTemplate().getName());
		
		writeLong(GalacticTime.now());
		
	}

}
