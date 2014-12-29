package com.ocdsoft.bacta.swg.precu.service.name.generator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.network.soe.ServerState;
import com.ocdsoft.bacta.swg.network.soe.data.CouchbaseDatabaseConnector;
import com.ocdsoft.bacta.swg.server.game.service.name.NameService;
import com.ocdsoft.bacta.swg.server.game.util.Gender;
import com.ocdsoft.bacta.swg.server.game.util.Race;

import java.security.InvalidParameterException;
import java.util.Set;

/**
 * Created by Kyle on 8/17/2014.
 */
@Singleton
public class PlayerNameGenerator extends CreatureNameGenerator {

    private final Set<String> allFirstNames;

    @Inject
    public PlayerNameGenerator(ServerState serverState, CouchbaseDatabaseConnector databaseConnector) {
        allFirstNames = databaseConnector.getClusterCharacterSet(serverState.getId());
    }

    @Override
    public String validateName(String name, Object... args) {

        Race race;
        Gender gender;

        try {
            race = (Race) args[0];
            gender = (Gender) args[1];
        } catch (Exception e) {
            throw new InvalidParameterException("Expecting args [Race, Gender]");
        }

        String firstName = name.indexOf(" ") != -1 ? name.substring(0, name.indexOf(" ")) : name;

        if(allFirstNames.contains(firstName.toLowerCase())) {
            return NameService.NAME_DECLINED_IN_USE;
        }

        return super.validateName(name, args);
    }

    public void addPlayerName(String firstName) {
        if(!allFirstNames.add(firstName)) {
            throw new RuntimeException("Created a character with a duplicate first name");
        }
    }
}
