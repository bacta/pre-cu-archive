package com.ocdsoft.bacta.swg.shared.template.definition;

import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.shared.datatable.DataTableTest;
import org.junit.Test;

import java.io.File;

/**
 * Created by crush on 4/18/2016.
 */
public class TemplateDefinitionFileTest {
    private static final String resourcesPath = new File(DataTableTest.class.getResource("/").getFile()).getAbsolutePath();

    @Test
    public void shouldParseFile() throws Exception {
        final String filename = "creature_object_template";
        final File templateFile = new File(resourcesPath, filename + ".tdf");
        final TemplateDefinitionFile objectTemplateTdf = new TemplateDefinitionFile();
        objectTemplateTdf.parse(templateFile);
    }


    @Test
    public void shouldWriteSingle() {
        final String s = "SetLfgInterests";
        System.out.println(String.format("0x%x : %s", SOECRC32.hashCode(s), s));
    }
}
