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

import org.eclipse.che.ide.api.mvp.View;
import org.eclipse.che.ide.api.parts.base.BaseActionDelegate;

/**
 * Panel to display static html content parsed form .cheatcheet.xml content.
 *
 * @author Alexander Andrienko
 */
public interface DocsViewPart extends View<DocsViewPart.ActionDelegate> {

    /** Set the URL to load content into view. */
    void setUrl(String url);

    interface ActionDelegate extends BaseActionDelegate {
    }
}
