package com.nhlstenden.Accessors;

import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideItem;
import com.nhlstenden.Slide.TextItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class AccessorTest
{
    private DemoPresentation demoPresentation;
    private Presentation presentation;

    @BeforeEach
    void setUp()
    {
        demoPresentation = new DemoPresentation();
        presentation = new Presentation();
    }

    @Test
    void getDemoAccessor_shouldReturnDemoPresentationInstance()
    {
        Accessor accessor = Accessor.getDemoAccessor();
        assertNotNull(accessor);
        assertTrue(accessor instanceof DemoPresentation);
    }

    @Test
    void constants_shouldHaveCorrectValues()
    {
        assertEquals("Demonstration presentation", Accessor.DEMO_NAME);
        assertEquals(".xml", Accessor.DEFAULT_EXTENSION);
    }

    @Test
    void demoPresentation_loadFile_shouldCreatePresentationWithCorrectTitle()
    {
        demoPresentation.loadFile(presentation, "anyname.xml");
        assertEquals("Demo com.nhlstenden.Presentation", presentation.getTitle());
    }

    @Test
    void demoPresentation_loadFile_shouldCreateThreeSlides()
    {
        demoPresentation.loadFile(presentation, "anyname.xml");
        assertEquals(3, presentation.getSize());
    }

    @Test
    void demoPresentation_loadFile_firstSlideShouldHaveCorrectContent()
    {
        demoPresentation.loadFile(presentation, "anyname.xml");
        Slide firstSlide = presentation.getSlide(0);

        assertEquals("com.nhlstenden.JabberPoint", firstSlide.getTitle());
        Vector<SlideItem> items = firstSlide.getSlideItems();

        TextItem firstItem = (TextItem) items.get(0);
        assertEquals("The Java com.nhlstenden.Presentation Tool", firstItem.getText());
        assertEquals(1, firstItem.getLevel());

        TextItem navItem = (TextItem) items.get(6);
        assertEquals("Navigate:", navItem.getText());
        assertEquals(1, navItem.getLevel());
    }

    @Test
    void demoPresentation_loadFile_secondSlideShouldDemonstrateLevels()
    {
        demoPresentation.loadFile(presentation, "anyname.xml");
        Slide secondSlide = presentation.getSlide(1);

        assertEquals("Demonstration of levels and styles", secondSlide.getTitle());
        Vector<SlideItem> items = secondSlide.getSlideItems();

        assertEquals(1, ((TextItem) items.get(0)).getLevel());
        assertEquals(2, ((TextItem) items.get(1)).getLevel());
        assertEquals(3, ((TextItem) items.get(5)).getLevel());
    }

    @Test
    void demoPresentation_loadFile_thirdSlideShouldContainImage()
    {
        demoPresentation.loadFile(presentation, "anyname.xml");
        Slide thirdSlide = presentation.getSlide(2);

        assertEquals("The third slide", thirdSlide.getTitle());
        Vector<SlideItem> items = thirdSlide.getSlideItems();

        boolean hasImage = items.stream()
                .anyMatch(item -> item instanceof com.nhlstenden.Slide.BitmapItem);
        assertTrue(hasImage, "Third slide should contain an image");
    }

    @Test
    void demoPresentation_loadFile_shouldIgnoreFilenameParameter()
    {
        assertDoesNotThrow(() -> demoPresentation.loadFile(new Presentation(), null));
        assertDoesNotThrow(() -> demoPresentation.loadFile(new Presentation(), ""));
        assertDoesNotThrow(() -> demoPresentation.loadFile(new Presentation(), "nonexistent.xml"));
    }

    @Test
    void demoPresentation_saveFile_shouldAlwaysThrowIllegalStateException()
    {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> demoPresentation.saveFile(presentation, "jabber-point/test.xml"));

        assertEquals("Save As->Demo! called", exception.getMessage());

        assertThrows(IllegalStateException.class,
                () -> demoPresentation.saveFile(null, "valid.xml"));
        assertThrows(IllegalStateException.class,
                () -> demoPresentation.saveFile(presentation, null));
        assertThrows(IllegalStateException.class,
                () -> demoPresentation.saveFile(null, null));
    }
}