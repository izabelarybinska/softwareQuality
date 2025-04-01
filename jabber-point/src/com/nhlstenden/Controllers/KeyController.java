package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyController extends KeyAdapter
{
    private final Presentation presentation;
    private final SystemExitHandler exitHandler;

    public KeyController(Presentation presentation)
    {
        this(presentation, System::exit);
    }

    // Package-private for testing
    KeyController(Presentation presentation, SystemExitHandler exitHandler)
    {
        this.presentation = presentation;
        this.exitHandler = exitHandler;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        if (keyEvent == null) return;

        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_ENTER:
            case '+':
                if (presentation != null) presentation.nextSlide();
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case '-':
                if (presentation != null) presentation.prevSlide();
                break;
            case 'q':
            case 'Q':
                exitHandler.exit(0);
                break;
            default:
                break;
        }
    }

    @FunctionalInterface
    public interface SystemExitHandler
    {
        void exit(int status);
    }
}