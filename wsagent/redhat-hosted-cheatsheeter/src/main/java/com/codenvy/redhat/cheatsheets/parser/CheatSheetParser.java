/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.redhat.cheatsheets.parser;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import com.codenvy.redhat.cheatsheets.parser.model.entity.CheatSheet;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;

import org.eclipse.che.api.core.ApiException;
import org.eclipse.che.commons.lang.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author Alexander Andrienko
 */
@Singleton
public class CheatSheetParser {

    private static final Logger LOG = LoggerFactory.getLogger(CheatSheetParser.class);

    private Unmarshaller unmarshaller;
    private Template     template;

    @Inject
    public CheatSheetParser() {
    }

    public String parse(File file) throws ApiException {
        try {
            JAXBElement element = (JAXBElement) unmarshaller.unmarshal(file);

            CheatSheet cheatSheet = (CheatSheet) element.getValue();

            //Prepare the template input:
            Map<String, Object> input = ImmutableMap.of("cheatsheet", cheatSheet);

            Writer strWriter = new StringWriter();
            template.process(input, strWriter);

            return strWriter.toString();
        } catch (Exception e) {
            throw new ApiException(format("Failed to parse cheatsheets docs. Cause: '%s'", e.getCause().getLocalizedMessage()));
        }
    }
//todo remove it
    public static void main(String[] args) throws IOException, ApiException {
        File file = IoUtil.downloadFile(null, "cheatsheet", null, new URL("https://raw.githubusercontent.com/jboss-developer/jboss-eap-quickstarts/7.1.Alpha/kitchensink/.cheatsheet.xml"));
        System.out.println(file.exists());

        CheatSheetParser cheatSheetParser = new CheatSheetParser();
        cheatSheetParser.prepareParser();
        System.out.println(cheatSheetParser.parse(file));
    }

//    private void checkCheetSheet(CheatSheet cheatSheet) throws IOException {
//        if (cheatSheet.getIntro() == null) {
//            throw new IOException("The sheat sheet content file must contain an 'intro'')
//        }
//    }

    @VisibleForTesting
    @PostConstruct
    protected void prepareParser() {
        try {
            //create unmashaller
            JAXBContext jc = JAXBContext.newInstance("com.codenvy.redhat.cheatsheets.parser.model");
            unmarshaller = jc.createUnmarshaller();

            Configuration cfg = new Configuration();

            // Set up folder with template
            cfg.setClassForTemplateLoading(CheatSheetParser.class, "templates");

            // Some settings:
            cfg.setIncompatibleImprovements(new Version(2, 3, 20));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setLocale(Locale.US);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            // Get the template
            cfg.setClassForTemplateLoading(CheatSheetParser.class, "/templates");
            template = cfg.getTemplate("template.ftl");
        } catch (Exception e) {
            LOG.error(format("Failed to prepare cheatsheets parser. Cause: '%s'", e.getMessage()));
        }
    }
}
