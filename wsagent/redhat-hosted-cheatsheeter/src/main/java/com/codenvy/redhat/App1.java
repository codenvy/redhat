package com.codenvy.redhat;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import com.codenvy.redhat.model.entity.CheatSheet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Alexander Andrienko
 */
public class App1 {//TODO unexpected element handle....
    public static void main(String[] args) throws JAXBException, IOException, TemplateException {
        CheatSheet cheatSheet = null;
        try {
            JAXBContext jc = JAXBContext.newInstance ("com.codenvy.redhat.model");

            Unmarshaller u = jc.createUnmarshaller ();

            File f = new File ("/home/user/projects/redhat/wsagent/redhat-hosted-cheatsheeter/src/main/resources/input.xml");
            JAXBElement element = (JAXBElement) u.unmarshal(f);

            cheatSheet = (CheatSheet) element.getValue();
            System.out.println(cheatSheet.getTitle());
        } catch (JAXBException e) {
            e.printStackTrace ();
        }

        Configuration cfg = new Configuration();

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(App1.class, "templates");

        // Some other recommended settings:
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        //Prepare the template input:
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("cheatsheet", cheatSheet);

        // Get the template
        cfg.setClassForTemplateLoading(App1.class, "/templates");
        Template template = cfg.getTemplate("template.ftl");

        // Write output to the console
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);
    }
}
