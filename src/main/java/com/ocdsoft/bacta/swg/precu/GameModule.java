package com.ocdsoft.bacta.swg.precu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.ocdsoft.bacta.swg.precu.chat.ChatServerAgent;
import com.ocdsoft.bacta.swg.precu.chat.ChatServerAgentFactory;
import com.ocdsoft.bacta.swg.precu.chat.xmpp.XmppChatServerAgent;
import com.ocdsoft.bacta.swg.precu.factory.ObjControllerMessageFactory;
import com.ocdsoft.bacta.swg.precu.factory.ObjControllerMessageFactoryImpl;
import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.service.name.DefaultNameService;
import com.ocdsoft.bacta.swg.precu.service.name.NameService;
import com.ocdsoft.bacta.swg.precu.zone.PlanetMap;
import com.ocdsoft.bacta.swg.precu.zone.ZoneMap;

public class GameModule extends AbstractModule implements Module {

    @Override
    protected void configure() {

        install(new FactoryModuleBuilder()
                .implement(ChatServerAgent.class, XmppChatServerAgent.class)
                .build(ChatServerAgentFactory.class));

        bind(ObjControllerMessageFactory.class).to(ObjControllerMessageFactoryImpl.class);
        install(new FactoryModuleBuilder().build(ObjControllerMessageFactory.class));

        bind(NameService.class).to(DefaultNameService.class);

        bind(ZoneMap.class).to(PlanetMap.class);
    }

}
