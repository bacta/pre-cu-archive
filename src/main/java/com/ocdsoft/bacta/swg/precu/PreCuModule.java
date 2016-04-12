package com.ocdsoft.bacta.swg.precu;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration;
import com.ocdsoft.bacta.engine.data.GameDatabaseConnector;
import com.ocdsoft.bacta.engine.object.NetworkIdGenerator;
import com.ocdsoft.bacta.engine.object.account.Account;
import com.ocdsoft.bacta.engine.security.authenticator.AccountService;
import com.ocdsoft.bacta.engine.security.password.PasswordHash;
import com.ocdsoft.bacta.engine.security.password.Pbkdf2SaltedPasswordHash;
import com.ocdsoft.bacta.engine.serialization.NetworkSerializer;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.engine.service.objectfactory.NetworkObjectFactory;
import com.ocdsoft.bacta.engine.service.objectfactory.impl.GuiceNetworkObjectFactory;
import com.ocdsoft.bacta.engine.service.scheduler.SchedulerService;
import com.ocdsoft.bacta.engine.service.scheduler.TaskSchedulerService;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseAccountService;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseGameDatabaseConnector;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseNetworkIdGenerator;
import com.ocdsoft.bacta.soe.dispatch.ObjectDispatcher;
import com.ocdsoft.bacta.soe.dispatch.SoeDevMessageDispatcher;
import com.ocdsoft.bacta.soe.dispatch.SoeMessageDispatcher;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.soe.service.SWGSessionKeyService;
import com.ocdsoft.bacta.soe.service.SessionKeyService;
import com.ocdsoft.bacta.swg.precu.data.GameObjectSerializer;
import com.ocdsoft.bacta.swg.precu.dispatch.PreCuObjectDispatcher;
import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.service.object.SceneObjectService;


public class PreCuModule extends AbstractModule implements Module {

	@Override
	protected void configure() {

        // Engine Level bindings
        bind(BactaConfiguration.class).to(IniBactaConfiguration.class);
        bind(NetworkObjectFactory.class).to(GuiceNetworkObjectFactory.class);
        bind(SchedulerService.class).to(TaskSchedulerService.class);
        bind(GameDatabaseConnector.class).to(CouchbaseGameDatabaseConnector.class);
        bind(NetworkIdGenerator.class).to(CouchbaseNetworkIdGenerator.class);
        bind(PasswordHash.class).to(Pbkdf2SaltedPasswordHash.class);
        bind(NetworkSerializer.class).to(GameObjectSerializer.class);
        bind(new TypeLiteral<ObjectService<SceneObject>>() {}).to(SceneObjectService.class);
        bind(new TypeLiteral<AccountService<SoeAccount>>(){}).to(new TypeLiteral<CouchbaseAccountService<SoeAccount>>(){});
        bind(Account.class).to(SoeAccount.class);

        // SOE Level Bindings
        bind(SessionKeyService.class).to(SWGSessionKeyService.class);
        bind(new TypeLiteral<ObjectDispatcher<ObjControllerMessage>>(){}).to(PreCuObjectDispatcher.class);
        bind(SoeMessageDispatcher.class).to(SoeDevMessageDispatcher.class);
        bind(ServerState.class).to(PreCuGameServerState.class);

        // SWG Level Bindings

        // Pre-cu specific bindings
        bind(GameServerState.class).to(PreCuGameServerState.class);

        // Singleton Bindings
        bind(MetricRegistry.class).in(Singleton.class);
    }

}
