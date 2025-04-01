package com.nhlstenden.Controllers;

import com.nhlstenden.Accessors.Accessor;
import com.nhlstenden.Accessors.XMLAccessor;
import com.nhlstenden.Presentation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SaveCommand implements Command {
    private final Presentation presentation;
    private final JFrame parentFrame;
    private final AccessorProvider accessorProvider;
    private final ErrorHandler errorHandler;

    public SaveCommand(Presentation presentation, JFrame parentFrame) {
        this(presentation, parentFrame, XMLAccessor::new, new DefaultErrorHandler());
    }

    SaveCommand(Presentation presentation, JFrame parentFrame,
                AccessorProvider accessorProvider, ErrorHandler errorHandler) {
        this.presentation = presentation;
        this.parentFrame = parentFrame;
        this.accessorProvider = accessorProvider;
        this.errorHandler = errorHandler;
    }

    @Override
    public void execute() {
        try {
            Accessor xmlAccessor = accessorProvider.get();
            xmlAccessor.saveFile(presentation, "dump.xml");
        } catch (Exception exc) {
            errorHandler.handleError(parentFrame, "Save Error", "IO Exception: " + exc);
        }
    }

    @FunctionalInterface
    public interface AccessorProvider {
        Accessor get();
    }

    public interface ErrorHandler {
        void handleError(JFrame parent, String title, String message);
    }

    private static class DefaultErrorHandler implements ErrorHandler {
        @Override
        public void handleError(JFrame parent, String title, String message) {
            if (parent != null && !GraphicsEnvironment.isHeadless()) {
                JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}