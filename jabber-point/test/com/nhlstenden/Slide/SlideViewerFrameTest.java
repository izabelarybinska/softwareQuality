package com.nhlstenden.Slide;

import com.nhlstenden.Controllers.KeyController;
import com.nhlstenden.Menu.MainMenu;
import com.nhlstenden.Presentation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SlideViewerFrameTest {

    @Mock
    private Presentation mockPresentation;

    @Mock
    private SlideViewerComponent mockSlideViewerComponent;

    @Mock
    private MainMenu mockMainMenu;

    @Mock
    private KeyController mockKeyController;

    private SlideViewerFrame slideViewerFrame;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockPresentation.getCurrentSlide()).thenReturn(mock(Slide.class));
        when(mockPresentation.getSize()).thenReturn(10);

        slideViewerFrame = new SlideViewerFrame("Test Frame", mockPresentation);
    }

    @Test
    public void testWindowSetup() {
        assertNotNull(slideViewerFrame.getContentPane().getComponent(0));

        assertEquals("Jabberpoint 1.6 - OU", slideViewerFrame.getTitle());

        WindowAdapter windowListener = (WindowAdapter) slideViewerFrame.getWindowListeners()[0];
        assertNotNull(windowListener);
    }

    @Test
    public void testSetPresentation() {
        Presentation newPresentation = mock(Presentation.class);

        slideViewerFrame.setPresentation(newPresentation);

        verify(mockSlideViewerComponent).update(newPresentation, 0);
    }

    @Test
    public void testUpdatePresentation() {
        Presentation newPresentation = mock(Presentation.class);

        slideViewerFrame.updatePresentation(newPresentation);

        verify(mockSlideViewerComponent).update(newPresentation, 0);
    }

    @Test
    public void testSetupKeyListener() {
        slideViewerFrame.addKeyListener(mockKeyController);

        assertTrue(slideViewerFrame.isFocusable());
    }


    //just stops, unsure how to verify
    @Test
    public void testWindowClosing() {
        WindowEvent mockWindowEvent = mock(WindowEvent.class);

        WindowAdapter windowListener = (WindowAdapter) slideViewerFrame.getWindowListeners()[0];
        windowListener.windowClosing(mockWindowEvent);

        verify(mockWindowEvent, never());
    }

    @Test
    public void testAddMenuBar() {
        assertTrue(slideViewerFrame.getMenuBar() instanceof MainMenu);
        MainMenu menuBar = (MainMenu) slideViewerFrame.getMenuBar();
    }
}
