package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class NewCommandTest
{
    private Presentation mockPresentation;
    private Frame mockParentFrame;
    private SlideViewerFrame mockViewerFrame;
    private NewCommand.DialogService mockDialogService;
    private NewCommand newCommand;

    @BeforeEach
    public void setUp()
    {
        mockPresentation = mock(Presentation.class);
        mockParentFrame = mock(Frame.class);
        mockViewerFrame = mock(SlideViewerFrame.class);
        mockDialogService = mock(NewCommand.DialogService.class);
        newCommand = new NewCommand(mockPresentation, mockParentFrame, mockViewerFrame, mockDialogService);
    }

    @Test
    public void execute_shouldClearPresentationImmediately()
    {
        when(mockDialogService.showInputDialog(any(), anyString())).thenReturn(null);
        newCommand.execute();
        verify(mockPresentation).clear();
    }

    @Test
    public void execute_withCanceledTitle_shouldNotSetTitle()
    {
        when(mockDialogService.showInputDialog(any(), anyString())).thenReturn(null);
        newCommand.execute();
        verify(mockPresentation, never()).setTitle(anyString());
    }

    @Test
    public void execute_withValidTitle_shouldSetTitle()
    {
        when(mockDialogService.showInputDialog(any(), anyString()))
                .thenReturn("My Presentation");
        newCommand.execute();
        verify(mockPresentation).setTitle("My Presentation");
    }

    @Test
    public void execute_withNoSlides_shouldNotUpdateViewer()
    {
        when(mockDialogService.showInputDialog(any(), anyString()))
                .thenReturn("Title")
                .thenReturn(null);
        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.DialogService.NO_OPTION);
        newCommand.execute();
        verify(mockViewerFrame, never()).updatePresentation(any());
    }

    @Test
    void execute_withOneSlide_shouldUpdateViewer()
    {
        when(mockDialogService.showInputDialog(any(), anyString()))
                .thenReturn("Presentation Title")
                .thenReturn("Slide 1");

        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.DialogService.NO_OPTION);

        when(mockDialogService.showOptionDialog(any(), any(), any(), anyInt(), anyInt(), any(), any()))
                .thenReturn(1);

        when(mockPresentation.getSize()).thenReturn(1);

        newCommand.execute();

        verify(mockViewerFrame).updatePresentation(mockPresentation);
    }

    @Test
    void execute_shouldAddMultipleSlidesWhenRequested()
    {
        when(mockDialogService.showInputDialog(any(), anyString()))
                .thenReturn("Presentation")
                .thenReturn("Slide 1")
                .thenReturn("Slide 2");

        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.DialogService.YES_OPTION)
                .thenReturn(NewCommand.DialogService.NO_OPTION);

        when(mockDialogService.showOptionDialog(any(), any(), any(), anyInt(), anyInt(), any(), any()))
                .thenReturn(1);

        newCommand.execute();

        verify(mockPresentation, times(2)).append(any(Slide.class));
    }

    @Test
    void execute_shouldSetSlideNumberToZeroWhenSlidesExist()
    {
        when(mockDialogService.showInputDialog(any(), anyString()))
                .thenReturn("Title")
                .thenReturn("Slide 1");

        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.DialogService.NO_OPTION);

        when(mockDialogService.showOptionDialog(any(), any(), any(), anyInt(), anyInt(), any(), any()))
                .thenReturn(1);

        when(mockPresentation.getSize()).thenReturn(1);

        newCommand.execute();

        verify(mockPresentation).setSlideNumber(0);
    }
}