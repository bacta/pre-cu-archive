package com.ocdsoft.bacta.swg.precu.message.game.messagequeue;

import com.ocdsoft.bacta.swg.network.soe.lang.UnicodeString;

import java.util.List;

/**
 * Created by crush on 8/13/2014.
 */
public class MessageQueueCraftIngredients {
    public class UnknownClass1 {
        int type;
        int minValue;
    }

    public class UnknownClass2 {
        int quantity;
        int maxValue;
    }

    public class Ingredient {
        UnicodeString name;
        UnknownClass1 u1;
        UnknownClass2 u2;
    }

    List<Ingredient> ingredients;
}
