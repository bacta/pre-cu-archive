package com.ocdsoft.bacta.swg.server.object.template.server;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.shared.foundation.DataResourceList;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.definition.TemplateDefinition;
import com.ocdsoft.bacta.swg.shared.utility.IntegerParam;
import com.ocdsoft.bacta.swg.shared.utility.StructParam;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
@TemplateDefinition
public class ServerArmorTemplate extends ObjectTemplate {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerArmorTemplate.class);
	public static final int TAG_SERVERARMORTEMPLATE = Tag.convertStringToTag("ARMO");

	private static void registerTemplateConstructors(final DataResourceList<ObjectTemplate> objectTemplateList) {
		objectTemplateList.registerTemplate(ServerArmorTemplate.TAG_SERVERARMORTEMPLATE, ServerArmorTemplate::new);
		objectTemplateList.registerTemplate(SpecialProtectionObjectTemplate.TAG_SPECIALPROTECTION, SpecialProtectionObjectTemplate::new);
	}

	private int templateVersion;

	private final IntegerParam rating = new IntegerParam(); //armor rating
	private final IntegerParam integrity = new IntegerParam(); //integrity
	private final IntegerParam effectiveness = new IntegerParam(); //default effectiveness (0 = only use special protection)
	private final List<StructParam<ObjectTemplate>> specialProtection = new ArrayList<>(); //damage-type specific protection
	private boolean specialProtectionLoaded;
	private boolean specialProtectionAppend;
	private final IntegerParam vulnerability = new IntegerParam(); //damaga types that this armor doesn't protect against
	private final IntegerParam[] encumbrance = new IntegerParam[]{ //reduction to attributes from wearing this armor
			new IntegerParam(),
			new IntegerParam(),
			new IntegerParam(),
	};

	public ServerArmorTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
		super(filename, objectTemplateList);
	}

	@Override
	public int getId() {
		return TAG_SERVERARMORTEMPLATE;
	}

	public ArmorRating getRating() {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!rating.isLoaded()) {
			if (base == null) {
				return ArmorRating.from(0);
			} else {
				return base.getRating();
			}
		}

		return ArmorRating.from(rating.getValue());
	}

	public int getIntegrity() {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!integrity.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getIntegrity();
			}
		}

		int value = this.integrity.getValue();
		final byte delta = this.integrity.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getIntegrity();
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

	public int getIntegrityMin() {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!integrity.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getIntegrityMin();
			}
		}

		int value = this.integrity.getMinValue();
		final byte delta = this.integrity.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getIntegrityMin();
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

	public int getIntegrityMax() {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!integrity.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getIntegrityMax();
			}
		}

		int value = this.integrity.getMaxValue();
		final byte delta = this.integrity.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getIntegrityMax();
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

	public int getEffectiveness() {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!effectiveness.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getEffectiveness();
			}
		}

		int value = this.effectiveness.getValue();
		final byte delta = this.effectiveness.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getEffectiveness();
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

	public int getEffectivenessMin() {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!effectiveness.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getEffectivenessMin();
			}
		}

		int value = this.effectiveness.getMinValue();
		final byte delta = this.effectiveness.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getEffectivenessMin();
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

	public int getEffectivenessMax() {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!effectiveness.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getEffectivenessMax();
			}
		}

		int value = this.effectiveness.getMaxValue();
		final byte delta = this.effectiveness.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getEffectivenessMax();
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

	public SpecialProtection getSpecialProtection(int index) {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!specialProtectionLoaded) {
			if (base == null) {
				return null;
			} else {
				return base.getSpecialProtection(index);
			}
		}

		if (specialProtectionAppend && base != null) {
			int baseCount = base.getSpecialProtectionCount();

			if (index < baseCount) {
				return base.getSpecialProtection(index);
			}
			index -= baseCount;
		}
		final ObjectTemplate structTemplate = specialProtection.get(index).getValue();
		Preconditions.checkNotNull(structTemplate);
		final SpecialProtectionObjectTemplate param = (SpecialProtectionObjectTemplate) structTemplate;

		final SpecialProtection data = new SpecialProtection();
		data.type = param.getType();
		data.effectiveness = param.getEffectiveness();

		return data;
	}

	public SpecialProtection getSpecialProtectionMin(int index) {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!specialProtectionLoaded) {
			if (base == null) {
				return null;
			} else {
				return base.getSpecialProtectionMin(index);
			}
		}

		if (specialProtectionAppend && base != null) {
			int baseCount = base.getSpecialProtectionCount();

			if (index < baseCount) {
				return base.getSpecialProtectionMin(index);
			}
			index -= baseCount;
		}
		final ObjectTemplate structTemplate = specialProtection.get(index).getValue();
		Preconditions.checkNotNull(structTemplate);
		final SpecialProtectionObjectTemplate param = (SpecialProtectionObjectTemplate) structTemplate;

		final SpecialProtection data = new SpecialProtection();
		data.type = param.getType();
		data.effectiveness = param.getEffectivenessMin();

		return data;
	}

	public SpecialProtection getSpecialProtectionMax(int index) {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!specialProtectionLoaded) {
			if (base == null) {
				return null;
			} else {
				return base.getSpecialProtectionMax(index);
			}
		}

		if (specialProtectionAppend && base != null) {
			int baseCount = base.getSpecialProtectionCount();

			if (index < baseCount) {
				return base.getSpecialProtectionMax(index);
			}
			index -= baseCount;
		}
		final ObjectTemplate structTemplate = specialProtection.get(index).getValue();
		Preconditions.checkNotNull(structTemplate);
		final SpecialProtectionObjectTemplate param = (SpecialProtectionObjectTemplate) structTemplate;

		final SpecialProtection data = new SpecialProtection();
		data.type = param.getType();
		data.effectiveness = param.getEffectivenessMax();

		return data;
	}

	public int getSpecialProtectionCount() {
		if (!specialProtectionLoaded) {
			if (baseData == null)
				return 0;

			final ServerArmorTemplate base = (ServerArmorTemplate) baseData;
			return base.getSpecialProtectionCount();
		}

		int count = specialProtection.size();

		if (specialProtectionAppend && baseData != null) {
			final ServerArmorTemplate base = (ServerArmorTemplate) baseData;
			count += base.getSpecialProtectionCount();
		}

		return count;
	}

	public int getVulnerability() {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!vulnerability.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getVulnerability();
			}
		}

		int value = this.vulnerability.getValue();
		final byte delta = this.vulnerability.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getVulnerability();
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

	public int getVulnerabilityMin() {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!vulnerability.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getVulnerabilityMin();
			}
		}

		int value = this.vulnerability.getMinValue();
		final byte delta = this.vulnerability.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getVulnerabilityMin();
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

	public int getVulnerabilityMax() {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!vulnerability.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getVulnerabilityMax();
			}
		}

		int value = this.vulnerability.getMaxValue();
		final byte delta = this.vulnerability.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getVulnerabilityMax();
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

	public int getEncumbrance(int index) {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!encumbrance[index].isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getEncumbrance(index);
			}
		}

		int value = this.encumbrance[index].getValue();
		final byte delta = this.encumbrance[index].getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getEncumbrance(index);
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

	public int getEncumbranceMin(int index) {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!encumbrance[index].isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getEncumbranceMin(index);
			}
		}

		int value = this.encumbrance[index].getMinValue();
		final byte delta = this.encumbrance[index].getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getEncumbranceMin(index);
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

	public int getEncumbranceMax(int index) {
		ServerArmorTemplate base = null;

		if (baseData instanceof ServerArmorTemplate)
			base = (ServerArmorTemplate) baseData;

		if (!encumbrance[index].isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getEncumbranceMax(index);
			}
		}

		int value = this.encumbrance[index].getMaxValue();
		final byte delta = this.encumbrance[index].getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getEncumbranceMax(index);
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
		if (iff.getCurrentName() != TAG_SERVERARMORTEMPLATE) {
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

			if ("rating".equalsIgnoreCase(parameterName)) {
				rating.loadFromIff(objectTemplateList, iff);
			} else if ("integrity".equalsIgnoreCase(parameterName)) {
				integrity.loadFromIff(objectTemplateList, iff);
			} else if ("effectiveness".equalsIgnoreCase(parameterName)) {
				effectiveness.loadFromIff(objectTemplateList, iff);
			} else if ("specialProtection".equalsIgnoreCase(parameterName)) {
				specialProtection.clear();
				specialProtectionAppend = iff.readBoolean();
				int listCount = iff.readInt();
				for (int j = 0; j < listCount; ++j) {
					final StructParam<ObjectTemplate> newData = new StructParam<ObjectTemplate>();
					newData.loadFromIff(objectTemplateList, iff);
					specialProtection.add(newData);
				}
				specialProtectionLoaded = true;
			} else if ("vulnerability".equalsIgnoreCase(parameterName)) {
				vulnerability.loadFromIff(objectTemplateList, iff);
			} else if ("encumbrance".equalsIgnoreCase(parameterName)) {
				int listCount = iff.readInt();
				int j;
				for (j = 0; j < 3 && j < listCount; ++j)
					encumbrance[j].loadFromIff(objectTemplateList, iff);
				for (; j < listCount; ++j) {
					final IntegerParam dummy = new IntegerParam();
					dummy.loadFromIff(objectTemplateList, iff);
				}
			} else {
				LOGGER.error("Unexpected parameter {}", parameterName);
			}

			iff.exitChunk();
		}
		iff.exitForm();
	}

	public enum ArmorRating {
		AR_armorRealNone(-1), //needed for internal reasons, do not use in templates! This means you!
		AR_armorNone(0), 
		AR_armorLight(1), 
		AR_armorMedium(2), 
		AR_armorHeavy(3); 

		private static final ArmorRating[] values = values();
		public final long value;

		ArmorRating(final long value) {
			this.value = value;
		}
		public static ArmorRating from(final long value) {
			for (final ArmorRating e : values)
				if (e.value == value) return e;
			throw new IllegalArgumentException(String.format("UNKNOWN value %d for enum ArmorRating", value));
		}
	}

	public enum DamageType {
		DT_kinetic(0x00000001),
		DT_energy(0x00000002),
		DT_blast(0x00000004),
		DT_stun(0x00000008),
		DT_restraint(0x00000010),
		DT_elemental_heat(0x00000020), 
		DT_elemental_cold(0x00000040), 
		DT_elemental_acid(0x00000080), 
		DT_elemental_electrical(0x00000100), 
		DT_environmental_heat(0x00000200), 
		DT_environmental_cold(0x00000400), 
		DT_environmental_acid(0x00000800), 
		DT_environmental_electrical(0x00001000); 

		private static final DamageType[] values = values();
		public final long value;

		DamageType(final long value) {
			this.value = value;
		}
		public static DamageType from(final long value) {
			for (final DamageType e : values)
				if (e.value == value) return e;
			throw new IllegalArgumentException(String.format("UNKNOWN value %d for enum DamageType", value));
		}
	}

	public static class SpecialProtection {
		@Getter
		protected DamageType type;
		@Getter
		protected int effectiveness;
	}

	protected static class SpecialProtectionObjectTemplate extends ObjectTemplate {
		private static final Logger LOGGER = LoggerFactory.getLogger(SpecialProtectionObjectTemplate.class);
		public static final int TAG_SPECIALPROTECTION = Tag.convertStringToTag("ARSP");

		public SpecialProtectionObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
			super(filename, objectTemplateList);
		}

		private final IntegerParam type = new IntegerParam(); //specific damage being protected from
		private final IntegerParam effectiveness = new IntegerParam(); //armor effectiveness ( <0 = no protection for this damage type, default protection will be ignored)

		@Override
		public int getId() {
			return TAG_SPECIALPROTECTION;
		}

		public DamageType getType() {
			SpecialProtectionObjectTemplate base = null;

			if (baseData instanceof SpecialProtectionObjectTemplate)
				base = (SpecialProtectionObjectTemplate) baseData;

			if (!type.isLoaded()) {
				if (base == null) {
					return DamageType.from(0);
				} else {
					return base.getType();
				}
			}

			return DamageType.from(type.getValue());
		}

		public int getEffectiveness() {
			SpecialProtectionObjectTemplate base = null;

			if (baseData instanceof SpecialProtectionObjectTemplate)
				base = (SpecialProtectionObjectTemplate) baseData;

			if (!effectiveness.isLoaded()) {
				if (base == null) {
					return 0;
				} else {
					return base.getEffectiveness();
				}
			}

			int value = this.effectiveness.getValue();
			final byte delta = this.effectiveness.getDeltaType();

			if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
				int baseValue = 0;

				if (baseData != null) {
					if (base != null)
						baseValue = base.getEffectiveness();
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

		public int getEffectivenessMin() {
			SpecialProtectionObjectTemplate base = null;

			if (baseData instanceof SpecialProtectionObjectTemplate)
				base = (SpecialProtectionObjectTemplate) baseData;

			if (!effectiveness.isLoaded()) {
				if (base == null) {
					return 0;
				} else {
					return base.getEffectivenessMin();
				}
			}

			int value = this.effectiveness.getMinValue();
			final byte delta = this.effectiveness.getDeltaType();

			if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
				int baseValue = 0;

				if (baseData != null) {
					if (base != null)
						baseValue = base.getEffectivenessMin();
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

		public int getEffectivenessMax() {
			SpecialProtectionObjectTemplate base = null;

			if (baseData instanceof SpecialProtectionObjectTemplate)
				base = (SpecialProtectionObjectTemplate) baseData;

			if (!effectiveness.isLoaded()) {
				if (base == null) {
					return 0;
				} else {
					return base.getEffectivenessMax();
				}
			}

			int value = this.effectiveness.getMaxValue();
			final byte delta = this.effectiveness.getDeltaType();

			if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
				int baseValue = 0;

				if (baseData != null) {
					if (base != null)
						baseValue = base.getEffectivenessMax();
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
			iff.enterForm();
			iff.enterChunk();
			final int paramCount = iff.readInt();
			iff.exitChunk();
			for (int i = 0; i < paramCount; ++i) {
				iff.enterChunk();
				final String parameterName = iff.readString();

				if ("type".equalsIgnoreCase(parameterName)) {
					type.loadFromIff(objectTemplateList, iff);
				} else if ("effectiveness".equalsIgnoreCase(parameterName)) {
					effectiveness.loadFromIff(objectTemplateList, iff);
				} else {
					LOGGER.error("Unexpected parameter {}", parameterName);
				}

				iff.exitChunk();
			}
			iff.exitForm();
		}

	}

}

