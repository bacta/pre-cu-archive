package com.ocdsoft.bacta.swg.precu.message.login;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
      00 09 00 00 04 00 96 1F 13 41 03 00 73 64 66 00
    00 0E 00 32 30 30 35 30 34 30 38 2D 31 38 3A 30
    30 00 4D E5 

*/
@Getter
public class LoginClientId extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(LoginClientId.class.getSimpleName());

    private String username;
    private String password;
    private String clientVersion;

    public LoginClientId() {
        super(priority, messageType);

        this.username = "";
        this.password = "";
        this.clientVersion = "";
    }

    public LoginClientId(final String username, final String password, final String clientVersion) {
        super(priority, messageType);

        this.username = username;
        this.password = password;
        this.clientVersion = clientVersion;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        this.username = BufferUtil.getAscii(buffer);
        this.password = BufferUtil.getAscii(buffer);
        this.clientVersion = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, username);
        BufferUtil.putAscii(buffer, password);
        BufferUtil.putAscii(buffer, clientVersion);
    }
}