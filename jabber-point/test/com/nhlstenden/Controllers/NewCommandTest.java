package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewCommandTest
{

    private Presentation mockPresentation;
    private Frame mockParentFrame;
    private SlideViewerFrame mockViewerFrame;
    private NewCommand.DialogService mockDialogService;
    private NewCommand.SlideBuilder mockSlideBuilder;
    private NewCommand newCommand;

    @BeforeEach
    void setUp()
    {
        mockPresentation = mock(Presentation.class);
        mockParentFrame = mock(Frame.class);
        mockViewerFrame = mock(SlideViewerFrame.class);
        mockDialogService = mock(NewCommand.DialogService.class);
        mockSlideBuilder = mock(NewCommand.SlideBuilder.class);

        newCommand = new NewCommand(
                mockPresentation,
                mockParentFrame,
                mockViewerFrame,
                mockDialogService,
                mockSlideBuilder
        );
    }

    @Test
    void execute_shouldPerformOperationsInCorrectOrder()
    {

        Slide mockSlide = mock(Slide.class);
        when(mockDialogService.showInputDialog(any(), anyString())).thenReturn("Test Title");
        when(mockSlideBuilder.buildSlide(any(), any())).thenReturn(mockSlide);
        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.NO_OPTION);
        when(mockPresentation.getSize()).thenReturn(1);

        newCommand.execute();

        InOrder inOrder = inOrder(mockPresentation, mockSlideBuilder, mockViewerFrame);
        inOrder.verify(mockPresentation).clear();
        inOrder.verify(mockPresentation).setTitle("Test Title");
        inOrder.verify(mockSlideBuilder).buildSlide(any(), any());
        inOrder.verify(mockPresentation).append(mockSlide);
        inOrder.verify(mockPresentation).setSlideNumber(0);
        inOrder.verify(mockViewerFrame).updatePresentation(mockPresentation);
    }

    @Test
    void clearPresentation_shouldCallPresentationClear()
    {

        newCommand.clearPresentation();

        verify(mockPresentation).clear();
    }

    @Test
    void setPresentationTitle_shouldSetTitleWhenInputProvided()
    {

        when(mockDialogService.showInputDialog(any(), anyString()))
                .thenReturn("Test Title");

        newCommand.setPresentationTitle();

        verify(mockPresentation).setTitle("Test Title");
    }

    @Test
    void setPresentationTitle_shouldNotSetTitleWhenInputEmpty()
    {
        // Arrange
        when(mockDialogService.showInputDialog(any(), anyString()))
                .thenReturn("");

        newCommand.setPresentationTitle();

        verify(mockPresentation, never()).setTitle(anyString());
    }

    @Test
    void buildPresentationSlides_shouldAddSlideWhenBuilt()
    {

        Slide mockSlide = mock(Slide.class);
        when(mockSlideBuilder.buildSlide(any(), any())).thenReturn(mockSlide);
        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.NO_OPTION);

        newCommand.buildPresentationSlides();

        verify(mockPresentation).append(mockSlide);
    }

    @Test
    void buildPresentationSlides_shouldNotAddSlideWhenNull()
    {

        when(mockSlideBuilder.buildSlide(any(), any())).thenReturn(null);
        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.NO_OPTION);

        newCommand.buildPresentationSlides();

        verify(mockPresentation, never()).append(any());
    }

    @Test
    void shouldAddAnotherSlide_shouldReturnTrueForYesOption()
    {

        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.YES_OPTION);

        assertTrue(newCommand.shouldAddAnotherSlide());
    }

    @Test
    void shouldAddAnotherSlide_shouldReturnFalseForNoOption()
    {

        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.NO_OPTION);

        assertFalse(newCommand.shouldAddAnotherSlide());
    }

    @Test
    void updatePresentationView_shouldUpdateWhenSlidesExist()
    {

        when(mockPresentation.getSize()).thenReturn(1);

        newCommand.updatePresentationView();

        verify(mockPresentation).setSlideNumber(0);
        verify(mockViewerFrame).updatePresentation(mockPresentation);
    }

    @Test
    void updatePresentationView_shouldNotUpdateWhenNoSlides()
    {

        when(mockPresentation.getSize()).thenReturn(0);

        newCommand.updatePresentationView();

        verify(mockPresentation, never()).setSlideNumber(anyInt());
        verify(mockViewerFrame, never()).updatePresentation(any());
    }

    @Test
    void textInputResult_shouldStoreValuesCorrectly()
    {
        String testText = "Test text";
        int testLevel = 2;

        NewCommand.TextInputResult result = new NewCommand.TextInputResult(testText, testLevel);

        assertEquals(testText, result.getText());
        assertEquals(testLevel, result.getLevel());
    }

    @Test
    void defaultConstructor_shouldInitializeWithDefaultImplementations()
    {
        NewCommand command = new NewCommand(mockPresentation, mockParentFrame, mockViewerFrame);

        assertNotNull(command);
    }
}