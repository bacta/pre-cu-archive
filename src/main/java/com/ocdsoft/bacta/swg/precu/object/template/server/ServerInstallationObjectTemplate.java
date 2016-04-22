package com.ocdsoft.bacta.swg.precu.object.template.server;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.foundation.Tag;
import com.ocdsoft.bacta.swg.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.template.ObjectTemplateList;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
public class ServerInstallationObjectTemplate extends ServerTangibleObjectTemplate {
    private static final int TAG_SERVERINSTALLATIONOBJECTTEMPLATE = Tag.convertStringToTag("0000");

    private int templateVersion;


    public ServerInstallationObjectTemplate(final String filename, final ObjectTemplateList objectTemplateList) {
        super(filename, objectTemplateList);
    }

    @Override
    public int getId() {
        return TAG_SERVERINSTALLATIONOBJECTTEMPLATE;
    }

    @Override
    protected void load(final Iff iff) {
        if (iff.getCurrentName() != TAG_SERVERINSTALLATIONOBJECTTEMPLATE)
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
        iff.exitForm();
    }

}

