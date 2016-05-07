package com.ocdsoft.bacta.swg.precu.object.template.server;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.shared.foundation.DataResourceList;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.definition.TemplateDefinition;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
@TemplateDefinition
public class ServerMissionListEntryObjectTemplate extends ServerIntangibleObjectTemplate {
	public static final int TAG_SERVERMISSIONLISTENTRYOBJECTTEMPLATE = Tag.convertStringToTag("MLEO");

	private static void registerTemplateConstructors(final DataResourceList<ObjectTemplate> objectTemplateList) {
		objectTemplateList.registerTemplate(ServerMissionListEntryObjectTemplate.TAG_SERVERMISSIONLISTENTRYOBJECTTEMPLATE, ServerMissionListEntryObjectTemplate::new);
	}

	private int templateVersion;


	public ServerMissionListEntryObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
		super(filename, objectTemplateList);
	}

	@Override
	public int getId() {
		return TAG_SERVERMISSIONLISTENTRYOBJECTTEMPLATE;
	}

	@Override
	protected void load(final Iff iff) {
		if (iff.getCurrentName() != TAG_SERVERMISSIONLISTENTRYOBJECTTEMPLATE) {
			super.load(iff);
			return;
		}

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

		super.load(iff);
		iff.exitForm();
	}

}

