package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeyControllerTest {

    @Mock
    private Presentation mockPresentation;

    @Mock
    private KeyEvent mockKeyEvent;

    @Mock
    private KeyController.SystemExitHandler mockExitHandler;

    private KeyController keyController;

    @BeforeEach
    void setUp() {
        keyController = new KeyController(mockPresentation, mockExitHandler);
    }

    // Next slide tests
    @Test
    void keyPressed_PageDown_ShouldCallNextSlide() {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_PAGE_DOWN);
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation).nextSlide();
    }

    @Test
    void keyPressed_DownArrow_ShouldCallNextSlide() {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation).nextSlide();
    }

    @Test
    void keyPressed_Enter_ShouldCallNextSlide() {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation).nextSlide();
    }

    @Test
    void keyPressed_Plus_ShouldCallNextSlide() {
        when(mockKeyEvent.getKeyCode()).thenReturn((int) '+');
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation).nextSlide();
    }

    // Previous slide tests
    @Test
    void keyPressed_PageUp_ShouldCallPrevSlide() {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_PAGE_UP);
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation).prevSlide();
    }

    @Test
    void keyPressed_UpArrow_ShouldCallPrevSlide() {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation).prevSlide();
    }

    @Test
    void keyPressed_Minus_ShouldCallPrevSlide() {
        when(mockKeyEvent.getKeyCode()).thenReturn((int) '-');
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation).prevSlide();
    }

    // Exit tests
    @Test
    void keyPressed_LowercaseQ_ShouldCallExitHandler() {
        when(mockKeyEvent.getKeyCode()).thenReturn((int) 'q');
        keyController.keyPressed(mockKeyEvent);
        verify(mockExitHandler).exit(0);
    }

    @Test
    void keyPressed_UppercaseQ_ShouldCallExitHandler() {
        when(mockKeyEvent.getKeyCode()).thenReturn((int) 'Q');
        keyController.keyPressed(mockKeyEvent);
        verify(mockExitHandler).exit(0);
    }

    // Edge cases
    @Test
    void keyPressed_DefaultCase_ShouldDoNothing() {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
        keyController.keyPressed(mockKeyEvent);
        verifyNoInteractions(mockPresentation, mockExitHandler);
    }

    @Test
    void keyPressed_NullKeyEvent_ShouldDoNothing() {
        keyController.keyPressed(null);
        verifyNoInteractions(mockPresentation, mockExitHandler);
    }

    @Test
    void keyPressed_NullPresentationNextSlide_ShouldNotThrow() {
        KeyController nullController = new KeyController(null, mockExitHandler);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);
        nullController.keyPressed(mockKeyEvent);
        verifyNoInteractions(mockPresentation);
    }

    @Test
    void keyPressed_NullPresentationPrevSlide_ShouldNotThrow() {
        KeyController nullController = new KeyController(null, mockExitHandler);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);
        nullController.keyPressed(mockKeyEvent);
        verifyNoInteractions(mockPresentation);
    }

    @Test
    void keyPressed_NullPresentationWithExit_ShouldStillExit() {
        KeyController nullController = new KeyController(null, mockExitHandler);
        when(mockKeyEvent.getKeyCode()).thenReturn((int) 'Q');
        nullController.keyPressed(mockKeyEvent);
        verify(mockExitHandler).exit(0);
    }
}
