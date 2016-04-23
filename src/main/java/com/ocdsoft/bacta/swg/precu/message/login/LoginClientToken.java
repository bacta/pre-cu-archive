package com.ocdsoft.bacta.swg.precu.message.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

public class LoginClientToken extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(LoginClientToken.class.getSimpleName());

    private String authToken;
    private int accountId;
    private String username;

    @Inject
    public LoginClientToken() {
        super(priority, messageType);

        this.authToken = "";
        this.accountId = -1;
        this.username = "";
    }
    public LoginClientToken(final String authToken,
                            final int accountId,
                            final String username) {
        super(priority, messageType);


        this.authToken = authToken;
        this.accountId = accountId;
        this.username = username;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        authToken = BufferUtil.getBinaryString(buffer);
        accountId = buffer.getInt();
        username = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
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
