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
package com.codenvy.redhat.plugin.quick.start.shared;

import org.eclipse.che.dto.shared.DTO;

@DTO
public interface GuideFragmentDto {

  String getTitle();

  void setTitle(String title);

  GuideFragmentDto withTitle(String title);

  String getText();

  void setText(String text);

  GuideFragmentDto withText(String text);

  ActionLinkDto getActionLink();

  void setActionLink(ActionLinkDto actionLink);

  GuideFragmentDto withActionLink(ActionLinkDto actionLink);
}
