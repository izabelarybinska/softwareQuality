package com.nhlstenden.Menu;

import com.nhlstenden.AboutBox;
import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainMenuTest {

    private Frame mockParent;
    private Presentation mockPresentation;
    private SlideViewerFrame mockFrame;
    private MainMenu mainMenu;

    @BeforeEach
    void setUp() {
        mockParent = mock(JFrame.class);
        mockPresentation = mock(Presentation.class);
        mockFrame = mock(SlideViewerFrame.class);

        Slide mockSlide = mock(Slide.class);
        when(mockPresentation.getCurrentSlide()).thenReturn(mockSlide);

        mainMenu = new MainMenu((JFrame) mockParent, mockPresentation, mockFrame);
    }

    @Test
    void constructor_initializesMenuStructure() {
        assertNotNull(mainMenu.getFileMenu());
        assertNotNull(mainMenu.getViewMenu());
        assertNotNull(mainMenu.getHelpMenu());
    }

    @Test
    void getters_returnCorrectValues() {
        assertEquals(mockParent, mainMenu.getParent());
        assertEquals(mockPresentation, mainMenu.getPresentation());
    }

    @Test
    void setPresentation_updatesPresentation() {
        Presentation newPresentation = mock(Presentation.class);
        Slide newSlide = mock(Slide.class);
        when(newPresentation.getCurrentSlide()).thenReturn(newSlide);

        mainMenu.setPresentation(newPresentation);

        assertEquals(newPresentation, mainMenu.getPresentation());
    }

    @Test
    void exitApp_callsPresentationExit() {
        mainMenu.exitApp(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Exit"));
        verify(mockPresentation).exit(0);
    }

    @Test
    void nextSlide_callsPresentationNextSlide() {
        mainMenu.nextSlide(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Next"));
        verify(mockPresentation).nextSlide();
    }

    @Test
    void prevSlide_callsPresentationPrevSlide() {
        mainMenu.prevSlide(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Prev"));
        verify(mockPresentation).prevSlide();
    }

    @Test
    void goToSlide_withValidNumber_setsSlideNumber() {
        try (MockedStatic<JOptionPane> mockedJOptionPane = Mockito.mockStatic(JOptionPane.class)) {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString()))
                    .thenReturn("2");

            mainMenu.goToSlide(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "GoTo"));
            verify(mockPresentation).setSlideNumber(1);
        }
    }

    @Test
    void showAboutBox_displaysAboutBox() {
        try (MockedStatic<AboutBox> mockedAboutBox = Mockito.mockStatic(AboutBox.class)) {
            mainMenu.showAboutBox(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "About"));
            mockedAboutBox.verify(() -> AboutBox.show(eq(mockParent)));
        }
    }

    @Test
    void createMenuItem_createsFunctionalMenuItem() {
        ActionListenerSpy spy = new ActionListenerSpy();
        MenuItem item = mainMenu.createMenuItem("Test", spy);

        assertEquals("Test", item.getLabel());
        item.getActionListeners()[0].actionPerformed(
                new ActionEvent(item, ActionEvent.ACTION_PERFORMED, "Test"));

        assertTrue(spy.wasCalled);
    }

    private static class ActionListenerSpy implements java.awt.event.ActionListener {
        public boolean wasCalled = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            wasCalled = true;
        }
    }
}
