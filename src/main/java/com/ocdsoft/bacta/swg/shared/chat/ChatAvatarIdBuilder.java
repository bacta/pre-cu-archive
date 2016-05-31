package com.ocdsoft.bacta.swg.shared.chat;

import com.google.common.base.Splitter;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatAvatarId;

import java.util.List;

/**
 * Created by crush on 5/30/2016.
 * <p>
 * Builds a ChatAvatarId when it is unknown if the input String has the gameCode or cluster information attached.
 */
public final class ChatAvatarIdBuilder {
    private static final Splitter NAME_SPLITTER = Splitter.on('.').limit(3).omitEmptyStrings().trimResults();

    private final String defaultGameCode;
    private final String defaultCluster;

    private ChatAvatarIdBuilder(final String defaultGameCode, final String defaultCluster) {
        this.defaultGameCode = defaultGameCode;
        this.defaultCluster = defaultCluster;
    }

    public static ChatAvatarIdBuilder newBuilder() {
        return new ChatAvatarIdBuilder("", "");
    }

    /**
     * Sets a default game code to use in case the input string for build doesn't have a game code.
     *
     * @param gameCode The game code to use if none is specified.
     * @return Instance of the builder.
     */
    public ChatAvatarIdBuilder withDefaultGameCode(final String gameCode) {
        return new ChatAvatarIdBuilder(gameCode, this.defaultCluster);
    }

    /**
     * Sets a default cluster name to use in case the input string for build doesn't have a cluster name.
     *
     * @param cluster The cluster name to use if none is specified.
     * @return Instance of the builder.
     */
    public ChatAvatarIdBuilder withDefaultCluster(final String cluster) {
        return new ChatAvatarIdBuilder(this.defaultGameCode, cluster);
    }

    public ChatAvatarId build(final String name) {
        final List<String> parts = NAME_SPLITTER.splitToList(name);
        final int size = parts.size();

        if (size == 1)
            return new ChatAvatarId(defaultGameCode, defaultCluster, parts.get(0));

        if (size == 2)
            return new ChatAvatarId(defaultGameCode, parts.get(0), parts.get(1));

        return new ChatAvatarId(parts.get(0), parts.get(1), parts.get(2));
    }
}
