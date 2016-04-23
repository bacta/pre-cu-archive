package com.ocdsoft.bacta.swg.precu.message.game;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
      04 00 26 92 89 D5 00 00 00 00 26 00 00 00 35 38
    33 37 31 30 36 30 34 38 34 33 34 32 37 38 39 31
    34 33 33 31 37 34 37 32 33 34 35 32 37 37 39 31
    34 33 34 30 0E 00 32 30 30 35 30 34 30 38 2D 31
    38 3A 30 30 

    // NGE Struct
     struct __cppobj ClientIdMsg : GameNetworkMessage
     {
         Archive::AutoVariable<unsigned long> m_gameBitsToClear;
         Archive::AutoArray<unsigned char> token;
         Archive::AutoVariable<std::string > version;
         char *tokenData;
     };
  */
@Getter
public class ClientIdMsg extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(ClientIdMsg.class.getSimpleName()); // 0xd5899226

    private int gameBitsToClear;
    private String token;
    private String clientVersion;

    @Inject
    public ClientIdMsg() {
        super(priority, messageType);
    }

    public ClientIdMsg(final int gameBitsToClear,
                       final String token,
                       final String clientVersion) {
        super(priority, messageType);

        this.gameBitsToClear = gameBitsToClear;
        this.token = token;
        this.clientVersion = clientVersion;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        gameBitsToClear = buffer.getInt();
        token = BufferUtil.getBinaryString(buffer);
        clientVersion = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(gameBitsToClear);
        BufferUtil.putBinaryString(buffer, token);
        BufferUtil.putAscii(buffer, clientVersion);
    }
}
