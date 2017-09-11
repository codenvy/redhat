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

import com.codenvy.redhat.plugin.quick.start.ide.panel.DocsPartPresenter;
import com.google.web.bindery.event.shared.EventBus;
import javax.inject.Inject;
import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.ide.api.machine.events.WsAgentStateEvent;
import org.eclipse.che.ide.api.machine.events.WsAgentStateHandler;

/**
 * Quick start panel entry point.
 *
 * @author Oleksander Andriienko
 */
@Extension(title = "QuickStart")
public class QuickStartDocsExtension {

  @Inject
  public QuickStartDocsExtension(final DocsPartPresenter docsPartPresenter, EventBus eventBus) {

    eventBus.addHandler(
        WsAgentStateEvent.TYPE,
        new WsAgentStateHandler() {
          @Override
          public void onWsAgentStarted(WsAgentStateEvent wsAgentStateEvent) {
            docsPartPresenter.init();
          }

          @Override
          public void onWsAgentStopped(WsAgentStateEvent wsAgentStateEvent) {}
        });
  }
}
