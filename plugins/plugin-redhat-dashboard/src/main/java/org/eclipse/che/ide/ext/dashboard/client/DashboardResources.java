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
package org.eclipse.che.ide.ext.dashboard.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

import org.vectomatic.dom.svg.ui.SVGResource;

/**
 * Dashboard extension resources (css styles, images).
 *
 * @author Oleksii Orel
 */
public interface DashboardResources extends ClientBundle {
    interface DashboardCSS extends CssResource {

        String dashboardArrow();
    }

    @Source({"Dashboard.css", "org/eclipse/che/ide/api/ui/style.css"})
    DashboardCSS dashboardCSS();

}
