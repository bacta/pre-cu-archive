package com.ocdsoft.bacta.swg.precu.object.template.shared;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.shared.foundation.DataResourceList;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.definition.TemplateDefinition;
import com.ocdsoft.bacta.swg.shared.utility.BoolParam;
import com.ocdsoft.bacta.swg.shared.utility.IntegerParam;
import com.ocdsoft.bacta.swg.shared.utility.StringParam;
import com.ocdsoft.bacta.swg.shared.utility.StructParam;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
@TemplateDefinition
public class SharedTangibleObjectTemplate extends SharedObjectTemplate {
	public static final int TAG_SHAREDTANGIBLEOBJECTTEMPLATE = Tag.convertStringToTag("STOT");

	private int templateVersion;

	private final List<StructParam<ObjectTemplate>> paletteColorCustomizationVariables = new ArrayList<>(); // all palette color customization variables exposed by an Object created with this template
	private boolean paletteColorCustomizationVariablesLoaded;
	private boolean paletteColorCustomizationVariablesAppend;
	private final List<StructParam<ObjectTemplate>> rangedIntCustomizationVariables = new ArrayList<>(); // all ranged-int style customization variables exposed by an Object created with this template
	private boolean rangedIntCustomizationVariablesLoaded;
	private boolean rangedIntCustomizationVariablesAppend;
	private final List<StructParam<ObjectTemplate>> constStringCustomizationVariables = new ArrayList<>(); // constant string values added to the Object's customization data, not persisted in the DB
	private boolean constStringCustomizationVariablesLoaded;
	private boolean constStringCustomizationVariablesAppend;
	private final List<IntegerParam> socketDestinations = new ArrayList<>(); // GOTs that this object can be socketed into
	private boolean socketDestinationsLoaded;
	private boolean socketDestinationsAppend;
	private final StringParam structureFootprintFileName = new StringParam();
	private final BoolParam useStructureFootprintOutline = new BoolParam(); 
	private final BoolParam targetable = new BoolParam(); // can the object be targetted by the client
	private final List<StringParam> certificationsRequired = new ArrayList<>(); // List of the certifications required to use this item (used in x1 only)
	private boolean certificationsRequiredLoaded;
	private boolean certificationsRequiredAppend;
	private final List<StructParam<ObjectTemplate>> customizationVariableMapping = new ArrayList<>(); // Allows remapping of variables when needed
	private boolean customizationVariableMappingLoaded;
	private boolean customizationVariableMappingAppend;
	private final IntegerParam clientVisabilityFlag = new IntegerParam(); // can the object be viewed on the client

	public SharedTangibleObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
		super(filename, objectTemplateList);
	}

	@Override
	public int getId() {
		return TAG_SHAREDTANGIBLEOBJECTTEMPLATE;
	}

	public PaletteColorCustomizationVariable getPaletteColorCustomizationVariables(int index) {
		SharedTangibleObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTangibleObjectTemplate) baseData;

		if (!paletteColorCustomizationVariablesLoaded) {
			if (base == null) {
				return null;
			} else {
				return base.getPaletteColorCustomizationVariables(index);
			}
		}

		if (paletteColorCustomizationVariablesAppend && base != null) {
			int baseCount = base.getPaletteColorCustomizationVariablesCount();

			if (index < baseCount) {
				return base.getPaletteColorCustomizationVariables(index);
			}
			index -= baseCount;
		}
		final ObjectTemplate structTemplate = paletteColorCustomizationVariables.get(index).getValue();
		Preconditions.checkNotNull(structTemplate);
		final PaletteColorCustomizationVariableObjectTemplate param = (PaletteColorCustomizationVariableObjectTemplate) structTemplate;

		final PaletteColorCustomizationVariable data = new PaletteColorCustomizationVariable();
		data.variableName = param.getVariableName();
		data.palettePathName = param.getPalettePathName();
		data.defaultPaletteIndex = param.getDefaultPaletteIndex();

		return data;
	}

	public int getPaletteColorCustomizationVariablesCount() {
		if (!paletteColorCustomizationVariablesLoaded) {
			if (baseData == null)
				return 0;

			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			return base.getPaletteColorCustomizationVariablesCount();
		}

		int count = paletteColorCustomizationVariables.size();

		if (paletteColorCustomizationVariablesAppend && baseData != null) {
			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			count += base.getPaletteColorCustomizationVariablesCount();
		}

		return count;
	}

	public RangedIntCustomizationVariable getRangedIntCustomizationVariables(int index) {
		SharedTangibleObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTangibleObjectTemplate) baseData;

		if (!rangedIntCustomizationVariablesLoaded) {
			if (base == null) {
				return null;
			} else {
				return base.getRangedIntCustomizationVariables(index);
			}
		}

		if (rangedIntCustomizationVariablesAppend && base != null) {
			int baseCount = base.getRangedIntCustomizationVariablesCount();

			if (index < baseCount) {
				return base.getRangedIntCustomizationVariables(index);
			}
			index -= baseCount;
		}
		final ObjectTemplate structTemplate = rangedIntCustomizationVariables.get(index).getValue();
		Preconditions.checkNotNull(structTemplate);
		final RangedIntCustomizationVariableObjectTemplate param = (RangedIntCustomizationVariableObjectTemplate) structTemplate;

		final RangedIntCustomizationVariable data = new RangedIntCustomizationVariable();
		data.variableName = param.getVariableName();
		data.minValueInclusive = param.getMinValueInclusive();
		data.defaultValue = param.getDefaultValue();
		data.maxValueExclusive = param.getMaxValueExclusive();

		return data;
	}

	public int getRangedIntCustomizationVariablesCount() {
		if (!rangedIntCustomizationVariablesLoaded) {
			if (baseData == null)
				return 0;

			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			return base.getRangedIntCustomizationVariablesCount();
		}

		int count = rangedIntCustomizationVariables.size();

		if (rangedIntCustomizationVariablesAppend && baseData != null) {
			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			count += base.getRangedIntCustomizationVariablesCount();
		}

		return count;
	}

	public ConstStringCustomizationVariable getConstStringCustomizationVariables(int index) {
		SharedTangibleObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTangibleObjectTemplate) baseData;

		if (!constStringCustomizationVariablesLoaded) {
			if (base == null) {
				return null;
			} else {
				return base.getConstStringCustomizationVariables(index);
			}
		}

		if (constStringCustomizationVariablesAppend && base != null) {
			int baseCount = base.getConstStringCustomizationVariablesCount();

			if (index < baseCount) {
				return base.getConstStringCustomizationVariables(index);
			}
			index -= baseCount;
		}
		final ObjectTemplate structTemplate = constStringCustomizationVariables.get(index).getValue();
		Preconditions.checkNotNull(structTemplate);
		final ConstStringCustomizationVariableObjectTemplate param = (ConstStringCustomizationVariableObjectTemplate) structTemplate;

		final ConstStringCustomizationVariable data = new ConstStringCustomizationVariable();
		data.variableName = param.getVariableName();
		data.constValue = param.getConstValue();

		return data;
	}

	public int getConstStringCustomizationVariablesCount() {
		if (!constStringCustomizationVariablesLoaded) {
			if (baseData == null)
				return 0;

			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			return base.getConstStringCustomizationVariablesCount();
		}

		int count = constStringCustomizationVariables.size();

		if (constStringCustomizationVariablesAppend && baseData != null) {
			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			count += base.getConstStringCustomizationVariablesCount();
		}

		return count;
	}

	public GameObjectType getSocketDestinations(int index) {
		SharedTangibleObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTangibleObjectTemplate) baseData;

		if (!socketDestinationsLoaded) {
			if (base == null) {
				return GameObjectType.from(0);
			} else {
				return base.getSocketDestinations(index);
			}
		}

		if (socketDestinationsAppend && base != null) {
			int baseCount = base.getSocketDestinationsCount();

			if (index < baseCount) {
				return base.getSocketDestinations(index);
			}
			index -= baseCount;
		}
		return GameObjectType.from(socketDestinations.get(index).getValue());
	}

	public int getSocketDestinationsCount() {
		if (!socketDestinationsLoaded) {
			if (baseData == null)
				return 0;

			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			return base.getSocketDestinationsCount();
		}

		int count = socketDestinations.size();

		if (socketDestinationsAppend && baseData != null) {
			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			count += base.getSocketDestinationsCount();
		}

		return count;
	}

	public String getStructureFootprintFileName() {
		SharedTangibleObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTangibleObjectTemplate) baseData;

		if (!structureFootprintFileName.isLoaded()) {
			if (base == null) {
				return null;
			} else {
				return base.getStructureFootprintFileName();
			}
		}

		String value = this.structureFootprintFileName.getValue();
		return value;
	}

	public boolean getUseStructureFootprintOutline() {
		SharedTangibleObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTangibleObjectTemplate) baseData;

		if (!useStructureFootprintOutline.isLoaded()) {
			if (base == null) {
				return false;
			} else {
				return base.getUseStructureFootprintOutline();
			}
		}

		boolean value = this.useStructureFootprintOutline.getValue();
		return value;
	}

	public boolean getTargetable() {
		SharedTangibleObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTangibleObjectTemplate) baseData;

		if (!targetable.isLoaded()) {
			if (base == null) {
				return false;
			} else {
				return base.getTargetable();
			}
		}

		boolean value = this.targetable.getValue();
		return value;
	}

	public String getCertificationsRequired(int index) {
		SharedTangibleObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTangibleObjectTemplate) baseData;

		if (!certificationsRequiredLoaded) {
			if (base == null) {
				return "";
			} else {
				return base.getCertificationsRequired(index);
			}
		}

		if (certificationsRequiredAppend && base != null) {
			int baseCount = base.getCertificationsRequiredCount();

			if (index < baseCount) {
				return base.getCertificationsRequired(index);
			}
			index -= baseCount;
		}
		String value = this.certificationsRequired.get(index).getValue();
		return value;
	}

	public int getCertificationsRequiredCount() {
		if (!certificationsRequiredLoaded) {
			if (baseData == null)
				return 0;

			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			return base.getCertificationsRequiredCount();
		}

		int count = certificationsRequired.size();

		if (certificationsRequiredAppend && baseData != null) {
			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			count += base.getCertificationsRequiredCount();
		}

		return count;
	}

	public CustomizationVariableMapping getCustomizationVariableMapping(int index) {
		SharedTangibleObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTangibleObjectTemplate) baseData;

		if (!customizationVariableMappingLoaded) {
			if (base == null) {
				return null;
			} else {
				return base.getCustomizationVariableMapping(index);
			}
		}

		if (customizationVariableMappingAppend && base != null) {
			int baseCount = base.getCustomizationVariableMappingCount();

			if (index < baseCount) {
				return base.getCustomizationVariableMapping(index);
			}
			index -= baseCount;
		}
		final ObjectTemplate structTemplate = customizationVariableMapping.get(index).getValue();
		Preconditions.checkNotNull(structTemplate);
		final CustomizationVariableMappingObjectTemplate param = (CustomizationVariableMappingObjectTemplate) structTemplate;

		final CustomizationVariableMapping data = new CustomizationVariableMapping();
		data.sourceVariable = param.getSourceVariable();
		data.dependentVariable = param.getDependentVariable();

		return data;
	}

	public int getCustomizationVariableMappingCount() {
		if (!customizationVariableMappingLoaded) {
			if (baseData == null)
				return 0;

			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			return base.getCustomizationVariableMappingCount();
		}

		int count = customizationVariableMapping.size();

		if (customizationVariableMappingAppend && baseData != null) {
			final SharedTangibleObjectTemplate base = (SharedTangibleObjectTemplate) baseData;
			count += base.getCustomizationVariableMappingCount();
		}

		return count;
	}

	public ClientVisabilityFlags getClientVisabilityFlag() {
		SharedTangibleObjectTemplate base = null;

		if (baseData != null)
			base = (SharedTangibleObjectTemplate) baseData;

		if (!clientVisabilityFlag.isLoaded()) {
			if (base == null) {
				return ClientVisabilityFlags.from(0);
			} else {
				return base.getClientVisabilityFlag();
			}
		}

		return ClientVisabilityFlags.from(clientVisabilityFlag.getValue());
	}

	@Override
	protected void load(final Iff iff) {
		if (iff.getCurrentName() != TAG_SHAREDTANGIBLEOBJECTTEMPLATE)
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

			if ("paletteColorCustomizationVariables".equalsIgnoreCase(parameterName)) {
				paletteColorCustomizationVariables.clear();
				paletteColorCustomizationVariablesAppend = iff.readBoolean();
				int listCount = iff.readInt();
				for (int j = 0; j < listCount; ++j) {
					final StructParam<ObjectTemplate> newData = new StructParam<ObjectTemplate>();
					newData.loadFromIff(objectTemplateList, iff);
					paletteColorCustomizationVariables.add(newData);
				}
				paletteColorCustomizationVariablesLoaded = true;
			} else if ("rangedIntCustomizationVariables".equalsIgnoreCase(parameterName)) {
				rangedIntCustomizationVariables.clear();
				rangedIntCustomizationVariablesAppend = iff.readBoolean();
				int listCount = iff.readInt();
				for (int j = 0; j < listCount; ++j) {
					final StructParam<ObjectTemplate> newData = new StructParam<ObjectTemplate>();
					newData.loadFromIff(objectTemplateList, iff);
					rangedIntCustomizationVariables.add(newData);
				}
				rangedIntCustomizationVariablesLoaded = true;
			} else if ("constStringCustomizationVariables".equalsIgnoreCase(parameterName)) {
				constStringCustomizationVariables.clear();
				constStringCustomizationVariablesAppend = iff.readBoolean();
				int listCount = iff.readInt();
				for (int j = 0; j < listCount; ++j) {
					final StructParam<ObjectTemplate> newData = new StructParam<ObjectTemplate>();
					newData.loadFromIff(objectTemplateList, iff);
					constStringCustomizationVariables.add(newData);
				}
				constStringCustomizationVariablesLoaded = true;
			} else if ("socketDestinations".equalsIgnoreCase(parameterName)) {
				socketDestinations.clear();
				socketDestinationsAppend = iff.readBoolean();
				int listCount = iff.readInt();
				for (int j = 0; j < listCount; ++j) {
					final IntegerParam newData = new IntegerParam();
					newData.loadFromIff(objectTemplateList, iff);
					socketDestinations.add(newData);
				}
				socketDestinationsLoaded = true;
			} else if ("structureFootprintFileName".equalsIgnoreCase(parameterName)) {
				structureFootprintFileName.loadFromIff(objectTemplateList, iff);
			} else if ("useStructureFootprintOutline".equalsIgnoreCase(parameterName)) {
				useStructureFootprintOutline.loadFromIff(objectTemplateList, iff);
			} else if ("targetable".equalsIgnoreCase(parameterName)) {
				targetable.loadFromIff(objectTemplateList, iff);
			} else if ("certificationsRequired".equalsIgnoreCase(parameterName)) {
				certificationsRequired.clear();
				certificationsRequiredAppend = iff.readBoolean();
				int listCount = iff.readInt();
				for (int j = 0; j < listCount; ++j) {
					final StringParam newData = new StringParam();
					newData.loadFromIff(objectTemplateList, iff);
					certificationsRequired.add(newData);
				}
				certificationsRequiredLoaded = true;
			} else if ("customizationVariableMapping".equalsIgnoreCase(parameterName)) {
				customizationVariableMapping.clear();
				customizationVariableMappingAppend = iff.readBoolean();
				int listCount = iff.readInt();
				for (int j = 0; j < listCount; ++j) {
					final StructParam<ObjectTemplate> newData = new StructParam<ObjectTemplate>();
					newData.loadFromIff(objectTemplateList, iff);
					customizationVariableMapping.add(newData);
				}
				customizationVariableMappingLoaded = true;
			} else if ("clientVisabilityFlag".equalsIgnoreCase(parameterName)) {
				clientVisabilityFlag.loadFromIff(objectTemplateList, iff);
			} else {
				throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
			}

			iff.exitChunk();
		}
		iff.exitForm();
	}

	public enum ClientVisabilityFlags {
		CVF_always(0),
		CVF_gm_only(1); 

		private static final ClientVisabilityFlags[] values = values();
		public final long value;

		ClientVisabilityFlags(final long value) {
			this.value = value;
		}
		public static ClientVisabilityFlags from(final long value) {
			for (final ClientVisabilityFlags e : values)
				if (e.value == value) return e;
			throw new IllegalArgumentException(String.format("UNKNOWN value %d for enum ClientVisabilityFlags", value));
		}
	}

	public static class RangedIntCustomizationVariable {
		@Getter
		protected String variableName;
		@Getter
		protected int minValueInclusive;
		@Getter
		protected int defaultValue;
		@Getter
		protected int maxValueExclusive;
	}

	protected static class RangedIntCustomizationVariableObjectTemplate extends ObjectTemplate {
		public static final int TAG_RANGEDINTCUSTOMIZATIONVARIABLE = Tag.convertStringToTag("RICV");

		public RangedIntCustomizationVariableObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
			super(filename, objectTemplateList);
		}

		private final StringParam variableName = new StringParam();
		private final IntegerParam minValueInclusive = new IntegerParam();
		private final IntegerParam defaultValue = new IntegerParam();
		private final IntegerParam maxValueExclusive = new IntegerParam(); 

		@Override
		public int getId() {
			return TAG_RANGEDINTCUSTOMIZATIONVARIABLE;
		}

		public String getVariableName() {
			RangedIntCustomizationVariableObjectTemplate base = null;

			if (baseData != null)
				base = (RangedIntCustomizationVariableObjectTemplate) baseData;

			if (!variableName.isLoaded()) {
				if (base == null) {
					return "";
				} else {
					return base.getVariableName();
				}
			}

			String value = this.variableName.getValue();
			return value;
		}

		public int getMinValueInclusive() {
			RangedIntCustomizationVariableObjectTemplate base = null;

			if (baseData != null)
				base = (RangedIntCustomizationVariableObjectTemplate) baseData;

			if (!minValueInclusive.isLoaded()) {
				if (base == null) {
					return 0;
				} else {
					return base.getMinValueInclusive();
				}
			}

			int value = this.minValueInclusive.getValue();
			final byte delta = this.minValueInclusive.getDeltaType();

			if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
				int baseValue = 0;

				if (baseData != null) {
					if (base != null)
						baseValue = base.getMinValueInclusive();
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

		public int getDefaultValue() {
			RangedIntCustomizationVariableObjectTemplate base = null;

			if (baseData != null)
				base = (RangedIntCustomizationVariableObjectTemplate) baseData;

			if (!defaultValue.isLoaded()) {
				if (base == null) {
					return 0;
				} else {
					return base.getDefaultValue();
				}
			}

			int value = this.defaultValue.getValue();
			final byte delta = this.defaultValue.getDeltaType();

			if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
				int baseValue = 0;

				if (baseData != null) {
					if (base != null)
						baseValue = base.getDefaultValue();
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

		public int getMaxValueExclusive() {
			RangedIntCustomizationVariableObjectTemplate base = null;

			if (baseData != null)
				base = (RangedIntCustomizationVariableObjectTemplate) baseData;

			if (!maxValueExclusive.isLoaded()) {
				if (base == null) {
					return 0;
				} else {
					return base.getMaxValueExclusive();
				}
			}

			int value = this.maxValueExclusive.getValue();
			final byte delta = this.maxValueExclusive.getDeltaType();

			if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
				int baseValue = 0;

				if (baseData != null) {
					if (base != null)
						baseValue = base.getMaxValueExclusive();
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

				if ("variableName".equalsIgnoreCase(parameterName)) {
					variableName.loadFromIff(objectTemplateList, iff);
				} else if ("minValueInclusive".equalsIgnoreCase(parameterName)) {
					minValueInclusive.loadFromIff(objectTemplateList, iff);
				} else if ("defaultValue".equalsIgnoreCase(parameterName)) {
					defaultValue.loadFromIff(objectTemplateList, iff);
				} else if ("maxValueExclusive".equalsIgnoreCase(parameterName)) {
					maxValueExclusive.loadFromIff(objectTemplateList, iff);
				} else {
					throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
				}

				iff.exitChunk();
			}
			iff.exitForm();
		}

	}

	public static class PaletteColorCustomizationVariable {
		@Getter
		protected String variableName;
		@Getter
		protected String palettePathName;
		@Getter
		protected int defaultPaletteIndex;
	}

	protected static class PaletteColorCustomizationVariableObjectTemplate extends ObjectTemplate {
		public static final int TAG_PALETTECOLORCUSTOMIZATIONVARIABLE = Tag.convertStringToTag("PCCV");

		public PaletteColorCustomizationVariableObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
			super(filename, objectTemplateList);
		}

		private final StringParam variableName = new StringParam();
		private final StringParam palettePathName = new StringParam();
		private final IntegerParam defaultPaletteIndex = new IntegerParam(); 

		@Override
		public int getId() {
			return TAG_PALETTECOLORCUSTOMIZATIONVARIABLE;
		}

		public String getVariableName() {
			PaletteColorCustomizationVariableObjectTemplate base = null;

			if (baseData != null)
				base = (PaletteColorCustomizationVariableObjectTemplate) baseData;

			if (!variableName.isLoaded()) {
				if (base == null) {
					return "";
				} else {
					return base.getVariableName();
				}
			}

			String value = this.variableName.getValue();
			return value;
		}

		public String getPalettePathName() {
			PaletteColorCustomizationVariableObjectTemplate base = null;

			if (baseData != null)
				base = (PaletteColorCustomizationVariableObjectTemplate) baseData;

			if (!palettePathName.isLoaded()) {
				if (base == null) {
					return null;
				} else {
					return base.getPalettePathName();
				}
			}

			String value = this.palettePathName.getValue();
			return value;
		}

		public int getDefaultPaletteIndex() {
			PaletteColorCustomizationVariableObjectTemplate base = null;

			if (baseData != null)
				base = (PaletteColorCustomizationVariableObjectTemplate) baseData;

			if (!defaultPaletteIndex.isLoaded()) {
				if (base == null) {
					return 0;
				} else {
					return base.getDefaultPaletteIndex();
				}
			}

			int value = this.defaultPaletteIndex.getValue();
			final byte delta = this.defaultPaletteIndex.getDeltaType();

			if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
				int baseValue = 0;

				if (baseData != null) {
					if (base != null)
						baseValue = base.getDefaultPaletteIndex();
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

				if ("variableName".equalsIgnoreCase(parameterName)) {
					variableName.loadFromIff(objectTemplateList, iff);
				} else if ("palettePathName".equalsIgnoreCase(parameterName)) {
					palettePathName.loadFromIff(objectTemplateList, iff);
				} else if ("defaultPaletteIndex".equalsIgnoreCase(parameterName)) {
					defaultPaletteIndex.loadFromIff(objectTemplateList, iff);
				} else {
					throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
				}

				iff.exitChunk();
			}
			iff.exitForm();
		}

	}

	public static class ConstStringCustomizationVariable {
		@Getter
		protected String variableName;
		@Getter
		protected String constValue;
	}

	protected static class ConstStringCustomizationVariableObjectTemplate extends ObjectTemplate {
		public static final int TAG_CONSTSTRINGCUSTOMIZATIONVARIABLE = Tag.convertStringToTag("CSCV");

		public ConstStringCustomizationVariableObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
			super(filename, objectTemplateList);
		}

		private final StringParam variableName = new StringParam();
		private final StringParam constValue = new StringParam(); 

		@Override
		public int getId() {
			return TAG_CONSTSTRINGCUSTOMIZATIONVARIABLE;
		}

		public String getVariableName() {
			ConstStringCustomizationVariableObjectTemplate base = null;

			if (baseData != null)
				base = (ConstStringCustomizationVariableObjectTemplate) baseData;

			if (!variableName.isLoaded()) {
				if (base == null) {
					return "";
				} else {
					return base.getVariableName();
				}
			}

			String value = this.variableName.getValue();
			return value;
		}

		public String getConstValue() {
			ConstStringCustomizationVariableObjectTemplate base = null;

			if (baseData != null)
				base = (ConstStringCustomizationVariableObjectTemplate) baseData;

			if (!constValue.isLoaded()) {
				if (base == null) {
					return "";
				} else {
					return base.getConstValue();
				}
			}

			String value = this.constValue.getValue();
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

				if ("variableName".equalsIgnoreCase(parameterName)) {
					variableName.loadFromIff(objectTemplateList, iff);
				} else if ("constValue".equalsIgnoreCase(parameterName)) {
					constValue.loadFromIff(objectTemplateList, iff);
				} else {
					throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
				}

				iff.exitChunk();
			}
			iff.exitForm();
		}

	}

	public static class CustomizationVariableMapping {
		@Getter
		protected String sourceVariable;
		@Getter
		protected String dependentVariable;
	}

	protected static class CustomizationVariableMappingObjectTemplate extends ObjectTemplate {
		public static final int TAG_CUSTOMIZATIONVARIABLEMAPPING = Tag.convertStringToTag("CVMM");

		public CustomizationVariableMappingObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
			super(filename, objectTemplateList);
		}

		private final StringParam sourceVariable = new StringParam();
		private final StringParam dependentVariable = new StringParam(); 

		@Override
		public int getId() {
			return TAG_CUSTOMIZATIONVARIABLEMAPPING;
		}

		public String getSourceVariable() {
			CustomizationVariableMappingObjectTemplate base = null;

			if (baseData != null)
				base = (CustomizationVariableMappingObjectTemplate) baseData;

			if (!sourceVariable.isLoaded()) {
				if (base == null) {
					return "";
				} else {
					return base.getSourceVariable();
				}
			}

			String value = this.sourceVariable.getValue();
			return value;
		}

		public String getDependentVariable() {
			CustomizationVariableMappingObjectTemplate base = null;

			if (baseData != null)
				base = (CustomizationVariableMappingObjectTemplate) baseData;

			if (!dependentVariable.isLoaded()) {
				if (base == null) {
					return "";
				} else {
					return base.getDependentVariable();
				}
			}

			String value = this.dependentVariable.getValue();
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

				if ("sourceVariable".equalsIgnoreCase(parameterName)) {
					sourceVariable.loadFromIff(objectTemplateList, iff);
				} else if ("dependentVariable".equalsIgnoreCase(parameterName)) {
					dependentVariable.loadFromIff(objectTemplateList, iff);
				} else {
					throw new IllegalStateException(String.format("Unexpected parameter %s", parameterName));
				}

				iff.exitChunk();
			}
			iff.exitForm();
		}

	}

}

