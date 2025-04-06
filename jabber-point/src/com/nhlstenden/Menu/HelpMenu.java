package com.nhlstenden.Menu;

import com.nhlstenden.AboutBox;
import com.nhlstenden.Presentation;

import java.awt.*;

public class HelpMenu extends Menu {
    protected static final String ABOUT = "About";
    protected static final String HELP = "Help";

    public HelpMenu(Frame parent, Presentation presentation) {
        super(HELP);

        add(createMenuItem(ABOUT, e -> AboutBox.show(parent)));
    }

    private MenuItem createMenuItem(String name, java.awt.event.ActionListener action) {
        MenuItem menuItem = new MenuItem(name);
        menuItem.addActionListener(action);
        return menuItem;
    }
}
