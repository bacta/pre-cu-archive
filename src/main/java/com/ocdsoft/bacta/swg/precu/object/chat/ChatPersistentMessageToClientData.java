package com.ocdsoft.bacta.swg.precu.object.chat;

import com.ocdsoft.bacta.swg.network.soe.lang.UnicodeString;

/**
 * Created by crush on 8/13/2014.
 */
public class ChatPersistentMessageToClientData {
    private String fromCharacterName;
    private String fromGameCode;
    private String fromServerCode;
    private int id;
    private boolean isHeader;
    private UnicodeString message;
    private UnicodeString subject;
    private UnicodeString outOfBand;
    private byte status;
    private int timeStamp;
}
