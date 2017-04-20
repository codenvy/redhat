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
import org.eclipse.che.api.core.rest.Service;
import org.eclipse.che.commons.lang.IoUtil;
import com.codenvy.redhat.cheatsheets.parser.CheatSheetParser;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.URL;

import static java.lang.String.format;

/**
 * @author Alexander Andrienko
 */
@Path("/cheatsheeter")
public class CheatSheeterService extends Service {

    private static final String DOCS_URL = "https://raw.githubusercontent.com/jboss-developer/jboss-eap-quickstarts/7.1.Alpha/kitchensink/.cheatsheet.xml";

    private final CheatSheetParser cheatSheetParser;

    @Inject
    public CheatSheeterService(CheatSheetParser cheatSheetParser) {
        this.cheatSheetParser = cheatSheetParser;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getDoc() throws ApiException {
        File file;
        try {
            URL url = new URL(DOCS_URL);
            file = IoUtil.downloadFile(null, "cheatsheet", null, url);
        } catch (Exception e) {
            throw new ApiException(format("Failed to upload docs. Cause: '%s'", e.getMessage()));
        }

        return cheatSheetParser.parse(file);
    }
}
