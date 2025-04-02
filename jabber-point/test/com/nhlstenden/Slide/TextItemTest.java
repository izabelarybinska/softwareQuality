package com.nhlstenden.Slide;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.ImageObserver;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TextItemTest {

    @InjectMocks
    private TextItem textItem;

    @Mock
    private Graphics mockGraphics;

    @Mock
    private ImageObserver mockObserver;

    @Mock
    private Style mockStyle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockStyle.indent = 10;
        mockStyle.leading = 5;
        mockStyle.color = Color.BLACK;
    }

    @Test
    void testConstructorWithValidText() {
        TextItem item = new TextItem(1, "Test Text");
        assertEquals("Test Text", item.getText());
        assertEquals(1, item.getLevel());
    }

    @Test
    void testConstructorWithNullText() {
        TextItem item = new TextItem(1, null);
        assertEquals("No Text Given", item.getText());
    }

    @Test
    void testConstructorWithEmptyText() {
        TextItem item = new TextItem(1, "");
        assertEquals("No Text Given", item.getText());
    }

    @Test
    void testSetTextWithValidText() {
        textItem.setText("New Text");
        assertEquals("New Text", textItem.getText());
    }

    @Test
    void testSetTextWithNullText() {
        textItem.setText(null);
        assertEquals("No Text Given", textItem.getText());
    }

    @Test
    void testSetTextWithEmptyText() {
        textItem.setText("   ");
        assertEquals("No Text Given", textItem.getText());
    }

    @Test
    void testGetBoundingBox() {
        when(mockStyle.getFont(anyFloat())).thenReturn(new Font("Arial", Font.PLAIN, 12));
        Rectangle boundingBox = textItem.getBoundingBox(mockGraphics, mockObserver, 1.0f, mockStyle);
        assertNotNull(boundingBox);
    }

    @Test
    void testDrawWithValidText() {
        textItem.setText("Sample Text");
        doNothing().when(mockGraphics).setColor(any(Color.class));
        textItem.draw(10, 10, 1.0f, mockGraphics, mockStyle, mockObserver);
        assertNotNull(textItem.getText());
    }

    @Test
    void testDrawWithEmptyText() {
        textItem.setText("");
        textItem.draw(10, 10, 1.0f, mockGraphics, mockStyle, mockObserver);
        assertEquals("No Text Given", textItem.getText());
    }

    @Test
    void testToString() {
        TextItem item = new TextItem(2, "Hello World");
        assertEquals("com.nhlstenden.Slide.TextItem[2,Hello World]", item.toString());
    }
}
