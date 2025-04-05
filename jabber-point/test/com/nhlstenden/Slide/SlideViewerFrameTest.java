package com.nhlstenden.Slide;

import com.nhlstenden.Controllers.KeyController;
import com.nhlstenden.Menu.MainMenu;
import com.nhlstenden.Presentation;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;

import java.awt.event.WindowAdapter;


import static org.junit.Assert.*;


    public class SlideViewerFrameTest {

        @Mock
        private Presentation mockPresentation;

        @Mock
        private SlideViewerComponent mockSlideViewerComponent;

        @Mock
        private MainMenu mockMainMenu;


        private SlideViewerFrame slideViewerFrame;


        @Mock
        private KeyController mockKeyController;

        @Before
        public void setUp() throws Exception {
            MockitoAnnotations.openMocks(this);

            when(mockPresentation.getCurrentSlide()).thenReturn(mock(Slide.class));
            when(mockPresentation.getSize()).thenReturn(10);

            slideViewerFrame = new SlideViewerFrame("Test Frame", mockPresentation);

            slideViewerFrame.slideViewerComponent = mockSlideViewerComponent;
        }


        @Test
        public void testWindowSetup() {
            assertNotNull(slideViewerFrame.getContentPane().getComponent(0));

            assertEquals("Jabberpoint 1.6 - OU", slideViewerFrame.getTitle());

            WindowAdapter windowListener = (WindowAdapter) slideViewerFrame.getWindowListeners()[0];
            assertNotNull(windowListener);
        }

        @Test
        public void testSetupKeyListener() {
            slideViewerFrame.addKeyListener(mockKeyController);

            assertTrue(slideViewerFrame.isFocusable());
        }

        @Test
        public void testAddMenuBar() {
            assertTrue(slideViewerFrame.getMenuBar() instanceof MainMenu);
            MainMenu menuBar = (MainMenu) slideViewerFrame.getMenuBar();
        }

        @Test
            public void testSetPresentation() {
                Presentation newPresentation = mock(Presentation.class);
                slideViewerFrame.setPresentation(newPresentation);

                verify(mockSlideViewerComponent).update(newPresentation, 0);
            }

            @Test
            public void testUpdatePresentation() {
                Presentation newPresentation = mock(Presentation.class);
                slideViewerFrame.updatePresentation(newPresentation);


                verify(mockSlideViewerComponent).update(newPresentation, 0);
            }
    }

