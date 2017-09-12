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
package com.codenvy.redhat.plugin.quick.start.ide.panel;

import com.codenvy.redhat.plugin.quick.start.ide.QuickStartLocalizationConstant;
import com.codenvy.redhat.plugin.quick.start.shared.dto.GuideDto;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.api.parts.PartStackUIResources;
import org.eclipse.che.ide.api.parts.base.BaseView;

/** @author Alexander Andrienko */
@Singleton
public class DocsViewPartImpl extends BaseView<DocsViewPart.ActionDelegate>
    implements DocsViewPart {

  @UiField Frame frame;

  interface DocsViewPartImplUiBinder extends UiBinder<Widget, DocsViewPartImpl> {}

  @Inject
  public DocsViewPartImpl(
      PartStackUIResources resources,
      QuickStartLocalizationConstant constants,
      DocsViewPartImplUiBinder uiBinder) {
    super(resources);
    setContentWidget(uiBinder.createAndBindUi(this));
    setTitle(constants.showPanelTitle());
  }

  @Override
  public void displayGuide(GuideDto guideDto) {}

  @Override
  public void showStub() {}
}
