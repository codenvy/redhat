package com.codenvy.redhat.model.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Alexander Andrienko
 */
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class Action {//todo write required fields
    @XmlAttribute
    private String pluginId;
    @XmlAttribute(name = "class")
    private String clazz;
    @XmlAttribute
    private String param1;
    @XmlAttribute
    private  String param2;

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }
}
