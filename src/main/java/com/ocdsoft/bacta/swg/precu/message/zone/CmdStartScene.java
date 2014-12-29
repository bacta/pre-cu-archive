package com.ocdsoft.bacta.swg.precu.message.zone;

public class CmdStartScene {
	//priority 0x09
	//opcode 0x3AE6DFAE "CmdStartScene"
	
	private boolean disableWorldSnapshot;
	private long creatureObjectId;
	private String terrainPath; //"terrain/corellia.trn"
	private float x; //These should be part of a Vector3 class.
	private float z;
	private float y;
	private String templatePath; //path to the player's creature template
	private long galacticTime; //seconds? milliseconds?
}
