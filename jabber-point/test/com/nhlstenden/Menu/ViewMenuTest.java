package com.nhlstenden.Menu;

import com.nhlstenden.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ViewMenuTest {

    @Mock
    private Frame mockParent;
    @Mock
    private Presentation mockPresentation;
    @Mock
    private MenuItem mockMenuItem;

    private ViewMenu viewMenu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewMenu = new ViewMenu(mockParent, mockPresentation);
    }

    @Test
    void constructor_createsMenuWithCorrectLabel() {
        assertEquals("View", viewMenu.getLabel());
    }

    @Test
    void nextMenuItem_triggersNextSlide() {
        // Simulate menu item click
        ActionEvent nextEvent = new ActionEvent(mockMenuItem, ActionEvent.ACTION_PERFORMED, "Next");
        viewMenu.getItem(0).getActionListeners()[0].actionPerformed(nextEvent);

        verify(mockPresentation).nextSlide();
    }

    @Test
    void prevMenuItem_triggersPrevSlide() {
        // Simulate menu item click
        ActionEvent prevEvent = new ActionEvent(mockMenuItem, ActionEvent.ACTION_PERFORMED, "Prev");
        viewMenu.getItem(1).getActionListeners()[0].actionPerformed(prevEvent);

        verify(mockPresentation).prevSlide();
    }

    @Test
    void gotoMenuItem_withValidNumber_setsSlideNumber() {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            // Mock JOptionPane to return valid number
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString()))
                    .thenReturn("2");

            // Simulate menu item click
            ActionEvent gotoEvent = new ActionEvent(mockMenuItem, ActionEvent.ACTION_PERFORMED, "Go to");
            viewMenu.getItem(2).getActionListeners()[0].actionPerformed(gotoEvent);

            verify(mockPresentation).setSlideNumber(1); // 2-1 = 1
        }
    }

    @Test
    void gotoMenuItem_withNullInput_doesNothing() {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            // Mock JOptionPane to return null (dialog cancelled)
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString()))
                    .thenReturn(null);

            // Simulate menu item click
            ActionEvent gotoEvent = new ActionEvent(mockMenuItem, ActionEvent.ACTION_PERFORMED, "Go to");
            viewMenu.getItem(2).getActionListeners()[0].actionPerformed(gotoEvent);

            // Verify no interactions with presentation
            verifyNoInteractions(mockPresentation);
        }
    }

    @Test
    void createMenuItem_createsFunctionalMenuItem() {
        // Test the helper method directly
        ActionListenerSpy spy = new ActionListenerSpy();
        MenuItem item = viewMenu.createMenuItem("Test", spy);

        assertEquals("Test", item.getLabel());

        // Simulate click
        item.getActionListeners()[0].actionPerformed(
                new ActionEvent(item, ActionEvent.ACTION_PERFORMED, "Test"));

        assertTrue(spy.wasCalled);
    }

    // Helper class to test action listeners
    private static class ActionListenerSpy implements java.awt.event.ActionListener {
        public boolean wasCalled = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            wasCalled = true;
        }
    }
}
