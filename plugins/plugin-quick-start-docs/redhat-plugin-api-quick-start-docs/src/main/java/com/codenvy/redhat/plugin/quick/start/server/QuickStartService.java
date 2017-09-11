/*
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package com.codenvy.redhat.plugin.quick.start.server;

import com.codenvy.redhat.plugin.quick.start.shared.GuideDto;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.eclipse.che.api.core.ApiException;
import org.eclipse.che.api.core.rest.Service;

/** @author Oleksander Andriienko */
@Path("/quick-start-docs")
public class QuickStartService extends Service {

  @Inject
  public QuickStartService() {}

  @GET
  @Produces(MediaType.TEXT_HTML)
  public String getDoc() throws ApiException {
    return "Hello Quick start";
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public GuideDto getQuickDocs(@QueryParam("projectPath") String projectPath) throws ApiException {
    return null;
  }
}
