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
import com.codenvy.redhat.plugin.quick.start.ide.QuickStartServiceClient;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.api.action.ActionManager;
import org.eclipse.che.ide.api.data.HasDataObject;
import org.eclipse.che.ide.api.event.SelectionChangedEvent;
import org.eclipse.che.ide.api.event.SelectionChangedHandler;
import org.eclipse.che.ide.api.machine.events.WsAgentStateEvent;
import org.eclipse.che.ide.api.machine.events.WsAgentStateHandler;
import org.eclipse.che.ide.api.parts.PartStack;
import org.eclipse.che.ide.api.parts.WorkspaceAgent;
import org.eclipse.che.ide.api.parts.base.BasePresenter;
import org.eclipse.che.ide.api.resources.Project;
import org.eclipse.che.ide.api.resources.Resource;
import org.eclipse.che.ide.api.selection.Selection;

/**
 * Presenter to manage {@link DocsViewPart}
 *
 * @author Oleksander Andriienko
 */
@Singleton
public class DocsPartPresenter extends BasePresenter
    implements DocsViewPart.ActionDelegate, SelectionChangedHandler {

  private final DocsViewPart view;
  private final QuickStartLocalizationConstant constants;
  private final WorkspaceAgent workspaceAgent;
  private final QuickStartServiceClient client;
  private final ActionManager actionManager;

  private Project lastSelected;

  @Inject
  public DocsPartPresenter(
      final DocsViewPart view,
      QuickStartLocalizationConstant constants,
      EventBus eventBus,
      final WorkspaceAgent workspaceAgent,
      QuickStartServiceClient client,
      ActionManager actionManager) {
    this.view = view;
    this.constants = constants;
    this.workspaceAgent = workspaceAgent;
    this.client = client;
    this.actionManager = actionManager;

    view.setDelegate(this);

    eventBus.addHandler(
        WsAgentStateEvent.TYPE,
        new WsAgentStateHandler() {
          @Override
          public void onWsAgentStarted(WsAgentStateEvent wsAgentStateEvent) {
            addPart();
            workspaceAgent.openPart(DocsPartPresenter.this, TOOLING);
          }

          @Override
          public void onWsAgentStopped(WsAgentStateEvent wsAgentStateEvent) {
            hidePart();
          }
        });

    eventBus.addHandler(SelectionChangedEvent.TYPE, this);
  }

  @Override
  public String getTitle() {
    return constants.guidePanelTitle();
  }

  @Override
  public IsWidget getView() {
    return view;
  }

  @Override
  public String getTitleToolTip() {
    return constants.guidePanelPopup();
  }

  @Override
  public void go(AcceptsOneWidget acceptsOneWidget) {
    acceptsOneWidget.setWidget(view);
  }

  @Override
  public void onSelectionChanged(SelectionChangedEvent selectionChangedEvent) {
    final Selection<?> selection = selectionChangedEvent.getSelection();
    if (selection instanceof Selection.NoSelectionProvided) {
      return;
    }

    Resource currentResource = null;

    if (selection == null
        || selection.getHeadElement() == null
        || selection.getAllElements().size() > 1) {
      return;
    }

    final Object headObject = selection.getHeadElement();

    if (headObject instanceof HasDataObject) {
      Object data = ((HasDataObject) headObject).getData();

      if (data instanceof Resource) {
        currentResource = (Resource) data;
      }
    } else if (headObject instanceof Resource) {
      currentResource = (Resource) headObject;
    }

    Project currentProject = currentResource != null ? currentResource.getProject() : null;

    if (currentProject != null && !currentProject.equals(lastSelected)) {
      lastSelected = currentProject;
      displayGuide(currentProject);
    }
  }

  public void onRefreshGuideButtonClick() {
    displayGuide(lastSelected);
  }

  private void displayGuide(Project project) {
    client
        .getGuide(project.getPath())
        .then(
            guideDto -> {
              view.displayGuide(project, guideDto);
            })
        .catchError(
            promiseError -> {
              view.showStub(constants.guidePanelNothingToShow());
            });
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

  @Override
  public void onActionLinkClick(String actionId, Map<String, String> parameters) {
    actionManager.performAction(actionId, parameters);
  }
}
