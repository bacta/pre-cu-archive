package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;

import java.util.StringTokenizer;

@Command(id = 0x4982E17B)
public class RequestWaypointAtPositionCommandController implements CommandController {

    @Override
    public void handleCommand(SoeUdpConnection connection, TangibleObject invoker,
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
