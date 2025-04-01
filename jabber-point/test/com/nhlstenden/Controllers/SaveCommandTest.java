package com.nhlstenden.Controllers;

import com.nhlstenden.Accessors.Accessor;
import com.nhlstenden.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;

import static org.mockito.Mockito.*;

class SaveCommandTest {

    private Presentation mockPresentation;
    private JFrame mockFrame;
    private SaveCommand.AccessorProvider mockAccessorProvider;
    private SaveCommand.ErrorHandler mockErrorHandler;
    private Accessor mockAccessor;

    @BeforeEach
    void setUp() {
        mockPresentation = mock(Presentation.class);
        mockFrame = mock(JFrame.class);
        mockAccessorProvider = mock(SaveCommand.AccessorProvider.class);
        mockErrorHandler = mock(SaveCommand.ErrorHandler.class);
        mockAccessor = mock(Accessor.class);

        when(mockAccessorProvider.get()).thenReturn(mockAccessor);
    }

    @Test
    void execute_ShouldSavePresentation() throws Exception {
        SaveCommand command = new SaveCommand(
                mockPresentation,
                mockFrame,
                mockAccessorProvider,
                mockErrorHandler
        );

        command.execute();

        verify(mockAccessor).saveFile(mockPresentation, "dump.xml");
        verifyNoInteractions(mockErrorHandler);
    }

    @Test
    void execute_ShouldHandleIOException() throws Exception {
        IOException testException = new IOException("Test error");
        doThrow(testException).when(mockAccessor).saveFile(any(), anyString());

        SaveCommand command = new SaveCommand(
                mockPresentation,
                mockFrame,
                mockAccessorProvider,
                mockErrorHandler
        );

        command.execute();

        verify(mockErrorHandler).handleError(
                mockFrame,
                "Save Error",
                "IO Exception: java.io.IOException: Test error"
        );
    }

    @Test
    void execute_ShouldHandleRuntimeException() throws Exception {
        RuntimeException testException = new RuntimeException("Unexpected error");
        doThrow(testException).when(mockAccessor).saveFile(any(), anyString());

        SaveCommand command = new SaveCommand(
                mockPresentation,
                mockFrame,
                mockAccessorProvider,
                mockErrorHandler
        );

        command.execute();

        verify(mockErrorHandler).handleError(
                mockFrame,
                "Save Error",
                "IO Exception: java.lang.RuntimeException: Unexpected error"
        );
    }

    @Test
    void execute_ShouldWorkWithNullFrame() throws Exception {
        SaveCommand command = new SaveCommand(
                mockPresentation,
                null,
                mockAccessorProvider,
                mockErrorHandler
        );

        command.execute();

        verify(mockAccessor).saveFile(mockPresentation, "dump.xml");
        verifyNoInteractions(mockErrorHandler);
    }
}