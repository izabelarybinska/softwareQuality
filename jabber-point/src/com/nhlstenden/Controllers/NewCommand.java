package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerFrame;
import com.nhlstenden.Slide.TextItem;

import javax.swing.*;
import java.awt.*;

public class NewCommand implements Command {
    private final Frame parentFrame;
    private final SlideViewerFrame viewerFrame;
    private final Presentation presentation;

    public NewCommand(Presentation presentation, Frame parentFrame, SlideViewerFrame viewerFrame) {
        this.presentation = presentation;
        this.parentFrame = parentFrame;
        this.viewerFrame = viewerFrame;
    }

    @Override
    public void execute() {
        Presentation newPresentation = new Presentation();

        String title = JOptionPane.showInputDialog(parentFrame, "Enter Presentation Title:");
        if (title != null && !title.trim().isEmpty()) {
            newPresentation.setTitle(title);
        }

        boolean addingSlides = true;
        while (addingSlides) {
            int choice = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "Add a new slide?",
                    "New Slide",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                Slide newSlide = createNewSlide();
                if (newSlide != null) {
                    newPresentation.append(newSlide);
                }
            } else {
                addingSlides = false;
            }
        }

        if (newPresentation.getSize() > 0) {
            updateViewer(newPresentation);
        }
    }

    private Slide createNewSlide() {
        Slide slide = new Slide();

        String slideTitle = JOptionPane.showInputDialog(parentFrame, "Enter Slide Title:");
        if (slideTitle != null && !slideTitle.trim().isEmpty()) {
            slide.setTitle(slideTitle);
        }

        boolean addingItems = true;
        while (addingItems) {
            String content = JOptionPane.showInputDialog(
                    parentFrame,
                    "Enter text content (or cancel to finish):"
            );

            if (content != null && !content.trim().isEmpty()) {
                slide.append(new TextItem(1, content));
            } else {
                addingItems = false;
            }
        }

        return slide;
    }

    private void updateViewer(Presentation newPresentation) {
        if (viewerFrame != null) {
            viewerFrame.setPresentation(newPresentation);
            viewerFrame.repaint();
        } else {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Viewer frame not available",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}