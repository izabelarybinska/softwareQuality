package com.nhlstenden.Slide;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StyleTest {

    @BeforeEach
    void setUp() {
        Style.createStyles();
    }

    @Test
    void testStyleInitializationLevel0() {
        Style style = Style.styles[0];
        assertNotNull(style);
        assertEquals(0, style.indent);
        assertEquals(Color.red, style.color);
        assertEquals(48, style.fontSize);
        assertEquals(20, style.leading);
        assertEquals("Helvetica", style.font.getName());
    }

    @Test
    void testStyleInitializationLevel1() {
        Style style = Style.styles[1];
        assertNotNull(style);
        assertEquals(20, style.indent);
        assertEquals(Color.blue, style.color);
        assertEquals(40, style.fontSize);
        assertEquals(10, style.leading);
        assertEquals("Helvetica", style.font.getName());
    }

    @Test
    void testStyleInitializationLevel2() {
        Style style = Style.styles[2];
        assertNotNull(style);
        assertEquals(50, style.indent);
        assertEquals(Color.black, style.color);
        assertEquals(36, style.fontSize);
        assertEquals(10, style.leading);
        assertEquals("Helvetica", style.font.getName());
    }

    @Test
    void testStyleInitializationLevel3() {
        Style style = Style.styles[3];
        assertNotNull(style);
        assertEquals(70, style.indent);
        assertEquals(Color.black, style.color);
        assertEquals(30, style.fontSize);
        assertEquals(10, style.leading);
        assertEquals("Helvetica", style.font.getName());
    }

    @Test
    void testStyleInitializationLevel4() {
        Style style = Style.styles[4];
        assertNotNull(style);
        assertEquals(90, style.indent);
        assertEquals(Color.black, style.color);
        assertEquals(24, style.fontSize);
        assertEquals(10, style.leading);
        assertEquals("Helvetica", style.font.getName());
    }

    @Test
    void testGetStyleOutOfBounds() {
        Style style = Style.getStyle(10);
        assertNotNull(style);
        assertEquals(90, style.indent); // Should return the last defined style
        assertEquals(Color.black, style.color);
        assertEquals(24, style.fontSize);
        assertEquals(10, style.leading);
    }

    @Test
    void testToStringMethod() {
        Style style = Style.getStyle(2);
        String expected = "[50,java.awt.Color[r=0,g=0,b=0]; 36 on 10]";
        assertEquals(expected, style.toString());
    }

    @Test
    void testGetFontWithScaling() {
        Style style = Style.getStyle(2);
        Font scaledFont = style.getFont(1.5f);
        assertEquals(36 * 1.5f, scaledFont.getSize());
        assertEquals("Helvetica", scaledFont.getName());
    }

}
