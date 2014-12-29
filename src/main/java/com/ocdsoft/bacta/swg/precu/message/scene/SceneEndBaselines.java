package com.ocdsoft.bacta.swg.precu.message.scene;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;

public class SceneEndBaselines extends SwgMessage {
	public SceneEndBaselines(SceneObject scno) {
		super(0x02, 0x2C436037);  // CRC
		writeLong(scno.getNetworkId());  // ObjectID

		/*StringBuffer msg;
		msg << hex << "SceneObjectCloseMessage [Object = " << scno->getId() << "]" << " of (" << scno->getObjectCRC() << ")\n";
		System::out << msg.toString();*/
	}

	public SceneEndBaselines(long oid) {
		super(0x02, 0x2C436037);  // CRC
		writeLong(oid);  // ObjectID

		/*StringBuffer msg;
		msg << hex << "SceneObjectCloseMessage [Object = " << oid  << "]\n";
		System::out << msg.toString(); */

	}
}
