package com.ocdsoft.bacta.swg.codegen;

import com.ocdsoft.bacta.swg.shared.template.definition.TemplateDefinitionCompiler;

import java.io.File;
import java.io.IOException;

/**
 * Created by crush on 5/31/2016.
 */
public final class TemplateDefinitionCompilerApplication {
    public static void main(final String[] args) {
        try {
            //TODO: Make this more relative so it's not bound to my system configuration

            //Shared
            final File sharedSourceDirectory = new File("C:\\Users\\crush\\Downloads\\swg-src_orig_noshare\\whitengold\\dsrc\\sku.0\\sys.shared\\compiled\\game");
            final File sharedDestinationDirectory = new File("C:/users/crush/git/bacta/pre-cu-master/pre-cu/src/main/java/com/ocdsoft/bacta/swg/server/object/template/shared");
            final String sharedTemplatePackage = "com.ocdsoft.bacta.swg.server.game.object.template.shared";

            //Server
            final File serverSourceDirectory = new File("C:\\Users\\crush\\Downloads\\swg-src_orig_noshare\\whitengold\\dsrc\\sku.0\\sys.server\\compiled\\game");
            final File serverDestinationDirectory = new File("C:/users/crush/git/bacta/pre-cu-master/pre-cu/src/main/java/com/ocdsoft/bacta/swg/server/object/template/server");
            final String serverTemplatePackage = "com.ocdsoft.bacta.swg.server.game.object.template.server";

            final TemplateDefinitionCompiler compiler = new TemplateDefinitionCompiler();
            compiler.compile(sharedSourceDirectory, sharedDestinationDirectory, sharedTemplatePackage);
            compiler.compile(serverSourceDirectory, serverDestinationDirectory, serverTemplatePackage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
