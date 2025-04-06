package com.nhlstenden.Slide;

import com.nhlstenden.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.swing.*;
import java.awt.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SlideViewerComponentTest {

    @Mock
    private Presentation mockPresentation;
    @Mock
    private Slide mockSlide;
    @Mock
    private SlideViewerFrame mockFrame;
    @Mock
    private Graphics mockGraphics;

    private SlideViewerComponent slideViewerComponent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        slideViewerComponent = new SlideViewerComponent(mockPresentation, mockSlide);
    }

    @Test
    public void testConstructorInitialization() {
        assertNotNull(slideViewerComponent);
        assertEquals(mockPresentation, slideViewerComponent.presentation);
        assertEquals(mockSlide, slideViewerComponent.slide);
    }

    @Test
    public void testSetPresentation() {
        when(mockPresentation.getCurrentSlide()).thenReturn(mockSlide);
        slideViewerComponent.setPresentation(mockPresentation);

        assertEquals(mockPresentation, slideViewerComponent.presentation);
        assertEquals(mockSlide, slideViewerComponent.slide);
    }

    @Test
    public void testGetParentFrame() {
        try (MockedStatic<SwingUtilities> mockedStatic = mockStatic(SwingUtilities.class)) {
            mockedStatic.when(() -> SwingUtilities.getWindowAncestor(slideViewerComponent))
                    .thenReturn(mockFrame);

            SlideViewerFrame parentFrame = slideViewerComponent.getParentFrame();

            assertEquals(mockFrame, parentFrame);
        }
    }

    @Test
    public void testUpdate() {
        Slide anotherMockSlide = mock(Slide.class);
        when(mockPresentation.getSlide(1)).thenReturn(anotherMockSlide);

        slideViewerComponent.update(mockPresentation, 1);

        assertEquals(anotherMockSlide, slideViewerComponent.slide);
    }

    @Test
    public void testPaintComponentWithValidSlide() {
        when(mockPresentation.getSlideNumber()).thenReturn(1);
        when(mockPresentation.getSize()).thenReturn(10);

        slideViewerComponent.paintComponent(mockGraphics);

        verify(mockGraphics).setColor(Color.WHITE);
        verify(mockGraphics).fillRect(0, 0, slideViewerComponent.getSize().width, slideViewerComponent.getSize().height);

        verify(mockGraphics).setColor(Color.BLACK);
        verify(mockGraphics).setFont(new Font("Arial", Font.PLAIN, 18));
        verify(mockGraphics).drawString("com.nhlstenden.Slide.Slide 2 of 10", 20, 20);
    }

    @Test
    public void testPaintComponentWhenNoSlide() {
        when(mockPresentation.getSlideNumber()).thenReturn(-1);
        slideViewerComponent.paintComponent(mockGraphics);

        verify(mockGraphics, never()).setColor(any());
        verify(mockGraphics, never()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
    }

    //smth is broken
    @Test
    public void testPaintComponentWhenSlideIsNull() {
        slideViewerComponent = new SlideViewerComponent(mockPresentation, null);
        slideViewerComponent.paintComponent(mockGraphics);

        verify(mockGraphics, never()).setColor(any());
        verify(mockGraphics, never()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
    }
}
