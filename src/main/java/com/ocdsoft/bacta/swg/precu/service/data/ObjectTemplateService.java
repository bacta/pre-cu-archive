package com.ocdsoft.bacta.swg.precu.service.data;

import bacta.iff.Iff;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.lang.NotImplementedException;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.shared.container.ArrangementDescriptorList;
import com.ocdsoft.bacta.swg.shared.container.SlotDescriptorList;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;
import com.ocdsoft.bacta.tre.TreeFile;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;

/**
 * Created by crush on 3/4/14.
 */
@Singleton
public class ObjectTemplateService extends ObjectTemplateList {

    private final static Logger LOGGER = LoggerFactory.getLogger(ObjectTemplateService.class);

    private final TIntObjectMap<Class<? extends ServerObject>> templateClassMap = new TIntObjectHashMap<>();
    //private final SlotDescriptorList slotDescriptorList;
    private final ArrangementDescriptorList arrangementDescriptorList;

    @Inject
    public ObjectTemplateService(
            SetupSharedFile sharedFile,
            //SlotDescriptorList slotDescriptorList,
            ArrangementDescriptorList arrangementDescriptorList) {

        //super(treeFile);

        //this.slotDescriptorList = slotDescriptorList;
        this.arrangementDescriptorList = arrangementDescriptorList;

        configureBindings();

        //crcStringTable.load("misc/object_template_crc_string_table.iff");

        load(); //TODO:Is there a better way to invoke loading?
    }

    private void configureBindings() {
        try {
//            StringParamIffLoader stringParamIffLoader = new StringParamIffLoader();
//
//            registerTemplateBaseLoader(BoolParam.class, new BoolParamIffLoader());
//            registerTemplateBaseLoader(DynamicVariableParam.class, new DynamicVariableParamIffLoader());
//            registerTemplateBaseLoader(FloatParam.class, new FloatParamIffLoader());
//            registerTemplateBaseLoader(IntegerParam.class, new IntegerParamIffLoader());
//            registerTemplateBaseLoader(StringParam.class, stringParamIffLoader);
//            registerTemplateBaseLoader(StringIdParam.class, new StringIdParamIffLoader(stringParamIffLoader));
//            registerTemplateBaseLoader(StructParam.class, new StructParamIffLoader(this));
//            registerTemplateBaseLoader(TriggerVolumeParam.class, new TriggerVolumeParamIffLoader());
//            registerTemplateBaseLoader(VectorParam.class, new VectorParamIffLoader());
//
//            assignBinding(ObjectTemplate.ID_CCLT, SharedCellObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_RCCT, SharedResourceContainerObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SBMK, SharedBattleFieldMarkerObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SBOT, SharedBuildingObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SCNC, SharedConstructionContractObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SCOT, SharedCreatureObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SCOU, SharedCountingObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SDSC, SharedDraftSchematicObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SISS, SharedDraftSchematicObjectTemplate.IngredientSlot::new);
//            assignBinding(ObjectTemplate.ID_DSSA, SharedDraftSchematicObjectTemplate.SchematicAttribute::new);
//            assignBinding(ObjectTemplate.ID_SFOT, SharedFactoryObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SGLD, SharedGuildObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SGRP, SharedGroupObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SHOT, SharedObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SIOT, SharedInstallationObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SITN, SharedIntangibleObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SJED, SharedJediManagerTemplate::new);
//            assignBinding(ObjectTemplate.ID_SMLE, SharedMissionListEntryObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SMSC, SharedManufactureSchematicObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SMSD, SharedMissionDataObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SMSO, SharedMissionObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SPLY, SharedPlayerObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SSHP, SharedShipObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_STAT, SharedStaticObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_STOK, SharedTokenObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_STOT, SharedTangibleObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_RICV, SharedTangibleObjectTemplate.RangedIntCustomizationVariable::new);
//            assignBinding(ObjectTemplate.ID_PCCV, SharedTangibleObjectTemplate.PaletteColorCustomizationVariable::new);
//            assignBinding(ObjectTemplate.ID_CSCV, SharedTangibleObjectTemplate.ConstStringCustomizationVariable::new);
//            assignBinding(ObjectTemplate.ID_CVMM, SharedTangibleObjectTemplate.CustomizationVariableMapping::new);
//            assignBinding(ObjectTemplate.ID_SUNI, SharedUniverseObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SWAY, SharedWaypointObjectTemplate::new);
//            assignBinding(ObjectTemplate.ID_SWOT, SharedWeaponObjectTemplate::new);

            //TODO: Finish this mapping
//            templateClassMap.put(ObjectTemplate.ID_CCLT, CellObject.class);
//            //templateClassMap.put(ObjectTemplate.RCCT, SharedResourceContainerObjectTemplate.class);
//            //templateClassMap.put(ObjectTemplate.SBMK, SharedBattleFieldMarkerObjectTemplate.class);
//            templateClassMap.put(ObjectTemplate.ID_SBOT, BuildingObject.class);
//            //templateClassMap.put(ObjectTemplate.SCNC, SharedConstructionContractObjectTemplate.class);
//            templateClassMap.put(ObjectTemplate.ID_SCOT, CreatureObject.class);
//            //templateClassMap.put(ObjectTemplate.SCOU, SharedCountingObjectTemplate.class);
//            //templateClassMap.put(ObjectTemplate.SDSC, SharedDraftSchematicObjectTemplate.class);
//            //templateClassMap.put(ObjectTemplate.SFOT, SharedFactoryObjectTemplate.class);
//            templateClassMap.put(ObjectTemplate.ID_SGLD, GuildObject.class);
//            templateClassMap.put(ObjectTemplate.ID_SGRP, GroupObject.class);
//            templateClassMap.put(ObjectTemplate.ID_SHOT, ServerObject.class);
//            templateClassMap.put(ObjectTemplate.ID_SIOT, InstallationObject.class);
//            templateClassMap.put(ObjectTemplate.ID_SITN, IntangibleObject.class);
//            //templateClassMap.put(ObjectTemplate.SMSC, SharedManufactureSchematicObjectTemplate.class);
//            //templateClassMap.put(ObjectTemplate.SJED, SharedJediManagerTemplate.class);
//            //templateClassMap.put(ObjectTemplate.SMLE, SharedMissionListEntryObjectTemplate.class);
//            //templateClassMap.put(ObjectTemplate.SMSD, SharedMissionDataObjectTemplate.class);
//            //templateClassMap.put(ObjectTemplate.SMSO, SharedMissionObjectTemplate.class);
//            templateClassMap.put(ObjectTemplate.ID_SPLY, PlayerObject.class);
//            //templateClassMap.put(ObjectTemplate.SSHP, SharedShipObjectTemplate.class);
//            //templateClassMap.put(ObjectTemplate.STAT, SharedStaticObjectTemplate.class);
//            //templateClassMap.put(ObjectTemplate.STOK, SharedTokenObjectTemplate.class);
//            templateClassMap.put(ObjectTemplate.ID_STOT, TangibleObject.class);
//            //templateClassMap.put(ObjectTemplate.SUNI, SharedUniverseObjectTemplate.class);
//            templateClassMap.put(ObjectTemplate.ID_SWAY, WaypointObject.class);
//            //templateClassMap.put(ObjectTemplate.SWOT, SharedWeaponObjectTemplate.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void load() {
//        Collection<ConstCharCrcString> templatePaths = crcStringTable.getAllStrings();
//
//        for (ConstCharCrcString crcString : templatePaths) {
//            ObjectTemplate objectTemplate = getObjectTemplate(crcString);
//
//            if (objectTemplate == null) {
//                logger.warn("Object template <{}> does not exist.", crcString.getString());
//            } else if (objectTemplate instanceof SharedObjectTemplate) {
//                SharedObjectTemplate sharedObjectTemplate = (SharedObjectTemplate) objectTemplate;
//
//                sharedObjectTemplate.setSlotDescriptor(
//                        slotDescriptorList.fetch(sharedObjectTemplate.getSlotDescriptorFilename()));
//
//                sharedObjectTemplate.setArrangementDescriptor(
//                        arrangementDescriptorList.fetch(sharedObjectTemplate.getArrangementDescriptorFilename()));
//            }
//        }
//
//        logger.info("Loaded {} object templates.", loadedTemplates.size());
    }

    /**
     * This gets the class associated with the provided template type
     * this is use in object creation.
     *
     * @param template template to get class object for
     * @return Class object related to the specified type
     * @throws NotImplementedException when chunk type is not mapped to a class
     */
    public <T extends ServerObject> Class<T> getClassForTemplate(ObjectTemplate template) {
        Class<T> clazz = (Class<T>) templateClassMap.get(template.getId());
        if(clazz == null) {
            LOGGER.error("Template with class mapping: " + Iff.getChunkName(template.getId()));
            throw new NotImplementedException();
        }
        return clazz;
    }
}
