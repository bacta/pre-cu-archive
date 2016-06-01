package com.ocdsoft.bacta.swg.server.game.object.template.server;

import com.ocdsoft.bacta.swg.server.game.service.data.ObjectTemplateService;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;
import com.ocdsoft.bacta.tre.TreeFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by crush on 5/6/2016.
 */
public class ServerCreatureObjectTemplateTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerCreatureObjectTemplateTest.class);

    private static final String resourcesPath = new File(ServerCreatureObjectTemplateTest.class.getResource("/").getFile()).getPath();

    private final TreeFile treeFile = new TreeFile();
    private ObjectTemplateList objectTemplateList;
    private ObjectTemplateService objectTemplateService;

    @Before
    public void setup() {
        treeFile.addSearchPath(resourcesPath, 1);

        objectTemplateList = new ObjectTemplateList(treeFile);
        objectTemplateService = new ObjectTemplateService(objectTemplateList);
        //register the templates with the object service. ^hidden complexity.
    }

    @Test
    public void shouldFindTemplate() {
        final byte[] bytes = treeFile.open("object/creature/player/human_male.iff");
        Assert.assertNotNull(bytes);
    }

    @Test
    public void shouldLoadServerCreatureObjectTemplate() {
        //TODO: Figure out why the maven task chokes on this test.
//        final ServerCreatureObjectTemplate template = objectTemplateList.fetch("object/creature/player/human_male.iff");
//        Assert.assertNotNull(template);
//        Assert.assertEquals("object/creature/player/shared_human_male.iff", template.getSharedTemplate());
    }

}
