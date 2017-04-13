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
package com.codenvy.redhat.plugin.cheatcheeter.docs.client.inject;

import com.codenvy.redhat.plugin.cheatcheeter.docs.client.docs.DocsViewPart;
import com.codenvy.redhat.plugin.cheatcheeter.docs.client.docs.DocsViewPartImpl;
import com.google.gwt.inject.client.AbstractGinModule;

import org.eclipse.che.ide.api.extension.ExtensionGinModule;

/**
 * @author Alexander Andrienko
 */
@ExtensionGinModule
public class CheatSheeterSinkDocsGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(DocsViewPart.class).to(DocsViewPartImpl.class);
    }
}
