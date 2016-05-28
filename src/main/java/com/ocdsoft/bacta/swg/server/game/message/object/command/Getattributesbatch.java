package com.ocdsoft.bacta.swg.server.game.message.object.command;

import java.nio.ByteBuffer;
import com.ocdsoft.bacta.soe.message.CommandMessage;

public class Getattributesbatch extends CommandMessage {

    public Getattributesbatch(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         23 00 00 00 16 01 00 00 43 04 00 00 01 00 00 00
    00 00 00 00 20 00 00 00 EF 50 45 16 00 00 00 00
    00 00 00 00 0D 00 00 00 34 00 36 00 38 00 35 00
    35 00 39 00 33 00 20 00 2D 00 32 00 35 00 35 00
    20 00 

     */
}
