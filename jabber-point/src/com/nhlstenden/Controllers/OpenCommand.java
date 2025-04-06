package com.nhlstenden.Controllers;

import com.nhlstenden.Accessors.Accessor;
import com.nhlstenden.Accessors.XMLAccessor;
import com.nhlstenden.Presentation;

import javax.swing.*;
import java.awt.*;

public class OpenCommand implements Command {
    private final Frame frame;
    private final Presentation presentation;
    private final AccessorProvider accessorProvider;
    private final ErrorHandler errorHandler;

    public OpenCommand(Presentation presentation, Frame frame) {
        this(presentation, frame, XMLAccessor::new, new DefaultErrorHandler());
    }

    OpenCommand(Presentation presentation, Frame frame,
                AccessorProvider accessorProvider, ErrorHandler errorHandler) {
        this.presentation = presentation;
        this.frame = frame;
        this.accessorProvider = accessorProvider;
        this.errorHandler = errorHandler;
    }

    @Override
    public void execute() {
        presentation.clear();

        try {
            Accessor xmlAccessor = accessorProvider.get();
            xmlAccessor.loadFile(presentation, "jabber-point/test.xml");
            presentation.setSlideNumber(0);
        } catch (Exception exc) {
            errorHandler.handleError(frame, "Load Error", "Exception: " + exc);
        }

        if (frame != null) {
            frame.repaint();
        }
    }

    @FunctionalInterface
    public interface AccessorProvider {
        Accessor get();
    }

    public interface ErrorHandler {
        void handleError(Component parent, String title, String message);
    }

    private static class DefaultErrorHandler implements ErrorHandler {
        @Override
        public void handleError(Component parent, String title, String message) {
            if (parent != null && !GraphicsEnvironment.isHeadless()) {
                JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}