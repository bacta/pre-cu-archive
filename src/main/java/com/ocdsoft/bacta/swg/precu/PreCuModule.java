package com.ocdsoft.bacta.swg.precu;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration;
import com.ocdsoft.bacta.engine.data.ConnectionDatabaseConnector;
import com.ocdsoft.bacta.engine.data.GameDatabaseConnector;
import com.ocdsoft.bacta.engine.object.NetworkIdGenerator;
import com.ocdsoft.bacta.engine.object.account.Account;
import com.ocdsoft.bacta.engine.security.authenticator.AccountService;
import com.ocdsoft.bacta.engine.security.password.PasswordHash;
import com.ocdsoft.bacta.engine.security.password.Pbkdf2SaltedPasswordHash;
import com.ocdsoft.bacta.engine.serialize.NetworkSerializer;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.engine.service.objectfactory.NetworkObjectFactory;
import com.ocdsoft.bacta.engine.service.objectfactory.impl.GuiceNetworkObjectFactory;
import com.ocdsoft.bacta.engine.service.scheduler.SchedulerService;
import com.ocdsoft.bacta.engine.service.scheduler.TaskSchedulerService;
import com.ocdsoft.bacta.soe.connection.ConnectionServerAgent;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseAccountService;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseConnectionDatabaseConnector;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseGameDatabaseConnector;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseNetworkIdGenerator;
import com.ocdsoft.bacta.soe.dispatch.CommandDispatcher;
import com.ocdsoft.bacta.soe.dispatch.ObjectDispatcher;
import com.ocdsoft.bacta.soe.dispatch.SoeDevMessageDispatcher;
import com.ocdsoft.bacta.soe.dispatch.SoeMessageDispatcher;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.soe.serialize.GameNetworkMessageSerializer;
import com.ocdsoft.bacta.soe.serialize.GameNetworkMessageSerializerImpl;
import com.ocdsoft.bacta.soe.service.SWGSessionKeyService;
import com.ocdsoft.bacta.soe.service.SessionKeyService;
import com.ocdsoft.bacta.swg.name.DefaultNameService;
import com.ocdsoft.bacta.swg.name.NameService;
import com.ocdsoft.bacta.swg.precu.connection.PreCuConnectionServerAgent;
import com.ocdsoft.bacta.swg.precu.data.GameObjectSerializer;
import com.ocdsoft.bacta.swg.precu.dispatch.PreCuCommandDispatcher;
import com.ocdsoft.bacta.swg.precu.dispatch.PreCuObjectDispatcher;
import com.ocdsoft.bacta.swg.precu.message.game.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.message.game.object.command.CommandMessage;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.service.data.SetupSharedFile;
import com.ocdsoft.bacta.swg.precu.service.object.SceneObjectService;
import com.ocdsoft.bacta.swg.precu.zone.PlanetMap;
import com.ocdsoft.bacta.swg.precu.zone.ZoneMap;
import com.ocdsoft.bacta.tre.TreeFile;


public class PreCuModule extends AbstractModule implements Module {

	@Override
	protected void configure() {

        // Engine Level bindings
        bind(BactaConfiguration.class).to(IniBactaConfiguration.class);
        bind(SchedulerService.class).to(TaskSchedulerService.class);
        bind(PasswordHash.class).to(Pbkdf2SaltedPasswordHash.class);
        bind(Account.class).to(SoeAccount.class);


        // SOE Level Bindings
        bind(SessionKeyService.class).to(SWGSessionKeyService.class);
        bind(SoeMessageDispatcher.class).to(SoeDevMessageDispatcher.class);
        bind(GameNetworkMessageSerializer.class).to(GameNetworkMessageSerializerImpl.class);
        bind(ConnectionDatabaseConnector.class).to(CouchbaseConnectionDatabaseConnector.class);
        bind(new TypeLiteral<AccountService<SoeAccount>>(){}).to(new TypeLiteral<CouchbaseAccountService<SoeAccount>>(){});


        // SWG Level Bindings


        // Pre-cu specific bindings



        // Singleton Bindings
        bind(MetricRegistry.class).in(Singleton.class);
    }

}
