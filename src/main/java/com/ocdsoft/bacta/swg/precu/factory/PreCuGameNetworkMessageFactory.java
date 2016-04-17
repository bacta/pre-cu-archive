package com.ocdsoft.bacta.swg.precu.factory;

import com.ocdsoft.bacta.soe.factory.GameNetworkMessageFactory;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 4/17/2016.
 */
public class PreCuGameNetworkMessageFactory implements GameNetworkMessageFactory {

    @Override
    public GameNetworkMessage create(int opcode, ByteBuffer buffer) {
        return null;
    }

}
