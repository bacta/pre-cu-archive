package com.ocdsoft.bacta.swg.server.game.message.object.command;

import java.nio.ByteBuffer;

public class Getfriendlist extends CommandMessage {

    public Getfriendlist(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         23 00 00 00 16 01 00 00 E3 01 00 00 01 00 00 00
    00 00 00 00 00 00 00 00 CE F9 21 EF 00 00 00 00
    00 00 00 00 00 00 00 00 

     */
}
