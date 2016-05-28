package com.ocdsoft.bacta.swg.server.game.message.object.command;

import com.ocdsoft.bacta.soe.message.CommandMessage;

import java.nio.ByteBuffer;

public class Handleclientlogin extends CommandMessage {

    public Handleclientlogin(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         23 00 00 00 16 01 00 00 43 02 00 00 01 00 00 00
    00 00 00 00 00 00 00 00 45 B1 2A 39 00 00 00 00
    00 00 00 00 00 00 00 00 

     */
}
