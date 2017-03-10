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
package com.codenvy.redhat.plugin.product.info.client;

import com.google.gwt.i18n.client.Messages;


/**
 * Codenvy product information constant.
 *
 * @author Oleksii Orel
 */
public interface CodenvyLocalizationConstant extends Messages {

    @Key("codenvy.tab.title")
    String codenvyTabTitle();

    @Key("codenvy.tab.title.with.workspace.name")
    String codenvyTabTitle(String workspaceName);

    @Key("get.support.link")
    String getSupportLink();

    @Key("get.product.name")
    String getProductName();

    @Key("support.title")
    String supportTitle();
}
