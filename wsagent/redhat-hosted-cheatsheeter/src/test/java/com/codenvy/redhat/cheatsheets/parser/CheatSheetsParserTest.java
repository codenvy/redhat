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
package com.codenvy.redhat.cheatsheets.parser;

import org.eclipse.che.api.core.ApiException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.lang.String.format;

/**
 * @author Alexander Andrienko
 */
public class CheatSheetsParserTest {

    private CheatSheetParser cheatSheetParser;

    @BeforeMethod
    public void setUpTest() {
        cheatSheetParser = new CheatSheetParser();
        cheatSheetParser.prepareParser();
    }

    @Test
    public void xmlSampleWithOnlyCheatSheetTagsShouldBeParsedSucessfully() throws IOException, ApiException {
        File file = getTestContent("TestInput1.xml");

        System.out.println(cheatSheetParser.parse(file));
    }

    private File getTestContent(String resourceName) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null) {
            throw new IOException(format("Resource '%s' was not found", resourceName));
        }
        return new File(url.getPath());
    }
}
