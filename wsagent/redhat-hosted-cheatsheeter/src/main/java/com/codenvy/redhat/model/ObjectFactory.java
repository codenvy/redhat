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
package com.codenvy.redhat.model;

import com.codenvy.redhat.model.entity.Action;
import com.codenvy.redhat.model.entity.CheatSheet;
import com.codenvy.redhat.model.entity.Command;
import com.codenvy.redhat.model.entity.Intro;
import com.codenvy.redhat.model.entity.Item;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _ACTION_QNAME      = new QName("", "action");
    private final static QName _COMMAND_QNAME     = new QName("", "command");
    private final static QName _ITEM_QNAME        = new QName("", "item");
    private final static QName _INTRO_QNAME       = new QName("", "intro");
    private final static QName _CHEAT_SHEET_QNAME = new QName("", "cheatsheet");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Action }
     *
     */
    public Action createAction() {
        return new Action();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Action }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "", name = "action")
    public JAXBElement<Action> createAction(Action value) {
        return new JAXBElement<Action>(_ACTION_QNAME, Action.class, null, value);
    }

    /**
     * Create an instance of {@link Command }
     *
     */
    public Command createCommand() {
        return new Command();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Command }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "", name = "command")
    public JAXBElement<Command> createCommand(Command value) {
        return new JAXBElement<Command>(_COMMAND_QNAME, Command.class, null, value);
    }

    /**
     * Create an instance of {@link Item }
     *
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Item }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "", name = "item")
    public JAXBElement<Item> createItem(Item value) {
        return new JAXBElement<Item>(_ITEM_QNAME, Item.class, null, value);
    }

    /**
     * Create an instance of {@link Intro }
     *
     */
    public Intro createIntro() {
        return new Intro();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Item }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "", name = "intro")
    public JAXBElement<Intro> createIntro(Intro value) {
        return new JAXBElement<Intro>(_INTRO_QNAME, Intro.class, null, value);
    }

    /**
     * Create an instance of {@link CheatSheet }
     *
     */
    public CheatSheet createCheatSheet() {
        return new CheatSheet();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheatSheet }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "", name = "cheatsheet")
    public JAXBElement<CheatSheet> createCheatSheet(CheatSheet value) {
        return new JAXBElement<CheatSheet>(_CHEAT_SHEET_QNAME, CheatSheet.class, null, value);
    }
}