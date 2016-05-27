package com.ocdsoft.bacta.swg.server.game.object.sui;

public class SuiEventType {
    public static final byte none = 0x0;
    public static final byte onButton = 0x1;
    public static final byte onCheckbox = 0x2;
    public static final byte onEnabledChanged = 0x3;
    public static final byte onGenericSelection = 0x4;
    public static final byte onSliderbar = 0x5;
    public static final byte onTabbedPane = 0x6;
    public static final byte onTextbox = 0x7;
    public static final byte onVisibilityChanged = 0x8;
    public static final byte onClosedOk = 0x9;
    public static final byte onClosedCancel = 0xA;
    public static final byte numEventTypes = 0xB;
}
