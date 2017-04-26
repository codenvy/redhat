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

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Alexander Andrienko
 */
@Path("/cheatsheeter")
public class CheatSheeterService extends Service {

    private final CheatSheeterParser cheatSheeterParser;

    @Inject
    public CheatSheeterService(CheatSheeterParser cheatSheeterParser) {
        this.cheatSheeterParser = cheatSheeterParser;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getDoc() throws ApiException {
        return cheatSheeterParser.parse();
    }
}