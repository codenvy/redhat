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
package com.codenvy.redhat.plugin.cheatsheeter.docs.client;

import com.codenvy.redhat.plugin.cheatsheeter.docs.client.docs.DocsPartPresenter;
import com.google.web.bindery.event.shared.EventBus;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.ide.api.machine.events.WsAgentStateEvent;
import org.eclipse.che.ide.api.machine.events.WsAgentStateHandler;

/** @author Alexander Andrienko */
@Singleton
@Extension(
  title = "CheatCheeter docs extension.",
  description = "Extension to display static html content parsed from '.cheatsheet.xml'",
  version = "0.1.0"
)
public class CheatSheeterExtension {

  @Inject
  public CheatSheeterExtension(final DocsPartPresenter docsPartPresenter, EventBus eventBus) {
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
