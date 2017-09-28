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

import static org.eclipse.che.ide.api.parts.PartStackType.TOOLING;

import com.codenvy.redhat.plugin.quick.start.ide.panel.DocsPartPresenter;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.api.action.Action;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.parts.WorkspaceAgent;

/**
 * Action to open quick guide panel and refresh content. This action can be useful to open guide
 * panel in the factory json.
 *
 * @author Alexander Andrienko
 */
@Singleton
public class OpenGuidePanelAction extends Action {

  private final DocsPartPresenter docsPartPresenter;
  private final WorkspaceAgent workspaceAgent;

  @Inject
  public OpenGuidePanelAction(DocsPartPresenter docsPartPresenter, WorkspaceAgent workspaceAgent) {
    this.docsPartPresenter = docsPartPresenter;
    this.workspaceAgent = workspaceAgent;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    DocsPartPresenter docsPartPresenter = this.docsPartPresenter;

    workspaceAgent.openPart(docsPartPresenter, TOOLING);

    docsPartPresenter.onRefreshGuideButtonClick();
  }
}
