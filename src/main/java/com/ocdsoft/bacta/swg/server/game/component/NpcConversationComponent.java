package com.ocdsoft.bacta.swg.server.game.component;

/**
 * Created by crush on 9/5/2014.
 */
public abstract class NpcConversationComponent implements ExtensibleComponent {
    @Override
    public final int getComponentType() { return ComponentTypes.NpcConversationComponent; }

    public abstract void onStartConversation();
    public abstract void onSelectConversationChoice();
    public abstract void onStopConverstaion();
}
