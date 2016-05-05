package com.ocdsoft.bacta.swg.precu.message.login;


import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.object.account.CharacterInfo;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.TreeSet;

public final class EnumerateCharacterId extends GameNetworkMessage {

    static {
        priority = 0x2;
        messageType = SOECRC32.hashCode(EnumerateCharacterId.class.getSimpleName());
    }

    private final Set<CharacterInfo> characterInfoList;

    private EnumerateCharacterId() {
        characterInfoList = new TreeSet<>();
    }

    public EnumerateCharacterId(final SoeAccount account) {
        this();
        characterInfoList.addAll(account.getCharacterList());
	}

    public EnumerateCharacterId(final ByteBuffer buffer) {
        this();
        for(int i = 0; i < buffer.getInt(); ++i) {
            CharacterInfo info = new CharacterInfo(buffer);
            characterInfoList.add(info);
        }
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {

        buffer.putInt(characterInfoList.size());
        for(CharacterInfo info : characterInfoList) {
            info.writeToBuffer(buffer);
        }
    }
}
