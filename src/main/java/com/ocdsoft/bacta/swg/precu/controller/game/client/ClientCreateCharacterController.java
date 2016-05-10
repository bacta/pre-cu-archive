package com.ocdsoft.bacta.swg.precu.controller.game.client;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.security.authenticator.AccountService;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.object.account.CharacterInfo;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.lang.Gender;
import com.ocdsoft.bacta.swg.lang.Race;
import com.ocdsoft.bacta.swg.name.NameService;
import com.ocdsoft.bacta.swg.precu.message.ErrorMessage;
import com.ocdsoft.bacta.swg.precu.message.game.client.ClientCreateCharacter;
import com.ocdsoft.bacta.swg.precu.message.game.client.ClientCreateCharacterFailed;
import com.ocdsoft.bacta.swg.precu.message.game.client.ClientCreateCharacterSuccess;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerCreatureObjectTemplate;
import com.ocdsoft.bacta.swg.precu.object.template.shared.SharedCreatureObjectTemplate;
import com.ocdsoft.bacta.swg.precu.service.data.ObjectTemplateService;
import com.ocdsoft.bacta.swg.precu.service.data.creation.*;
import com.ocdsoft.bacta.swg.precu.service.data.customization.AllowBald;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import com.ocdsoft.bacta.swg.shared.network.messages.chat.ChatAvatarId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@MessageHandled(handles = ClientCreateCharacter.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class ClientCreateCharacterController implements GameNetworkMessageController<ClientCreateCharacter> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCreateCharacterController.class);

    private final ObjectService<ServerObject> objectService;
    //    private final ZoneMap zoneMap;
    private final AccountService<SoeAccount> accountService;
    private final GameServerState serverState;
    private final ObjectTemplateService templateService;
    private final LoadoutEquipment loadoutEquipment;
    private final ProfessionMods professionMods;
    private final ProfessionDefaults professionDefaults;
    private final HairStyles hairStyles;
    private final BactaConfiguration bactaConfiguration;
    private final NameService nameService;
    private final AllowBald allowBald;
    private final StartingLocations startingLocations;
    //private final ContainerService<ServerObject> containerService;

    private final int minutesBetweenCreation;
    private final String defaultProfession;
    private final Set<String> disabledProfessions;

    //TODO: everything
    @Inject
    public ClientCreateCharacterController(
            final ObjectTemplateService templateService,
            final ObjectService<ServerObject> objectService,
//            final ZoneMap zoneMap,
            final AccountService<SoeAccount> accountService,
            final GameServerState serverState,
            final LoadoutEquipment loadoutEquipment,
            final ProfessionMods professionMods,
            final ProfessionDefaults professionDefaults,
            final HairStyles hairStyles,
            final AllowBald allowBald,
            final StartingLocations startingLocations,
            //final ContainerService<ServerObject> containerService,
            final BactaConfiguration bactaConfiguration,
            final NameService nameService) {

        //this.sharedFileService = sharedFileService;
//        this.zoneMap = zoneMap;
        this.objectService = objectService;
        this.accountService = accountService;
        this.serverState = serverState;
        this.templateService = templateService;
        this.loadoutEquipment = loadoutEquipment;
        this.professionMods = professionMods;
        this.professionDefaults = professionDefaults;
        this.bactaConfiguration = bactaConfiguration;
        this.hairStyles = hairStyles;
        this.allowBald = allowBald;
        this.nameService = nameService;
        this.startingLocations = startingLocations;
        //this.containerService = containerService;

        this.minutesBetweenCreation = bactaConfiguration.getIntWithDefault(
                "Bacta/GameServer/CharacterCreation",
                "MinutesBetweenCharCreate",
                15);

        this.disabledProfessions = new HashSet<>(bactaConfiguration.getStringCollection(
                "Bacta/GameServer/CharacterCreation",
                "DisabledProfession"));

        this.defaultProfession = bactaConfiguration.getStringWithDefault(
                "Bacta/GameServer/CharacterCreation",
                "DefaultProfession",
                "crafting_artisan");
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, ClientCreateCharacter message) {

        final SoeAccount account = accountService.getAccount(connection.getAccountUsername());

        if (account == null) {
            ErrorMessage error = new ErrorMessage("Error", "Account not found.", false);
            connection.sendMessage(error);
            LOGGER.info("Account <{}> with username {} was not found.",
                    account.getId(),
                    account.getUsername());
            return;
        }

        String profession = message.getProfession();
        if (this.disabledProfessions.contains(profession)) {
            profession = this.defaultProfession;
        }

        //TODO: Remove this after service side hair templates have been created...
        StringBuilder stringBuilder = new StringBuilder(message.getHairTemplateName());
        stringBuilder.insert(message.getHairTemplateName().lastIndexOf('/') + 1, "shared_");
        String hairTemplate = stringBuilder.toString();

        ServerCreatureObjectTemplate objectTemplate = templateService.getObjectTemplate(message.getTemplateName());

        if (objectTemplate == null) {

            LOGGER.error("Account <{}> attempted to create a player with invalid player template <{}>.",
                    account.getUsername(),
                    message.getTemplateName());

            connection.sendMessage(new ClientCreateCharacterFailed(NameService.NAME_DECLINED_NO_TEMPLATE));
            return;
        }

        ProfessionMods.ProfessionModInfo professionModInfo = professionMods.getProfessionModInfo(profession);
        ProfessionDefaults.ProfessionInfo professionInfo = professionDefaults.getProfessionInfo(profession);
        HairStyles.HairStyleInfo hairStyleInfo = hairStyles.getHairStyleInfo(objectTemplate.getResourceName());
        StartingLocations.StartingLocationInfo startingLocationInfo = startingLocations.getStartingLocationInfo(message.getStartingLocation());

        if (/*hairStyleInfo == null || */professionModInfo == null /*|| professionInfo == null*/) {
            //Only way for this to happen is if client data is not loaded or missing.
            LOGGER.error("Unable to retrieve profession information for profession <{}>.", profession);
            connection.sendMessage(new ClientCreateCharacterFailed(NameService.NAME_DECLINED_INTERNAL_ERROR));
            return;
        }

        //TODO: Remove this...it should just use the enums.
        String genderSpecies = message.getTemplateName().substring(message.getTemplateName().lastIndexOf('/') + 1, message.getTemplateName().length() - 4); //Gets the file name of the racefile. i.e. human_male.

        Race race = Race.valueOf(genderSpecies.substring(0, genderSpecies.indexOf("_")).toUpperCase());
        Gender gender = Gender.valueOf(genderSpecies.substring(genderSpecies.indexOf("_") + 1).toUpperCase());

        String firstName = message.getCharacterName().split(" ", 2)[0];

        String result = nameService.validateName(NameService.PLAYER, message.getCharacterName(), race, gender);

        if (result.equals(NameService.NAME_DECLINED_DEVELOPER) && firstName.equalsIgnoreCase(account.getUsername())) {
            result = NameService.NAME_APPROVED;
        }

        if (!result.equals(NameService.NAME_APPROVED)) {
            ClientCreateCharacterFailed failed = new ClientCreateCharacterFailed(result);
            connection.sendMessage(failed);
            return;
        }

        // check duration
        int minutesSinceLastCreation = (int)((System.currentTimeMillis() - account.getLastCharacterCreationTime()) / 1000 / 60);

        if(minutesSinceLastCreation < minutesBetweenCreation) {
            ClientCreateCharacterFailed failed = new ClientCreateCharacterFailed(NameService.NAME_DECLINED_TOO_FAST);
            connection.sendMessage(failed);
            return;
        }

        CreatureObject character = objectService.createObject(1, objectTemplate.getSharedTemplate());
//        character.setCondition(TangibleObject.Conditions.onOff);

        nameService.addPlayerName(firstName);

//        character.setAppearanceData(appearanceData); //TODO: appearance data validation soon.

        //TODO: Scale
//        if (message.getScaleFactor() > objectTemplate.getScaleMax() || message.getScaleFactor() < objectTemplate.getScaleMin()) {
//            LOGGER.error("Account <{}> attempted to create a character with scale factor of {} which is outside acceptable range.",
//                    account.getUsername(),
//                    message.getScaleFactor());
//
//            connection.sendMessage(new ClientCreateCharacterFailed(message.getCharacterName(), NameService.NAME_DECLINED_CANT_CREATE_AVATAR));
//            return;
//        }

//        character.setScaleFactor(scaleFactor);
//        character.setObjectName(new UnicodeString(characterName));
//        character.setRunSpeed(objectTemplate.getSpeed(SharedCreatureObjectTemplate.MovementTypes.Run));
//        character.setWalkSpeed(objectTemplate.getSpeed(SharedCreatureObjectTemplate.MovementTypes.Walk));
//        character.setSlopeModAngle(objectTemplate.getSlopeModAngle());
//        character.setSlopeModPercent(objectTemplate.getSlopeModPercent());
//        character.setWaterModPercent(objectTemplate.getWaterModPercent());

        //TODO: Change this to take the player to the tutorial zone when it is implemented.
        //TODO: Position should be a random point within the radius of these coordinates.
        final Vector position = new Vector(
                startingLocationInfo.getX(),
                startingLocationInfo.getY(),
                startingLocationInfo.getZ());

        final Transform transform = new Transform();
        transform.setPositionInParentSpace(position);
        transform.yaw(startingLocationInfo.getHeading());

        character.setTransform(transform);

        PlayerObject ghost = objectService.createObject(0, "object/player/shared_player.iff");
//        ghost.setClient(client);
//        ghost.setBiography(biography);
//        ghost.setInitialized();

        //containerService.transferItemToContainer(character, ghost);

//        TangibleObject bank = objectService.createObject(0, "object/tangible/bank/shared_character_bank.iff");
//        bank.setInitialized();
//        containerService.transferItemToContainer(character, bank);
//
//        TangibleObject datapad = objectService.createObject(0, "object/tangible/datapad/shared_character_datapad.iff");
//        datapad.setInitialized();
//        containerService.transferItemToContainer(character, datapad);
//
//        TangibleObject missionBag = objectService.createObject(0, "object/tangible/mission_bag/shared_mission_bag.iff");
//        missionBag.setInitialized();
//        containerService.transferItemToContainer(character, missionBag);
//
//        TangibleObject inventory = objectService.createObject(0, "object/tangible/inventory/shared_character_inventory.iff");
//        inventory.setInitialized();
//        containerService.transferItemToContainer(character, inventory);

//        if (!hairStyleInfo.containsTemplate(hairTemplate) && !allowBald.isAllowedBald(resourceName)) {//sharedTemplate.getResourceName())) {
//            LOGGER.error("Invalid hair style <{}> chosen. Setting to default hair style.", hairTemplate);
//            hairTemplate = hairStyleInfo.getDefaultTemplate();
//        }

        hairTemplate = "object/tangible/hair/human/hair_human_male_s01.iff";

//        TangibleObject hair = objectService.createObject(0, hairTemplate);
//
//        if (hair != null) {
//            hair.setAppearanceData(hairAppearanceData);
//            hair.setInitialized();
//            containerService.transferItemToContainer(character, hair);
//        } else {
//            logger.error("Failed to create hair with template <{}>.", hairTemplateName);
//        }

//        character.setAttributes(professionModInfo.getAttributes());
//        character.setMaxAttributes(professionModInfo.getAttributes());
//        character.setUnmodifiedMaxAttributes(professionModInfo.getAttributes());

//        for (String skill : professionInfo.getSkillsList())
//            character.addSkill(skill);
//
//        for (String itemTemplate : professionInfo.getItemTemplates(sharedTemplate.getName())) {
//            logger.debug("Creating profession item with template <{}>.", itemTemplate);
//            SceneObject item = objectService.createObject(0, itemTemplate);
//            item.setInitialized();
//            containerService.transferItemToContainer(character, item);
//        }

//        character.setInitialized();

        ClientCreateCharacterSuccess success = new ClientCreateCharacterSuccess(character.getNetworkId());
        connection.sendMessage(success);

        //Post character setup.
        CharacterInfo info = new CharacterInfo(
                message.getCharacterName(),
                SOECRC32.hashCode(message.getTemplateName()),
                character.getNetworkId(),
                serverState.getId(),
                CharacterInfo.Type.NORMAL,
                false
        );

        //Create a new chat avatar id, then register it with the chat server.
        ChatAvatarId chatAvatarId = new ChatAvatarId(
                bactaConfiguration.getStringWithDefault("Bacta/GameServer", "Game", "SWG"),
                bactaConfiguration.getStringWithDefault("Bacta/GameServer", "Name", "Bacta"),
                firstName
        );

        //TODO: Register chat avatar id here only.
        //TODO: Where can we stash the chat avatar id...

        account.addCharacter(info);
        account.setLastCharacterCreationTime(System.currentTimeMillis());
        accountService.updateAccount(account);

        LOGGER.trace("Successfully created character {} with network id {}. ",
                message.getCharacterName(),
                character.getNetworkId());

        objectService.updateObject(character);
    }
}

