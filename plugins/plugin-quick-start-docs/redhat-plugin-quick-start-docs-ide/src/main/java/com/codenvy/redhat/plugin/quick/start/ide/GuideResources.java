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
package com.codenvy.redhat.plugin.quick.start.ide;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/** @author Alexander Andrienko */
public interface GuideResources extends ClientBundle {

  @Source({"guide.css"})
  GuideStyles getGuideStyle();

  interface GuideStyles extends CssResource {

    @ClassName("chapter-without-title")
    String chapterWithoutTitle();

    @ClassName(("error-stub"))
    String stub();

    @ClassName(("action-button"))
    String actionButton();

    @ClassName(("action-button-container"))
    String actionButtonContainer();

    @ClassName(("fullWidthContainer"))
    String fullWidthContainer();
  }
}
