package com.nhlstenden.Menu;

import com.nhlstenden.Presentation;

import java.awt.*;
import java.awt.event.ActionEvent;

public class HelpMenu extends MainMenu {
    protected static final String ABOUT = "About";
    protected static final String HELP = "Help";

    public HelpMenu(Frame parent, Presentation presentation, MenuAction menuAction) {
        super(parent, presentation, menuAction);
    }

    @Override
    public void click(ActionEvent actionEvent) {
        System.out.println("Help menu clicked: " + actionEvent.getActionCommand());
    }
}
