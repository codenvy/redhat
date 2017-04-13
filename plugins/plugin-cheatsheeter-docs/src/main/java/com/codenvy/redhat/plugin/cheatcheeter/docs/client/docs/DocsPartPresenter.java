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
package com.codenvy.redhat.plugin.cheatcheeter.docs.client.docs;

import com.codenvy.redhat.plugin.cheatcheeter.docs.client.CheatSheeterLocalizationConstant;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

import org.eclipse.che.api.core.model.workspace.Workspace;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.parts.base.BasePresenter;
import org.eclipse.che.ide.util.loging.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Presenter to manage {@link DocsViewPart}
 *
 * @author Alexander Andrienko
 */
@Singleton
public class DocsPartPresenter extends BasePresenter implements DocsViewPart.ActionDelegate {

    private static final String DOC_URL = "https://github.com/jboss-developer/jboss-eap-quickstarts/raw/7.0.x/kitchensink/.cheatsheet.xml";

    private final DocsViewPart                     view;
    private final CheatSheeterLocalizationConstant constants;
    private final AppContext                       appContext;

    @Inject
    public DocsPartPresenter(DocsViewPart view,
                             CheatSheeterLocalizationConstant constants,
                             AppContext appContext) {
        this.view = view;
        this.constants = constants;
        this.appContext = appContext;

        view.setDelegate(this);
    }

    public void init() {
        Workspace workspace = appContext.getWorkspace();
        if (workspace == null || appContext.getResources() == null || appContext.getResources().length == 0) {
            return;
        }

        view.setUrl(DOC_URL);
        Log.info(getClass(), DOC_URL);
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
        return DOC_URL;
    }
}
