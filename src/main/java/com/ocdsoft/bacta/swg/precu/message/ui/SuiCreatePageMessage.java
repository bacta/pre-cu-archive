package com.ocdsoft.bacta.swg.precu.message.ui;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class SuiCreatePageMessage extends GameNetworkMessage {
    //SuiPageData pageData;


    public SuiCreatePageMessage() {
        super(0x02, 0xD44B7259);

        writeInt(0x01);
        writeAscii("Script.listBox");
        writeInt(2);


        writeByte(5); //Update type
        writeInt(0);
        writeInt(7);  //How many items are in the list.
        writeAscii(""); //Empty string
        writeAscii("T"); //Tab character
        writeAscii("onCrushIsAmazing");
        writeAscii("List.lstList");
        writeAscii("SelectedRow"); //We want the selected row of the list returned to us.
        writeAscii("bg.caption.lblTitle");
        writeAscii("Text"); //We want the text of the title returned to us.

        writeByte(5);
        writeInt(0);
        writeInt(7);
        writeAscii("");
        writeAscii("T");
        writeAscii("onCrushIsAmazing");
        writeAscii("List.lstList");
        writeAscii("SelectedRow");
        writeAscii("bg.caption.lblTitle");
        writeAscii("Text");


        writeLong(0);
        writeFloat(0);
        writeLong(0);
    }
}
