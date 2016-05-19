package com.ocdsoft.bacta.swg.server.object.template.server;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.shared.foundation.DataResourceList;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.definition.TemplateDefinition;
import com.ocdsoft.bacta.swg.shared.utility.FloatParam;
import com.ocdsoft.bacta.swg.shared.utility.IntegerParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generated by the TemplateDefinitionWriter.
 * MANUAL MODIFICATIONS MAY BE OVERWRITTEN.
 */
@TemplateDefinition
public class ServerWeaponObjectTemplate extends ServerTangibleObjectTemplate {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerWeaponObjectTemplate.class);
	public static final int TAG_SERVERWEAPONOBJECTTEMPLATE = Tag.convertStringToTag("WEAO");

	private static void registerTemplateConstructors(final DataResourceList<ObjectTemplate> objectTemplateList) {
		objectTemplateList.registerTemplate(ServerWeaponObjectTemplate.TAG_SERVERWEAPONOBJECTTEMPLATE, ServerWeaponObjectTemplate::new);
	}

	private int templateVersion;

	private final IntegerParam weaponType = new IntegerParam(); //General type of weapon
	private final IntegerParam attackType = new IntegerParam(); //Type of attack this weapon is used with.
	private final IntegerParam damageType = new IntegerParam(); //The kind of damage this weapon deals.
	private final IntegerParam elementalType = new IntegerParam(); //The kind of special elemental damage this weapon deals.
	private final IntegerParam elementalValue = new IntegerParam(); //The amount of special elemental damage this weapon deals.
	private final IntegerParam minDamageAmount = new IntegerParam(); //The amount of damage done by a weapon is between min-max evenly, randomly, distributed.
	private final IntegerParam maxDamageAmount = new IntegerParam(); //The amount of damage done by a weapon is between min-max evenly, randomly, distributed.
	private final FloatParam attackSpeed = new FloatParam(); //The weapon's intrisic firing rate.
	private final FloatParam audibleRange = new FloatParam(); //Distance message gets sent to NPCs when fired.
	private final FloatParam minRange = new FloatParam(); //range where the min range mod is set
	private final FloatParam maxRange = new FloatParam(); //range where the max range mod is set
	private final FloatParam damageRadius = new FloatParam(); //Blast radius for area effect weapons
	private final FloatParam woundChance = new FloatParam(); //Base % chance for a wound to occur on a successful attack
	private final IntegerParam attackCost = new IntegerParam(); //Amount of H/A/M drained by using the weapon
	private final IntegerParam accuracy = new IntegerParam(); //Accuracy bonus/penalty for this weapon

	public ServerWeaponObjectTemplate(final String filename, final DataResourceList<ObjectTemplate> objectTemplateList) {
		super(filename, objectTemplateList);
	}

	@Override
	public int getId() {
		return TAG_SERVERWEAPONOBJECTTEMPLATE;
	}

	public WeaponType getWeaponType() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!weaponType.isLoaded()) {
			if (base == null) {
				return WeaponType.from(0);
			} else {
				return base.getWeaponType();
			}
		}

		return WeaponType.from(weaponType.getValue());
	}

	public AttackType getAttackType() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!attackType.isLoaded()) {
			if (base == null) {
				return AttackType.from(0);
			} else {
				return base.getAttackType();
			}
		}

		return AttackType.from(attackType.getValue());
	}

	public DamageType getDamageType() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!damageType.isLoaded()) {
			if (base == null) {
				return DamageType.from(0);
			} else {
				return base.getDamageType();
			}
		}

		return DamageType.from(damageType.getValue());
	}

	public DamageType getElementalType() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!elementalType.isLoaded()) {
			if (base == null) {
				return DamageType.from(0);
			} else {
				return base.getElementalType();
			}
		}

		return DamageType.from(elementalType.getValue());
	}

	public int getElementalValue() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!elementalValue.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getElementalValue();
			}
		}

		int value = this.elementalValue.getValue();
		final byte delta = this.elementalValue.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getElementalValue();
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

	public int getElementalValueMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!elementalValue.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getElementalValueMin();
			}
		}

		int value = this.elementalValue.getMinValue();
		final byte delta = this.elementalValue.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getElementalValueMin();
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

	public int getElementalValueMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!elementalValue.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getElementalValueMax();
			}
		}

		int value = this.elementalValue.getMaxValue();
		final byte delta = this.elementalValue.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getElementalValueMax();
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

	public int getMinDamageAmount() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!minDamageAmount.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getMinDamageAmount();
			}
		}

		int value = this.minDamageAmount.getValue();
		final byte delta = this.minDamageAmount.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMinDamageAmount();
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

	public int getMinDamageAmountMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!minDamageAmount.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getMinDamageAmountMin();
			}
		}

		int value = this.minDamageAmount.getMinValue();
		final byte delta = this.minDamageAmount.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMinDamageAmountMin();
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

	public int getMinDamageAmountMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!minDamageAmount.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getMinDamageAmountMax();
			}
		}

		int value = this.minDamageAmount.getMaxValue();
		final byte delta = this.minDamageAmount.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMinDamageAmountMax();
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

	public int getMaxDamageAmount() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!maxDamageAmount.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getMaxDamageAmount();
			}
		}

		int value = this.maxDamageAmount.getValue();
		final byte delta = this.maxDamageAmount.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMaxDamageAmount();
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

	public int getMaxDamageAmountMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!maxDamageAmount.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getMaxDamageAmountMin();
			}
		}

		int value = this.maxDamageAmount.getMinValue();
		final byte delta = this.maxDamageAmount.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMaxDamageAmountMin();
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

	public int getMaxDamageAmountMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!maxDamageAmount.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getMaxDamageAmountMax();
			}
		}

		int value = this.maxDamageAmount.getMaxValue();
		final byte delta = this.maxDamageAmount.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMaxDamageAmountMax();
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

	public float getAttackSpeed() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!attackSpeed.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getAttackSpeed();
			}
		}

		float value = this.attackSpeed.getValue();
		final byte delta = this.attackSpeed.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAttackSpeed();
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

	public float getAttackSpeedMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!attackSpeed.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getAttackSpeedMin();
			}
		}

		float value = this.attackSpeed.getMinValue();
		final byte delta = this.attackSpeed.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAttackSpeedMin();
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

	public float getAttackSpeedMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!attackSpeed.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getAttackSpeedMax();
			}
		}

		float value = this.attackSpeed.getMaxValue();
		final byte delta = this.attackSpeed.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAttackSpeedMax();
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

	public float getAudibleRange() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!audibleRange.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getAudibleRange();
			}
		}

		float value = this.audibleRange.getValue();
		final byte delta = this.audibleRange.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAudibleRange();
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

	public float getAudibleRangeMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!audibleRange.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getAudibleRangeMin();
			}
		}

		float value = this.audibleRange.getMinValue();
		final byte delta = this.audibleRange.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAudibleRangeMin();
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

	public float getAudibleRangeMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!audibleRange.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getAudibleRangeMax();
			}
		}

		float value = this.audibleRange.getMaxValue();
		final byte delta = this.audibleRange.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAudibleRangeMax();
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

	public float getMinRange() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!minRange.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getMinRange();
			}
		}

		float value = this.minRange.getValue();
		final byte delta = this.minRange.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMinRange();
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

	public float getMinRangeMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!minRange.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getMinRangeMin();
			}
		}

		float value = this.minRange.getMinValue();
		final byte delta = this.minRange.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMinRangeMin();
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

	public float getMinRangeMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!minRange.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getMinRangeMax();
			}
		}

		float value = this.minRange.getMaxValue();
		final byte delta = this.minRange.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMinRangeMax();
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

	public float getMaxRange() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!maxRange.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getMaxRange();
			}
		}

		float value = this.maxRange.getValue();
		final byte delta = this.maxRange.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMaxRange();
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

	public float getMaxRangeMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!maxRange.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getMaxRangeMin();
			}
		}

		float value = this.maxRange.getMinValue();
		final byte delta = this.maxRange.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMaxRangeMin();
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

	public float getMaxRangeMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!maxRange.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getMaxRangeMax();
			}
		}

		float value = this.maxRange.getMaxValue();
		final byte delta = this.maxRange.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getMaxRangeMax();
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

	public float getDamageRadius() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!damageRadius.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getDamageRadius();
			}
		}

		float value = this.damageRadius.getValue();
		final byte delta = this.damageRadius.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getDamageRadius();
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

	public float getDamageRadiusMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!damageRadius.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getDamageRadiusMin();
			}
		}

		float value = this.damageRadius.getMinValue();
		final byte delta = this.damageRadius.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getDamageRadiusMin();
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

	public float getDamageRadiusMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!damageRadius.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getDamageRadiusMax();
			}
		}

		float value = this.damageRadius.getMaxValue();
		final byte delta = this.damageRadius.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getDamageRadiusMax();
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

	public float getWoundChance() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!woundChance.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getWoundChance();
			}
		}

		float value = this.woundChance.getValue();
		final byte delta = this.woundChance.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getWoundChance();
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

	public float getWoundChanceMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!woundChance.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getWoundChanceMin();
			}
		}

		float value = this.woundChance.getMinValue();
		final byte delta = this.woundChance.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getWoundChanceMin();
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

	public float getWoundChanceMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!woundChance.isLoaded()) {
			if (base == null) {
				return 0.0f;
			} else {
				return base.getWoundChanceMax();
			}
		}

		float value = this.woundChance.getMaxValue();
		final byte delta = this.woundChance.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			float baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getWoundChanceMax();
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

	public int getAttackCost() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!attackCost.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getAttackCost();
			}
		}

		int value = this.attackCost.getValue();
		final byte delta = this.attackCost.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAttackCost();
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

	public int getAttackCostMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!attackCost.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getAttackCostMin();
			}
		}

		int value = this.attackCost.getMinValue();
		final byte delta = this.attackCost.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAttackCostMin();
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

	public int getAttackCostMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!attackCost.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getAttackCostMax();
			}
		}

		int value = this.attackCost.getMaxValue();
		final byte delta = this.attackCost.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAttackCostMax();
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

	public int getAccuracy() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!accuracy.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getAccuracy();
			}
		}

		int value = this.accuracy.getValue();
		final byte delta = this.accuracy.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAccuracy();
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

	public int getAccuracyMin() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!accuracy.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getAccuracyMin();
			}
		}

		int value = this.accuracy.getMinValue();
		final byte delta = this.accuracy.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAccuracyMin();
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

	public int getAccuracyMax() {
		ServerWeaponObjectTemplate base = null;

		if (baseData != null && baseData instanceof ServerWeaponObjectTemplate)
			base = (ServerWeaponObjectTemplate) baseData;

		if (!accuracy.isLoaded()) {
			if (base == null) {
				return 0;
			} else {
				return base.getAccuracyMax();
			}
		}

		int value = this.accuracy.getMaxValue();
		final byte delta = this.accuracy.getDeltaType();

		if (delta == '+' || delta == '-' || delta == '_' || delta == '=') {
			int baseValue = 0;

			if (baseData != null) {
				if (base != null)
					baseValue = base.getAccuracyMax();
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
		if (iff.getCurrentName() != TAG_SERVERWEAPONOBJECTTEMPLATE) {
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

			if ("weaponType".equalsIgnoreCase(parameterName)) {
				weaponType.loadFromIff(objectTemplateList, iff);
			} else if ("attackType".equalsIgnoreCase(parameterName)) {
				attackType.loadFromIff(objectTemplateList, iff);
			} else if ("damageType".equalsIgnoreCase(parameterName)) {
				damageType.loadFromIff(objectTemplateList, iff);
			} else if ("elementalType".equalsIgnoreCase(parameterName)) {
				elementalType.loadFromIff(objectTemplateList, iff);
			} else if ("elementalValue".equalsIgnoreCase(parameterName)) {
				elementalValue.loadFromIff(objectTemplateList, iff);
			} else if ("minDamageAmount".equalsIgnoreCase(parameterName)) {
				minDamageAmount.loadFromIff(objectTemplateList, iff);
			} else if ("maxDamageAmount".equalsIgnoreCase(parameterName)) {
				maxDamageAmount.loadFromIff(objectTemplateList, iff);
			} else if ("attackSpeed".equalsIgnoreCase(parameterName)) {
				attackSpeed.loadFromIff(objectTemplateList, iff);
			} else if ("audibleRange".equalsIgnoreCase(parameterName)) {
				audibleRange.loadFromIff(objectTemplateList, iff);
			} else if ("minRange".equalsIgnoreCase(parameterName)) {
				minRange.loadFromIff(objectTemplateList, iff);
			} else if ("maxRange".equalsIgnoreCase(parameterName)) {
				maxRange.loadFromIff(objectTemplateList, iff);
			} else if ("damageRadius".equalsIgnoreCase(parameterName)) {
				damageRadius.loadFromIff(objectTemplateList, iff);
			} else if ("woundChance".equalsIgnoreCase(parameterName)) {
				woundChance.loadFromIff(objectTemplateList, iff);
			} else if ("attackCost".equalsIgnoreCase(parameterName)) {
				attackCost.loadFromIff(objectTemplateList, iff);
			} else if ("accuracy".equalsIgnoreCase(parameterName)) {
				accuracy.loadFromIff(objectTemplateList, iff);
			} else {
				LOGGER.error("Unexpected parameter {}", parameterName);
			}

			iff.exitChunk();
		}
		iff.exitForm();

		super.load(iff);
		iff.exitForm();
	}

	public enum WeaponType {
		WT_rifle(0),
		WT_lightRifle(1),
		WT_pistol(2),
		WT_heavyWeapon(3),
		WT_1handMelee(4),
		WT_2handMelee(5),
		WT_unarmed(6),
		WT_polearm(7), 
		WT_thrown(8), 
		WT_1handLightsaber(9), 
		WT_2handLightsaber(10), 
		WT_polearmLightsaber(11), 
		WT_groundTargetting(12), 
		WT_directionTargetting(13); 

		private static final WeaponType[] values = values();
		public final long value;

		WeaponType(final long value) {
			this.value = value;
		}
		public static WeaponType from(final long value) {
			for (final WeaponType e : values)
				if (e.value == value) return e;
			throw new IllegalArgumentException(String.format("UNKNOWN value %d for enum WeaponType", value));
		}
	}

	public enum AttribModDecaySpecial {
		AMDS_pool(-1), //use the attribute pool recovery rate (normal recovery)
		AMDS_wound(-2), //can only be healed by skill/item use, heal difficulty adjusted by shock wound value
		AMDS_antidote(-3); //used to clear all attrib mods for a given attribute

		private static final AttribModDecaySpecial[] values = values();
		public final long value;

		AttribModDecaySpecial(final long value) {
			this.value = value;
		}
		public static AttribModDecaySpecial from(final long value) {
			for (final AttribModDecaySpecial e : values)
				if (e.value == value) return e;
			throw new IllegalArgumentException(String.format("UNKNOWN value %d for enum AttribModDecaySpecial", value));
		}
	}

	public enum AttackType {
		AT_melee(0), 
		AT_ranged(1), 
		AT_thrown(2); 

		private static final AttackType[] values = values();
		public final long value;

		AttackType(final long value) {
			this.value = value;
		}
		public static AttackType from(final long value) {
			for (final AttackType e : values)
				if (e.value == value) return e;
			throw new IllegalArgumentException(String.format("UNKNOWN value %d for enum AttackType", value));
		}
	}

}

