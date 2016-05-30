package com.ocdsoft.bacta.swg.server.game.name.generator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.data.ConnectionDatabaseConnector;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.game.name.NameService;
import com.ocdsoft.bacta.swg.server.game.util.Gender;
import com.ocdsoft.bacta.swg.server.game.util.Race;

import java.util.Set;

/**
 * Created by Kyle on 8/17/2014.
 */
@Singleton
public class PlayerNameGenerator extends CreatureNameGenerator {

    private final Set<String> allFirstNames;

    @Inject
    public PlayerNameGenerator(GameServerState serverState, ConnectionDatabaseConnector databaseConnector) {
        allFirstNames = databaseConnector.getClusterCharacterSet(serverState.getClusterId());
    }

    @Override
    public String validateName(final String name, final Race race, final Gender gender) {

        String firstName = name.indexOf(" ") != -1 ? name.substring(0, name.indexOf(" ")) : name;

        if (allFirstNames.contains(firstName.toLowerCase())) {
            return NameService.NAME_DECLINED_IN_USE;
        }

        return super.validateName(name, race, gender);
    }

    public void addPlayerName(String firstName) {
        if (!allFirstNames.add(firstName)) {
            throw new RuntimeException("Created a character with a duplicate first name");
        }
    }
}
