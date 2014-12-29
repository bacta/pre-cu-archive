package com.ocdsoft.bacta.swg.precu.object;

/**
 * Created by crush on 8/13/2014.
 */
public class CraftingStage {
    public static final CraftingStage None = new CraftingStage(0);
    public static final CraftingStage SelectDraftSchematic = new CraftingStage(1);
    public static final CraftingStage Assembly = new CraftingStage(2);
    public static final CraftingStage Experiment = new CraftingStage(3);
    public static final CraftingStage Customize = new CraftingStage(4);
    public static final CraftingStage Finish = new CraftingStage(5);

    private int value;

    private CraftingStage(int value) {
        this.value = value;
    }
}