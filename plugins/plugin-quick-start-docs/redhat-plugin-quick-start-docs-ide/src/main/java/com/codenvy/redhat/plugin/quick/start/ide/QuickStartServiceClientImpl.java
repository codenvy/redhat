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
package com.codenvy.redhat.plugin.quick.start.ide;

import static com.codenvy.redhat.plugin.quick.start.shared.Constants.QUICK_START_DOCS;
import static org.eclipse.che.ide.rest.HTTPHeader.ACCEPT;

import com.codenvy.redhat.plugin.quick.start.shared.dto.GuideDto;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.ide.MimeType;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.rest.AsyncRequestFactory;
import org.eclipse.che.ide.rest.DtoUnmarshallerFactory;

/** @author Alexander Andrienko */
@Singleton
public class QuickStartServiceClientImpl implements QuickStartServiceClient {

  private final AppContext appContext;
  private final AsyncRequestFactory requestFactory;
  private final DtoUnmarshallerFactory unmarshaller;

  @Inject
  public QuickStartServiceClientImpl(
      AppContext appContext,
      AsyncRequestFactory requestFactory,
      DtoUnmarshallerFactory unmarshaller) {
    this.appContext = appContext;
    this.requestFactory = requestFactory;
    this.unmarshaller = unmarshaller;
  }

  @Override
  public Promise<GuideDto> getGuide(String projectPath) {
    return requestFactory
        .createGetRequest(getBaseUrl() + "?projectPath=" + projectPath)
        .header(ACCEPT, MimeType.APPLICATION_JSON)
        .send(unmarshaller.newUnmarshaller(GuideDto.class));
  }

  /**
   * Returns the base url for the quick start quide service. It consists of workspace agent base url
   * plus unique service path prefix.
   *
   * @return base url for guide service
   */
  private String getBaseUrl() {
    return appContext.getDevMachine().getWsAgentBaseUrl() + QUICK_START_DOCS;
  }
}
