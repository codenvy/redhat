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

import static com.codenvy.redhat.plugin.quick.start.shared.Constants.DOT_CHE;
import static com.codenvy.redhat.plugin.quick.start.shared.Constants.QUICK_START_DOCS;
import static com.codenvy.redhat.plugin.quick.start.shared.Constants.WALK_THOUGHT_JSON;

import com.codenvy.redhat.plugin.quick.start.shared.dto.GuideDto;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.eclipse.che.api.core.ApiException;
import org.eclipse.che.api.core.BadRequestException;
import org.eclipse.che.api.core.ServerException;
import org.eclipse.che.api.core.rest.Service;
import org.eclipse.che.dto.server.DtoFactory;

/** @author Oleksander Andriienko */
@Path(QUICK_START_DOCS)
public class QuickStartService extends Service {

  private final File workspaceStorage;
  private final DtoFactory dtoFactory;

  @Inject
  public QuickStartService(@Named("che.user.workspaces.storage") File root) {
    this.workspaceStorage = root;
    this.dtoFactory = DtoFactory.getInstance();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public GuideDto getQuickDocs(@QueryParam("projectPath") String projectPath) throws ApiException {
    if (projectPath == null) {
      throw new BadRequestException("Project path should be required.");
    }

    String guideContent = getGuideContent(projectPath);

    try {
      return dtoFactory.createDtoFromJson(guideContent, GuideDto.class);
    } catch (Exception e) {
      throw new ServerException("Failed to guide content. Cause: ", e);
    }
  }

  private String getGuideContent(String projectPath) throws ApiException {
    projectPath =
        projectPath.startsWith("/") ? projectPath.substring(1, projectPath.length()) : projectPath;

    java.nio.file.Path guideFilePath =
        workspaceStorage.toPath().resolve(projectPath).resolve(DOT_CHE).resolve(WALK_THOUGHT_JSON);

    if (!Files.exists(guideFilePath)) {
      return "{}";
    }

    StringBuilder result = new StringBuilder();
    try (BufferedReader reader = Files.newBufferedReader(guideFilePath)) {
      String line;
      while ((line = reader.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      throw new ServerException("Failed to read guide file", e);
    }
    return result.toString();
  }
}
