package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ExitCommandTest {

    @Mock
    private Presentation mockPresentation;

    @Test
    void constructor_ShouldStorePresentation() {
        ExitCommand command = new ExitCommand(mockPresentation);

    }

    @Test
    void execute_ShouldCallExitWithZeroOnPresentation() {
        ExitCommand command = new ExitCommand(mockPresentation);

        command.execute();

        verify(mockPresentation).exit(0);
        verifyNoMoreInteractions(mockPresentation);
    }

    @Test
    void execute_ShouldOnlyCallExitOnce() {
        ExitCommand command = new ExitCommand(mockPresentation);

        command.execute();

        verify(mockPresentation).exit(0);
    }
}
