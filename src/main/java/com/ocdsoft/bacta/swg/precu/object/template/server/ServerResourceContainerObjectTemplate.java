package com.ocdsoft.bacta.swg.precu.object.template.server;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.foundation.Tag;
import com.ocdsoft.bacta.swg.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.template.ObjectTemplateList;
import com.ocdsoft.bacta.swg.utility.IntegerParam;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
public class ServerResourceContainerObjectTemplate extends ServerTangibleObjectTemplate {
    private static final int TAG_SERVERRESOURCECONTAINEROBJECTTEMPLATE = Tag.convertStringToTag("0000");

    private int templateVersion;

    private final IntegerParam maxResources = new IntegerParam(); //Maximum number of units of resource the container can hold.

    public ServerResourceContainerObjectTemplate(final String filename, final ObjectTemplateList objectTemplateList) {
        super(filename, objectTemplateList);
    }

    @Override
    public int getId() {
        return TAG_SERVERRESOURCECONTAINEROBJECTTEMPLATE;
    }

    public int getMaxResources() {
        ServerResourceContainerObjectTemplate base = null;

        if (baseData != null)
            base = (ServerResourceContainerObjectTemplate) baseData;

        if (!maxResources.isLoaded()) {
            if (base == null) {
                return 0;
            } else {
                return base.getMaxResources();
            }
        }

        int value = this.maxResources.getValue();
        final byte delta = this.maxResources.getDeltaType();

        if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
            int baseValue = 0;

            if (baseData != null) {
                if (base != null)
                    baseValue = base.getMaxResources();
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

    @Override
    protected void load(final Iff iff) {
        if (iff.getCurrentName() != TAG_SERVERRESOURCECONTAINEROBJECTTEMPLATE)
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
                maxResources.loadFromIff(objectTemplateList, iff);
            } else {
                throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
            }

            iff.exitChunk();
        }
        iff.exitForm();
    }

}

