package com.nhlstenden;

import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class PresentationTest {

    private Presentation presentation;
    private SlideViewerComponent mockViewerComponent;

    @BeforeEach
    void setUp() {
        mockViewerComponent = mock(SlideViewerComponent.class);
        presentation = new Presentation(mockViewerComponent);
    }

    @Test
    void testGetSize() {
        assertEquals(0, presentation.getSize());

        Slide slide1 = mock(Slide.class);
        presentation.append(slide1);

        assertEquals(1, presentation.getSize());
    }

    @Test
    void testGetTitle() {
        String title = "Test Presentation";
        presentation.setTitle(title);
        assertEquals(title, presentation.getTitle());
    }

    @Test
    void testSetTitle() {
        String title = "New Title";
        presentation.setTitle(title);
        assertEquals("New Title", presentation.getTitle());
    }

    @Test
    void testSetShowView() {
        SlideViewerComponent newViewer = mock(SlideViewerComponent.class);
        presentation.setShowView(newViewer);
        assertNotNull(presentation);
    }

    @Test
    void testSetSlideNumber() {
        Slide slide1 = mock(Slide.class);
        Slide slide2 = mock(Slide.class);
        presentation.append(slide1);
        presentation.append(slide2);

        presentation.setSlideNumber(1);
        assertEquals(1, presentation.getSlideNumber());

        verify(mockViewerComponent).update(presentation, 1);
    }

    @Test
    void testPrevSlide() {
        Slide slide1 = mock(Slide.class);
        Slide slide2 = mock(Slide.class);
        presentation.append(slide1);
        presentation.append(slide2);

        presentation.setSlideNumber(1);
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void testNextSlide() {
        Slide slide1 = mock(Slide.class);
        Slide slide2 = mock(Slide.class);
        presentation.append(slide1);
        presentation.append(slide2);

        presentation.setSlideNumber(0);
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    void testClear() {
        Slide slide1 = mock(Slide.class);
        presentation.append(slide1);
        assertEquals(1, presentation.getSize());

        presentation.clear();
        assertEquals(0, presentation.getSize());
    }

    @Test
    void testGetSlide() {
        Slide slide1 = mock(Slide.class);
        presentation.append(slide1);

        assertEquals(slide1, presentation.getSlide(0));
        assertNull(presentation.getSlide(1));
    }

    @Test
    void testGetCurrentSlide() {
        Slide slide1 = mock(Slide.class);
        presentation.append(slide1);

        assertEquals(slide1, presentation.getCurrentSlide());
    }

    //review
    @Test
    void testExit() {
        int exitCode = 0;
    }
}
