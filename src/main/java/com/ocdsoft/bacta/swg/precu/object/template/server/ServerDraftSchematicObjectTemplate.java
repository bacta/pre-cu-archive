package com.ocdsoft.bacta.swg.precu.object.template.server;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.localization.StringId;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;
import com.ocdsoft.bacta.swg.shared.utility.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
public class ServerDraftSchematicObjectTemplate extends ServerIntangibleObjectTemplate {
    private static final int TAG_SERVERDRAFTSCHEMATICOBJECTTEMPLATE = Tag.convertStringToTag("0000");

    private int templateVersion;

    private final IntegerParam category = new IntegerParam(); //schematic category (food, weapon, etc)
    private final StringParam craftedObjectTemplate = new StringParam(); //what object we create
    private final StringParam crateObjectTemplate = new StringParam(); //the "crate" to use when manufacturing multiple copies of the object
    private final List<StructParam<ObjectTemplate>> slots = new ArrayList<>(); //ingredient slots
    private boolean slotsLoaded;
    private boolean slotsAppend;
    private final List<StringParam> skillCommands = new ArrayList<>(); //skill commands needed to access this schematic and required slots
    private boolean skillCommandsLoaded;
    private boolean skillCommandsAppend;
    private final BoolParam destroyIngredients = new BoolParam(); //flag that the ingredients used in the design stage should be destroyed
    private final List<StringParam> manufactureScripts = new ArrayList<>(); //scripts that will be attached to a manufacturing schematic created from this schematic
    private boolean manufactureScriptsLoaded;
    private boolean manufactureScriptsAppend;
    private final IntegerParam itemsPerContainer = new IntegerParam(); //when manufacturing, how many items will be put in a container (1 = items not in a container)
    private final FloatParam manufactureTime = new FloatParam(); //time to manufacture an item (in secs) per complexity point
    private final FloatParam prototypeTime = new FloatParam(); //time to create a prototype (in secs) per complexity point

    public ServerDraftSchematicObjectTemplate(final String filename, final ObjectTemplateList objectTemplateList) {
        super(filename, objectTemplateList);
    }

    @Override
    public int getId() {
        return TAG_SERVERDRAFTSCHEMATICOBJECTTEMPLATE;
    }

    public CraftingType getCategory() {
        ServerDraftSchematicObjectTemplate base = null;

        if (baseData != null)
            base = (ServerDraftSchematicObjectTemplate) baseData;

        if (!category.isLoaded()) {
            if (base == null) {
                return CraftingType.from(0);
            } else {
                return base.getCategory();
            }
        }

        return CraftingType.from(category.getValue());
    }

    public ServerObjectTemplate getCraftedObjectTemplate() {
        ServerDraftSchematicObjectTemplate base = null;

        if (baseData != null)
            base = (ServerDraftSchematicObjectTemplate) baseData;

        if (!craftedObjectTemplate.isLoaded()) {
            if (base == null) {
                return null;
            } else {
                return base.getCraftedObjectTemplate();
            }
        }

        ServerObjectTemplate returnValue = null;
        final String templateName = craftedObjectTemplate.getValue();

        if (!templateName.isEmpty()) {
            returnValue = objectTemplateList.fetch(templateName);

            if (returnValue == null)
                throw new IllegalStateException(String.format("error loading template %s", templateName));
        }

        return returnValue;
    }

    public ServerFactoryObjectTemplate getCrateObjectTemplate() {
        ServerDraftSchematicObjectTemplate base = null;

        if (baseData != null)
            base = (ServerDraftSchematicObjectTemplate) baseData;

        if (!crateObjectTemplate.isLoaded()) {
            if (base == null) {
                return null;
            } else {
                return base.getCrateObjectTemplate();
            }
        }

        ServerFactoryObjectTemplate returnValue = null;
        final String templateName = crateObjectTemplate.getValue();

        if (!templateName.isEmpty()) {
            returnValue = objectTemplateList.fetch(templateName);

            if (returnValue == null)
                throw new IllegalStateException(String.format("error loading template %s", templateName));
        }

        return returnValue;
    }

    public IngredientSlot getSlots(int index) {
        ServerDraftSchematicObjectTemplate base = null;

        if (baseData != null)
            base = (ServerDraftSchematicObjectTemplate) baseData;

        if (!slotsLoaded) {
            if (base == null) {
                return null;
            } else {
                return base.getSlots(index);
            }
        }

        if (slotsAppend && base != null) {
            int baseCount = base.getSlotsCount();

            if (index < baseCount) {
                return base.getSlots(index);
            }
            index -= baseCount;
        }
        final ObjectTemplate structTemplate = slots.get(index).getValue();
        Preconditions.checkNotNull(structTemplate);
        final IngredientSlotObjectTemplate param = (IngredientSlotObjectTemplate) structTemplate;

        final IngredientSlot data = new IngredientSlot();
        data.optional = param.getOptional();
        data.name = param.getName();
        for (int i = 0; i < param.getOptionsCount(); ++i)
            data.options.add(param.getOptions(i));
        data.optionalSkillCommand = param.getOptionalSkillCommand();
        data.complexity = param.getComplexity();
        data.appearance = param.getAppearance();

        return data;
    }

    public int getSlotsCount() {
        if (!slotsLoaded) {
            if (baseData == null)
                return 0;

            final ServerDraftSchematicObjectTemplate base = (ServerDraftSchematicObjectTemplate) baseData;
            return base.getSlotsCount();
        }

        int count = slots.size();

        if (slotsAppend && baseData != null) {
            final ServerDraftSchematicObjectTemplate base = (ServerDraftSchematicObjectTemplate) baseData;
            count += base.getSlotsCount();
        }

        return count;
    }

    public String getSkillCommands(int index) {
        ServerDraftSchematicObjectTemplate base = null;

        if (baseData != null)
            base = (ServerDraftSchematicObjectTemplate) baseData;

        if (!skillCommandsLoaded) {
            if (base == null) {
                return "";
            } else {
                return base.getSkillCommands(index);
            }
        }

        if (skillCommandsAppend && base != null) {
            int baseCount = base.getSkillCommandsCount();

            if (index < baseCount) {
                return base.getSkillCommands(index);
            }
            index -= baseCount;
        }
        String value = this.skillCommands.get(index).getValue();
        return value;
    }

    public int getSkillCommandsCount() {
        if (!skillCommandsLoaded) {
            if (baseData == null)
                return 0;

            final ServerDraftSchematicObjectTemplate base = (ServerDraftSchematicObjectTemplate) baseData;
            return base.getSkillCommandsCount();
        }

        int count = skillCommands.size();

        if (skillCommandsAppend && baseData != null) {
            final ServerDraftSchematicObjectTemplate base = (ServerDraftSchematicObjectTemplate) baseData;
            count += base.getSkillCommandsCount();
        }

        return count;
    }

    public boolean getDestroyIngredients() {
        ServerDraftSchematicObjectTemplate base = null;

        if (baseData != null)
            base = (ServerDraftSchematicObjectTemplate) baseData;

        if (!destroyIngredients.isLoaded()) {
            if (base == null) {
                return false;
            } else {
                return base.getDestroyIngredients();
            }
        }

        boolean value = this.destroyIngredients.getValue();
        return value;
    }

    public String getManufactureScripts(int index) {
        ServerDraftSchematicObjectTemplate base = null;

        if (baseData != null)
            base = (ServerDraftSchematicObjectTemplate) baseData;

        if (!manufactureScriptsLoaded) {
            if (base == null) {
                return "";
            } else {
                return base.getManufactureScripts(index);
            }
        }

        if (manufactureScriptsAppend && base != null) {
            int baseCount = base.getManufactureScriptsCount();

            if (index < baseCount) {
                return base.getManufactureScripts(index);
            }
            index -= baseCount;
        }
        String value = this.manufactureScripts.get(index).getValue();
        return value;
    }

    public int getManufactureScriptsCount() {
        if (!manufactureScriptsLoaded) {
            if (baseData == null)
                return 0;

            final ServerDraftSchematicObjectTemplate base = (ServerDraftSchematicObjectTemplate) baseData;
            return base.getManufactureScriptsCount();
        }

        int count = manufactureScripts.size();

        if (manufactureScriptsAppend && baseData != null) {
            final ServerDraftSchematicObjectTemplate base = (ServerDraftSchematicObjectTemplate) baseData;
            count += base.getManufactureScriptsCount();
        }

        return count;
    }

    public int getItemsPerContainer() {
        ServerDraftSchematicObjectTemplate base = null;

        if (baseData != null)
            base = (ServerDraftSchematicObjectTemplate) baseData;

        if (!itemsPerContainer.isLoaded()) {
            if (base == null) {
                return 0;
            } else {
                return base.getItemsPerContainer();
            }
        }

        int value = this.itemsPerContainer.getValue();
        final byte delta = this.itemsPerContainer.getDeltaType();

        if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
            int baseValue = 0;

            if (baseData != null) {
                if (base != null)
                    baseValue = base.getItemsPerContainer();
            }

            if (delta == '+')
                value = baseValue + value;
            if (delta == '-')
                value = baseValue - value;
            if (delta == '=')
                value = baseValue + (int) (baseValue * (value / 100.0f));
            if (delta == '_')
                value = baseValue - (int) (baseValue * (value / 100.0f));
        }
        return value;
    }

    public float getManufactureTime() {
        ServerDraftSchematicObjectTemplate base = null;

        if (baseData != null)
            base = (ServerDraftSchematicObjectTemplate) baseData;

        if (!manufactureTime.isLoaded()) {
            if (base == null) {
                return 0.0f;
            } else {
                return base.getManufactureTime();
            }
        }

        float value = this.manufactureTime.getValue();
        final byte delta = this.manufactureTime.getDeltaType();

        if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
            float baseValue = 0;

            if (baseData != null) {
                if (base != null)
                    baseValue = base.getManufactureTime();
            }

            if (delta == '+')
                value = baseValue + value;
            if (delta == '-')
                value = baseValue - value;
            if (delta == '=')
                value = baseValue + (float) (baseValue * (value / 100.0f));
            if (delta == '_')
                value = baseValue - (float) (baseValue * (value / 100.0f));
        }
        return value;
    }

    public float getPrototypeTime() {
        ServerDraftSchematicObjectTemplate base = null;

        if (baseData != null)
            base = (ServerDraftSchematicObjectTemplate) baseData;

        if (!prototypeTime.isLoaded()) {
            if (base == null) {
                return 0.0f;
            } else {
                return base.getPrototypeTime();
            }
        }

        float value = this.prototypeTime.getValue();
        final byte delta = this.prototypeTime.getDeltaType();

        if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
            float baseValue = 0;

            if (baseData != null) {
                if (base != null)
                    baseValue = base.getPrototypeTime();
            }

            if (delta == '+')
                value = baseValue + value;
            if (delta == '-')
                value = baseValue - value;
            if (delta == '=')
                value = baseValue + (float) (baseValue * (value / 100.0f));
            if (delta == '_')
                value = baseValue - (float) (baseValue * (value / 100.0f));
        }
        return value;
    }

    @Override
    protected void load(final Iff iff) {
        if (iff.getCurrentName() != TAG_SERVERDRAFTSCHEMATICOBJECTTEMPLATE)
            return;

        iff.enterForm();
        templateVersion = iff.getCurrentName();

        if (templateVersion == Tag.TAG_DERV) {
            iff.enterForm();
            iff.enterChunk();
            final String baseFilename = iff.readString();
            iff.exitChunk();
            final ObjectTemplate base = objectTemplateList.fetch(baseFilename);
            Preconditions.checkNotNull(base, "was unable to load base template %s", baseFilename);
            if (baseData == base && base != null) {
                base.releaseReference();
            } else {
                if (baseData != null)
                    baseData.releaseReference();
                baseData = base;
            }
            iff.exitForm();
            templateVersion = iff.getCurrentName();
        }

        iff.enterForm();
        iff.enterChunk();
        final int paramCount = iff.readInt();
        iff.exitChunk();
        for (int i = 0; i < paramCount; ++i) {
            iff.enterChunk();
            final String parameterName = iff.readString();

            if ("".equalsIgnoreCase(parameterName)) {
                category.loadFromIff(objectTemplateList, iff);
            } else if ("".equalsIgnoreCase(parameterName)) {
                craftedObjectTemplate.loadFromIff(objectTemplateList, iff);
            } else if ("".equalsIgnoreCase(parameterName)) {
                crateObjectTemplate.loadFromIff(objectTemplateList, iff);
            } else if ("".equalsIgnoreCase(parameterName)) {
                slots.clear();
                slotsAppend = iff.readBoolean();
                int listCount = iff.readInt();
                for (int j = 0; j < listCount; ++j) {
                    final StructParam<ObjectTemplate> newData = new StructParam<ObjectTemplate>();
                    newData.loadFromIff(objectTemplateList, iff);
                    slots.add(newData);
                }
                slotsLoaded = true;
            } else if ("".equalsIgnoreCase(parameterName)) {
                skillCommands.clear();
                skillCommandsAppend = iff.readBoolean();
                int listCount = iff.readInt();
                for (int j = 0; j < listCount; ++j) {
                    final StringParam newData = new StringParam();
                    newData.loadFromIff(objectTemplateList, iff);
                    skillCommands.add(newData);
                }
                skillCommandsLoaded = true;
            } else if ("".equalsIgnoreCase(parameterName)) {
                destroyIngredients.loadFromIff(objectTemplateList, iff);
            } else if ("".equalsIgnoreCase(parameterName)) {
                manufactureScripts.clear();
                manufactureScriptsAppend = iff.readBoolean();
                int listCount = iff.readInt();
                for (int j = 0; j < listCount; ++j) {
                    final StringParam newData = new StringParam();
                    newData.loadFromIff(objectTemplateList, iff);
                    manufactureScripts.add(newData);
                }
                manufactureScriptsLoaded = true;
            } else if ("".equalsIgnoreCase(parameterName)) {
                itemsPerContainer.loadFromIff(objectTemplateList, iff);
            } else if ("".equalsIgnoreCase(parameterName)) {
                manufactureTime.loadFromIff(objectTemplateList, iff);
            } else if ("".equalsIgnoreCase(parameterName)) {
                prototypeTime.loadFromIff(objectTemplateList, iff);
            } else {
                throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
            }

            iff.exitChunk();
        }
        iff.exitForm();
    }

    public static class IngredientSlot {
        @Getter
        protected boolean optional;
        @Getter
        protected StringId name;
        protected List<Ingredient> options = new ArrayList<>(1);
        @Getter
        protected String optionalSkillCommand;
        @Getter
        protected float complexity;
        @Getter
        protected String appearance;
    }

    protected static class IngredientSlotObjectTemplate extends ObjectTemplate {
        private static final int TAG_INGREDIENTSLOT = Tag.convertStringToTag("DINS");

        public IngredientSlotObjectTemplate(final String filename, final ObjectTemplateList objectTemplateList) {
            super(filename, objectTemplateList);
        }

        private final BoolParam optional = new BoolParam(); //is the slot optional
        private final StringIdParam name = new StringIdParam(); //slot name
        private final List<StructParam<ObjectTemplate>> options = new ArrayList<>(); //possible ingredients that can be used to fill the slot
        private boolean optionsLoaded;
        private boolean optionsAppend;
        private final StringParam optionalSkillCommand = new StringParam(); //skill commands needed to access this slot if it is optional (ignored for required slots)
        private final FloatParam complexity = new FloatParam(); //adjustment to complexity by using this slot
        private final StringParam appearance = new StringParam(); //if the slot is a component, the name of the hardpoint associated with the slot; if the slot is a resource, a string used to build an appearance file name

        @Override
        public int getId() {
            return TAG_INGREDIENTSLOT;
        }

        public boolean getOptional() {
            IngredientSlotObjectTemplate base = null;

            if (baseData != null)
                base = (IngredientSlotObjectTemplate) baseData;

            if (!optional.isLoaded()) {
                if (base == null) {
                    return false;
                } else {
                    return base.getOptional();
                }
            }

            boolean value = this.optional.getValue();
            return value;
        }

        public StringId getName() {
            IngredientSlotObjectTemplate base = null;

            if (baseData != null)
                base = (IngredientSlotObjectTemplate) baseData;

            if (!name.isLoaded()) {
                if (base == null) {
                    return StringId.INVALID;
                } else {
                    return base.getName();
                }
            }

            StringId value = this.name.getValue();
            return value;
        }

        public Ingredient getOptions(int index) {
            IngredientSlotObjectTemplate base = null;

            if (baseData != null)
                base = (IngredientSlotObjectTemplate) baseData;

            if (!optionsLoaded) {
                if (base == null) {
                    return null;
                } else {
                    return base.getOptions(index);
                }
            }

            if (optionsAppend && base != null) {
                int baseCount = base.getOptionsCount();

                if (index < baseCount) {
                    return base.getOptions(index);
                }
                index -= baseCount;
            }
            final ObjectTemplate structTemplate = options.get(index).getValue();
            Preconditions.checkNotNull(structTemplate);
            final IngredientObjectTemplate param = (IngredientObjectTemplate) structTemplate;

            final Ingredient data = new Ingredient();
            data.ingredientType = param.getIngredientType();
            for (int i = 0; i < param.getIngredientsCount(); ++i)
                data.ingredients.add(param.getIngredients(i));
            data.complexity = param.getComplexity();
            data.skillCommand = param.getSkillCommand();

            return data;
        }

        public int getOptionsCount() {
            if (!optionsLoaded) {
                if (baseData == null)
                    return 0;

                final IngredientSlotObjectTemplate base = (IngredientSlotObjectTemplate) baseData;
                return base.getOptionsCount();
            }

            int count = options.size();

            if (optionsAppend && baseData != null) {
                final IngredientSlotObjectTemplate base = (IngredientSlotObjectTemplate) baseData;
                count += base.getOptionsCount();
            }

            return count;
        }

        public String getOptionalSkillCommand() {
            IngredientSlotObjectTemplate base = null;

            if (baseData != null)
                base = (IngredientSlotObjectTemplate) baseData;

            if (!optionalSkillCommand.isLoaded()) {
                if (base == null) {
                    return "";
                } else {
                    return base.getOptionalSkillCommand();
                }
            }

            String value = this.optionalSkillCommand.getValue();
            return value;
        }

        public float getComplexity() {
            IngredientSlotObjectTemplate base = null;

            if (baseData != null)
                base = (IngredientSlotObjectTemplate) baseData;

            if (!complexity.isLoaded()) {
                if (base == null) {
                    return 0.0f;
                } else {
                    return base.getComplexity();
                }
            }

            float value = this.complexity.getValue();
            final byte delta = this.complexity.getDeltaType();

            if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
                float baseValue = 0;

                if (baseData != null) {
                    if (base != null)
                        baseValue = base.getComplexity();
                }

                if (delta == '+')
                    value = baseValue + value;
                if (delta == '-')
                    value = baseValue - value;
                if (delta == '=')
                    value = baseValue + (float) (baseValue * (value / 100.0f));
                if (delta == '_')
                    value = baseValue - (float) (baseValue * (value / 100.0f));
            }
            return value;
        }

        public String getAppearance() {
            IngredientSlotObjectTemplate base = null;

            if (baseData != null)
                base = (IngredientSlotObjectTemplate) baseData;

            if (!appearance.isLoaded()) {
                if (base == null) {
                    return "";
                } else {
                    return base.getAppearance();
                }
            }

            String value = this.appearance.getValue();
            return value;
        }

        @Override
        protected void load(final Iff iff) {
            iff.enterForm();
            iff.enterChunk();
            final int paramCount = iff.readInt();
            iff.exitChunk();
            for (int i = 0; i < paramCount; ++i) {
                iff.enterChunk();
                final String parameterName = iff.readString();

                if ("	".equalsIgnoreCase(parameterName)) {
                    optional.loadFromIff(objectTemplateList, iff);
                } else if ("	".equalsIgnoreCase(parameterName)) {
                    name.loadFromIff(objectTemplateList, iff);
                } else if ("	".equalsIgnoreCase(parameterName)) {
                    options.clear();
                    optionsAppend = iff.readBoolean();
                    int listCount = iff.readInt();
                    for (int j = 0; j < listCount; ++j) {
                        final StructParam<ObjectTemplate> newData = new StructParam<ObjectTemplate>();
                        newData.loadFromIff(objectTemplateList, iff);
                        options.add(newData);
                    }
                    optionsLoaded = true;
                } else if ("	".equalsIgnoreCase(parameterName)) {
                    optionalSkillCommand.loadFromIff(objectTemplateList, iff);
                } else if ("	".equalsIgnoreCase(parameterName)) {
                    complexity.loadFromIff(objectTemplateList, iff);
                } else if ("	".equalsIgnoreCase(parameterName)) {
                    appearance.loadFromIff(objectTemplateList, iff);
                } else {
                    throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
                }

                iff.exitChunk();
            }
            iff.exitForm();
        }

    }

}
