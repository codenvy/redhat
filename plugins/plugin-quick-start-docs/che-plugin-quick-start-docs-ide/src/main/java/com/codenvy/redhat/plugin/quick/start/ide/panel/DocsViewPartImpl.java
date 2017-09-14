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

import com.codenvy.redhat.plugin.quick.start.ide.GuideResources;
import com.codenvy.redhat.plugin.quick.start.ide.QuickStartLocalizationConstant;
import com.codenvy.redhat.plugin.quick.start.shared.dto.GuideDto;
import com.codenvy.redhat.plugin.quick.start.shared.dto.GuideFragmentDto;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.api.parts.PartStackUIResources;
import org.eclipse.che.ide.api.parts.base.BaseView;

/** @author Alexander Andrienko */
@Singleton
public class DocsViewPartImpl extends BaseView<DocsViewPart.ActionDelegate>
    implements DocsViewPart {

  @UiField VerticalPanel chapters;

  private final QuickStartLocalizationConstant constants;

  interface DocsViewPartImplUiBinder extends UiBinder<Widget, DocsViewPartImpl> {}

  @Inject
  public DocsViewPartImpl(
      PartStackUIResources parStackRes,
      GuideResources guideResources,
      QuickStartLocalizationConstant constants,
      DocsViewPartImplUiBinder uiBinder) {
    super(parStackRes);
    setTitle(constants.showPanelTitle());

    this.constants = constants;

    setContentWidget(uiBinder.createAndBindUi(this));
  }

  /** Create a form that contains undisclosed advanced options. */
  private Widget addChapter(GuideFragmentDto fragmentDto) {
    DisclosurePanel advancedDisclosure = new DisclosurePanel(fragmentDto.getTitle());

    advancedDisclosure.ensureDebugId("cwDisclosurePanel");
    advancedDisclosure.setContent(new HTML(fragmentDto.getText()));

    return advancedDisclosure;
  }

  /** Display guide in the view. * */
  @Override
  public void displayGuide(GuideDto guideDto) {
    chapters.clear();
    chapters.setSpacing(8);

    setTitle(guideDto.getTitle());

    for (GuideFragmentDto fragment : guideDto.getFragments()) {
      chapters.add(addChapter(fragment));
    }
  }

  /** Show stub if guide is unavailable * */
  @Override
  public void showStub() {}
}
