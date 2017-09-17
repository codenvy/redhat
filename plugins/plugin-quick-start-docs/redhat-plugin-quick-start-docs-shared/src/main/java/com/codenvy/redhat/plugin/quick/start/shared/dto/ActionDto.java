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

import java.util.Map;
import org.eclipse.che.dto.shared.DTO;

@DTO
public interface ActionDto {

  String getLabel();

  void setLabel(String label);

  ActionDto withLabel(String label);

  String getActionId();

  void setActionId(String actionId);

  ActionDto withActionId(String actionId);

  Map<String, String> getParameters();

  void setParameters(Map<String, String> parameters);

  ActionDto withParameters(Map<String, String> parameters);
}
