package com.ocdsoft.bacta.swg.server.util

import com.codahale.metrics.MetricRegistry
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration
import com.ocdsoft.bacta.soe.connection.ReliableUdpMessageBuilder
import com.ocdsoft.bacta.soe.connection.SoeUdpMessageBuilder
import com.ocdsoft.bacta.soe.io.udp.GameNetworkConfiguration
import com.ocdsoft.bacta.soe.util.SOECRC32
import com.ocdsoft.bacta.soe.util.SoeMessageUtil
import com.ocdsoft.bacta.swg.server.PreCuGameServerState
import com.ocdsoft.bacta.swg.server.login.message.EnumerateCharacterId
import com.ocdsoft.bacta.swg.server.login.message.LoginClientToken
import com.ocdsoft.bacta.swg.server.login.message.LoginClusterStatus
import com.ocdsoft.bacta.swg.server.login.message.LoginEnumCluster
import com.ocdsoft.bacta.swg.server.login.object.ClusterServer
import com.ocdsoft.bacta.swg.server.login.object.SoeAccount
import com.ocdsoft.bacta.swg.shared.serialize.GameNetworkMessageSerializerImpl
import spock.lang.Specification

/**
 * Created by kburkhardt on 2/10/15.
 */
class MultiGameMessageSpec extends Specification {

    def "BuildGroupMessage"() {
        
        setup:
        def bactaConfig = new IniBactaConfiguration()
        def networkConfiguration = new GameNetworkConfiguration(bactaConfig)
        def gameServerState = new PreCuGameServerState(bactaConfig, networkConfiguration)

        def clusterEntry = new ClusterServer(bactaConfig, networkConfiguration, gameServerState)
        def loginClientToken = new LoginClientToken("Test", 0, "kyle")
        Set<ClusterServer> clusterEntries = new HashSet<>()
        clusterEntries.add(clusterEntry)
        def loginEnumCluster = new LoginEnumCluster(clusterEntries, 2)
        def loginClusterStatus = new LoginClusterStatus(clusterEntries)
        def account = new SoeAccount()
        def enumerateCharacterId = new EnumerateCharacterId(account)
        def messageProcessor = new ReliableUdpMessageBuilder(null, networkConfiguration)

        def gameNetworkMessageSerializer = new GameNetworkMessageSerializerImpl(new MetricRegistry(), null)

        when:
        messageProcessor.add(gameNetworkMessageSerializer.writeToBuffer(loginClientToken))
        messageProcessor.add(gameNetworkMessageSerializer.writeToBuffer(loginEnumCluster))
        messageProcessor.add(gameNetworkMessageSerializer.writeToBuffer(loginClusterStatus))
        messageProcessor.add(gameNetworkMessageSerializer.writeToBuffer(enumerateCharacterId))
        def buffer = messageProcessor.buildNext()

        then:
        System.out.println(SoeMessageUtil.bytesToHex(buffer))
        noExceptionThrown()
    }
}