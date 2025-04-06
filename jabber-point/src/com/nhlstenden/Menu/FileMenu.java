package com.nhlstenden.Menu;

import com.nhlstenden.Controllers.*;
import com.nhlstenden.Presentation;

import java.awt.*;
import java.awt.MenuItem;

public class FileMenu extends Menu {
    protected static final String NEW = "New";
    protected static final String SAVE = "Save";
    protected static final String OPEN = "Open";
    protected static final String FILE = "File";
    protected static final String EXIT = "Exit";

    public FileMenu(Frame parent, Presentation presentation, OpenCommand openFile, NewCommand newFile, SaveCommand saveFile, ExitCommand exitCommand) {
        super(FILE);

        add(createMenuItem(OPEN, openFile));
        add(createMenuItem(NEW, newFile));
        add(createMenuItem(SAVE, saveFile));
        addSeparator();
        add(createMenuItem(EXIT, exitCommand));
    }

    private MenuItem createMenuItem(String name, Command command) {
        MenuItem menuItem = new MenuItem(name);
        menuItem.addActionListener(e -> command.execute());
        return menuItem;
    }
}

