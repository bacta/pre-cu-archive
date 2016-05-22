package com.ocdsoft.bacta.swg.server.controller.game.client;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.security.authenticator.AccountService;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.swg.server.message.ErrorMessage;
import com.ocdsoft.bacta.swg.server.message.game.creation.ClientCreateCharacter;
import com.ocdsoft.bacta.swg.server.message.game.creation.ClientCreateCharacterFailed;
import com.ocdsoft.bacta.swg.server.message.game.creation.ClientCreateCharacterSuccess;
import com.ocdsoft.bacta.swg.server.name.NameService;
import com.ocdsoft.bacta.swg.server.object.ServerObject;
import com.ocdsoft.bacta.swg.server.object.intangible.player.PlayerObject;
import com.ocdsoft.bacta.swg.server.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.object.template.server.ServerCreatureObjectTemplate;
import com.ocdsoft.bacta.swg.server.object.template.shared.SharedObjectTemplate;
import com.ocdsoft.bacta.swg.server.service.data.ObjectTemplateService;
import com.ocdsoft.bacta.swg.server.service.data.creation.StartingLocations;
import com.ocdsoft.bacta.swg.server.service.player.BiographyService;
import com.ocdsoft.bacta.swg.server.service.player.CharacterCreationService;
import com.ocdsoft.bacta.swg.server.service.player.NewbieTutorialService;
import com.ocdsoft.bacta.swg.server.util.Gender;
import com.ocdsoft.bacta.swg.server.util.Race;
import com.ocdsoft.bacta.swg.shared.collision.CollisionProperty;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.foundation.ConstCharCrcLowerString;
import com.ocdsoft.bacta.swg.shared.foundation.CrcString;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = ClientCreateCharacter.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class ClientCreateCharacterController implements GameNetworkMessageController<ClientCreateCharacter> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCreateCharacterController.class);

    private final CharacterCreationService characterCreationService;
    private final NewbieTutorialService newbieTutorialService;
    private final BiographyService biographyService;
//    private final ContainerService<ServerObject> containerService;
    private final SlotIdManager slotIdManager;
    private final AccountService<SoeAccount> accountService;
    private final ObjectService<ServerObject> objectService;
    private final ObjectTemplateService templateService;
    private final NameService nameService;
    private final StartingLocations startingLocations;
    private final int minutesBetweenCreation;

    @Inject
    public ClientCreateCharacterController(
            final CharacterCreationService characterCreationService,
            final NewbieTutorialService newbieTutorialService,
            final BiographyService biographyService,
//            final ContainerService<ServerObject> containerService,
            final SlotIdManager slotIdManager,
            final ObjectTemplateService templateService,
            final ObjectService<ServerObject> objectService,
            final AccountService<SoeAccount> accountService,
            final StartingLocations startingLocations,
            final BactaConfiguration bactaConfiguration,
            final NameService nameService) {

        this.characterCreationService = characterCreationService;
        this.newbieTutorialService = newbieTutorialService;
        this.biographyService = biographyService;
//        this.containerService = containerService;
        this.slotIdManager = slotIdManager;
        this.accountService = accountService;
        this.objectService = objectService;
        this.templateService = templateService;
        this.nameService = nameService;
        this.startingLocations = startingLocations;

        this.minutesBetweenCreation = bactaConfiguration.getIntWithDefault(
                "Bacta/GameServer/CharacterCreation",
                "MinutesBetweenCharCreate",
                15);
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
            ClientCreateCharacterFailed failed = new ClientCreateCharacterFailed(createMessage.getCharacterName(), result);
            connection.sendMessage(failed);
            return;
        }

        // check duration
        int minutesSinceLastCreation = (int)((System.currentTimeMillis() - account.getLastCharacterCreationTime()) / 1000 / 60);

        if(minutesSinceLastCreation < minutesBetweenCreation) {
            ClientCreateCharacterFailed failed = new ClientCreateCharacterFailed(createMessage.getCharacterName(), NameService.NAME_DECLINED_TOO_FAST);
            connection.sendMessage(failed);
            return;
        }


        ServerCreatureObjectTemplate objectTemplate = templateService.getObjectTemplate(createMessage.getTemplateName());
        if (objectTemplate == null) {

            LOGGER.error("Account <{}> attempted to create a player with invalid player template <{}>.",
                    account.getUsername(),
                    createMessage.getTemplateName());

            connection.sendMessage(new ClientCreateCharacterFailed(createMessage.getCharacterName(), NameService.NAME_DECLINED_NO_TEMPLATE));
            return;
        }

        final CreatureObject newCharacterObject = objectService.createObject(createMessage.getTemplateName());
        if (newCharacterObject == null) {

            LOGGER.error("Account <{}> attempted to create a player but the object service returned null <{}>.",
                    account.getUsername(),
                    createMessage.getTemplateName());

            connection.sendMessage(new ClientCreateCharacterFailed(createMessage.getCharacterName(), NameService.NAME_DECLINED_CANT_CREATE_AVATAR));
            return;
        }

        newCharacterObject.setObjectName(createMessage.getCharacterName());
        newCharacterObject.setOwnerId(newCharacterObject.getNetworkId());
        newCharacterObject.setPlayerControlled(true);

        StartingLocations.StartingLocationInfo startingLocationInfo = startingLocations.getStartingLocationInfo(createMessage.getStartingLocation());

        final Transform transform = new Transform();
        if(createMessage.isUseNewbieTutorial()) {
            newbieTutorialService.setupCharacterForTutorial(newCharacterObject);
            final Vector newbieTutorialLocation = newbieTutorialService.getTutorialLocation();
            transform.setPositionInParentSpace(newbieTutorialLocation);
        } else {
            newbieTutorialService.setupCharacterToSkipTutorial(newCharacterObject);

            //TODO: Position should be a random point within the radius of these coordinates.
            final Vector position = new Vector(
                    startingLocationInfo.getX(),
                    startingLocationInfo.getY(),
                    startingLocationInfo.getZ());

            transform.setPositionInParentSpace(position);
            transform.yaw(startingLocationInfo.getHeading());
        }

        newCharacterObject.setTransformObjectToParent(transform);

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

        //TODO: Appearance inventory
        // Setup initial A-Tab inventory.
//        SlottedContainer container = newCharacterObject.getSlottedContainerProperty();
//        if(container != null) {
//            int slot = slotIdManager.findSlotId(SlotNames.appearance);
//            //Container::ContainerErrorCode tmp;
//            if(slot != SlotId.INVALID) {
//                Container itemId = container.getObjectInSlot(slot, tmp);
//                Object appearanceInventory = itemId.getObject();
//
//                if(appearanceInventory == null)  {
//                    appearanceInventory = objectService.createObject(s_appearanceTemplate, newCharacterObject);
//                    assert appearanceInventory != null : "Could not create an appearance inventory for the player who lost theirs";
//                }
//            }
//        }


        newCharacterObject.setSceneIdOnThisAndContents(startingLocationInfo.getPlanet());

        // Persist object (Done in Object Manager)

        nameService.addPlayerName(firstName);
        connection.sendMessage(new ClientCreateCharacterSuccess(newCharacterObject.getNetworkId()));

        // TODO: Chat Avatars
        // Destroy any existing chat avatar that may be using the new character's name
//        GenericValueTypeMessage<String> chatDestroyAvatar("ChatDestroyAvatar", firstName);
//        Chat::sendToChatServer(chatDestroyAvatar);

    }

    private static class SlotNames {
        public static CrcString appearance = new ConstCharCrcLowerString("appearance_inventory");
    }
}

