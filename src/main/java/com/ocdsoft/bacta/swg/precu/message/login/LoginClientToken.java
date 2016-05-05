package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

@AllArgsConstructor
public final class LoginClientToken extends GameNetworkMessage {

    static {
        priority = 0x4;
        messageType = SOECRC32.hashCode(LoginClientToken.class.getSimpleName());// 0x41131f96
    }

    private final String authToken;
    private final int accountId;
    private final String username;

    public LoginClientToken(final ByteBuffer buffer) {
        authToken = BufferUtil.getBinaryString(buffer);
        accountId = buffer.getInt();
        username = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putBinaryString(buffer, authToken);
        buffer.putInt(accountId);
        BufferUtil.putAscii(buffer, username);
        buffer.put((byte) 0);
        buffer.putShort((short) 0);
    }

    /**  Example
	 *  04 00 
	 *  C6 96 B2 AA 
	 *  08 00 00 00 
	 *  EF F8 3C 5D 
	 *  66 28 00 00 
	 *  40 D9 52 76 
	 *  04 00 4B 79 6C 65 
	 *  00 
	 *  00 00 
	 */
}
