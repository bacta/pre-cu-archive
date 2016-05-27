package com.ocdsoft.bacta.swg.server.game.data;

import com.esotericsoftware.kryo.Kryo;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.serialize.KryoSerializer;
import com.ocdsoft.bacta.engine.serialize.NetworkObjectSerializer;
import com.ocdsoft.bacta.swg.archive.delta.*;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.server.game.object.matchmaking.MatchMakingId;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.object.universe.group.GroupInviter;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import de.javakaffee.kryoserializers.BitSetSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by Kyle on 8/14/2014.
 */
@Singleton
public final class GameObjectSerializer extends KryoSerializer {

    private static final Logger logger = LoggerFactory.getLogger(GameObjectSerializer.class);

    @Inject
    public GameObjectSerializer(Injector injector) {
        super(injector);
    }

    @Override
    public void registerTypes(Kryo kryo, Injector injector) {
        kryo.register(ArrayList.class);
        kryo.register(HashMap.class);
        kryo.register(TreeSet.class);

        kryo.register(ServerObject.class, injector.getInstance(NetworkObjectSerializer.class));
        kryo.register(TangibleObject.class, injector.getInstance(NetworkObjectSerializer.class));
        kryo.register(CreatureObject.class, injector.getInstance(NetworkObjectSerializer.class));
        kryo.register(PlayerObject.class, injector.getInstance(NetworkObjectSerializer.class));

        kryo.register(AutoDeltaFloat.class);
        kryo.register(AutoDeltaByteStream.class);
        kryo.register(AutoDeltaVariable.class);
        kryo.register(AutoDeltaInt.class);
        kryo.register(AutoDeltaString.class);
        kryo.register(AutoDeltaBoolean.class);
        kryo.register(AutoDeltaLong.class);
        kryo.register(AutoDeltaByte.class);
        kryo.register(AutoDeltaShort.class);

        kryo.register(GroupInviter.class);
        kryo.register(MatchMakingId.class);
        kryo.register(BitSet.class, new BitSetSerializer());
        kryo.register(Quat4f.class);
        kryo.register(Vector3f.class);
        kryo.register(StringId.class);
    }
}
