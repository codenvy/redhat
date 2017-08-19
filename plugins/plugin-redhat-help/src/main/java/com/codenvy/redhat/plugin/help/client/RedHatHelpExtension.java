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
package com.codenvy.redhat.plugin.help.client;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.api.action.ActionManager;
import org.eclipse.che.ide.api.action.DefaultActionGroup;
import org.eclipse.che.ide.api.action.IdeActions;
import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.ide.ext.help.client.HelpAboutExtension;
import org.eclipse.che.ide.ext.help.client.RedirectToSupportAction;

/** @author Anatolii Bazko */
@Singleton
@Extension(title = "Help Extension", version = "3.0.0")
public class RedHatHelpExtension {

  /**
   * Dependency on {@link HelpAboutExtension} allows to initialize all actions in the first
   * instance. Then simple remove {@link RedirectToSupportAction} from the help menu.
   */
  @Inject
  public RedHatHelpExtension(
      ActionManager actionManager,
      final RedirectToSupportAction redirectToSupportAction,
      final HelpAboutExtension helpAboutExtension) {
    DefaultActionGroup helpGroup =
        (DefaultActionGroup) actionManager.getAction(IdeActions.GROUP_HELP);
    helpGroup.remove(redirectToSupportAction);

    actionManager.unregisterAction("redirectToSupport");
  }
}
