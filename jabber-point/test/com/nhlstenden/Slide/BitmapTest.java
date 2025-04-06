package com.nhlstenden.Slide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import static org.junit.jupiter.api.Assertions.*;

public class BitmapTest {
    private BitmapItem bitmapItem;

    @BeforeEach
    public void setup() {
        bitmapItem = new BitmapItem(2, "jabber-point/serclogo_fc.jpg");
        bitmapItem.setBufferedImage(new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB));
    }

    @Test
    public void testBoundingBoxCalculation() {
        Style.createStyles();

        ImageObserver dummyObserver = (img, flags, x, y, w, h) -> true;
        Graphics dummyGraphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
        Style style = Style.getStyle(2);

        Rectangle box = bitmapItem.getBoundingBox(dummyGraphics, dummyObserver, 1.0f, style);
        assertEquals((int) (style.indent * 1.0f), box.x);
        assertEquals(100, box.width); // image width
        assertEquals(style.leading + 50, box.height);
    }

    @Test
    public void testToStringContainsAllFields() {
        String result = bitmapItem.toString();
        assertTrue(result.contains("BitmapItem"));
        assertTrue(result.contains("jabber-point/serclogo_fc.jpg"));
    }

    @Test
    public void testDraw() {
        Style.createStyles();

        BitmapItem item = new BitmapItem(1, "jabber-point/serclogo_fc.jpg");
        item.setBufferedImage(new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB));

        Graphics g = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB).getGraphics();
        Style style = Style.getStyle(1);

        assertDoesNotThrow(() -> item.draw(0, 0, 1.0f, g, style, null));
    }

    @Test
    public void testImageNameCanBeNull() {
        BitmapItem item = new BitmapItem(0, "jabber-point/serclogo_fc.jpg");
        item.setImageName(null);

        assertNull(item.getImageName());
        assertTrue(item.toString().contains("BitmapItem"));
    }

    @Test
    public void testBufferedImageCanBeNull() {
        BitmapItem item = new BitmapItem(0, "jabber-point/serclogo_fc.jpg");
        item.setBufferedImage(null);
        assertNull(item.getBufferedImage());
    }
}