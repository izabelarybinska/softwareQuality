package com.nhlstenden.Menu;

import com.nhlstenden.Controllers.Command;
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

    public FileMenu(Frame parent, Presentation presentation, Command Command)
    {
        super(parent, presentation, Command);
    }


    @Override
    public void click(ActionEvent actionEvent)
    {
        System.out.println("File menu clicked: " + actionEvent.getActionCommand());
    }
}


