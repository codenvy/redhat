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
import com.codenvy.redhat.plugin.quick.start.shared.dto.ActionLinkDto;
import com.codenvy.redhat.plugin.quick.start.shared.dto.GuideDto;
import com.codenvy.redhat.plugin.quick.start.shared.dto.GuideFragmentDto;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
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

  private final GuideResources guideResources;

  interface DocsViewPartImplUiBinder extends UiBinder<Widget, DocsViewPartImpl> {}

  @Inject
  public DocsViewPartImpl(
      PartStackUIResources parStackRes,
      GuideResources guideResources,
      QuickStartLocalizationConstant constants,
      DocsViewPartImplUiBinder uiBinder) {
    super(parStackRes);
    setTitle(constants.guidePanelTitle());

    this.guideResources = guideResources;

    setContentWidget(uiBinder.createAndBindUi(this));
  }

  /** Create guide fragment. */
  private Widget createFragment(GuideFragmentDto fragmentDto) {
    if (fragmentDto.getTitle() == null) {
      Widget chapter = createChapter(fragmentDto);
      chapter.addStyleName(guideResources.getGuideStyle().chapterWithoutTitle());
      return chapter;
    }

    // create chapter with title
    DisclosurePanel advancedDisclosure = new DisclosurePanel(fragmentDto.getTitle());
    advancedDisclosure.setContent(createChapter(fragmentDto));
    return advancedDisclosure;
  }

  private Widget createChapter(GuideFragmentDto fragmentDto) {
    if (fragmentDto.getActionLink() != null) {
      Widget safeHtmlWidget = createSafeHtmlWidget(fragmentDto.getText());

      FlowPanel buttonPanel = new FlowPanel();
      buttonPanel.addStyleName(guideResources.getGuideStyle().actionButtonContainer());
      Button actionButton = createActionButton(fragmentDto.getActionLink());
      actionButton.addStyleName(guideResources.getGuideStyle().actionButton());
      buttonPanel.add(actionButton);

      FlowPanel flowPanel = new FlowPanel();
      flowPanel.add(safeHtmlWidget);
      flowPanel.add(buttonPanel);

      return flowPanel;
    }

    return createSafeHtmlWidget(fragmentDto.getText());
  }

  private Widget createSafeHtmlWidget(String htmlText) {
    SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder().appendHtmlConstant(htmlText);
    return new HTMLPanel(safeHtmlBuilder.toSafeHtml());
  }

  private Button createActionButton(ActionLinkDto actionLink) {
    Button actionButton = new Button(actionLink.getLabel());
    actionButton.addClickHandler(
        event -> delegate.onActionLinkClick(actionLink.getActionId(), actionLink.getParameters()));
    return actionButton;
  }

  /** Display guide in the view. */
  @Override
  public void displayGuide(GuideDto guideDto) {
    chapters.clear();

    setTitle(guideDto.getTitle());

    for (GuideFragmentDto fragment : guideDto.getFragments()) {
      chapters.add(createFragment(fragment));
    }
  }

  /** Show stub if guide is unavailable */
  @Override
  public void showStub(String stubMessage) {
    chapters.clear();
    setTitle("");
    FlowPanel stubPanel = new FlowPanel();
    stubPanel.add(new Label(stubMessage));
    stubPanel.addStyleName(guideResources.getGuideStyle().stub());
    chapters.add(stubPanel);
  }
}
