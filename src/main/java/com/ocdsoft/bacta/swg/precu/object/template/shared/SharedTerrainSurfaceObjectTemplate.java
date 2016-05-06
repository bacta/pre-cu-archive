package com.ocdsoft.bacta.swg.precu.object.template.shared;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.shared.foundation.DataResourceList;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.definition.TemplateDefinition;
import com.ocdsoft.bacta.swg.shared.utility.FloatParam;
import com.ocdsoft.bacta.swg.shared.utility.StringParam;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
@TemplateDefinition
public class SharedTerrainSurfaceObjectTemplate extends ObjectTemplate {
	public static final int TAG_SHAREDTERRAINSURFACEOBJECTTEMPLATE = Tag.convertStringToTag("STER");

	private int templateVersion;

	private final FloatParam cover = new FloatParam();
	private final StringParam surfaceType = new StringParam(); 

	public SharedTerrainSurfaceObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
		super(filename, objectTemplateList);
	}

	@Override
	public int getId() {
		return TAG_SHAREDTERRAINSURFACEOBJECTTEMPLATE;
	}

	public float getCover() {
		SharedTerrainSurfaceObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTerrainSurfaceObjectTemplate) baseData;

		if (!cover.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getCover();
			}
		}

		float value = this.cover.getValue();
		final byte delta = this.cover.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getCover();
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

	public String getSurfaceType() {
		SharedTerrainSurfaceObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTerrainSurfaceObjectTemplate) baseData;

		if (!surfaceType.isLoaded()) {
			if (base == null) {
				return "";
			} else {
				return base.getSurfaceType();
			}
		}

		String value = this.surfaceType.getValue();
		return value;
	}

	@Override
	protected void load(final Iff iff) {
		if (iff.getCurrentName() != TAG_SHAREDTERRAINSURFACEOBJECTTEMPLATE)
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

			if ("cover".equalsIgnoreCase(parameterName)) {
				cover.loadFromIff(objectTemplateList, iff);
			} else if ("surfaceType".equalsIgnoreCase(parameterName)) {
				surfaceType.loadFromIff(objectTemplateList, iff);
			} else {
				throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
			}

			iff.exitChunk();
		}
		iff.exitForm();
	}

}

