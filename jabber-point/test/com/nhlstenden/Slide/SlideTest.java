package com.nhlstenden.Slide;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SlideTest {
    private Slide slide;
    private Graphics realGraphics;


    @Mock
    private ImageObserver mockObserver;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        slide = new Slide();
    }

    @Test
    public void testSetAndGetTitle() {
        slide.setTitle("Test Title");
        assertEquals("Test Title", slide.getTitle());
    }

    @Test
    public void testAppendSlideItem() {
        SlideItem mockItem = mock(SlideItem.class);
        slide.append(mockItem);
        assertEquals(1, slide.getSize());
    }

    @Test
    public void testAppendUsingFactory() {
        SlideItemFactory mockFactory = mock(SlideItemFactory.class);
        SlideItem mockItem = mock(SlideItem.class);
        when(mockFactory.createSlideItem("text", 1, "Content"))
                .thenReturn(mockItem);

        slide.append("text", 1, "Content");
        assertEquals(1, slide.getSize());
    }

    @Test
    public void testDrawMethod() {
        SlideItem mockItem = mock(SlideItem.class);
        slide.append(mockItem);
        Rectangle testArea = new Rectangle(0, 0, Slide.WIDTH, Slide.HEIGHT);

        slide.draw(realGraphics, testArea, mockObserver);

        verify(mockItem, atLeastOnce()).draw(anyInt(), anyInt(), anyFloat(), eq(realGraphics), any(Style.class), eq(mockObserver));
    }

}
