package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;

import com.ocdsoft.bacta.engine.security.authenticator.AccountService;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.ClientCreateCharacter;
import com.ocdsoft.bacta.swg.precu.message.ClientCreateCharacterFailed;
import com.ocdsoft.bacta.swg.precu.message.ClientCreateCharacterSuccess;
import com.ocdsoft.bacta.swg.precu.message.ErrorMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.precu.service.data.ObjectTemplateService;
import com.ocdsoft.bacta.swg.precu.service.data.creation.*;
import com.ocdsoft.bacta.swg.precu.service.data.customization.AllowBald;
import com.ocdsoft.bacta.swg.precu.service.name.NameService;
import com.ocdsoft.bacta.swg.precu.util.Gender;
import com.ocdsoft.bacta.swg.precu.util.Race;
import com.ocdsoft.bacta.swg.precu.zone.ZoneMap;
import com.ocdsoft.bacta.swg.shared.object.template.SharedCreatureObjectTemplate;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@GameNetworkMessageHandled(ClientCreateCharacter.class)
public class ClientCreateCharacterController implements GameNetworkMessageController<ClientCreateCharacter> {

	private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
	
	private final ObjectService<SceneObject> objectService;
	private final ZoneMap zoneMap;
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
    private final ContainerService containerService;

    private final int minutesBetweenCreation;
    private final String defaultProfession;
    private final Set<String> disabledProfessions;


    @Inject
	public ClientCreateCharacterController(
            ObjectTemplateService templateService,
            ObjectService<SceneObject> objectService,
			ZoneMap zoneMap,
            AccountService<SoeAccount> accountService,
            GameServerState serverState,
            LoadoutEquipment loadoutEquipment,
            ProfessionMods professionMods,
            ProfessionDefaults professionDefaults,
            HairStyles hairStyles,
            AllowBald allowBald,
            StartingLocations startingLocations,
            ContainerService containerService,
            BactaConfiguration bactaConfiguration,
            NameService nameService) {
	
		//this.sharedFileService = sharedFileService;
		this.zoneMap = zoneMap;
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
        this.containerService = containerService;

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
    public void handleIncoming(SoeUdpConnection connection, ClientCreateCharacter message) throws Exception {

        final SoeAccount account = accountService.getAccount(connection.getAccountUsername());

        if (account == null) {
            ErrorMessage errorMessage = new ErrorMessage("Error", "Account not found.", false);
            connection.sendMessage(errorMessage);
            logger.info("Account <{}> with username {} was not found.",
                    account.getId(),
                    account.getUsername());
            return;
        }

        if (this.disabledProfessions.contains(message.getProfession())) {
            message.setProfession(this.defaultProfession);
        }

        //TODO: Remove this after service side hair templates have been created...
        String hairTemplate = message.getHairTemplateName();
        StringBuilder stringBuilder = new StringBuilder(hairTemplate);
        stringBuilder.insert(hairTemplate.lastIndexOf('/') + 1, "shared_");
        message.setHairTemplateName(stringBuilder.toString());

        SharedCreatureObjectTemplate objectTemplate = templateService.getObjectTemplate(templateName);
        SharedCreatureObjectTemplate sharedTemplate = (SharedCreatureObjectTemplate) objectTemplate.getBaseData();

        if (objectTemplate == null
                || !(objectTemplate instanceof SharedCreatureObjectTemplate) //TODO: Change this to ServerCreatureObjectTemplate when ready.
                || sharedTemplate == null) {
            logger.error("Account <{}> attempted to create a player with invalid player template <{}>.",
                    account.getUsername(),
                    templateName);

            client.sendMessage(new ClientCreateCharacterFailed(characterName, NameService.NAME_DECLINED_NO_TEMPLATE));
            return;
        }

        ProfessionMods.ProfessionModInfo professionModInfo = professionMods.getProfessionModInfo(profession);
        ProfessionDefaults.ProfessionInfo professionInfo = professionDefaults.getProfessionInfo(profession);
        HairStyles.HairStyleInfo hairStyleInfo = hairStyles.getHairStyleInfo(sharedTemplate.getName());
        StartingLocations.StartingLocationInfo startingLocationInfo = startingLocations.getStartingLocationInfo(startingLocation);

        if (hairStyleInfo == null || professionModInfo == null || professionInfo == null) {
            //Only way for this to happen is if client data is not loaded or missing.
            logger.error("Unable to retrieve profession information for profession <{}>.", profession);
            client.sendMessage(new ClientCreateCharacterFailed(characterName, NameService.NAME_DECLINED_INTERNAL_ERROR));
            return;
        }

        //TODO: Remove this...it should just use the enums.
		String genderSpecies = templateName.substring(templateName.lastIndexOf('/') + 1, templateName.length() - 4); //Gets the file name of the racefile. i.e. human_male.

        Race race = Race.valueOf(genderSpecies.substring(0, genderSpecies.indexOf("_")).toUpperCase());
        Gender gender = Gender.valueOf(genderSpecies.substring(genderSpecies.indexOf("_") + 1).toUpperCase());

        String firstName = characterName.split(" ", 2)[0];

        String result = nameService.validateName(NameService.PLAYER, characterName, race, gender);

        if (result == NameService.NAME_DECLINED_DEVELOPER && firstName.equalsIgnoreCase(account.getUsername())) {
            result = NameService.NAME_APPROVED;
        }

        if (result != NameService.NAME_APPROVED) {
            ClientCreateCharacterFailed failed = new ClientCreateCharacterFailed(characterName, result);
            client.sendMessage(failed);
            return;
        }

        // check duration
        int minutesSinceLastCreation = (int)((System.currentTimeMillis() - account.getLastCharacterCreationTime()) / 1000 / 60);

        if(minutesSinceLastCreation < minutesBetweenCreation) {
            ClientCreateCharacterFailed failed = new ClientCreateCharacterFailed(characterName, NameService.NAME_DECLINED_TOO_FAST);
            client.sendMessage(failed);
            return;
        }

        CreatureObject character = objectService.createObject(1, sharedTemplate.getName());
        character.setCondition(TangibleObject.Conditions.onOff);

        nameService.addPlayerName(firstName);

        character.setAppearanceData(appearanceData); //TODO: appearance data validation soon.

        if (scaleFactor > objectTemplate.getScaleMax() || scaleFactor < objectTemplate.getScaleMin()) {
            logger.error("Account <{}> attempted to create a character with scale factor of {} which is outside acceptable range.",
                    account.getUsername(),
                    scaleFactor);

            client.sendMessage(new ClientCreateCharacterFailed(characterName, NameService.NAME_DECLINED_CANT_CREATE_AVATAR));
            return;
        }

        character.setScaleFactor(scaleFactor);
        character.setObjectName(new UnicodeString(characterName));
        character.setRunSpeed(objectTemplate.getSpeed(SharedCreatureObjectTemplate.MovementTypes.Run));
        character.setWalkSpeed(objectTemplate.getSpeed(SharedCreatureObjectTemplate.MovementTypes.Walk));
        character.setSlopeModAngle(objectTemplate.getSlopeModAngle());
        character.setSlopeModPercent(objectTemplate.getSlopeModPercent());
        character.setWaterModPercent(objectTemplate.getWaterModPercent());

        //TODO: Change this to take the player to the tutorial zone when it is implemented.
        character.setPosition(
                startingLocationInfo.getX(),
                startingLocationInfo.getY(),
                startingLocationInfo.getZ()
        );

		PlayerObject ghost = objectService.createObject(0, "object/player/shared_player.iff");
        ghost.setClient(client);
        ghost.setBiography(biography);
        ghost.setInitialized();
        containerService.transferItemToContainer(character, ghost);

		TangibleObject bank = objectService.createObject(0, "object/tangible/bank/shared_character_bank.iff");
        bank.setInitialized();
        containerService.transferItemToContainer(character, bank);

		TangibleObject datapad = objectService.createObject(0, "object/tangible/datapad/shared_character_datapad.iff");
        datapad.setInitialized();
        containerService.transferItemToContainer(character, datapad);

		TangibleObject missionBag = objectService.createObject(0, "object/tangible/mission_bag/shared_mission_bag.iff");
        missionBag.setInitialized();
        containerService.transferItemToContainer(character, missionBag);

		TangibleObject inventory = objectService.createObject(0, "object/tangible/inventory/shared_character_inventory.iff");
        inventory.setInitialized();
        containerService.transferItemToContainer(character, inventory);

        if (!hairStyleInfo.containsTemplate(hairTemplateName) && !allowBald.isAllowedBald(sharedTemplate.getName())) {
            logger.error("INVALID hair style <{}> chosen. Setting to default hair style.", hairTemplateName);
            hairTemplateName = hairStyleInfo.getDefaultTemplate();
        }

        TangibleObject hair = objectService.createObject(0, hairTemplateName);

        if (hair != null) {
            hair.setAppearanceData(hairAppearanceData);
            hair.setInitialized();
            containerService.transferItemToContainer(character, hair);
        } else {
            logger.error("Failed to create hair with template <{}>.", hairTemplateName);
        }

        character.setAttributes(professionModInfo.getAttributes());
        character.setMaxAttributes(professionModInfo.getAttributes());
        character.setUnmodifiedMaxAttributes(professionModInfo.getAttributes());

        for (String skill : professionInfo.getSkillsList())
            character.addSkill(skill);

        for (String itemTemplate : professionInfo.getItemTemplates(sharedTemplate.getName())) {
            logger.debug("Creating profession item with template <{}>.", itemTemplate);
            SceneObject item = objectService.createObject(0, itemTemplate);
            item.setInitialized();
            containerService.transferItemToContainer(character, item);
        }

        character.setInitialized();

        ClientCreateCharacterSuccess success = new ClientCreateCharacterSuccess(character);
        client.sendMessage(success);

        //Post character setup.
        CharacterInfo info = new CharacterInfo();
        info.setNetworkId(character.getNetworkId());
        info.setObjectTemplateId(SOECRC32.hashCode(templateName));
        info.setClusterId(serverState.getId());
        info.setCharacterType(CharacterInfo.CT_normal);
        info.setName(characterName);


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

        logger.trace("Successfully created character {} with network id {}. ",
                characterName,
                character.getNetworkId());

        objectService.updateObject(character);
	}
}
