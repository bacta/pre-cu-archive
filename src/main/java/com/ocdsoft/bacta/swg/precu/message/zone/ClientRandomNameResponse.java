package com.ocdsoft.bacta.swg.precu.message.zone;



import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.shared.localization.StringId;

/**
 * Created by Kyle on 3/15/14.
 */
public class ClientRandomNameResponse extends SwgMessage {


    public ClientRandomNameResponse(String fullName, String race) {
        super(4, 0xE85FB868);

        writeAscii(race);
        writeUnicode(fullName);

        StringId stringId = new StringId("ui", "name_approved");
        stringId.writeToBuffer(this);
    }
}
