package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
    00 09 00 00 04 00 96 1F 13 41 04 00 61 73 64 66
    00 00 0E 00 32 30 30 35 30 34 30 38 2D 31 38 3A
    30 30 00 FC 79
  */
@Getter
public class LoginClientId extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(LoginClientId.class.getSimpleName()); // 0x41131f96

    private String username;
    private String password;
    private String clientVersion;

    @Inject
    public LoginClientId() {
        super(priority, messageType);
    }

    public LoginClientId(final String username, final String password, final String clientVersion) {
        super(priority, messageType);

        this.username = username;
        this.password = password;
        this.clientVersion = clientVersion;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        username = BufferUtil.getAscii(buffer);
        password = BufferUtil.getAscii(buffer);
        clientVersion = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, username);
        BufferUtil.putAscii(buffer, password);
        BufferUtil.putAscii(buffer, clientVersion);
    }
}
