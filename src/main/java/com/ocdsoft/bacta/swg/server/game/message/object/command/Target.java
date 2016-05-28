package com.ocdsoft.bacta.swg.server.game.message.object.command;

import java.nio.ByteBuffer;
import com.ocdsoft.bacta.soe.message.CommandMessage;

public class Target extends CommandMessage {

    public Target(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         23 00 00 00 16 01 00 00 43 04 00 00 01 00 00 00
    00 00 00 00 00 00 00 00 02 B7 A8 D6 00 00 00 00
    00 00 00 00 00 00 00 00 

     */
}
