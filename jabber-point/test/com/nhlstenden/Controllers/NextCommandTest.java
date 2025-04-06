package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NextCommandTest {

    @Mock
    private Presentation mockPresentation;

    private NextCommand nextCommand;

    @BeforeEach
    void setUp() {
        nextCommand = new NextCommand(mockPresentation);
    }

    @Test
    void constructor_ShouldThrowNPEWhenPresentationIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new NextCommand(null);
        });
    }

    @Test
    void execute_ShouldCallNextSlideOnPresentation() {
        nextCommand.execute();
        verify(mockPresentation).nextSlide();
        verifyNoMoreInteractions(mockPresentation);
    }

    @Test
    void execute_ShouldThrowNPEWhenPresentationIsNull() throws Exception {
        NextCommand command = new NextCommand(mockPresentation);
        Field field = NextCommand.class.getDeclaredField("presentation");
        field.setAccessible(true);
        field.set(command, null);

        assertThrows(NullPointerException.class, () -> {
            command.execute();
        });
    }

    @Test
    void execute_MultipleCalls_ShouldCallNextSlideEachTime() {
        nextCommand.execute();
        nextCommand.execute();
        nextCommand.execute();
        verify(mockPresentation, times(3)).nextSlide();
    }
}