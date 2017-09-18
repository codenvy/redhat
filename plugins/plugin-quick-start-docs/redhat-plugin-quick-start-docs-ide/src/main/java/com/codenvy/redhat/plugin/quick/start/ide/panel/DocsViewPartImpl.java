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
import com.codenvy.redhat.plugin.quick.start.shared.dto.ActionDto;
import com.codenvy.redhat.plugin.quick.start.shared.dto.GuideDto;
import com.codenvy.redhat.plugin.quick.start.shared.dto.SectionDto;
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
import java.util.HashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.api.parts.PartStackUIResources;
import org.eclipse.che.ide.api.parts.base.BaseView;
import org.eclipse.che.ide.api.resources.Project;

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

  /** Create guide section. */
  private Widget createSection(Project project, SectionDto sectionDto) {
    if (sectionDto.getTitle() == null) {
      Widget chapter = createChapter(project, sectionDto);
      chapter.addStyleName(guideResources.getGuideStyle().chapterWithoutTitle());
      return chapter;
    }

    // create chapter with title
    DisclosurePanel advancedDisclosure = new DisclosurePanel(sectionDto.getTitle());
    advancedDisclosure.setContent(createChapter(project, sectionDto));
    return advancedDisclosure;
  }

  private Widget createChapter(Project project, SectionDto sectionDto) {
    FlowPanel chapterPanel = new FlowPanel();

    if (sectionDto.getText() != null) {
      Widget safeHtmlWidget = createSafeHtmlWidget(sectionDto.getText());
      chapterPanel.add(safeHtmlWidget);
    }

    if (sectionDto.getAction() != null) {
      FlowPanel buttonPanel = new FlowPanel();
      buttonPanel.addStyleName(guideResources.getGuideStyle().actionButtonContainer());
      Button actionButton = createActionButton(project, sectionDto.getAction());
      actionButton.addStyleName(guideResources.getGuideStyle().actionButton());
      buttonPanel.add(actionButton);
      chapterPanel.add(buttonPanel);
    }

    return chapterPanel;
  }

  private Widget createSafeHtmlWidget(String htmlText) {
    SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder().appendHtmlConstant(htmlText);
    return new HTMLPanel(safeHtmlBuilder.toSafeHtml());
  }

  private Button createActionButton(Project project, ActionDto actionDto) {
    HashMap<String, String> parameters = new HashMap<>(actionDto.getParameters());
    parameters.put("projectPath", project.getPath());

    Button actionButton = new Button(actionDto.getLabel());
    actionButton.addClickHandler(
        event -> delegate.onActionLinkClick(actionDto.getActionId(), parameters));
    return actionButton;
  }

  /** Display guide in the view. */
  @Override
  public void displayGuide(Project project, GuideDto guideDto) {
    chapters.clear();

    setTitle(guideDto.getTitle());

    for (SectionDto section : guideDto.getSections()) {
      chapters.add(createSection(project, section));
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
