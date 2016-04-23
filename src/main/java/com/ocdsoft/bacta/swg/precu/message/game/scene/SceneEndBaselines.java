package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;

import java.nio.ByteBuffer;

public class SceneEndBaselines extends GameNetworkMessage {

	private long objectId;

    public SceneEndBaselines(long oid) {
        super(0x02, 0x2C436037);  // CRC
        this.objectId = oid;

		/*StringBuffer msg;
		msg << hex << "SceneObjectCloseMessage [Object = " << oid  << "]\n";
		System::out << msg.toString(); */

    }

	public SceneEndBaselines(ServerObject scno) {
		this(scno.getNetworkId());
	}


	@Override
	public void readFromBuffer(ByteBuffer buffer) {

	}

	@Override
	public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(objectId);
	}
}
