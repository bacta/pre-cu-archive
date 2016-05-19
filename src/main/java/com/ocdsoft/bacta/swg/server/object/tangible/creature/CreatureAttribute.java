package com.ocdsoft.bacta.swg.server.object.tangible.creature;

public class CreatureAttribute {
	public static final int HEALTH = 0x00;
	public static final int STRENGTH = 0x01;
	public static final int CONSTITUTION = 0x02;
	public static final int ACTION = 0x03;
	public static final int QUICKNESS = 0x04;
	public static final int STAMINA = 0x05;
	public static final int MIND = 0x06;
	public static final int FOCUS = 0x07;
	public static final int WILLPOWER = 0x08;
	public static final int SIZE = 0x09;
	
	public static final boolean isValidAttribute(final int attribute) {
		return attribute >= HEALTH && attribute <= WILLPOWER;
	}
}
