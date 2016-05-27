package com.ocdsoft.bacta.swg.server.game.object.component;

public class PlayerComponent { //implements GhostComponent {

//	@Override
//	public void sendTo(boolean doClose) {
//		
//		setMovementCounter(0);
//
//		owner->balancePacketCheckupTime();
//
//		BactaBuffer* byteFlag = new unkByteFlag();
//		owner->sendMessage(byteFlag);
//
//		BactaBuffer* startScene = new CmdStartScene(_this.get());
//		owner->sendMessage(startScene);
//
//		BactaBuffer* parameters = new ParametersMessage();
//		owner->sendMessage(parameters);
//
//		ManagedReference<GuildManager*> guildManager =
//				server->getZoneServer()->getGuildManager();
//		guildManager->sendBaselinesTo(_this.get());
//
//		ManagedReference<ServerObject*> grandParent = getRootParent();
//
//		if (grandParent != NULL) {
//			grandParent->sendTo(_this.get(), true);
//		} else
//			sendTo(_this.get(), doClose);
//
//		SortedVector < ManagedReference<QuadTreeEntry*> > *closeObjects
//		= getCloseObjects();
//
//		for (int i = 0; i < closeObjects->size(); ++i) {
//			ServerObject* obj = cast<ServerObject*> (closeObjects->get(i).get());
//
//			if (obj != _this.get()) {
//				if (obj != grandParent) {
//					notifyInsert(obj);
//					//obj->sendTo(_this.get(), true);
//				}
//
//				if (obj->isPlayerCreature()) { //we need to destroy object to reset movement counter on near clients
//					obj->notifyDissapear(_this.get());
//				}
//
//				//obj->notifyInsert(_this.get());
//				sendTo(obj, true);
//			}
//
//		}
//
//		if (group != NULL)
//			group->sendTo(_this.get(), true);
//
//		owner->resetPacketCheckupTime();
//	}

}
