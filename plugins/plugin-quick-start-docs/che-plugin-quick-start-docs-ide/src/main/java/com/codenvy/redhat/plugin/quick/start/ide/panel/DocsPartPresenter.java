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
package com.codenvy.redhat.plugin.quick.start.ide.panel;

import static org.eclipse.che.ide.api.parts.PartStackType.TOOLING;

import com.codenvy.redhat.plugin.quick.start.ide.QuickStartLocalizationConstant;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.event.SelectionChangedEvent;
import org.eclipse.che.ide.api.parts.PartStack;
import org.eclipse.che.ide.api.parts.WorkspaceAgent;
import org.eclipse.che.ide.api.parts.base.BasePresenter;
import org.eclipse.che.ide.api.resources.Project;

/**
 * Presenter to manage {@link DocsViewPart}
 *
 * @author Oleksander Andriienko
 */
@Singleton
public class DocsPartPresenter extends BasePresenter implements DocsViewPart.ActionDelegate {

  private static final String CHEAT_SHEETER_DOCS = "/quickstart";

  private final DocsViewPart view;
  private final QuickStartLocalizationConstant constants;
  private final AppContext appContext;
  private final WorkspaceAgent workspaceAgent;

  private Project lastSelected;

  @Inject
  public DocsPartPresenter(
      final DocsViewPart view,
      QuickStartLocalizationConstant constants,
      AppContext appContext,
      EventBus eventBus,
      final WorkspaceAgent workspaceAgent) {
    this.view = view;
    this.constants = constants;
    this.appContext = appContext;
    this.workspaceAgent = workspaceAgent;

    view.setDelegate(this);

    eventBus.addHandler(SelectionChangedEvent.TYPE, event -> processCurrentProject());
  }

  public void init() {
    view.setUrl(getDocsUrl());
  }

  @Override
  public String getTitle() {
    return constants.showPanelTitle();
  }

  @Override
  public IsWidget getView() {
    return view;
  }

  @Override
  public String getTitleToolTip() {
    return constants.showPanelDocsPopup();
  }

  @Override
  public void go(AcceptsOneWidget acceptsOneWidget) {
    acceptsOneWidget.setWidget(view);
  }

  public String getDocsUrl() {
    return appContext.getDevMachine().getWsAgentBaseUrl() + CHEAT_SHEETER_DOCS;
  }

  private void processCurrentProject() {
    final Project rootProject = appContext.getRootProject();

    if (rootProject == null) {
      hidePart();
      lastSelected = null;
      return;
    }

    if (lastSelected != null && lastSelected.equals(rootProject)) {
      return;
    }

    addPart();
    lastSelected = rootProject;
  }

  private void hidePart() {
    PartStack partStack = workspaceAgent.getPartStack(TOOLING);
    if (partStack != null && partStack.containsPart(this)) {
      workspaceAgent.hidePart(this);
      workspaceAgent.removePart(this);
    }
  }

  private void addPart() {
    PartStack partStack = workspaceAgent.getPartStack(TOOLING);
    if (partStack != null && !partStack.containsPart(this)) {
      partStack.addPart(this);
    }
  }
}
