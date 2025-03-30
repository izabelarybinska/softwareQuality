package com.nhlstenden.Controllers;

import com.nhlstenden.Menu.MainMenu;
import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerComponent;
import com.nhlstenden.Slide.SlideViewerFrame;
import com.nhlstenden.Slide.TextItemFactory;

import javax.swing.*;
import java.awt.*;

public class NewCommand implements Command {
    private Frame frame;
    private MainMenu mainMenu;
    private Presentation presentation;

    public NewCommand(Presentation presentation, Frame frame, MainMenu mainMenu) {
        this.presentation = presentation;
        this.frame = frame;
        this.mainMenu = mainMenu;
    }

    @Override
    public void execute() {
        JFrame parentFrame = (JFrame) frame;

        Slide initialSlide = new Slide();

        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, initialSlide);


        Presentation newPresentation = new Presentation(slideViewerComponent);


        String title = JOptionPane.showInputDialog(parentFrame, "Enter Presentation Title:");
        if (title != null && !title.trim().isEmpty()) {
            newPresentation.setTitle(title);
        }


        int addSlide = JOptionPane.showConfirmDialog(parentFrame, "Do you want to add a new slide?", "New Slide", JOptionPane.YES_NO_OPTION);
        if (addSlide == JOptionPane.YES_OPTION) {
            String content = JOptionPane.showInputDialog(parentFrame, "Enter slide content:");
            if (content != null && !content.trim().isEmpty()) {
                Slide newSlide = new Slide();
                newSlide.append(new TextItemFactory(), 1, content);
                newPresentation.append(newSlide);
                newPresentation.setSlideNumber(0);
            }
        }


        if (parentFrame instanceof SlideViewerFrame) {
            ((SlideViewerFrame) parentFrame).setPresentation(newPresentation);
        }

        parentFrame.repaint();
    }
}
