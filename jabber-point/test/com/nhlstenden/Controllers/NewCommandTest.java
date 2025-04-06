package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerFrame;
import com.nhlstenden.Slide.TextItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewCommandTest {

    private Presentation mockPresentation;
    private Frame mockParentFrame;
    private SlideViewerFrame mockViewerFrame;
    private NewCommand.DialogService mockDialogService;
    private NewCommand.SlideBuilder mockSlideBuilder;
    private NewCommand newCommand;

    @BeforeEach
    void setUp() {
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
    void execute_shouldPerformOperationsInCorrectOrder() {

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
    void clearPresentation_shouldCallPresentationClear() {

        newCommand.clearPresentation();

        verify(mockPresentation).clear();
    }

    @Test
    void setPresentationTitle_shouldSetTitleWhenInputProvided() {

        when(mockDialogService.showInputDialog(any(), anyString()))
                .thenReturn("Test Title");

        newCommand.setPresentationTitle();

        verify(mockPresentation).setTitle("Test Title");
    }

    @Test
    void setPresentationTitle_shouldNotSetTitleWhenInputEmpty() {
        // Arrange
        when(mockDialogService.showInputDialog(any(), anyString()))
                .thenReturn("");

        newCommand.setPresentationTitle();

        verify(mockPresentation, never()).setTitle(anyString());
    }

    @Test
    void buildPresentationSlides_shouldAddSlideWhenBuilt() {

        Slide mockSlide = mock(Slide.class);
        when(mockSlideBuilder.buildSlide(any(), any())).thenReturn(mockSlide);
        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.NO_OPTION);

        newCommand.buildPresentationSlides();

        verify(mockPresentation).append(mockSlide);
    }

    @Test
    void buildPresentationSlides_shouldNotAddSlideWhenNull() {

        when(mockSlideBuilder.buildSlide(any(), any())).thenReturn(null);
        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.NO_OPTION);

        newCommand.buildPresentationSlides();

        verify(mockPresentation, never()).append(any());
    }

    @Test
    void shouldAddAnotherSlide_shouldReturnTrueForYesOption() {

        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.YES_OPTION);

        assertTrue(newCommand.shouldAddAnotherSlide());
    }

    @Test
    void shouldAddAnotherSlide_shouldReturnFalseForNoOption() {

        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.NO_OPTION);

        assertFalse(newCommand.shouldAddAnotherSlide());
    }

    @Test
    void updatePresentationView_shouldUpdateWhenSlidesExist() {

        when(mockPresentation.getSize()).thenReturn(1);

        newCommand.updatePresentationView();

        verify(mockPresentation).setSlideNumber(0);
        verify(mockViewerFrame).updatePresentation(mockPresentation);
    }

    @Test
    void updatePresentationView_shouldNotUpdateWhenNoSlides() {

        when(mockPresentation.getSize()).thenReturn(0);

        newCommand.updatePresentationView();

        verify(mockPresentation, never()).setSlideNumber(anyInt());
        verify(mockViewerFrame, never()).updatePresentation(any());
    }

    @Test
    void textInputResult_shouldStoreValuesCorrectly() {
        String testText = "Test text";
        int testLevel = 2;

        NewCommand.TextInputResult result = new NewCommand.TextInputResult(testText, testLevel);

        assertEquals(testText, result.getText());
        assertEquals(testLevel, result.getLevel());
    }

    @Test
    void defaultConstructor_shouldInitializeWithDefaultImplementations() {
        NewCommand command = new NewCommand(mockPresentation, mockParentFrame, mockViewerFrame);

        assertNotNull(command);
    }

    @Test
    void defaultSlideBuilder_setSlideTitle_shouldSetTitleWhenInputProvided() {
        NewCommand.DefaultSlideBuilder builder = new NewCommand.DefaultSlideBuilder();
        Slide mockSlide = mock(Slide.class);
        when(mockDialogService.showInputDialog(any(), anyString())).thenReturn("Test Slide Title");

        builder.setSlideTitle(mockParentFrame, mockDialogService, mockSlide);

        verify(mockSlide).setTitle("Test Slide Title");
    }

    @Test
    void defaultSlideBuilder_setSlideTitle_shouldNotSetTitleWhenInputEmpty() {
        NewCommand.DefaultSlideBuilder builder = new NewCommand.DefaultSlideBuilder();
        Slide mockSlide = mock(Slide.class);
        when(mockDialogService.showInputDialog(any(), anyString())).thenReturn("");

        builder.setSlideTitle(mockParentFrame, mockDialogService, mockSlide);

        verify(mockSlide, never()).setTitle(anyString());
    }

    @Test
    void defaultSlideBuilder_addTextItemToSlide_shouldAddItemWhenTextProvided() {
        NewCommand.DefaultSlideBuilder builder = new NewCommand.DefaultSlideBuilder();
        Slide mockSlide = mock(Slide.class);
        NewCommand.TextInputResult mockResult = new NewCommand.TextInputResult("Test text", 2);

        when(mockDialogService.showTextInputDialog(any(), anyString(), anyString(), anyString(),
                any(), anyInt())).thenReturn(mockResult);

        builder.addTextItemToSlide(mockParentFrame, mockDialogService, mockSlide);

        verify(mockSlide).append(any(TextItem.class));
    }

    @Test
    void defaultSlideBuilder_addTextItemToSlide_shouldNotAddItemWhenNoText() {
        NewCommand.DefaultSlideBuilder builder = new NewCommand.DefaultSlideBuilder();
        Slide mockSlide = mock(Slide.class);

        when(mockDialogService.showTextInputDialog(any(), anyString(), anyString(), anyString(),
                any(), anyInt())).thenReturn(null);

        builder.addTextItemToSlide(mockParentFrame, mockDialogService, mockSlide);

        verify(mockSlide, never()).append(any());
    }

    @Test
    void defaultSlideBuilder_processSlideItemAddition_shouldReturnTrueForAddTextChoice() {
        NewCommand.DefaultSlideBuilder builder = new NewCommand.DefaultSlideBuilder();
        Slide mockSlide = mock(Slide.class);

        when(mockDialogService.showOptionDialog(any(), anyString(), anyString(), anyInt(),
                anyInt(), any(), anyString())).thenReturn(0);

        boolean result = builder.processSlideItemAddition(mockParentFrame, mockDialogService, mockSlide);

        assertTrue(result);
    }

    @Test
    void defaultSlideBuilder_processSlideItemAddition_shouldReturnFalseForFinishChoice() {
        NewCommand.DefaultSlideBuilder builder = new NewCommand.DefaultSlideBuilder();
        Slide mockSlide = mock(Slide.class);

        when(mockDialogService.showOptionDialog(any(), anyString(), anyString(), anyInt(),
                anyInt(), any(), anyString())).thenReturn(1);

        boolean result = builder.processSlideItemAddition(mockParentFrame, mockDialogService, mockSlide);

        assertFalse(result);
    }

    @Test
    void defaultDialogService_showInputDialog_shouldReturnInput() {
        NewCommand.DefaultDialogService dialogService = new NewCommand.DefaultDialogService();
        String expected = "Test input";

        try (MockedStatic<javax.swing.JOptionPane> mocked = mockStatic(javax.swing.JOptionPane.class)) {
            mocked.when(() -> javax.swing.JOptionPane.showInputDialog(any(), anyString()))
                    .thenReturn(expected);

            String result = dialogService.showInputDialog(mockParentFrame, "Test");
            assertEquals(expected, result);
        }
    }

    @Test
    void defaultDialogService_showConfirmDialog_shouldReturnOption() {
        NewCommand.DefaultDialogService dialogService = new NewCommand.DefaultDialogService();
        int expected = NewCommand.YES_OPTION;

        try (MockedStatic<javax.swing.JOptionPane> mocked = mockStatic(javax.swing.JOptionPane.class)) {
            mocked.when(() -> javax.swing.JOptionPane.showConfirmDialog(any(), anyString(), anyString(), anyInt()))
                    .thenReturn(expected);

            int result = dialogService.showConfirmDialog(mockParentFrame, "Test", "Title", NewCommand.YES_NO_OPTION);
            assertEquals(expected, result);
        }
    }

    @Test
    void defaultDialogService_showOptionDialog_shouldReturnOption() {
        NewCommand.DefaultDialogService dialogService = new NewCommand.DefaultDialogService();
        int expected = 1;

        try (MockedStatic<javax.swing.JOptionPane> mocked = mockStatic(javax.swing.JOptionPane.class)) {
            mocked.when(() -> javax.swing.JOptionPane.showOptionDialog(any(), anyString(), anyString(),
                            anyInt(), anyInt(), any(), any(), any()))
                    .thenReturn(expected);

            int result = dialogService.showOptionDialog(mockParentFrame, "Test", "Title",
                    NewCommand.YES_NO_OPTION, NewCommand.QUESTION_MESSAGE, new String[]{"A", "B"}, "A");
            assertEquals(expected, result);
        }
    }

    @Test
    void defaultDialogService_showTextInputDialog_shouldReturnNullWhenCancel() {
        NewCommand.DefaultDialogService dialogService = new NewCommand.DefaultDialogService();

        try (MockedStatic<JOptionPane> mocked = mockStatic(javax.swing.JOptionPane.class)) {
            mocked.when(() -> javax.swing.JOptionPane.showConfirmDialog(any(), any(), anyString(),
                    anyInt(), anyInt())).thenReturn(javax.swing.JOptionPane.CANCEL_OPTION);

            NewCommand.TextInputResult result = dialogService.showTextInputDialog(
                    mockParentFrame, "Title", "Text:", "Level:", new Integer[]{0, 1, 2, 3, 4}, 1);

            assertNull(result);
        }
    }

    @Test
    void execute_shouldHandleNullViewerFrame() {
        NewCommand command = new NewCommand(
                mockPresentation,
                mockParentFrame,
                null,
                mockDialogService,
                mockSlideBuilder
        );

        Slide mockSlide = mock(Slide.class);
        when(mockDialogService.showInputDialog(any(), anyString())).thenReturn("Test Title");
        when(mockSlideBuilder.buildSlide(any(), any())).thenReturn(mockSlide);
        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.NO_OPTION);
        when(mockPresentation.getSize()).thenReturn(1);

        command.execute();

        verify(mockPresentation).setSlideNumber(0);
    }

    @Test
    void buildPresentationSlides_shouldContinueAddingUntilNoOptionSelected() {
        Slide mockSlide = mock(Slide.class);
        when(mockSlideBuilder.buildSlide(any(), any())).thenReturn(mockSlide);
        when(mockDialogService.showConfirmDialog(any(), any(), any(), anyInt()))
                .thenReturn(NewCommand.YES_OPTION)  // First time continue
                .thenReturn(NewCommand.YES_OPTION)  // Second time continue
                .thenReturn(NewCommand.NO_OPTION);  // Third time stop

        newCommand.buildPresentationSlides();

        verify(mockPresentation, times(3)).append(mockSlide);
        verify(mockDialogService, times(3)).showConfirmDialog(any(), any(), any(), anyInt());
    }
}