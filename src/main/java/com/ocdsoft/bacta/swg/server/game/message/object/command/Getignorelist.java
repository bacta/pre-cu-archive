package com.ocdsoft.bacta.swg.server.game.message.object.command;

import com.ocdsoft.bacta.soe.message.CommandMessage;

import java.nio.ByteBuffer;

public class Getignorelist extends CommandMessage {

    public Getignorelist(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         23 00 00 00 16 01 00 00 E3 01 00 00 01 00 00 00
    00 00 00 00 00 00 00 00 A3 A6 8B 78 00 00 00 00
    00 00 00 00 00 00 00 00 

     */
}
