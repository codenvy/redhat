package com.codenvy.redhat.model.entity;

import com.codenvy.redhat.handlers.DescriptionTagHandler;
import com.sun.xml.internal.ws.transport.http.DeploymentDescriptorParser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Alexander Andrienko
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    @XmlAttribute
    private boolean    skip;
    @XmlAttribute
    private String     label;
    @XmlAttribute
    private String     title;
    @XmlAnyElement(value = DescriptionTagHandler.class)
    private String     description;
    @XmlElement
    private Action     action;
    @XmlElement
    private Command    command;
    @XmlElement
    private List<Item> subitem;

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public List<Item> getSubitem() {
        return subitem;
    }

    public void setSubitem(List<Item> subitem) {
        this.subitem = subitem;
    }
}
