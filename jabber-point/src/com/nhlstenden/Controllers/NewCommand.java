package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerFrame;
import com.nhlstenden.Slide.TextItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        presentation.clear();

        String title = JOptionPane.showInputDialog(parentFrame, "Enter Presentation Title:");
        if (title != null && !title.trim().isEmpty()) {
            presentation.setTitle(title);
        }

        boolean addingSlides = true;
        while (addingSlides) {
            Slide slide = createSlide();
            if (slide != null) {
                presentation.append(slide);
            }

            int choice = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "Add another slide?",
                    "Continue",
                    JOptionPane.YES_NO_OPTION
            );
            addingSlides = (choice == JOptionPane.YES_OPTION);
        }

        if (presentation.getSize() > 0) {
            presentation.setSlideNumber(0); // Reset to first slide
            if (viewerFrame != null) {
                viewerFrame.updatePresentation(presentation);
            }
        }
    }

    private ArrayList<Slide> createSlides() {
        ArrayList<Slide> slides = new ArrayList<>();
        boolean creatingSlides = true;

        while (creatingSlides) {
            Slide slide = createSlide();
            if (slide != null) {
                slides.add(slide);

                int choice = JOptionPane.showConfirmDialog(
                        parentFrame,
                        "Add another slide?",
                        "Continue",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice != JOptionPane.YES_OPTION) {
                    creatingSlides = false;
                }
            } else {
                creatingSlides = false;
            }
        }

        return slides;
    }

    private Slide createSlide() {
        Slide slide = new Slide();

        String title = JOptionPane.showInputDialog(parentFrame, "Enter Slide Title:");
        if (title != null && !title.trim().isEmpty()) {
            slide.setTitle(title);
        }

        boolean addingItems = true;
        while (addingItems) {
            Object[] options = {"Add Text", "Finish Slide"};
            int choice = JOptionPane.showOptionDialog(
                    parentFrame,
                    "Add content to slide:",
                    "Slide Content",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == JOptionPane.YES_OPTION) {
                addTextItemToSlide(slide);
            } else {
                addingItems = false;
            }
        }

        return slide;
    }

    private void addTextItemToSlide(Slide slide) {
        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(new JLabel("Text:"));
        JTextArea textArea = new JTextArea(3, 20);
        panel.add(new JScrollPane(textArea));

        panel.add(new JLabel("Level (0-4):"));
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
        }
    }
}