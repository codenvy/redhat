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
package com.codenvy.redhat.plugin.quick.start.shared.dto;

import java.util.List;
import org.eclipse.che.dto.shared.DTO;

@DTO
public interface GuideDto {

  String getTitle();

  void setTitle(String title);

  GuideDto withTitle(String title);

  List<SectionDto> getSections();

  void setSections(List<SectionDto> sections);

  GuideDto withSections(List<SectionDto> sections);
}
