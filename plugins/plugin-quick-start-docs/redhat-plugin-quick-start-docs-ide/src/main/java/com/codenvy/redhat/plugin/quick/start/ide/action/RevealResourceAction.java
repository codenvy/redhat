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
package com.codenvy.redhat.plugin.quick.start.ide.action;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Collections.singletonList;
import static org.eclipse.che.ide.resource.Path.valueOf;
import static org.eclipse.che.ide.workspace.perspectives.project.ProjectPerspective.PROJECT_PERSPECTIVE_ID;

import com.google.common.annotations.Beta;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.eclipse.che.ide.api.action.AbstractPerspectiveAction;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.resources.Resource;
import org.eclipse.che.ide.resource.Path;
import org.eclipse.che.ide.resources.reveal.RevealResourceEvent;

/**
 * Scrolls from resource in the context to the stored location in the Project View.
 *
 * @author Vlad Zhukovskiy
 * @since 4.4.0
 */
@Beta
@Singleton
public class RevealResourceAction extends AbstractPerspectiveAction {

  private static final String PATH = "path";

  private final AppContext appContext;
  private final EventBus eventBus;

  @Inject
  public RevealResourceAction(AppContext appContext, EventBus eventBus) {
    super(singletonList(PROJECT_PERSPECTIVE_ID), "Reveal Resource", null, null, null);
    this.appContext = appContext;
    this.eventBus = eventBus;
  }

  /** {@inheritDoc} */
  @Override
  public void updateInPerspective(@NotNull ActionEvent event) {
    final Resource[] resources = appContext.getResources();

    event.getPresentation().setVisible(true);
    event.getPresentation().setEnabled(resources != null && resources.length == 1);
  }

  /** {@inheritDoc} */
  @Override
  public void actionPerformed(ActionEvent e) {
    Map<String, String> params = e.getParameters();
    String pathToReveal = params.get(PATH);

    if (pathToReveal != null) {
      Path path = valueOf(pathToReveal);
      if (!path.isAbsolute()) {
        path = valueOf(params.get("projectPath")).append(path);
      }

      checkState(!path.isEmpty());

      eventBus.fireEvent(new RevealResourceEvent(path));
    } else {
      final Resource[] resources = appContext.getResources();

      checkState(resources != null && resources.length == 1);

      eventBus.fireEvent(new RevealResourceEvent(resources[0]));
    }
  }
}
