package com.ocdsoft.bacta.swg.server.game.message.client;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Priority(0x4)
public class ClientIdMsg extends GameNetworkMessage {

    private final int gameBitsToClear;
    private final String token;
    private final String clientVersion;

    public ClientIdMsg(ByteBuffer buffer) {
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
