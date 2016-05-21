package com.ocdsoft.bacta.swg.server.object.template.shared;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.shared.foundation.DataResourceList;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.definition.TemplateDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
@TemplateDefinition
public class SharedPlayerObjectTemplate extends SharedIntangibleObjectTemplate {
	private static final Logger LOGGER = LoggerFactory.getLogger(SharedPlayerObjectTemplate.class);
	public static final int TAG_SHAREDPLAYEROBJECTTEMPLATE = Tag.convertStringToTag("SPLY");

	private static void registerTemplateConstructors(final DataResourceList<ObjectTemplate> objectTemplateList) {
		objectTemplateList.registerTemplate(SharedPlayerObjectTemplate.TAG_SHAREDPLAYEROBJECTTEMPLATE, SharedPlayerObjectTemplate::new);
	}

	private int templateVersion;


	public SharedPlayerObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
		super(filename, objectTemplateList);
	}

	@Override
	public int getId() {
		return TAG_SHAREDPLAYEROBJECTTEMPLATE;
	}

	@Override
	protected void load(final Iff iff) {
		if (iff.getCurrentName() != TAG_SHAREDPLAYEROBJECTTEMPLATE) {
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

