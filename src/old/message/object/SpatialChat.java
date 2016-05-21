package com.ocdsoft.bacta.swg.precu.message.game.object;

public class SpatialChat extends ObjControllerMessage {
	public SpatialChat(long receiverId, long senderId, String message, long targetId, int moodid, int mood2, byte lang) {
		super(0x0B, 0xF4, receiverId, 0);
		
		writeLong(senderId);
		writeLong(targetId);
		writeUnicode(message);
		writeShort(0x32);
		writeShort(mood2);
		writeShort(moodid);
		writeByte(0x00);
		writeByte(lang);
		writeLong(0);
		writeInt(0);
		writeInt(0);
		
	}

//
//    UnicodeString m_text;
//    NetworkId m_sourceId;
//    NetworkId m_targetId;
//    unsigned int m_flags;
//    unsigned __int16 m_volume;
//    unsigned __int16 m_chatType;
//    unsigned __int16 m_moodType;
//    char m_language;
//    UnicodeString m_outOfBand;
//    UnicodeString m_sourceName;
}
