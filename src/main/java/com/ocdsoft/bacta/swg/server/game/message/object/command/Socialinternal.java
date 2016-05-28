package com.ocdsoft.bacta.swg.server.game.message.object.command;

import com.ocdsoft.bacta.soe.message.CommandMessage;

import java.nio.ByteBuffer;

public class Socialinternal extends CommandMessage {

    public Socialinternal(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         23 00 00 00 16 01 00 00 16 04 00 00 01 00 00 00
    00 00 00 00 00 00 00 00 EE 1B CF 32 00 00 00 00
    00 00 00 00 09 00 00 00 30 00 20 00 33 00 36 00
    35 00 20 00 31 00 20 00 31 00 

     */
}
