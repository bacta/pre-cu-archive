package com.ocdsoft.bacta.swg.server;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.ocdsoft.bacta.engine.data.GameDatabaseConnector;
import com.ocdsoft.bacta.engine.object.NetworkIdGenerator;
import com.ocdsoft.bacta.engine.serialize.NetworkSerializer;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.engine.service.objectfactory.NetworkObjectFactory;
import com.ocdsoft.bacta.soe.io.udp.GameNetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.MessageSubscriptionService;
import com.ocdsoft.bacta.swg.server.game.GameServer;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.login.object.ClusterServer;
import com.ocdsoft.bacta.swg.server.object.tangible.factory.GuiceNetworkObjectFactory;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseGameDatabaseConnector;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseNetworkIdGenerator;
import com.ocdsoft.bacta.soe.dispatch.CommandDispatcher;
import com.ocdsoft.bacta.soe.dispatch.ObjectDispatcher;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;
import com.ocdsoft.bacta.swg.server.data.GameObjectSerializer;
import com.ocdsoft.bacta.swg.server.dispatch.PreCuCommandDispatcher;
import com.ocdsoft.bacta.swg.server.dispatch.PreCuObjectDispatcher;
import com.ocdsoft.bacta.swg.server.message.game.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.server.message.game.object.command.CommandMessage;
import com.ocdsoft.bacta.swg.server.name.DefaultNameService;
import com.ocdsoft.bacta.swg.server.name.NameService;
import com.ocdsoft.bacta.swg.server.object.PreCuObjectTemplateList;
import com.ocdsoft.bacta.swg.server.object.ServerObject;
import com.ocdsoft.bacta.swg.server.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.service.GameMessageSubscriptionService;
import com.ocdsoft.bacta.swg.server.service.data.SetupSharedFile;
import com.ocdsoft.bacta.swg.server.service.data.SharedFileService;
import com.ocdsoft.bacta.swg.server.service.object.ServerObjectService;
import com.ocdsoft.bacta.swg.server.zone.PlanetMap;
import com.ocdsoft.bacta.swg.server.zone.ZoneMap;
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
        bind(GameServerState.class).to(PreCuGameServerState.class);
        bind(NetworkSerializer.class).to(GameObjectSerializer.class);
        bind(new TypeLiteral<ObjectService<ServerObject>>() {}).to(ServerObjectService.class);
        bind(GameDatabaseConnector.class).to(CouchbaseGameDatabaseConnector.class);
        bind(NetworkIdGenerator.class).to(CouchbaseNetworkIdGenerator.class);
        bind(new TypeLiteral<ObjectService<ServerObject>>() {}).to(ServerObjectService.class);
        bind(ObjectService.class).to(ServerObjectService.class);
        bind(NetworkObjectFactory.class).to(GuiceNetworkObjectFactory.class);
        //bind(new TypeLiteral<ContainerService<ServerObject>>(){}).to(PreCuContainerService.class);
        bind(new TypeLiteral<ObjectDispatcher<ObjControllerMessage>>(){}).to(PreCuObjectDispatcher.class);
        bind(ObjectDispatcher.class).to(PreCuObjectDispatcher.class);
        bind(new TypeLiteral<CommandDispatcher<CommandMessage, TangibleObject>>(){}).to(PreCuCommandDispatcher.class);


        bind(NameService.class).to(DefaultNameService.class);
        bind(ZoneMap.class).to(PlanetMap.class);
        bind(GameServerState.class).to(PreCuGameServerState.class);
        bind(ServerState.class).to(PreCuGameServerState.class);
        bind(NameService.class).to(DefaultNameService.class);
        bind(MessageSubscriptionService.class).to(GameMessageSubscriptionService.class);

        bind(ObjectTemplateList.class).to(PreCuObjectTemplateList.class);
    }

}
