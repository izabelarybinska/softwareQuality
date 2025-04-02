package com.nhlstenden.Slide;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.lang.reflect.Method;
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
    public void testTitleGetterAndSetter() {
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
    public void testAppendTextItem() {
        slide.append("text", 1, "Test message");
        assertEquals(1, slide.getSize());

        SlideItem item = slide.getSlideItem(0);
        assertTrue(item instanceof TextItem);
        assertEquals(1, item.getLevel());
        assertTrue(((TextItem) item).getText().contains("Test"));
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

    @Test
    public void testGetSlideItemsReturnsVector() {
        slide.append("text", 1, "Test message");
        slide.append("image", 3, "Test message2");

        Vector<SlideItem> items = slide.getSlideItems();
        assertEquals(2, items.size());
    }

    @Test
    public void testGetScaleReflection() throws Exception {
        Rectangle area = new Rectangle(0, 0, 1200, 800);
        Method m = Slide.class.getDeclaredMethod("getScale", Rectangle.class);
        m.setAccessible(true);

        float scale = (float) m.invoke(slide, area);
        assertEquals(1.0f, scale, 0.01f);
    }

    @Test
    public void testAppendNullSlideItem() {
        Slide slide = new Slide();
        assertDoesNotThrow(() -> slide.append(null));
        assertEquals(1, slide.getSize());
        assertNull(slide.getSlideItem(0));
    }

    @Test
    public void testAppendTextWithNullText() {
        Slide slide = new Slide();
        assertDoesNotThrow(() -> slide.append("text", 1, null));
        SlideItem item = slide.getSlideItem(0);
        assertTrue(item instanceof TextItem);
        assertEquals("", ((TextItem) item).getText());
    }

    @Test
    public void testAppendWithNegativeLevel() {
        Slide slide = new Slide();
        slide.append("text", -1, "Test message");
        SlideItem item = slide.getSlideItem(0);
        assertEquals(-1, item.getLevel());
    }

    @Test
    public void testSetTitleToNull() {
        Slide slide = new Slide();
        assertDoesNotThrow(() -> slide.setTitle(null));
        assertNull(slide.getTitle());
    }

    @Test
    public void testDrawWithNullObserverDoesNotThrow() {
        Style.createStyles();
        Slide slide = new Slide();
        slide.setTitle("Draw me");
        slide.append("text", 1, "Test message");

        Graphics g = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB).getGraphics();
        Rectangle area = new Rectangle(0, 0, 800, 600);
        assertDoesNotThrow(() -> slide.draw(g, area, null));
    }

    @Test
    public void testDrawWithNoItemsDoesNotThrow() {
        Style.createStyles();
        Slide slide = new Slide();
        slide.setTitle("Empty slide");
        Graphics g = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB).getGraphics();
        Rectangle area = new Rectangle(0, 0, 800, 600);
        assertDoesNotThrow(() -> slide.draw(g, area, (img, f, x, y, w, h) -> true));
    }

    @Test
    public void testScaleSmallArea() throws Exception {
        Slide slide = new Slide();
        Method m = Slide.class.getDeclaredMethod("getScale", Rectangle.class);
        m.setAccessible(true);
        Rectangle smallArea = new Rectangle(0, 0, 10, 10);
        float result = (float) m.invoke(slide, smallArea);
        assertTrue(result > 0);
    }

    @Test
    public void testAppendInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            slide.append("type", 1, "Test message");
        });
    }


}
