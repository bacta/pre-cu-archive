package com.ocdsoft.bacta.swg.precu.message.ui;

import com.ocdsoft.bacta.swg.precu.object.sui.SuiCommand;

import java.util.List;

/**
 * Created by crush on 8/13/2014.
 */
public class SuiCreatePage extends SUIMessage {
    String pageName;
    List<SuiCommand> commands;
    //NetworkId associatedObjectId;
    float maxRangeFromObject;
}
