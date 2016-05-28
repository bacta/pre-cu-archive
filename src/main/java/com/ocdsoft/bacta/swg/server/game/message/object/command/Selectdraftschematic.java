package com.ocdsoft.bacta.swg.server.game.message.object.command;

import java.nio.ByteBuffer;
import com.ocdsoft.bacta.soe.message.CommandMessage;

public class Selectdraftschematic extends CommandMessage {

    public Selectdraftschematic(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         23 00 00 00 16 01 00 00 46 04 00 00 01 00 00 00
    00 00 00 00 40 00 00 00 02 2E 24 89 00 00 00 00
    00 00 00 00 00 00 00 00 

     */
}
