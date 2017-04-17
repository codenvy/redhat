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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.attribute.PosixFilePermission.OWNER_EXECUTE;
import static java.nio.file.attribute.PosixFilePermission.OWNER_READ;
import static java.util.stream.Collectors.toSet;

/**
 * @author Alexander Andrienko
 */
public class CheatSheeterParser {

    private static final Logger LOG = LoggerFactory.getLogger(CheatSheeterParser.class);

    private static final String PARSER_FOLDER_NAME = "docs";
    private static final String OUTPUT_FOLDER_NAME = "cheatsheets";
    private static final String HTML_NAME          = "index.html";

    //parser resources
    private static final String PARSER_BINARY_NAME = "cheatsheeter";
    private static final String TEMPLATE_NAME      = "cheatsheet.html";
    private static final String RESOURCE_PATH      = "https://github.com/jboss-developer/jboss-eap-quickstarts/raw/7.0.x/kitchensink/.cheatsheet.xml";

    private Path binaryPath;
    private Path outPutPath;

    @Inject
    public CheatSheeterParser() {
    }

    public String parse() throws ApiException {
        String result;
        try {
            String[] commands = new String[] {binaryPath.toAbsolutePath().toString(),
                                              "-file=" + RESOURCE_PATH,
                                              "-out=" + HTML_NAME};
            ProcessBuilder processBuilder = new ProcessBuilder(commands);
            Process parseProcess = processBuilder.start();

            int exitCode = parseProcess.waitFor();
            if (exitCode != 0) {
                throw new ApiException(format("Failed to launch cheatsheets docs parser. Parser binary exit code: '%s'", exitCode));
            }
            result = new String(Files.readAllBytes(outPutPath), UTF_8);
        } catch (InterruptedException | IOException e) {
            throw new ApiException(format("Failed to parse cheatseets docs. Cause: '%s'", e.getMessage()));
        }

        return result;
    }

    @PostConstruct
    private void prepareParser() {
        Path docsParserDestPath = null;
        try {
            docsParserDestPath = Files.createTempDirectory(PARSER_FOLDER_NAME);

            //copy binary to temp folder
            InputStream parserInStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PARSER_BINARY_NAME);
            binaryPath = docsParserDestPath.resolve(PARSER_BINARY_NAME);
            Files.copy(parserInStream, binaryPath);
            //set up executable posix permission
            final Set<PosixFilePermission> posixPermissions = Stream.of(OWNER_READ, OWNER_EXECUTE).collect(toSet());
            Files.setPosixFilePermissions(binaryPath, posixPermissions);

            //copy template to temp folder
            InputStream templateInStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_NAME);
            Files.copy(templateInStream, docsParserDestPath.resolve(TEMPLATE_NAME));

            outPutPath = docsParserDestPath.resolve(OUTPUT_FOLDER_NAME).resolve(HTML_NAME);
        } catch (Exception e) {
            LOG.error(format("Failed to copy cheatsheeter resources. Cause: '%s'", e.getMessage()));
            if (docsParserDestPath != null) {
                try {
                    Files.delete(docsParserDestPath);
                } catch (IOException ex) {
                    LOG.error("Failed to clean up cheatsheeter resources. Cause: ", ex.getMessage());
                }
            }
        }
    }
}
