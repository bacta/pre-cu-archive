package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class ClientCreateCharacter extends GameNetworkMessage {

    private String appearanceData;
    private String characterName;
    private String templateName;
    private String startingLocation;
    private String hairTemplateName;
    private String hairAppearanceData;
    private String profession;
    private boolean jedi;
    private float scaleFactor;
    private String biography;
    private boolean useNewbieTutorial;

    public ClientCreateCharacter() {
        super(0x74, 0x59b97f30);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        appearanceData = BufferUtil.getAscii(buffer);
        characterName = BufferUtil.getUnicode(buffer);
        templateName = BufferUtil.getAscii(buffer);
        startingLocation = BufferUtil.getAscii(buffer);  // City name
        hairTemplateName = BufferUtil.getAscii(buffer);
        hairAppearanceData = BufferUtil.getAscii(buffer);
        profession = BufferUtil.getAscii(buffer);
        jedi = BufferUtil.getBoolean(buffer);
        scaleFactor = buffer.getFloat();
        biography = BufferUtil.getUnicode(buffer);
        useNewbieTutorial = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, appearanceData);
        BufferUtil.putUnicode(buffer, characterName);
        BufferUtil.putAscii(buffer, templateName);
        BufferUtil.putAscii(buffer, startingLocation);
        BufferUtil.putAscii(buffer, hairTemplateName);
        BufferUtil.putAscii(buffer, hairAppearanceData);
        BufferUtil.putAscii(buffer, profession);
        BufferUtil.putBoolean(buffer, jedi);
        buffer.putFloat(scaleFactor);
        BufferUtil.putUnicode(buffer, biography);
        BufferUtil.putBoolean(buffer, useNewbieTutorial);
    }
    /**
         00 09 00 03 0C 00 74 30 7F B9 59 00 01 23 17 FF
    01 18 31 1C 45 1B FF 01 05 FF 01 1A 41 19 FF 01
    0D DA 09 FF 01 12 82 13 FF 01 20 FF 01 10 85 21
    EB 0F FF 01 14 24 11 20 0E FF 01 03 B9 0B FF 01
    0C 10 06 FF 01 08 0D 15 FF 01 16 64 04 E8 07 92
    0A FF 01 23 04 25 04 24 FF 01 01 1B 1D FF 01 1F
    13 1E 04 FF 03 0D 00 00 00 53 00 72 00 75 00 74
    00 72 00 75 00 6C 00 69 00 20 00 57 00 68 00 61
    00 70 00 25 00 6F 62 6A 65 63 74 2F 63 72 65 61
    74 75 72 65 2F 70 6C 61 79 65 72 2F 68 75 6D 61
    6E 5F 6D 61 6C 65 2E 69 66 66 07 00 62 65 73 74
    69 6E 65 32 00 6F 62 6A 65 63 74 2F 74 61 6E 67
    69 62 6C 65 2F 68 61 69 72 2F 68 75 6D 61 6E 2F
    68 61 69 72 5F 68 75 6D 61 6E 5F 6D 61 6C 65 5F
    73 30 37 2E 69 66 66 06 00 01 01 02 11 FF 03 10
    00 63 72 61 66 74 69 6E 67 5F 61 72 74 69 73 61
    6E 00 36 48 86 3F 00 00 00 00 00 

     */
}
