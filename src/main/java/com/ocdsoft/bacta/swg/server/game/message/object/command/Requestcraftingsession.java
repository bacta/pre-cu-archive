package com.ocdsoft.bacta.swg.server.game.message.object.command;

import java.nio.ByteBuffer;
import com.ocdsoft.bacta.soe.message.CommandMessage;

public class Requestcraftingsession extends CommandMessage {

    public Requestcraftingsession(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         23 00 00 00 16 01 00 00 43 04 00 00 01 00 00 00
    00 00 00 00 20 00 00 00 16 C5 4A 09 97 32 91 00
    00 00 00 00 00 00 00 00 

     */
}
