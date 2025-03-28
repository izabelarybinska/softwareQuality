package com.nhlstenden.Menu;

import com.nhlstenden.Presentation;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewMenu extends MainMenu {
    protected static final String NEXT = "Next";
    protected static final String PREV = "Prev";
    protected static final String VIEW = "View";
    protected static final String PAGENR = "Page number";
    protected static final String GOTO = "Go to";

    public ViewMenu(Frame parent, Presentation presentation, MenuAction menuAction) {
        super(parent, presentation, menuAction);
    }

    @Override
    public void click(ActionEvent actionEvent) {
        System.out.println("View menu clicked: " + actionEvent.getActionCommand());
    }
}
