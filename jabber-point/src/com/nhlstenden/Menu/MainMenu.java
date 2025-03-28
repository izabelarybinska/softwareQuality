package com.nhlstenden.Menu;

import com.nhlstenden.AboutBox;
import com.nhlstenden.Controllers.Command;
import com.nhlstenden.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static com.nhlstenden.Menu.ViewMenu.PAGENR;

public abstract class MainMenu {
    private static final long serialVersionUID = 227L;
    protected Frame parent;
    protected Presentation presentation;
    protected Command command;

    public MainMenu(Frame parent, Presentation presentation, Command Command) {
        this.parent = parent;
        this.presentation = presentation;
        this.command = command;
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
        String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
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

    public abstract void click(ActionEvent actionEvent);
}
