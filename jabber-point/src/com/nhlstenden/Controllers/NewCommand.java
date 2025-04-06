package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerFrame;
import com.nhlstenden.Slide.TextItem;

import java.awt.*;

public class NewCommand implements Command {
    public static final int YES_NO_OPTION = javax.swing.JOptionPane.YES_NO_OPTION;
    public static final int YES_OPTION = javax.swing.JOptionPane.YES_OPTION;
    public static final int NO_OPTION = javax.swing.JOptionPane.NO_OPTION;
    public static final int QUESTION_MESSAGE = javax.swing.JOptionPane.QUESTION_MESSAGE;

    private final Frame parentFrame;
    private final SlideViewerFrame viewerFrame;
    private final Presentation presentation;
    private final DialogService dialogService;
    private final SlideBuilder slideBuilder;

    public NewCommand(Presentation presentation, Frame parentFrame, SlideViewerFrame viewerFrame) {
        this(presentation, parentFrame, viewerFrame,
                new DefaultDialogService(), new DefaultSlideBuilder());
    }

    NewCommand(Presentation presentation, Frame parentFrame,
               SlideViewerFrame viewerFrame, DialogService dialogService,
               SlideBuilder slideBuilder) {
        this.presentation = presentation;
        this.parentFrame = parentFrame;
        this.viewerFrame = viewerFrame;
        this.dialogService = dialogService;
        this.slideBuilder = slideBuilder;
    }

    // when new command is triggered execute is called and it follows the sequence clear -> set title -> build slide -> update view
    @Override
    public void execute() {
        clearPresentation();
        setPresentationTitle();
        buildPresentationSlides();
        updatePresentationView();
    }

    protected void clearPresentation() {
        presentation.clear();
    }

    protected void setPresentationTitle() {
        String title = dialogService.showInputDialog(parentFrame, "Enter Presentation Title:");
        if (title != null && !title.trim().isEmpty()) {
            presentation.setTitle(title);
        }
    }

    protected void buildPresentationSlides() {
        boolean addingSlides = true;
        while (addingSlides) {
            Slide slide = slideBuilder.buildSlide(parentFrame, dialogService);
            if (slide != null) {
                presentation.append(slide);
            }
            addingSlides = shouldAddAnotherSlide();
        }
    }

    protected boolean shouldAddAnotherSlide() {
        int choice = dialogService.showConfirmDialog(
                parentFrame,
                "Add another slide?",
                "Continue",
                YES_NO_OPTION
        );
        return (choice == YES_OPTION);
    }

    protected void updatePresentationView() {
        if (presentation.getSize() > 0) {
            presentation.setSlideNumber(0);
            if (viewerFrame != null) {
                viewerFrame.updatePresentation(presentation);
            }
        }
    }

    //interface for inputs when creating new presentation
    public interface DialogService {
        String showInputDialog(Component parent, String message);

        int showConfirmDialog(Component parent, String message, String title, int optionType);

        int showOptionDialog(Component parent, String message, String title,
                             int optionType, int messageType, String[] options, String initialValue);

        TextInputResult showTextInputDialog(Component parent, String title,
                                            String textLabel, String levelLabel,
                                            Integer[] levels, int defaultLevel);
    }

    public static class TextInputResult {
        private final String text;
        private final int level;

        public TextInputResult(String text, int level) {
            this.text = text;
            this.level = level;
        }

        public String getText() {
            return text;
        }

        public int getLevel() {
            return level;
        }
    }

    public interface SlideBuilder {
        Slide buildSlide(Component parent, DialogService dialogService);
    }

    public static class DefaultSlideBuilder implements SlideBuilder {
        @Override
        public Slide buildSlide(Component parent, DialogService dialogService) {
            Slide slide = new Slide();
            setSlideTitle(parent, dialogService, slide);
            addSlideContent(parent, dialogService, slide);
            return slide;
        }

        protected void setSlideTitle(Component parent, DialogService dialogService, Slide slide) {
            String title = dialogService.showInputDialog(parent, "Enter Slide Title:");
            if (title != null && !title.trim().isEmpty()) {
                slide.setTitle(title);
            }
        }

        protected void addSlideContent(Component parent, DialogService dialogService, Slide slide) {
            boolean addingItems = true;
            while (addingItems) {
                addingItems = processSlideItemAddition(parent, dialogService, slide);
            }
        }

        protected boolean processSlideItemAddition(Component parent, DialogService dialogService, Slide slide) {
            int choice = dialogService.showOptionDialog(
                    parent,
                    "Add content to slide:",
                    "Slide Content",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE,
                    new String[]{"Add Text", "Finish Slide"},
                    "Add Text"
            );

            if (choice == 0) {
                addTextItemToSlide(parent, dialogService, slide);
                return true;
            }
            return false;
        }

        protected void addTextItemToSlide(Component parent, DialogService dialogService, Slide slide) {
            TextInputResult result = dialogService.showTextInputDialog(
                    parent,
                    "Add Text Item",
                    "Text:",
                    "Level (0-4):",
                    new Integer[]{0, 1, 2, 3, 4},
                    1
            );

            if (result != null && result.getText() != null && !result.getText().isEmpty()) {
                slide.append(new TextItem(result.getLevel(), result.getText()));
            }
        }
    }

    protected static class DefaultDialogService implements DialogService {
        @Override
        public String showInputDialog(Component parent, String message) {
            return javax.swing.JOptionPane.showInputDialog(parent, message);
        }

        @Override
        public int showConfirmDialog(Component parent, String message, String title, int optionType) {
            return javax.swing.JOptionPane.showConfirmDialog(parent, message, title, optionType);
        }

        @Override
        public int showOptionDialog(Component parent, String message, String title,
                                    int optionType, int messageType, String[] options, String initialValue) {
            return javax.swing.JOptionPane.showOptionDialog(
                    parent, message, title, optionType, messageType,
                    null, options, initialValue);
        }

        @Override
        public TextInputResult showTextInputDialog(Component parent, String title,
                                                   String textLabel, String levelLabel,
                                                   Integer[] levels, int defaultLevel) {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(2, 2));

            panel.add(new javax.swing.JLabel(textLabel));
            javax.swing.JTextArea textArea = new javax.swing.JTextArea(3, 20);
            panel.add(new javax.swing.JScrollPane(textArea));

            panel.add(new javax.swing.JLabel(levelLabel));
            javax.swing.JComboBox<Integer> levelCombo = new javax.swing.JComboBox<>(levels);
            levelCombo.setSelectedIndex(defaultLevel);
            panel.add(levelCombo);

            int result = javax.swing.JOptionPane.showConfirmDialog(
                    parent,
                    panel,
                    title,
                    javax.swing.JOptionPane.OK_CANCEL_OPTION,
                    javax.swing.JOptionPane.PLAIN_MESSAGE
            );

            if (result == javax.swing.JOptionPane.OK_OPTION) {
                String content = textArea.getText().trim();
                if (!content.isEmpty()) {
                    int level = (int) levelCombo.getSelectedItem();
                    return new TextInputResult(content, level);
                }
            }
            return null;
        }
    }
}