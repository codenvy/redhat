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
package com.codenvy.redhat.inject;

import com.codenvy.redhat.CheatSheeterService;
import com.codenvy.redhat.cheatsheets.parser.CheatSheetParser;
import com.google.inject.AbstractModule;

import org.eclipse.che.inject.DynaModule;

/**
 * @author Alexander Andrienko
 */
@DynaModule
public class CheatSheeterModule extends AbstractModule {
    protected void configure() {
        bind(CheatSheeterService.class);
        bind(CheatSheetParser.class);
    }
}
