package com.ocdsoft.bacta.swg.precu.object.template.shared;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.shared.foundation.DataResourceList;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.definition.TemplateDefinition;
import com.ocdsoft.bacta.swg.shared.utility.BoolParam;
import com.ocdsoft.bacta.swg.shared.utility.StringParam;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
@TemplateDefinition
public class SharedShipObjectTemplate extends SharedTangibleObjectTemplate {
	public static final int TAG_SHAREDSHIPOBJECTTEMPLATE = Tag.convertStringToTag("SSHP");

	private int templateVersion;

	private final StringParam cockpitFilename = new StringParam();
	private final BoolParam hasWings = new BoolParam();
	private final BoolParam playerControlled = new BoolParam(); 
	private final StringParam interiorLayoutFileName = new StringParam(); 

	public SharedShipObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
		super(filename, objectTemplateList);
	}

	@Override
	public int getId() {
		return TAG_SHAREDSHIPOBJECTTEMPLATE;
	}

	public String getCockpitFilename() {
		SharedShipObjectTemplate base = null;

		if (baseData != null)
			base = (SharedShipObjectTemplate) baseData;

		if (!cockpitFilename.isLoaded()) {
			if (base == null) {
				return null;
			} else {
				return base.getCockpitFilename();
			}
		}

		String value = this.cockpitFilename.getValue();
		return value;
	}

	public boolean getHasWings() {
		SharedShipObjectTemplate base = null;

		if (baseData != null)
			base = (SharedShipObjectTemplate) baseData;

		if (!hasWings.isLoaded()) {
			if (base == null) {
				return false;
			} else {
				return base.getHasWings();
			}
		}

		boolean value = this.hasWings.getValue();
		return value;
	}

	public boolean getPlayerControlled() {
		SharedShipObjectTemplate base = null;

		if (baseData != null)
			base = (SharedShipObjectTemplate) baseData;

		if (!playerControlled.isLoaded()) {
			if (base == null) {
				return false;
			} else {
				return base.getPlayerControlled();
			}
		}

		boolean value = this.playerControlled.getValue();
		return value;
	}

	public String getInteriorLayoutFileName() {
		SharedShipObjectTemplate base = null;

		if (baseData != null)
			base = (SharedShipObjectTemplate) baseData;

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
		if (iff.getCurrentName() != TAG_SHAREDSHIPOBJECTTEMPLATE) {
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
		for (int i = 0; i < paramCount; ++i) {
			iff.enterChunk();
			final String parameterName = iff.readString();

			if ("cockpitFilename".equalsIgnoreCase(parameterName)) {
				cockpitFilename.loadFromIff(objectTemplateList, iff);
			} else if ("hasWings".equalsIgnoreCase(parameterName)) {
				hasWings.loadFromIff(objectTemplateList, iff);
			} else if ("playerControlled".equalsIgnoreCase(parameterName)) {
				playerControlled.loadFromIff(objectTemplateList, iff);
			} else if ("interiorLayoutFileName".equalsIgnoreCase(parameterName)) {
				interiorLayoutFileName.loadFromIff(objectTemplateList, iff);
			} else {
				throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
			}

			iff.exitChunk();
		}
		iff.exitForm();

		super.load(iff);
		iff.exitForm();
	}

}

