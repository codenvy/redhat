package com.codenvy.redhat.model.entity;

import com.codenvy.redhat.handlers.DescriptionTagHandler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alexander Andrienko
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Intro {
    @XmlAnyElement(value = DescriptionTagHandler.class)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
