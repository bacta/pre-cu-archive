package com.ocdsoft.bacta.swg.server;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration;
import com.ocdsoft.bacta.engine.data.ConnectionDatabaseConnector;
import com.ocdsoft.bacta.engine.object.account.Account;
import com.ocdsoft.bacta.engine.security.PasswordHash;
import com.ocdsoft.bacta.engine.security.Pbkdf2SaltedPasswordHash;
import com.ocdsoft.bacta.engine.service.AccountService;
import com.ocdsoft.bacta.engine.service.scheduler.SchedulerService;
import com.ocdsoft.bacta.engine.service.scheduler.TaskSchedulerService;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseAccountService;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseConnectionDatabaseConnector;
import com.ocdsoft.bacta.soe.dispatch.SoeDevMessageDispatcher;
import com.ocdsoft.bacta.soe.dispatch.SoeMessageDispatcher;
import com.ocdsoft.bacta.soe.serialize.GameNetworkMessageSerializer;
import com.ocdsoft.bacta.soe.service.SessionKeyService;
import com.ocdsoft.bacta.swg.server.login.object.SoeAccount;
import com.ocdsoft.bacta.swg.server.login.service.SWGSessionKeyService;
import com.ocdsoft.bacta.swg.shared.serialize.GameNetworkMessageSerializerImpl;


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
        bind(new TypeLiteral<AccountService<SoeAccount>>() {
        }).to(new TypeLiteral<CouchbaseAccountService<SoeAccount>>() {
        });

        // SWG Level Bindings


        // Pre-cu specific bindings


        // Singleton Bindings
        bind(MetricRegistry.class).in(Singleton.class);
    }

}
