package com.nhlstenden.Controllers;

import com.nhlstenden.Accessors.Accessor;
import com.nhlstenden.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;

import static org.mockito.Mockito.*;

class OpenCommandTest {

    private Presentation mockPresentation;
    private Frame mockFrame;
    private OpenCommand.AccessorProvider mockAccessorProvider;
    private OpenCommand.ErrorHandler mockErrorHandler;
    private Accessor mockAccessor;

    @BeforeEach
    void setUp() {
        mockPresentation = mock(Presentation.class);
        mockFrame = mock(Frame.class);
        mockAccessorProvider = mock(OpenCommand.AccessorProvider.class);
        mockErrorHandler = mock(OpenCommand.ErrorHandler.class);
        mockAccessor = mock(Accessor.class);

        when(mockAccessorProvider.get()).thenReturn(mockAccessor);
    }

    @Test
    void execute_ShouldClearPresentationAndLoadFile() throws Exception {
        OpenCommand command = new OpenCommand(
                mockPresentation,
                mockFrame,
                mockAccessorProvider,
                mockErrorHandler
        );

        command.execute();

        verify(mockPresentation).clear();
        verify(mockAccessor).loadFile(mockPresentation, "jabber-point/test.xml");
        verify(mockPresentation).setSlideNumber(0);
        verify(mockFrame).repaint();
        verifyNoInteractions(mockErrorHandler);
    }

    @Test
    void execute_ShouldThrowIOException() throws Exception {
        IOException testException = new IOException("Test error");
        doThrow(testException).when(mockAccessor).loadFile(any(), anyString());

        OpenCommand command = new OpenCommand(
                mockPresentation,
                mockFrame,
                mockAccessorProvider,
                mockErrorHandler
        );

        command.execute();

        verify(mockPresentation).clear();
        verify(mockFrame).repaint();
        verify(mockErrorHandler).handleError(
                mockFrame,
                "Load Error",
                "Exception: java.io.IOException: Test error"
        );
    }

    @Test
    void execute_ShouldThrowRuntimeException() throws Exception {
        RuntimeException testException = new RuntimeException("Unexpected error");
        doThrow(testException).when(mockAccessor).loadFile(any(), anyString());

        OpenCommand command = new OpenCommand(
                mockPresentation,
                mockFrame,
                mockAccessorProvider,
                mockErrorHandler
        );

        command.execute();

        verify(mockPresentation).clear();
        verify(mockFrame).repaint();
        verify(mockErrorHandler).handleError(
                mockFrame,
                "Load Error",
                "Exception: java.lang.RuntimeException: Unexpected error"
        );
    }

    @Test
    void execute_ShouldWorkWithNullFrame() throws Exception {
        OpenCommand command = new OpenCommand(
                mockPresentation,
                null,
                mockAccessorProvider,
                mockErrorHandler
        );

        command.execute();

        verify(mockPresentation).clear();
        verify(mockAccessor).loadFile(mockPresentation, "jabber-point/test.xml");
        verify(mockPresentation).setSlideNumber(0);
        verifyNoInteractions(mockErrorHandler);
    }
}
