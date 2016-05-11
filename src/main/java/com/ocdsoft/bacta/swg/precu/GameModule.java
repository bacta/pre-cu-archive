package com.ocdsoft.bacta.swg.precu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.ocdsoft.bacta.engine.data.GameDatabaseConnector;
import com.ocdsoft.bacta.engine.object.NetworkIdGenerator;
import com.ocdsoft.bacta.engine.serialize.NetworkSerializer;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.engine.service.objectfactory.NetworkObjectFactory;
import com.ocdsoft.bacta.engine.service.objectfactory.impl.GuiceNetworkObjectFactory;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.connection.ConnectionServerAgent;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseGameDatabaseConnector;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseNetworkIdGenerator;
import com.ocdsoft.bacta.soe.dispatch.CommandDispatcher;
import com.ocdsoft.bacta.soe.dispatch.ObjectDispatcher;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.game.GameNetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.game.GameServer;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;
import com.ocdsoft.bacta.swg.name.DefaultNameService;
import com.ocdsoft.bacta.swg.name.NameService;
import com.ocdsoft.bacta.swg.precu.connection.PreCuConnectionServerAgent;
import com.ocdsoft.bacta.swg.precu.data.GameObjectSerializer;
import com.ocdsoft.bacta.swg.precu.dispatch.PreCuCommandDispatcher;
import com.ocdsoft.bacta.swg.precu.dispatch.PreCuObjectDispatcher;
import com.ocdsoft.bacta.swg.precu.message.game.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.message.game.object.command.CommandMessage;
import com.ocdsoft.bacta.swg.precu.object.PreCuObjectTemplateList;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.service.data.SetupSharedFile;
import com.ocdsoft.bacta.swg.precu.service.data.SharedFileService;
import com.ocdsoft.bacta.swg.precu.service.object.ServerObjectService;
import com.ocdsoft.bacta.swg.precu.zone.PlanetMap;
import com.ocdsoft.bacta.swg.precu.zone.ZoneMap;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

public class GameModule extends AbstractModule implements Module {

    @Override
    protected void configure() {

        bind(SetupSharedFile.class).asEagerSingleton();
        bind(SharedFileService.class).asEagerSingleton();
        bind(SlotIdManager.class).asEagerSingleton();

        bind(NetworkConfiguration.class).to(GameNetworkConfiguration.class);
        bind(OutgoingConnectionService.class).to(GameServer.GameOutgoingConnectionService.class);
        bind(new TypeLiteral<GameServerState<ClusterEntry>>(){}).to(PreCuGameServerState.class);
        bind(GameServerState.class).to(PreCuGameServerState.class);
        bind(NetworkSerializer.class).to(GameObjectSerializer.class);
        bind(new TypeLiteral<ObjectService<ServerObject>>() {}).to(ServerObjectService.class);
        bind(GameDatabaseConnector.class).to(CouchbaseGameDatabaseConnector.class);
        bind(NetworkIdGenerator.class).to(CouchbaseNetworkIdGenerator.class);
        bind(new TypeLiteral<ObjectService<ServerObject>>() {}).to(ServerObjectService.class);
        bind(ObjectService.class).to(ServerObjectService.class);
        bind(NetworkObjectFactory.class).to(GuiceNetworkObjectFactory.class);
        bind(new TypeLiteral<ObjectDispatcher<ObjControllerMessage>>(){}).to(PreCuObjectDispatcher.class);
        //bind(new TypeLiteral<ContainerService<ServerObject>>(){}).to(PreCuContainerService.class);
        bind(new TypeLiteral<CommandDispatcher<CommandMessage, TangibleObject>>(){}).to(PreCuCommandDispatcher.class);
        bind(new TypeLiteral<ObjectDispatcher<ObjControllerMessage>>(){}).to(PreCuObjectDispatcher.class);
        bind(ConnectionServerAgent.class).to(PreCuConnectionServerAgent.class);
        bind(NameService.class).to(DefaultNameService.class);
        bind(ZoneMap.class).to(PlanetMap.class);
        bind(new TypeLiteral<GameServerState<ClusterEntry>>(){}).to(PreCuGameServerState.class);
        bind(GameServerState.class).to(PreCuGameServerState.class);
        bind(ServerState.class).to(PreCuGameServerState.class);
        bind(NameService.class).to(DefaultNameService.class);

        bind(ObjectTemplateList.class).to(PreCuObjectTemplateList.class);
    }

}
