package com.ocdsoft.bacta.soe.connection

import com.codahale.metrics.MetricRegistry
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration
import com.ocdsoft.bacta.soe.io.udp.GameNetworkConfiguration
import com.ocdsoft.bacta.soe.util.SOECRC32
import com.ocdsoft.bacta.swg.server.PreCuGameServerState
import com.ocdsoft.bacta.swg.server.login.message.LoginEnumCluster
import com.ocdsoft.bacta.swg.server.login.object.ClusterServer
import com.ocdsoft.bacta.swg.shared.serialize.GameNetworkMessageSerializerImpl
import spock.lang.Specification

import java.nio.ByteBuffer

/**
 * Created by kyle on 5/29/2016.
 */
class MultiSpec extends Specification {

    def "ProcessMultiMessages"() {

        setup:
        def bactaConfig = new IniBactaConfiguration()
        def networkConfiguration = new GameNetworkConfiguration(bactaConfig)
        def soeMessageBuilder = new SoeUdpMessageBuilder(networkConfiguration)


        when:
        soeMessageBuilder.add(ByteBuffer.allocate(50))
        soeMessageBuilder.add(ByteBuffer.allocate(200))
        soeMessageBuilder.add(ByteBuffer.allocate(140))

        soeMessageBuilder.add(ByteBuffer.allocate(200))

        soeMessageBuilder.add(ByteBuffer.allocate(256))

        soeMessageBuilder.add(ByteBuffer.allocate(100))
        soeMessageBuilder.add(ByteBuffer.allocate(255))
        soeMessageBuilder.add(ByteBuffer.allocate(130))

        soeMessageBuilder.add(ByteBuffer.allocate(100))
        soeMessageBuilder.add(ByteBuffer.allocate(255))
        soeMessageBuilder.add(ByteBuffer.allocate(131))

        def buffer1 = soeMessageBuilder.buildNext()
        def buffer2 = soeMessageBuilder.buildNext()
        def buffer3 = soeMessageBuilder.buildNext()
        def buffer4 = soeMessageBuilder.buildNext()
        def buffer5 = soeMessageBuilder.buildNext()

        then:
        noExceptionThrown()
        // Add 1 byte per message added for the size, and 2 bytes being in a multi
        buffer1.limit() == 390 + 3 + 2
        buffer2.limit() == 200
        buffer3.limit() == 256
        buffer4.limit() == 485 + 3 + 2
        buffer5.limit() == 355 + 2 + 2

    }
}
