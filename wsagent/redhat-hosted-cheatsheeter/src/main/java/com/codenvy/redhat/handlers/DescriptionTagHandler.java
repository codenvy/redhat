package com.codenvy.redhat.handlers;

/**
 * Created by antey on 14.04.17.
 */
public class DescriptionTagHandler extends InnerTextParseDomHandler {

    public DescriptionTagHandler() {
        super("<description>", "</description");
    }
}
