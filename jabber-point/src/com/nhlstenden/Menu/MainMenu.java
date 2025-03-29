package com.nhlstenden.Menu;

import com.nhlstenden.AboutBox;
import com.nhlstenden.Controllers.*;
import com.nhlstenden.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends MenuBar {
    private static final long serialVersionUID = 227L;

    private final FileMenu fileMenu;
    private final ViewMenu viewMenu;
    private final HelpMenu helpMenu;
    protected Frame parent;
    protected Presentation presentation;

    public MainMenu(Frame parent, Presentation presentation) {
        this.parent = parent;
        this.presentation = presentation;

        OpenCommand openCommand = new OpenCommand(presentation, parent);
        NewCommand newCommand = new NewCommand(presentation, parent);
        SaveCommand saveCommand = new SaveCommand(presentation, parent);
        ExitCommand exitCommand = new ExitCommand(presentation);
        NextCommand nextCommand = new NextCommand(presentation);
        PrevCommand prevCommand = new PrevCommand(presentation);

        fileMenu = new FileMenu(parent, presentation, openCommand, newCommand, saveCommand, exitCommand);
        viewMenu = new ViewMenu(parent, presentation);
        helpMenu = new HelpMenu(parent, presentation);

        add(fileMenu);
        add(viewMenu);
        add(helpMenu);
    }

    protected MenuItem createMenuItem(String name, ActionListener action) {
        MenuItem menuItem = new MenuItem(name, new MenuShortcut(name.charAt(0)));
        menuItem.addActionListener(action);
        return menuItem;
    }

    public void exitApp(ActionEvent actionEvent) {
        presentation.exit(0);
    }

    public void nextSlide(ActionEvent actionEvent) {
        presentation.nextSlide();
    }

    public void prevSlide(ActionEvent actionEvent) {
        presentation.prevSlide();
    }

    public void goToSlide(ActionEvent actionEvent) {
        String pageNumberStr = JOptionPane.showInputDialog("Enter Page Number:");
        try {
            int pageNumber = Integer.parseInt(pageNumberStr);
            presentation.setSlideNumber(pageNumber - 1);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid page number!");
        }
    }

    public void showAboutBox(ActionEvent actionEvent) {
        AboutBox.show(parent);
    }
}
