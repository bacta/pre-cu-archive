package com.ocdsoft.bacta.swg.precu.object.tangible.creature;

public class CreatureAttribute {
	public static final int Health = 0x00;
	public static final int Strength = 0x01;
	public static final int Constitution = 0x02;
	public static final int Action = 0x03;
	public static final int Quickness = 0x04;
	public static final int Stamina = 0x05;
	public static final int Mind = 0x06;
	public static final int Focus = 0x07;
	public static final int Willpower = 0x08;
	
	public static final boolean isValidAttribute(final int attribute) {
		return attribute >= Health && attribute <= Willpower;
	}
}
