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
package com.codenvy.redhat.plugin.cheatsheeter.docs.client.docs;

import com.codenvy.redhat.plugin.cheatsheeter.docs.client.CheatSheeterLocalizationConstant;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;

import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.event.SelectionChangedEvent;
import org.eclipse.che.ide.api.event.SelectionChangedHandler;
import org.eclipse.che.ide.api.parts.PartStack;
import org.eclipse.che.ide.api.parts.WorkspaceAgent;
import org.eclipse.che.ide.api.parts.base.BasePresenter;
import org.eclipse.che.ide.api.resources.Project;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.eclipse.che.ide.api.parts.PartStackType.TOOLING;

/**
 * Presenter to manage {@link DocsViewPart}
 *
 * @author Alexander Andrienko
 */
@Singleton
public class DocsPartPresenter extends BasePresenter implements DocsViewPart.ActionDelegate {

    private static final String CHEAT_SHEETER_DOCS = "/cheatsheeter";

    private final DocsViewPart                     view;
    private final CheatSheeterLocalizationConstant constants;
    private final AppContext                       appContext;
    private final WorkspaceAgent                   workspaceAgent;

    private Project lastSelected;

    @Inject
    public DocsPartPresenter(final DocsViewPart view,
                             CheatSheeterLocalizationConstant constants,
                             AppContext appContext,
                             EventBus eventBus,
                             final WorkspaceAgent workspaceAgent) {
        this.view = view;
        this.constants = constants;
        this.appContext = appContext;
        this.workspaceAgent = workspaceAgent;

        view.setDelegate(this);

        eventBus.addHandler(SelectionChangedEvent.TYPE, new SelectionChangedHandler() {
            @Override
            public void onSelectionChanged(SelectionChangedEvent event) {
                processCurrentProject();
            }
        });
    }

    public void init() {
        view.setUrl(getDocsUrl());
    }

    @Override
    public String getTitle() {
        return constants.showCheatSheeterTitle();
    }

    @Override
    public IsWidget getView() {
        return view;
    }

    @Override
    public String getTitleToolTip() {
        return constants.showCheatSheeterDocsPopup();
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
