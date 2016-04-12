package com.ocdsoft.bacta.swg.precu.dispatch;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.network.dispatch.MessageDispatcher;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;

import java.nio.ByteBuffer;

@Singleton
public interface CommandDispatcher extends MessageDispatcher {
    void dispatchCommand(int opcode, SoeUdpConnection connection, ByteBuffer message, TangibleObject invoker);
}
