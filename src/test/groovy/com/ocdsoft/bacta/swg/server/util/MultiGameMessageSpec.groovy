package com.ocdsoft.bacta.swg.precu.util

import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration
import com.ocdsoft.bacta.soe.connection.ReliableUdpMessageBuilder
import com.ocdsoft.bacta.soe.io.udp.game.GameNetworkConfiguration
import com.ocdsoft.bacta.soe.object.account.SoeAccount
import com.ocdsoft.bacta.soe.serialize.GameNetworkMessageSerializerImpl
import com.ocdsoft.bacta.soe.util.SOECRC32
import com.ocdsoft.bacta.soe.util.SoeMessageUtil
import com.ocdsoft.bacta.swg.precu.message.login.EnumerateCharacterId
import com.ocdsoft.bacta.swg.precu.message.login.LoginClientToken
import com.ocdsoft.bacta.swg.precu.message.login.LoginClusterStatus
import com.ocdsoft.bacta.swg.precu.message.login.LoginEnumCluster
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry
import spock.lang.Specification

/**
 * Created by kburkhardt on 2/10/15.
 */
class MultiGameMessageSpec extends Specification {

    def "BuildMessage"() {
        
        setup:
        def bactaConfig = new IniBactaConfiguration()
        def clusterEntry = new ClusterEntry(bactaConfig)
        def loginClientToken = new LoginClientToken("Test", 0, "kyle")
        Set<ClusterEntry> clusterEntries = new HashSet<>()
        clusterEntries.add(clusterEntry)
        def loginEnumCluster = new LoginEnumCluster(clusterEntries, 2)
        def loginClusterStatus = new LoginClusterStatus(clusterEntries)
        def account = new SoeAccount()
        def enumerateCharacterId = new EnumerateCharacterId(account)
        def networkConfig = new GameNetworkConfiguration(bactaConfig)
        def messageProcessor = new ReliableUdpMessageBuilder(null, networkConfig)

        def gameNetworkMessageSerializer = new GameNetworkMessageSerializerImpl()
        gameNetworkMessageSerializer.addHandledMessageClass(SOECRC32.hashCode(LoginClientToken.class.simpleName), LoginClientToken.class)
        gameNetworkMessageSerializer.addHandledMessageClass(SOECRC32.hashCode(LoginEnumCluster.class.simpleName), LoginEnumCluster.class)
        gameNetworkMessageSerializer.addHandledMessageClass(SOECRC32.hashCode(LoginClusterStatus.class.simpleName), LoginClusterStatus.class)
        gameNetworkMessageSerializer.addHandledMessageClass(SOECRC32.hashCode(EnumerateCharacterId.class.simpleName), EnumerateCharacterId.class)

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