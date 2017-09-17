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
package com.codenvy.redhat.plugin.quick.start.ide.inject;

import com.codenvy.redhat.plugin.quick.start.ide.QuickStartServiceClient;
import com.codenvy.redhat.plugin.quick.start.ide.QuickStartServiceClientImpl;
import com.codenvy.redhat.plugin.quick.start.ide.panel.DocsPartPresenter;
import com.codenvy.redhat.plugin.quick.start.ide.panel.DocsViewPart;
import com.codenvy.redhat.plugin.quick.start.ide.panel.DocsViewPartImpl;
import com.google.gwt.inject.client.AbstractGinModule;
import org.eclipse.che.ide.api.extension.ExtensionGinModule;

/** @author Oleksander Andriienko */
@ExtensionGinModule
public class QuickStartGinModule extends AbstractGinModule {
  @Override
  protected void configure() {
    bind(DocsViewPart.class).to(DocsViewPartImpl.class);
    bind(QuickStartServiceClient.class).to(QuickStartServiceClientImpl.class);
    bind(DocsPartPresenter.class);
  }
}
