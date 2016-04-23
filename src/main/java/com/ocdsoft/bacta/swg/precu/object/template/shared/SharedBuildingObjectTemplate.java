package com.ocdsoft.bacta.swg.precu.object.template.shared;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;
import com.ocdsoft.bacta.swg.shared.utility.StringParam;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
public class SharedBuildingObjectTemplate extends SharedTangibleObjectTemplate {
    private static final int TAG_SHAREDBUILDINGOBJECTTEMPLATE = Tag.convertStringToTag("0000");

    private int templateVersion;

    private final StringParam terrainModificationFileName = new StringParam();
    private final StringParam interiorLayoutFileName = new StringParam();

    public SharedBuildingObjectTemplate(final String filename, final ObjectTemplateList objectTemplateList) {
        super(filename, objectTemplateList);
    }

    @Override
    public int getId() {
        return TAG_SHAREDBUILDINGOBJECTTEMPLATE;
    }

    public String getTerrainModificationFileName() {
        SharedBuildingObjectTemplate base = null;

        if (baseData != null)
            base = (SharedBuildingObjectTemplate) baseData;

        if (!terrainModificationFileName.isLoaded()) {
            if (base == null) {
                return null;
            } else {
                return base.getTerrainModificationFileName();
            }
        }

        String value = this.terrainModificationFileName.getValue();
        return value;
    }

    public String getInteriorLayoutFileName() {
        SharedBuildingObjectTemplate base = null;

        if (baseData != null)
            base = (SharedBuildingObjectTemplate) baseData;

        if (!interiorLayoutFileName.isLoaded()) {
            if (base == null) {
                return null;
            } else {
                return base.getInteriorLayoutFileName();
            }
        }

        String value = this.interiorLayoutFileName.getValue();
        return value;
    }

    @Override
    protected void load(final Iff iff) {
        if (iff.getCurrentName() != TAG_SHAREDBUILDINGOBJECTTEMPLATE)
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
                terrainModificationFileName.loadFromIff(objectTemplateList, iff);
            } else if ("".equalsIgnoreCase(parameterName)) {
                interiorLayoutFileName.loadFromIff(objectTemplateList, iff);
            } else {
                throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
            }

            iff.exitChunk();
        }
        iff.exitForm();
    }

}

