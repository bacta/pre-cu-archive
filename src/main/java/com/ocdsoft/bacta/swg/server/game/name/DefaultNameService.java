
package com.ocdsoft.bacta.swg.server.game.name;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.name.generator.CreatureNameGenerator;
import com.ocdsoft.bacta.swg.server.game.name.generator.NameGenerator;
import com.ocdsoft.bacta.swg.server.game.name.generator.PlayerNameGenerator;
import com.ocdsoft.bacta.swg.server.game.util.Gender;
import com.ocdsoft.bacta.swg.server.game.util.Race;
import com.ocdsoft.bacta.tre.TreeFile;
import org.apache.commons.codec.language.ColognePhonetic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by kburkhardt on 3/28/14.
 */
@Singleton
public final class DefaultNameService implements NameService {

    private final Logger logger = LoggerFactory.getLogger(DefaultNameService.class);

    private final Set<String> fictionalNames;
    private final Set<String> developersNames;
    private final List<ProfaneWord> profaneWords;
    private final ColognePhonetic phonetic;
    private final Cache<String, Integer> lockedNames;

    private final Map<Integer, NameGenerator> nameGenerators;

    @Inject
    public DefaultNameService(final TreeFile treeFile,
                              final CreatureNameGenerator creatureNameGenerator,
                              final PlayerNameGenerator playerNameGenerator) throws IOException {

        nameGenerators = new HashMap<>();
        nameGenerators.put(NameService.CREATURE, creatureNameGenerator);
        nameGenerators.put(NameService.PLAYER, playerNameGenerator);
        lockedNames = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();

        phonetic = new ColognePhonetic();

        fictionalNames = loadList(this.getClass().getResourceAsStream("/name/fictionalreserved.lst"));
        developersNames = loadList(this.getClass().getResourceAsStream("/name/developers.lst"));
        profaneWords = new ArrayList<>();

        ResourceBundle bundle = ResourceBundle.getBundle("name.profane");
        for (String word : bundle.keySet()) {
            boolean substringMatch = bundle.getString(word).equalsIgnoreCase("1");
            ProfaneWord newWord = new ProfaneWord(word.toLowerCase(), substringMatch);
            profaneWords.add(newWord);
        }
//
//        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
//        final DataTable dataTable = dataTableReader.read(new ChunkReader("datatables/chat/profanity_filter.iff", treeFile.open("datatables/chat/profanity_filter.iff")));
//
//        for (DataTableRow row : dataTable.getRows()) {
//            String word = row.get(0).getString();
//            boolean substringMatch = row.get(1).getInt() == 1;
//            ProfaneWord newWord = new ProfaneWord(word.toLowerCase(), substringMatch);
//            profaneWords.add(newWord);
//        }
    }

    private Set<String> loadList(InputStream inputStream) {

        Set<String> newSet = new TreeSet<>();

        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            newSet.add(phonetic.encode(scanner.next()));
        }
        return newSet;
    }

    @Override
    public String generateName(int type, final Race race, final Gender gender) {
        NameGenerator generator = nameGenerators.get(type);
        if (generator == null) {
            logger.error("Name Generator for type " + type + " not available");
            return NAME_DECLINED_NO_NAME_GENERATOR;
        }

        String name = generator.createName(race, gender);
        while (!validateName(type, -1, name, race, gender).equals(NameService.NAME_APPROVED)) {
            name = generator.createName(race, gender);
        }

        return name;
    }

    @Override
    public String validateName(final int type, final int bactaId, final String name, final Race race, final Gender gender) {

        final Integer reservedForId = lockedNames.getIfPresent(name);
        if (reservedForId != null) {
            if(reservedForId != bactaId) {
                return NAME_DECLINED_TOO_FAST;
            }
        }

        NameGenerator generator = nameGenerators.get(type);
        if (generator == null) {
            logger.error("Name Generator for type " + type + " not available");
            return NAME_DECLINED_NO_NAME_GENERATOR;
        }

        StringTokenizer nameTokens = new StringTokenizer(name);
        while (nameTokens.hasMoreTokens()) {
            String token = nameTokens.nextToken();
            String result = namePreChecks(token);
            if (!result.equals(NameService.NAME_APPROVED)) {
                return result;
            }
        }

        return generator.validateName(name, race, gender);
    }

    @Override
    public String verifyAndLockName(final String name, final int bactaId, final Race race, final Gender gender) {
        String result = validateName(PLAYER, bactaId, name, race, gender);
        if (result.equals(NAME_APPROVED)) {
            lockedNames.put(name, bactaId);
        }

        return result;
    }

    private String namePreChecks(String name) {

        for (ProfaneWord word : profaneWords) {
            if (word.subStringMatch) {
                if (name.toLowerCase().contains(word.word)) {
                    return NAME_DECLINED_RESERVED;
                }
            } else {
                if (name.equalsIgnoreCase(word.word)) {
                    return NAME_DECLINED_RESERVED;
                }
            }
        }

        String encodedName = phonetic.encode(name);

        if (fictionalNames.contains(encodedName)) {
            return NAME_DECLINED_FICTIONALLY_RESERVED;
        }

        if (developersNames.contains(encodedName)) {
            return NAME_DECLINED_DEVELOPER;
        }

        return NAME_APPROVED;
    }

    @Override
    public void addPlayerName(String firstName) {
        PlayerNameGenerator nameGenerator = (PlayerNameGenerator) nameGenerators.get(NameService.PLAYER);
        nameGenerator.addPlayerName(firstName);
    }

    private class ProfaneWord {
        String word;
        boolean subStringMatch;

        public ProfaneWord(String word, boolean subStringMatch) {
            this.word = word;
            this.subStringMatch = subStringMatch;
        }
    }
}
