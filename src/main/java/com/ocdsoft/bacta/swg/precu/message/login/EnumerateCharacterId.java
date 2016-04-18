package com.ocdsoft.bacta.swg.precu.message.login;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.object.account.CharacterInfo;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.TreeSet;

public class EnumerateCharacterId extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(EnumerateCharacterId.class.getSimpleName());

    private Set<CharacterInfo> characterInfoList;

    public EnumerateCharacterId(SoeAccount account) {
        super(priority, messageType);

        characterInfoList = new TreeSet<>();
        characterInfoList.addAll(account.getCharacterList());
	}

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

        characterInfoList = new TreeSet<>();
        for(int i = 0; i < buffer.getInt(); ++i) {
            CharacterInfo info = new CharacterInfo(buffer);
            characterInfoList.add(info);
        }
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

        buffer.putInt(characterInfoList.size());
        for(CharacterInfo info : characterInfoList) {
            info.writeToBuffer(buffer);
        }
    }
}
