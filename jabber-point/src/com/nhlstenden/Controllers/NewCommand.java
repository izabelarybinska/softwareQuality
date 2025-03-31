package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerFrame;
import com.nhlstenden.Slide.TextItem;
import com.nhlstenden.Slide.Style;

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
        
        Style.createStyles();
    }

    @Override
    public void execute() {
        Presentation newPresentation = new Presentation();
        setPresentationTitle(newPresentation);
        addSlides(newPresentation);
        updateViewer(newPresentation);
    }

    private void setPresentationTitle(Presentation presentation) {
        String title = JOptionPane.showInputDialog(parentFrame, "Enter Presentation Title:");
        if (title != null && !title.trim().isEmpty()) {
            presentation.setTitle(title);
        }
    }

    private void addSlides(Presentation presentation) {
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
                    presentation.append(newSlide);
                }
            } else {
                addingSlides = false;
            }
        }
    }

    private Slide createNewSlide() {
        Slide slide = new Slide();
        setSlideTitle(slide);
        addTextItems(slide);
        return slide;
    }

    private void setSlideTitle(Slide slide) {
        String title = JOptionPane.showInputDialog(parentFrame, "Enter Slide Title:");
        if (title != null && !title.trim().isEmpty()) {
            slide.setTitle(title);
        }
    }

    private void addTextItems(Slide slide) {
        boolean addingItems = true;
        while (addingItems) {

            JPanel panel = new JPanel(new GridLayout(2, 2));

            panel.add(new JLabel("Text content:"));
            JTextArea textArea = new JTextArea(3, 20);
            panel.add(new JScrollPane(textArea));

            panel.add(new JLabel("Text level (0-4):"));
            JComboBox<Integer> levelCombo = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4});
            levelCombo.setSelectedIndex(1);
            panel.add(levelCombo);

            int result = JOptionPane.showConfirmDialog(
                    parentFrame,
                    panel,
                    "Add Text Item",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String content = textArea.getText().trim();
                if (!content.isEmpty()) {
                    int level = (int) levelCombo.getSelectedItem();
                    slide.append(new TextItem(level, content));
                }
            } else {
                addingItems = false;
            }
        }
    }

    private void updateViewer(Presentation newPresentation) {
        if (viewerFrame != null && newPresentation.getSize() > 0) {
            viewerFrame.setPresentation(newPresentation);
            viewerFrame.repaint();
        }
    }
}