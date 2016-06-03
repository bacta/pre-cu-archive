package com.ocdsoft.bacta.swg.server.game.player.creation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.service.AccountService;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import com.ocdsoft.bacta.swg.server.login.object.SoeAccount;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kyle on 5/9/2016.
 */
@Singleton
public final class CharacterCreationService {

    private final AccountService<SoeAccount> accountService;
    private final ServerObjectService serverObjectService;
    private final ProfessionDefaultsService professionDefaultsService;
    private final String defaultProfession;
    private final Set<String> disabledProfessions;

    @Inject
    public CharacterCreationService(final AccountService<SoeAccount> accountService,
                                    final ServerObjectService serverObjectService,
                                    final ProfessionDefaultsService professionDefaultsService,
                                    final BactaConfiguration bactaConfiguration) {

        this.accountService = accountService;
        this.serverObjectService = serverObjectService;
        this.professionDefaultsService = professionDefaultsService;

        this.disabledProfessions = new HashSet<>(bactaConfiguration.getStringCollection(
                "Bacta/GameServer/CharacterCreation",
                "DisabledProfession"));

        this.defaultProfession = bactaConfiguration.getStringWithDefault(
                "Bacta/GameServer/CharacterCreation",
                "DefaultProfession",
                "crafting_artisan");
    }

    public void setupPlayer(final CreatureObject newCharacterObject, final String profession, final boolean jedi) {

        /*if (this.disabledProfessions.contains(profession)) {
            profession = this.defaultProfession;
        }*/

        final String sharedTemplateName = newCharacterObject.getSharedTemplate().getResourceName();


        final ProfessionInfo professionInfo = professionDefaultsService.getDefaults(profession);

        if (professionInfo != null) {
            final List<EquipmentInfo> equipmentList = professionInfo.getEquipmentForTemplate(sharedTemplateName);

            for (final EquipmentInfo equipmentInfo : equipmentList) {
                final String serverTemplateName = equipmentInfo.getServerTemplateName();

                serverObjectService.createObject(serverTemplateName, newCharacterObject);
            }
        }













                /*
        //TODO: Remove this after service side hair templates have been created...
        StringBuilder stringBuilder = new StringBuilder(message.getHairTemplateName());
        stringBuilder.insert(message.getHairTemplateName().lastIndexOf('/') + 1, "shared_");
        String hairTemplate = stringBuilder.toString();


/*
        ProfessionMods.ProfessionModInfo professionModInfo = professionMods.getProfessionModInfo(profession);
        //TODO: Iff reading
        ProfessionDefaultsService.ProfessionInfo professionInfo = professionDefaults.getProfessionInfo(profession);
        HairStyles.HairStyleInfo hairStyleInfo = hairStyles.getHairStyleInfo(objectTemplate.getResourceName());

        if (hairStyleInfo == null || professionModInfo == null || professionInfo == null) {
            //Only way for this to happen is if client data is not loaded or missing.
            LOGGER.error("Unable to retrieve profession information for profession <{}>.", profession);
            connection.sendMessage(new ClientCreateCharacterFailed(NameService.NAME_DECLINED_INTERNAL_ERROR));
            return;
        }

        character.setCondition(ServerTangibleObjectTemplate.Conditions.C_onOff);

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

        /*
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
        */
    }
}
