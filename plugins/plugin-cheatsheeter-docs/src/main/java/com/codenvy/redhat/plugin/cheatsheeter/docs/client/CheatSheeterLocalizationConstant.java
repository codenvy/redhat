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

import com.google.gwt.i18n.client.Messages;

/**
 * @author Alexander Andrienko
 */
public interface CheatSheeterLocalizationConstant extends Messages {

    @Key("show.cheatsheeter.sink.docs.title")
    String showCheatSheeterTitle();

    @Key("show.cheatsheeter.sink.docs.popup")
    String showCheatSheeterDocsPopup();
}
