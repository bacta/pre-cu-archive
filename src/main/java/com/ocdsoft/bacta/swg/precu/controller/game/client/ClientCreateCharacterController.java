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
import com.ocdsoft.bacta.soe.service.ContainerService;
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
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerCreatureObjectTemplate;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerTangibleObjectTemplate;
import com.ocdsoft.bacta.swg.precu.object.template.shared.SharedObjectTemplate;
import com.ocdsoft.bacta.swg.precu.service.player.BiographyService;
import com.ocdsoft.bacta.swg.precu.service.player.CharacterCreationService;
import com.ocdsoft.bacta.swg.precu.service.player.NewbieTutorialService;
import com.ocdsoft.bacta.swg.precu.service.data.ObjectTemplateService;
import com.ocdsoft.bacta.swg.precu.service.data.creation.*;
import com.ocdsoft.bacta.swg.precu.service.data.customization.AllowBald;
import com.ocdsoft.bacta.swg.shared.collision.CollisionProperty;
import com.ocdsoft.bacta.swg.shared.container.Container;
import com.ocdsoft.bacta.swg.shared.container.SlotId;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.container.SlottedContainer;
import com.ocdsoft.bacta.swg.shared.foundation.ConstCharCrcLowerString;
import com.ocdsoft.bacta.swg.shared.foundation.CrcString;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import com.ocdsoft.bacta.swg.shared.network.messages.chat.ChatAvatarId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@MessageHandled(handles = ClientCreateCharacter.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class ClientCreateCharacterController implements GameNetworkMessageController<ClientCreateCharacter> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCreateCharacterController.class);

    private final CharacterCreationService characterCreationService;
    private final NewbieTutorialService newbieTutorialService;
    private final BiographyService biographyService;
    private final ContainerService<ServerObject> containerService;
    private final SlotIdManager slotIdManager;


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


    private final int minutesBetweenCreation;
    private final String defaultProfession;
    private final Set<String> disabledProfessions;

    //TODO: everything
    @Inject
    public ClientCreateCharacterController(
            final CharacterCreationService characterCreationService,
            final NewbieTutorialService newbieTutorialService,
            final BiographyService biographyService,
            final ContainerService<ServerObject> containerService,
            final SlotIdManager slotIdManager,

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

            final BactaConfiguration bactaConfiguration,
            final NameService nameService) {

        this.characterCreationService = characterCreationService;
        this.newbieTutorialService = newbieTutorialService;
        this.biographyService = biographyService;
        this.containerService = containerService;
        this.slotIdManager = slotIdManager;

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
    public void handleIncoming(SoeUdpConnection connection, ClientCreateCharacter createMessage) {

        // Get the account object for this connection
        final SoeAccount account = accountService.getAccount(connection.getAccountUsername());
        if (account == null) {
            ErrorMessage error = new ErrorMessage("Error", "Account not found.", false);
            connection.sendMessage(error);
            LOGGER.info("Account <{}> with username {} was not found.",
                    account.getId(),
                    account.getUsername());
            return;
        }

        // Validate name based on Gender and Race
        String genderSpecies = createMessage.getTemplateName().substring(createMessage.getTemplateName().lastIndexOf('/') + 1, createMessage.getTemplateName().length() - 4); //Gets the file name of the racefile. i.e. human_male.
        Gender gender = Gender.valueOf(genderSpecies.substring(genderSpecies.indexOf("_") + 1).toUpperCase());

        Race race = Race.valueOf(genderSpecies.substring(0, genderSpecies.indexOf("_")).toUpperCase());

        String firstName = createMessage.getCharacterName().split(" ", 2)[0];
        String result = nameService.validateName(NameService.PLAYER, createMessage.getCharacterName(), race, gender);
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


        ServerCreatureObjectTemplate objectTemplate = templateService.getObjectTemplate(createMessage.getTemplateName());
        if (objectTemplate == null) {

            LOGGER.error("Account <{}> attempted to create a player with invalid player template <{}>.",
                    account.getUsername(),
                    createMessage.getTemplateName());

            connection.sendMessage(new ClientCreateCharacterFailed(NameService.NAME_DECLINED_NO_TEMPLATE));
            return;
        }

        final CreatureObject newCharacterObject = objectService.createObject(objectTemplate.getSharedTemplate());
        if (newCharacterObject == null) {

            LOGGER.error("Account <{}> attempted to create a player but the object service returned null <{}>.",
                    account.getUsername(),
                    createMessage.getTemplateName());

            connection.sendMessage(new ClientCreateCharacterFailed(NameService.NAME_DECLINED_CANT_CREATE_AVATAR));
            return;
        }

        newCharacterObject.setAssignedObjectName();setObjectName(createMessage.getCharacterName());
        newCharacterObject.setOwnerId(newCharacterObject.getNetworkId());
        newCharacterObject.setPlayerControlled(true);

        final Transform transform = new Transform();
        if(createMessage.isUseNewbieTutorial()) {
            newbieTutorialService.setupCharacterForTutorial(newCharacterObject);
            transform.setPositionInParentSpace(newbieTutorialService.getTutorialLocation());
        } else {
            newbieTutorialService.setupCharacterToSkipTutorial(newCharacterObject);
            StartingLocations.StartingLocationInfo startingLocationInfo = startingLocations.getStartingLocationInfo(createMessage.getStartingLocation());

            //TODO: Position should be a random point within the radius of these coordinates.
            final Vector position = new Vector(
                    startingLocationInfo.getX(),
                    startingLocationInfo.getY(),
                    startingLocationInfo.getZ());

            transform.setPositionInParentSpace(position);
            transform.yaw(startingLocationInfo.getHeading());
        }

        newCharacterObject.setTransform(transform);

        final CollisionProperty collision = newCharacterObject.getCollisionProperty();
        if(collision != null) {
            collision.setPlayerControlled(true);
        }

        float scaleFactor = createMessage.getScaleFactor();
        final SharedObjectTemplate tmpl = newCharacterObject.getSharedTemplate();

        if (tmpl != null) {
            final float scaleMax = tmpl.getScaleMax();
            final float scaleMin = tmpl.getScaleMin();

            scaleFactor = Math.min(scaleFactor, scaleMax);
            scaleFactor = Math.max(scaleFactor, scaleMin);
        }

        newCharacterObject.setScaleFactor(scaleFactor);
        newCharacterObject.setScale(Vector.XYZ111.multiply(scaleFactor));

        final TangibleObject tangibleObject = TangibleObject.asTangibleObject(newCharacterObject);

        if (tangibleObject != null) {
            tangibleObject.setAppearanceData(createMessage.getAppearanceData());
        }

        // hair equip hack - lives on
        if(!createMessage.getHairTemplateName().isEmpty()) {
            final ServerObject hair = objectService.createObject(createMessage.getHairTemplateName(), newCharacterObject);
            assert hair != null : String.format("Could not create hair %s\n", createMessage.getHairTemplateName());

            final TangibleObject tangibleHair = TangibleObject.asTangibleObject(hair);
            assert tangibleHair != null : String.format("Hair is not tangible, wtf.  Can't customize it.  (among other things, probably)...");
        }

        if (!createMessage.getProfession().isEmpty()) {
            characterCreationService.setupPlayer(newCharacterObject, createMessage.getProfession(), createMessage.isJedi());
        }

        if (!createMessage.getBiography().isEmpty()) {
            biographyService.setBiography(newCharacterObject.getNetworkId(), createMessage.getBiography());
        }

        PlayerObject play = objectService.createObject("object/player/player.iff", newCharacterObject);

        assert play != null : String.format("%d unable to create player object for new character %s", account.getId(), newCharacterObject.getNetworkId());

        play.setStationId(account.getId());
        play.setBornDate((int) DateTime.now().getMillis());
        play.setSkillTemplate(createMessage.getSkillTemplate(), true);
        play.setWorkingSkill(createMessage.getWorkingSkill(), true);

        // Setup initial A-Tab inventory.
        SlottedContainer container = newCharacterObject.getSlottedContainerProperty();
        if(container != null) {
            int slot = slotIdManager.findSlotId(SlotNames.appearance);
            //Container::ContainerErrorCode tmp;
            if(slot != SlotId.INVALID) {
                Container itemId = container.getObjectInSlot(slot, tmp);
                Object appearanceInventory = itemId.getObject();

                if(appearanceInventory == null)  {
                    WARNING(true, ("Player %s has lost their appearance inventory", newCharacterObject.getNetworkId());
                    appearanceInventory = ServerWorld::createNewObject(s_appearanceTemplate, *newCharacterObject, slot, false);
                    if(!appearanceInventory)
                    {
                        DEBUG_FATAL(true, ("Could not create an appearance inventory for the player who lost theirs"));
                    }
                }
            }
        }


        newCharacterObject.setSceneIdOnThisAndContents(createMessage.getPlanetName());

        // ----------------------------------------------------------------------
        // Tell DB Process we're about to send it a character, then send it
        AddCharacterMessage acm(createMessage->getStationId(), newCharacterObject->getNetworkId(),getProcessId(),createMessage->getCharacterName(),createMessage->getJedi());
        sendToDatabaseServer(acm);
        newCharacterObject.persist();
        nameService.addPlayerName(firstName);

        // ----------------------------------------------------------------------
        // Delete the new character -- will reload it from DB when player logs in
        // (This is so that the player could log in to a different server, or not log in at all, and things would still work.)
        ServerWorld::removeObjectFromGame(*newCharacterObject);
        delete newCharacterObject;

        m_charactersPendingCreation->erase(vrn.getStationId());
        delete createMessage;

        // Destroy any existing chat avatar that may be using the new character's name
        GenericValueTypeMessage<String> chatDestroyAvatar("ChatDestroyAvatar", firstName);
        Chat::sendToChatServer(chatDestroyAvatar);

    }

    private static class SlotNames {
        public static CrcString appearance = new ConstCharCrcLowerString("appearance_inventory");
    }
}

