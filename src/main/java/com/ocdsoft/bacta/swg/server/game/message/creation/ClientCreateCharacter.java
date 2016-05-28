package com.ocdsoft.bacta.swg.server.game.message.creation;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
     struct __cppobj ClientCreateCharacter : GameNetworkMessage
     {
     Archive::AutoVariable<std::string > m_appearanceData;
     Archive::AutoVariable<UnicodeString > m_characterName;
     Archive::AutoVariable<std::string > m_templateName;
     Archive::AutoVariable<std::string > m_startingLocation;
     Archive::AutoVariable<std::string > m_hairTemplateName;
     Archive::AutoVariable<std::string > m_hairAppearanceData;
     Archive::AutoVariable<std::string > m_profession;
     Archive::AutoVariable<bool> m_jedi;
     Archive::AutoVariable<float> m_scaleFactor;
     Archive::AutoVariable<UnicodeString > m_biography;
     Archive::AutoVariable<bool> m_useNewbieTutorial;
     Archive::AutoVariable<std::string > m_skillTemplate;  //NGE Only
     Archive::AutoVariable<std::string > m_workingSkill;  // NGE Only
     };

      5A 00 01 23 17 FF 01 18 CD 1C FF 01 1B B6 05 63
    1A FF 01 19 7C 0D C7 09 FF 01 12 FF 01 13 3A 20
    FF 01 10 27 21 4B 0F FF 01 14 0C 11 FF 01 0E 3C
    03 FF 01 0B 3B 0C FF 01 06 A7 08 FF 01 15 A9 16
    FF 01 04 60 07 28 0A FF 01 23 0C 25 FF 01 24 FF
    01 01 04 1D FF 01 1F 05 1E 06 FF 03 0F 00 00 00
    4E 00 69 00 62 00 72 00 20 00 4D 00 65 00 70 00
    69 00 6C 00 6F 00 70 00 72 00 69 00 68 00 25 00
    6F 62 6A 65 63 74 2F 63 72 65 61 74 75 72 65 2F
    70 6C 61 79 65 72 2F 68 75 6D 61 6E 5F 6D 61 6C
    65 2E 69 66 66 07 00 62 65 73 74 69 6E 65 32 00
    6F 62 6A 65 63 74 2F 74 61 6E 67 69 62 6C 65 2F
    68 61 69 72 2F 68 75 6D 61 6E 2F 68 61 69 72 5F
    68 75 6D 61 6E 5F 6D 61 6C 65 5F 73 32 33 2E 69
    66 66 06 00 01 01 02 05 FF 03 10 00 63 72 61 66
    74 69 6E 67 5F 61 72 74 69 73 61 6E 00 6B 5F 88
    3F 00 00 00 00 00 

  */
@Getter
@AllArgsConstructor
@Priority(0xc)
public final class ClientCreateCharacter extends GameNetworkMessage {

    private final String appearanceData;
    private final String characterName; // UnicodeString
    private final String templateName;
    private final String startingLocation;
    private final String hairTemplateName;
    private final String hairAppearanceData;
    private final String profession;
    private final boolean jedi;
    private final float scaleFactor;
    private final String biography;
    private final boolean useNewbieTutorial;
    private final String skillTemplate;  //NGE Only
    private final String workingSkill;  // NGE Only

    public ClientCreateCharacter(final ByteBuffer buffer) {
        this.appearanceData = BufferUtil.getAscii(buffer);
        this.characterName = BufferUtil.getUnicode(buffer);
        this.templateName = BufferUtil.getAscii(buffer);
        this.startingLocation = BufferUtil.getAscii(buffer);
        this.hairTemplateName = BufferUtil.getAscii(buffer);
        this.hairAppearanceData = BufferUtil.getAscii(buffer);
        this.profession = BufferUtil.getAscii(buffer);
        this.jedi = BufferUtil.getBoolean(buffer);
        this.scaleFactor = buffer.getFloat();
        this.biography = BufferUtil.getUnicode(buffer);
        this.useNewbieTutorial = BufferUtil.getBoolean(buffer);
        this.skillTemplate = BufferUtil.getAscii(buffer);
        this.workingSkill = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, appearanceData);
        BufferUtil.putUnicode(buffer, characterName);
        BufferUtil.putAscii(buffer, templateName);
        BufferUtil.putAscii(buffer, startingLocation);
        BufferUtil.putAscii(buffer, hairTemplateName);
        BufferUtil.putAscii(buffer, hairAppearanceData);
        BufferUtil.putAscii(buffer, profession);
        BufferUtil.put(buffer, jedi);
        BufferUtil.put(buffer, scaleFactor);
        BufferUtil.putUnicode(buffer, biography);
        BufferUtil.put(buffer, useNewbieTutorial);
        BufferUtil.putAscii(buffer, skillTemplate);
        BufferUtil.putAscii(buffer, workingSkill);
    }
}
