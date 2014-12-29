package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.annotations.Command;

import java.util.StringTokenizer;

@Command(id = 0x4982E17B)
public class RequestWaypointAtPositionCommandController implements CommandController {

    @Override
    public void handleCommand(GameClient client, TangibleObject invoker,
                              TangibleObject target, String params) {

        try {
            StringTokenizer tokenizer = new StringTokenizer(params);

            String planetName = tokenizer.nextToken();
            float x = Float.valueOf(tokenizer.nextToken());
            float z = Float.valueOf(tokenizer.nextToken());
            float y = Float.valueOf(tokenizer.nextToken());

            String label = "";
            while (tokenizer.hasMoreTokens())
                label += " " + label;

            if (label.length() > 0)
                label = label.substring(1);

            //Create waypoint.
            //Send it in PLAY8 delta message.

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
