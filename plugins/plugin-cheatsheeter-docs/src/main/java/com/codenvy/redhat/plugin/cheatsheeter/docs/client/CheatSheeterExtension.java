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
package com.codenvy.redhat.plugin.cheatsheeter.docs.client;

import com.codenvy.redhat.plugin.cheatsheeter.docs.client.docs.DocsPartPresenter;
import com.google.web.bindery.event.shared.EventBus;

import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.event.project.CreateProjectEvent;
import org.eclipse.che.ide.api.event.project.CreateProjectHandler;
import org.eclipse.che.ide.api.event.project.DeleteProjectEvent;
import org.eclipse.che.ide.api.event.project.DeleteProjectHandler;
import org.eclipse.che.ide.api.event.project.ProjectUpdatedEvent;
import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.ide.api.machine.events.WsAgentStateEvent;
import org.eclipse.che.ide.api.machine.events.WsAgentStateHandler;
import org.eclipse.che.ide.api.parts.PartStackType;
import org.eclipse.che.ide.api.parts.WorkspaceAgent;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Alexander Andrienko
 */
@Singleton
@Extension(title = "CheatCheeter docs extension.",
           description = "Extension to display static html content parsed from '.cheatsheet.xml'",
           version = "1.0.0")
public class CheatSheeterExtension {

    @Inject
    public CheatSheeterExtension(EventBus eventBus,
                                 DocsPartPresenter docsPartPresenter,
                                 WorkspaceAgent workspaceAgent,
                                 AppContext appContext) {
        eventBus.addHandler(WsAgentStateEvent.TYPE, new WsAgentStateHandler() {
            @Override
            public void onWsAgentStarted(WsAgentStateEvent wsAgentStateEvent) {
                if ((docsPartPresenter.getPartStack() == null || !docsPartPresenter.getPartStack().containsPart(docsPartPresenter))
                    && appContext.getProjects().length > 0) {
                    docsPartPresenter.init();
                    workspaceAgent.openPart(docsPartPresenter, PartStackType.TOOLING);
                }
            }

            @Override
            public void onWsAgentStopped(WsAgentStateEvent wsAgentStateEvent) {
                workspaceAgent.removePart(docsPartPresenter);
            }
        });
        eventBus.addHandler(DeleteProjectEvent.TYPE, new DeleteProjectHandler() {
            @Override
            public void onProjectDeleted(DeleteProjectEvent deleteProjectEvent) {
                if (appContext.getProjects() == null) {
                    workspaceAgent.removePart(docsPartPresenter);
                }
            }
        });
        //todo remove project action and add project action. Find project by path
        eventBus.addHandler(CreateProjectEvent.TYPE, new CreateProjectHandler() {
            @Override
            public void onProjectCreated(CreateProjectEvent createProjectEvent) {
                if (appContext.getProjectsRoot() != null) {
                    workspaceAgent.openPart(docsPartPresenter, PartStackType.TOOLING);
                }
            }
        });

        eventBus.addHandler(ProjectUpdatedEvent.getType(), new ProjectUpdatedEvent.ProjectUpdatedHandler() {
            @Override
            public void onProjectUpdated(ProjectUpdatedEvent projectUpdatedEvent) {
                if (appContext.getProjectsRoot() != null) {
                    workspaceAgent.openPart(docsPartPresenter, PartStackType.TOOLING);
                }
            }
        });
    }
}
