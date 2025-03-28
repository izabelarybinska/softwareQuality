package com.nhlstenden.Menu;

import com.nhlstenden.Presentation;

import java.awt.*;
import java.awt.event.ActionEvent;

public class FileMenu extends MainMenu
{

    protected static final String New = "New";
    protected static final String Save = "Save";
    protected static final String Open = "Open";
    protected static final String File = "File";
    protected static final String Exit = "Exit";

    public FileMenu(Frame parent, Presentation presentation, MenuAction menuAction)
    {
        super(parent, presentation, menuAction);
    }

    @Override
    public void click(ActionEvent actionEvent)
    {
        System.out.println("File menu clicked: " + actionEvent.getActionCommand());
    }
}


