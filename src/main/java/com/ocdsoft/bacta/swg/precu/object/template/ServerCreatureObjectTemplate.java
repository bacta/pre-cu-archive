package com.ocdsoft.bacta.swg.precu.object.template;

import com.ocdsoft.bacta.swg.shared.object.template.SharedCreatureObjectTemplate;
import com.ocdsoft.bacta.swg.shared.object.template.param.IntegerParam;

/**
 * Created by crush on 3/6/14.
 */
public class ServerCreatureObjectTemplate extends SharedCreatureObjectTemplate {
    public ServerCreatureObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SRCO; };

    private IntegerParam balanceBank = new IntegerParam();
    private IntegerParam balanceCash = new IntegerParam();


}
