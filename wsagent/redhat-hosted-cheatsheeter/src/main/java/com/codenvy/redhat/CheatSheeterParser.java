/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.redhat;

import org.eclipse.che.api.core.ApiException;
import org.everrest.core.ApplicationContext;
import org.everrest.core.impl.EnvironmentContext;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Alexander Andrienko
 */
public class CheatSheeterParser {

    private static final String PARSER_BINARY_NAME = "cheatsheeter";

    private static final String HTML_NAME       = "index.html";
    private static final String RESOURCE_PATH   = "https://github.com/jboss-developer/jboss-eap-quickstarts/raw/7.0.x/kitchensink/.cheatsheet.xml";
    private static final String OUT_PATH        = "/home/user/docsparser/docs/" + HTML_NAME;

    @Inject
    public CheatSheeterParser() {
    }

    public String parse() throws ApiException {
        String result;
        try {
            String[] commands = new String[]{getParserLocation(),
                                             "-file=" + RESOURCE_PATH,
                                             "-out=" + HTML_NAME};
            ProcessBuilder processBuilder = new ProcessBuilder(commands);//use processUtil
            Process parseProces = processBuilder.start();

            int exitCode = parseProces.waitFor();
            if (exitCode != 0) {
                throw new ApiException("Failed to launch docs parser.");
            }
            result = new String(Files.readAllBytes(Paths.get(OUT_PATH)), UTF_8);
        } catch (InterruptedException | IOException e) {
            throw new ApiException("Failed to parse docs");
        }

        return result;
    }

    //todo move it to some another place
    private String getParserLocation() throws IOException {
        String resourceFolder = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
        Path execFile = Paths.get(resourceFolder,  PARSER_BINARY_NAME);
        if (Files.exists(execFile)) {
            throw new IOException(format("Binary file for '%s' was not found.", PARSER_BINARY_NAME));
        }
        return execFile.toAbsolutePath().toString();
    }
}
