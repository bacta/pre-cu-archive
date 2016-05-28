package com.ocdsoft.bacta.swg.server.game.message.object.command;

import com.ocdsoft.bacta.soe.message.CommandMessage;

import java.nio.ByteBuffer;

public class Requestcharactersheetinfo extends CommandMessage {

    public Requestcharactersheetinfo(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {

    }
    /**
     23 00 00 00 16 01 00 00 0A 00 00 00 01 00 00 00
     00 00 00 00 00 00 00 00 61 54 7B 88 00 00 00 00
     00 00 00 00 00 00 00 00

     */
}
