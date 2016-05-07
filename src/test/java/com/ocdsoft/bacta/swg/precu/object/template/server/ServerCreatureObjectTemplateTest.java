package com.ocdsoft.bacta.swg.precu.object.template.server;

import com.ocdsoft.bacta.swg.precu.service.data.ObjectTemplateService;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;
import com.ocdsoft.bacta.tre.TreeFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by crush on 5/6/2016.
 */
public class ServerCreatureObjectTemplateTest {
    private static final String resourcesPath = new File(ServerCreatureObjectTemplateTest.class.getResource("/").getFile()).getAbsolutePath();

    private final TreeFile treeFile = new TreeFile();
    private final ObjectTemplateList objectTemplateList = new ObjectTemplateList(treeFile);
    private ObjectTemplateService objectTemplateService;

    @Before
    public void setup() {
        treeFile.addSearchPath(resourcesPath, 1);

        objectTemplateService = new ObjectTemplateService(objectTemplateList);
    }

    @Test
    public void shouldFindTemplate() {
        final byte[] bytes = treeFile.open("object/creature/player/human_male.iff");
        Assert.assertNotNull(bytes);
    }

    @Test
    public void shouldLoadServerCreatureObjectTemplate() {
        final ServerCreatureObjectTemplate template = objectTemplateList.fetch("object/creature/player/human_male.iff");
        Assert.assertNotNull(template);
        Assert.assertEquals("object/creature/player/shared_human_male.iff", template.getSharedTemplate());
    }

}
