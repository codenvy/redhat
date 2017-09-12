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
import com.codenvy.redhat.plugin.quick.start.shared.dto.GuideDto;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.api.data.HasDataObject;
import org.eclipse.che.ide.api.event.SelectionChangedEvent;
import org.eclipse.che.ide.api.machine.events.WsAgentStateEvent;
import org.eclipse.che.ide.api.machine.events.WsAgentStateHandler;
import org.eclipse.che.ide.api.parts.PartStack;
import org.eclipse.che.ide.api.parts.WorkspaceAgent;
import org.eclipse.che.ide.api.parts.base.BasePresenter;
import org.eclipse.che.ide.api.resources.Project;
import org.eclipse.che.ide.api.resources.Resource;
import org.eclipse.che.ide.api.selection.Selection;
import org.eclipse.che.ide.util.loging.Log;

/**
 * Presenter to manage {@link DocsViewPart}
 *
 * @author Oleksander Andriienko
 */
@Singleton
public class DocsPartPresenter extends BasePresenter implements DocsViewPart.ActionDelegate {

  private final DocsViewPart view;
  private final QuickStartLocalizationConstant constants;
  private final WorkspaceAgent workspaceAgent;
  private final QuickStartServiceClient client;

  private Project lastSelected;

  @Inject
  public DocsPartPresenter(
      final DocsViewPart view,
      QuickStartLocalizationConstant constants,
      EventBus eventBus,
      final WorkspaceAgent workspaceAgent,
      QuickStartServiceClient client) {
    this.view = view;
    this.constants = constants;
    this.workspaceAgent = workspaceAgent;
    this.client = client;

    view.setDelegate(this);

    eventBus.addHandler(SelectionChangedEvent.TYPE, this::processCurrentSelection);
    eventBus.addHandler(WsAgentStateEvent.TYPE,
                        new WsAgentStateHandler() {
                      @Override
                      public void onWsAgentStarted(WsAgentStateEvent wsAgentStateEvent) {
                        addPart();
                      }

                      @Override
                      public void onWsAgentStopped(WsAgentStateEvent wsAgentStateEvent) {}
                    });
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

  private void processCurrentSelection(SelectionChangedEvent event) {
    final Selection<?> selection = event.getSelection();
    if (selection instanceof Selection.NoSelectionProvided) {
      return;
    }

    Resource currentResource = null;

    if (selection == null
        || selection.getHeadElement() == null
        || selection.getAllElements().size() > 1) {
      // todo we still display previous selected project. Do we need improve this logic?
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
      Log.info(getClass(), currentProject.getPath());
      final String projectPath = currentProject.getPath();
      client
          .getGuide(projectPath)
          .then(
              guide -> {
                view.displayGuide(guide); //todo simplify by quadro point
                Log.info(getClass(), guide);
              })
          .catchError(
              err -> {
                Log.info(getClass(), "Failed to display guide by path " + projectPath);
                //todo show stab
              });
    }

    //    EditorPartStack activePartStack = editorMultiPartStack.getActivePartStack();
    //    if (currentResource == null || activePartStack == null || activeEditor == null) {
    //      return;
    //    }
    //
    //    final Path locationOfActiveOpenedFile = activeEditor.getEditorInput().getFile().getLocation();
    //    final Path selectedResourceLocation = currentResource.getLocation();
    //    if (!(activePart instanceof ProjectExplorerPresenter)
    //            && selectedResourceLocation.equals(locationOfActiveOpenedFile)) {
    //      return;
    //    }
    //
    //    PartPresenter partPresenter = activePartStack.getPartByPath(selectedResourceLocation);
    //    if (partPresenter != null) {
    //      workspaceAgent.setActivePart(partPresenter, EDITING);
    //    }
    //
    //
    //    selectionEvent.

    //    final Project rootProject = appContext.getRootProject();
    //
    //    if (rootProject == null) {
    //      hidePart();
    //      lastSelected = null;
    //      return;
    //    }
    //
    //    if (lastSelected != null && lastSelected.equals(rootProject)) {
    //      return;
    //    }
    //
    //    addPart();
    //    lastSelected = rootProject;
  }

  //don't update widget if panel is hidden, only update lastSelected...
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
