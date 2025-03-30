package com.nhlstenden.Menu;

import com.nhlstenden.AboutBox;
import com.nhlstenden.Controllers.*;
import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends MenuBar {
    private static final long serialVersionUID = 227L;

    private JFrame parentFrame;
    private SlideViewerComponent slideViewerComponent;
    private final FileMenu fileMenu;
    private final ViewMenu viewMenu;
    private final HelpMenu helpMenu;
    protected Frame parent;
    protected Presentation presentation;

    public MainMenu(Frame parent, Presentation presentation) {
        this.parent = parent;
        this.presentation = presentation;


        this.parentFrame = (JFrame) parent;


        Slide initialSlide = presentation.getCurrentSlide();


        if (initialSlide == null) {
            initialSlide = new Slide();
        }


        this.slideViewerComponent = new SlideViewerComponent(presentation, initialSlide);


        OpenCommand openCommand = new OpenCommand(presentation, parent);
        NewCommand newCommand = new NewCommand(presentation, parent, this);
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

    public FileMenu getFileMenu()
    {
        return fileMenu;
    }

    public ViewMenu getViewMenu()
    {
        return viewMenu;
    }

    @Override
    public HelpMenu getHelpMenu()
    {
        return helpMenu;
    }

    @Override
    public Frame getParent()
    {
        return parent;
    }

    public void setParent(Frame parent)
    {
        this.parent = parent;
    }

    public Presentation getPresentation()
    {
        return presentation;
    }

    public void setPresentation(Presentation newPresentation) {
        this.presentation = newPresentation;

        slideViewerComponent.update(newPresentation, newPresentation.getCurrentSlide().getSize());
        parentFrame.repaint();
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
