package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerFrame;
import com.nhlstenden.Slide.TextItem;

import java.awt.*;

public class NewCommand implements Command
{
    private final Frame parentFrame;
    private final SlideViewerFrame viewerFrame;
    private final Presentation presentation;
    private final DialogService dialogService;

    public NewCommand(Presentation presentation, Frame parentFrame, SlideViewerFrame viewerFrame)
    {
        this(presentation, parentFrame, viewerFrame, new DefaultDialogService());
    }

    // Package-private for testing
    NewCommand(Presentation presentation, Frame parentFrame, SlideViewerFrame viewerFrame, DialogService dialogService)
    {
        this.presentation = presentation;
        this.parentFrame = parentFrame;
        this.viewerFrame = viewerFrame;
        this.dialogService = dialogService;
    }

    @Override
    public void execute()
    {
        presentation.clear();

        String title = dialogService.showInputDialog(parentFrame, "Enter Presentation Title:");
        if (title != null && !title.trim().isEmpty())
        {
            presentation.setTitle(title);
        }

        boolean addingSlides = true;
        while (addingSlides)
        {
            Slide slide = createSlide();
            if (slide != null)
            {
                presentation.append(slide);
            }

            int choice = dialogService.showConfirmDialog(
                    parentFrame,
                    "Add another slide?",
                    "Continue",
                    DialogService.YES_NO_OPTION
            );
            addingSlides = (choice == DialogService.YES_OPTION);
        }

        if (presentation.getSize() > 0)
        {
            presentation.setSlideNumber(0);
            if (viewerFrame != null)
            {
                viewerFrame.updatePresentation(presentation);
            }
        }
    }

    protected Slide createSlide()
    {
        Slide slide = new Slide();

        String title = dialogService.showInputDialog(parentFrame, "Enter Slide Title:");
        if (title != null && !title.trim().isEmpty())
        {
            slide.setTitle(title);
        }

        boolean addingItems = true;
        while (addingItems)
        {
            int choice = dialogService.showOptionDialog(
                    parentFrame,
                    "Add content to slide:",
                    "Slide Content",
                    DialogService.YES_NO_OPTION,
                    DialogService.QUESTION_MESSAGE,
                    new String[]{"Add Text", "Finish Slide"},
                    "Add Text"
            );

            if (choice == 0)
            { // "Add Text" selected
                addTextItemToSlide(slide);
            }
            else
            {
                addingItems = false;
            }
        }

        return slide;
    }

    protected void addTextItemToSlide(Slide slide)
    {
        TextInputResult result = dialogService.showTextInputDialog(
                parentFrame,
                "Add Text Item",
                "Text:",
                "Level (0-4):",
                new Integer[]{0, 1, 2, 3, 4},
                1
        );

        if (result != null && result.getText() != null && !result.getText().isEmpty())
        {
            slide.append(new TextItem(result.getLevel(), result.getText()));
        }
    }

    public interface DialogService
    {
        int YES_NO_OPTION = 0;
        int YES_OPTION = 0;
        int NO_OPTION = 1;
        int QUESTION_MESSAGE = 3;

        String showInputDialog(Component parent, String message);

        int showConfirmDialog(Component parent, String message, String title, int optionType);

        int showOptionDialog(Component parent, String message, String title,
                             int optionType, int messageType, String[] options, String initialValue);

        TextInputResult showTextInputDialog(Component parent, String title,
                                            String textLabel, String levelLabel,
                                            Integer[] levels, int defaultLevel);
    }

    public static class TextInputResult
    {
        private final String text;
        private final int level;

        public TextInputResult(String text, int level)
        {
            this.text = text;
            this.level = level;
        }

        public String getText()
        {
            return text;
        }

        public int getLevel()
        {
            return level;
        }
    }

    private static class DefaultDialogService implements DialogService
    {
        @Override
        public String showInputDialog(Component parent, String message)
        {
            return javax.swing.JOptionPane.showInputDialog(parent, message);
        }

        @Override
        public int showConfirmDialog(Component parent, String message, String title, int optionType)
        {
            return javax.swing.JOptionPane.showConfirmDialog(parent, message, title, optionType);
        }

        @Override
        public int showOptionDialog(Component parent, String message, String title,
                                    int optionType, int messageType, String[] options, String initialValue)
        {
            return javax.swing.JOptionPane.showOptionDialog(
                    parent, message, title, optionType, messageType,
                    null, options, initialValue);
        }

        @Override
        public TextInputResult showTextInputDialog(Component parent, String title,
                                                   String textLabel, String levelLabel,
                                                   Integer[] levels, int defaultLevel)
        {
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

            if (result == javax.swing.JOptionPane.OK_OPTION)
            {
                String content = textArea.getText().trim();
                if (!content.isEmpty())
                {
                    int level = (int) levelCombo.getSelectedItem();
                    return new TextInputResult(content, level);
                }
            }
            return null;
        }
    }
}