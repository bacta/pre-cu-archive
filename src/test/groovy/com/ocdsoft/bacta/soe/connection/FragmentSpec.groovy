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

/**
 * Created by kyle on 5/29/2016.
 */
class FragmentSpec extends Specification {

    def "ProcessFragments"() {

        setup:
        def clusterSize = 15000
        def bactaConfig = new IniBactaConfiguration()
        def networkConfiguration = new GameNetworkConfiguration(bactaConfig)
        def gameState = new PreCuGameServerState(bactaConfig, networkConfiguration)

        def clusterIdField = GameNetworkConfiguration.class.getDeclaredField("clusterId")
        clusterIdField.setAccessible(true)

        def clusterName = ClusterServer.class.getDeclaredField("name")
        clusterName.setAccessible(true)

        Set<ClusterServer> clusterEntries = new HashSet<>()

        for(int i = 0; i < clusterSize; ++i)  {
            clusterIdField.set(networkConfiguration, 2 + i)
            assert 2 + i == networkConfiguration.getClusterId()
            def clusterServer = new ClusterServer(bactaConfig, networkConfiguration, gameState)
            clusterName.set(clusterServer, "Bacta")
            clusterEntries.add(clusterServer)
        }
        def loginEnumCluster = new LoginEnumCluster(clusterEntries, 2)
        def messageProcessor = new ReliableUdpMessageBuilder(null, networkConfiguration)

        def gameNetworkMessageSerializer = new GameNetworkMessageSerializerImpl(new MetricRegistry(), null)
        gameNetworkMessageSerializer.loadMessageClass(LoginEnumCluster.class)
        def fragmentContainer = new IncomingFragmentContainer()

        when:
        messageProcessor.add(gameNetworkMessageSerializer.writeToBuffer(loginEnumCluster))

        def combinedBuffer
        def nextBuffer;
        while((nextBuffer = messageProcessor.buildNext()) != null) {
            nextBuffer.position(4)
            combinedBuffer = fragmentContainer.addFragment(nextBuffer)
        }

        def combinedBufferSize = combinedBuffer.position()

        combinedBuffer.position(6)
        LoginEnumCluster message = gameNetworkMessageSerializer.readFromBuffer(SOECRC32.hashCode("LoginEnumCluster"), combinedBuffer)

        then:
        noExceptionThrown()
        message.getClusterDataSet().size() == clusterSize

    }
}
