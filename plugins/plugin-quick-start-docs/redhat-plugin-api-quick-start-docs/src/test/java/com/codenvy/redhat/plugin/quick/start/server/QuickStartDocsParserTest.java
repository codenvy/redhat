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
package com.codenvy.redhat.plugin.quick.start.server;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/** @author Alexander Andrienko */
public class QuickStartDocsParserTest {

  //    private QuickStartDocsParser parser;

  @BeforeClass
  public void setUp() {
    //        parser = new QuickStartDocsParser(DtoFactory.getInstance());
  }

  //    @Test
  //    public void shouldParseGuideWithoutTitleAndFragments() throws ApiException {
  //        String guideJson = "{}";
  //        GuideDto guide = parser.parse(guideJson);
  //
  //        assertNull(guide.getTitle());
  //        assertTrue(guide.getFragments().isEmpty());
  //    }
  //
  //    @Test
  //    public void shouldParseGuideWithTitleButWithoutFragments() throws ApiException {
  //        String guideJson = "{title\" : \"Spring MVC\"}";
  //        GuideDto guide = parser.parse(guideJson);
  //
  //        assertEquals(guide.getFragments());
  //    }

  @Test
  public void shouldParseGuideWithFragments1() {
    //        String guideJson = "{title\" : \"Spring MVC\"}";
    //        parser.parse(guideJson);
  }
}
