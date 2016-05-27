package com.ocdsoft.bacta.swg.server.game.synchronizedui;

import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaBoolean;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaByte;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaFloat;
import com.ocdsoft.bacta.swg.archive.delta.vector.AutoDeltaFloatVector;
import com.ocdsoft.bacta.swg.archive.delta.vector.AutoDeltaIntVector;
import com.ocdsoft.bacta.swg.archive.delta.vector.AutoDeltaObjectVector;
import com.ocdsoft.bacta.swg.archive.delta.vector.AutoDeltaStringVector;
import com.ocdsoft.bacta.swg.server.game.object.intangible.manufacture.ManufactureSchematicObject;
import com.ocdsoft.bacta.swg.shared.localization.StringId;

/**
 * Created by crush on 5/7/2016.
 */
public class ManufactureSchematicSynchronizedUi extends ServerSynchronizedUi {

    //The following fields are parallel arrays for ingredients
    private final AutoDeltaObjectVector<StringId> slotName; // slot name
    private final AutoDeltaIntVector slotType; // enum Crafting::IngredientType
    //private final AutoDeltaVector<TLongList slotIngredient = new AutoDeltaVector<>(); // actual ingredients used per slot
    //private final AutoDeltaVector<TIntList> slotIngredientCount = new AutoDeltaVector<>(); // number of ingredients used per slot
    private final AutoDeltaFloatVector slotComplexity; // cuurent slot complexity
    private final AutoDeltaIntVector slotDraftOption; // the draft schematic slot option
    private final AutoDeltaIntVector slotDraftIndex; // the draft schematic slot index
    private final AutoDeltaByte slotIngredientsChanged; // used to signal changes

    //The following fields are parallel arrays for experimentation
    private final AutoDeltaObjectVector<StringId> attributeName; // attributes that can be experimented with
    private final AutoDeltaFloatVector attributeValue; // current value of the attributes
    private final AutoDeltaFloatVector minAttribute; // min value of the attribute
    private final AutoDeltaFloatVector maxAttribute; // max value of the attribute
    private final AutoDeltaFloatVector resourceMaxAttribute; // max value of the attribute due to resource selection

    // The following fields are parallel arrays for customizations
    private final AutoDeltaStringVector customName; // customization property name
    private final AutoDeltaIntVector customIndex; // palette/decal current index
    private final AutoDeltaIntVector customMinIndex; // palette/decal minimum index
    private final AutoDeltaIntVector customMaxIndex; // palette/decal maximum index
    private final AutoDeltaByte customChanged; // used to signal changes

    private final AutoDeltaFloat experimentMod; // approximate mod that will be applied to experimentation tries
    private final AutoDeltaStringVector appearance; // list of appearances the player may choose from
    private final AutoDeltaBoolean ready; // flag that the data is ready


    public ManufactureSchematicSynchronizedUi(final ManufactureSchematicObject owner) {
        super(owner);

        slotName = new AutoDeltaObjectVector<StringId>(StringId::new);
        slotType = new AutoDeltaIntVector();
        slotComplexity = new AutoDeltaFloatVector();
        slotDraftOption = new AutoDeltaIntVector();
        slotDraftIndex = new AutoDeltaIntVector();
        slotIngredientsChanged = new AutoDeltaByte();
        attributeName = new AutoDeltaObjectVector<StringId>(StringId::new);
        attributeValue = new AutoDeltaFloatVector();
        minAttribute = new AutoDeltaFloatVector();
        maxAttribute = new AutoDeltaFloatVector();
        resourceMaxAttribute = new AutoDeltaFloatVector();
        customName = new AutoDeltaStringVector();
        customIndex = new AutoDeltaIntVector();
        customMinIndex = new AutoDeltaIntVector();
        customMaxIndex = new AutoDeltaIntVector();
        customChanged = new AutoDeltaByte();
        experimentMod = new AutoDeltaFloat();
        appearance = new AutoDeltaStringVector();
        ready = new AutoDeltaBoolean();

        addToUiPackage(slotName);
        addToUiPackage(slotType);
        //addToUiPackage(slotIngredient);
        //addToUiPackage(slotIngredientCount);
        addToUiPackage(slotComplexity);
        addToUiPackage(slotDraftOption);
        addToUiPackage(slotDraftIndex);
        addToUiPackage(slotIngredientsChanged);
        addToUiPackage(attributeName);
        addToUiPackage(attributeValue);
        addToUiPackage(minAttribute);
        addToUiPackage(maxAttribute);
        addToUiPackage(resourceMaxAttribute);
        addToUiPackage(customName);
        addToUiPackage(customIndex);
        addToUiPackage(customMinIndex);
        addToUiPackage(customMaxIndex);
        addToUiPackage(customChanged);
        addToUiPackage(experimentMod);
        addToUiPackage(appearance);
        addToUiPackage(ready);
    }
}
