package com.nhlstenden.Controllers;

import com.nhlstenden.Accessors.Accessor;
import com.nhlstenden.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaveCommandTest
{

    private Presentation mockPresentation;
    private JFrame mockFrame;
    private SaveCommand.AccessorProvider mockAccessorProvider;
    private SaveCommand.ErrorHandler mockErrorHandler;
    private Accessor mockAccessor;

    @BeforeEach
    void setUp()
    {
        mockPresentation = mock(Presentation.class);
        mockFrame = mock(JFrame.class);
        mockAccessorProvider = mock(SaveCommand.AccessorProvider.class);
        mockErrorHandler = mock(SaveCommand.ErrorHandler.class);
        mockAccessor = mock(Accessor.class);

        when(mockAccessorProvider.get()).thenReturn(mockAccessor);
    }

    @Test
    void execute_ShouldSavePresentation() throws Exception
    {
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
    void execute_ShouldHandleIOException() throws Exception
    {
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
    void execute_ShouldHandleRuntimeException() throws Exception
    {
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
    void execute_ShouldWorkWithNullFrame() throws Exception
    {
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

    @Test
    void defaultErrorHandler_shouldShowMessageDialogInNonHeadlessEnvironment()
    {
        SaveCommand.DefaultErrorHandler errorHandler = new SaveCommand.DefaultErrorHandler();
        JFrame mockFrame = mock(JFrame.class);

        try (MockedStatic<GraphicsEnvironment> geMock = mockStatic(GraphicsEnvironment.class);
             MockedStatic<JOptionPane> jopMock = mockStatic(JOptionPane.class))
        {

            geMock.when(GraphicsEnvironment::isHeadless).thenReturn(false);

            errorHandler.handleError(mockFrame, "Error Title", "Error Message");

            jopMock.verify(() ->
                    JOptionPane.showMessageDialog(
                            eq(mockFrame),
                            eq("Error Message"),
                            eq("Error Title"),
                            eq(JOptionPane.ERROR_MESSAGE)
                    )
            );
        }
    }

    @Test
    void defaultErrorHandler_shouldNotShowDialogInHeadlessEnvironment()
    {
        SaveCommand.DefaultErrorHandler errorHandler = new SaveCommand.DefaultErrorHandler();
        JFrame mockFrame = mock(JFrame.class);

        try (MockedStatic<GraphicsEnvironment> geMock = mockStatic(GraphicsEnvironment.class);
             MockedStatic<JOptionPane> jopMock = mockStatic(JOptionPane.class))
        {

            geMock.when(GraphicsEnvironment::isHeadless).thenReturn(true);

            errorHandler.handleError(mockFrame, "Error Title", "Error Message");

            jopMock.verifyNoInteractions();
        }
    }

    @Test
    void defaultErrorHandler_shouldHandleNullParentFrame()
    {
        SaveCommand.DefaultErrorHandler errorHandler = new SaveCommand.DefaultErrorHandler();

        try (MockedStatic<GraphicsEnvironment> geMock = mockStatic(GraphicsEnvironment.class);
             MockedStatic<JOptionPane> jopMock = mockStatic(JOptionPane.class))
        {

            geMock.when(GraphicsEnvironment::isHeadless).thenReturn(false);

            errorHandler.handleError(null, "Error Title", "Error Message");

            jopMock.verify(() ->
                    JOptionPane.showMessageDialog(
                            isNull(),
                            eq("Error Message"),
                            eq("Error Title"),
                            eq(JOptionPane.ERROR_MESSAGE)
                    )
            );
        }
    }

    @Test
    void defaultConstructor_shouldInitializeWithDefaultImplementations()
    {
        SaveCommand command = new SaveCommand(mockPresentation, mockFrame);

        assertNotNull(command);
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    void accessorProviderFunctionalInterface_shouldWorkWithLambda() throws IOException
    {
        Accessor mockAccessor = mock(Accessor.class);
        SaveCommand.AccessorProvider provider = () -> mockAccessor;

        Accessor result = provider.get();

        assertSame(mockAccessor, result);
    }

    @Test
    void errorHandlerFunctionalInterface_shouldWorkWithLambda()
    {
        SaveCommand.ErrorHandler handler = (parent, title, message) -> {
        };

        assertDoesNotThrow(() -> handler.handleError(mockFrame, "Test", "Test"));
    }

    
}