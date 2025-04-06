package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrevCommandTest {

    @Mock
    private Presentation mockPresentation;

    private PrevCommand prevCommand;

    @BeforeEach
    void setUp() {
        prevCommand = new PrevCommand(mockPresentation);
    }

    @Test
    void constructor_ShouldSetPresentation() {
        prevCommand.execute();
        verify(mockPresentation).prevSlide();
    }

    @Test
    void execute_ShouldCallPrevSlideOnPresentation() {
        prevCommand.execute();

        verify(mockPresentation).prevSlide();
        verifyNoMoreInteractions(mockPresentation);
    }

    @Test
    void execute_ShouldThrowNPEWhenPresentationIsNull() throws Exception {
        PrevCommand commandWithNull = new PrevCommand(mockPresentation);
        var field = PrevCommand.class.getDeclaredField("presentation");
        field.setAccessible(true);
        field.set(commandWithNull, null);

        assertThrows(NullPointerException.class, () -> {
            commandWithNull.execute();
        });
    }

    @Test
    void execute_MultipleCalls_ShouldCallPrevSlideEachTime() {
        prevCommand.execute();
        prevCommand.execute();
        prevCommand.execute();

        verify(mockPresentation, times(3)).prevSlide();
    }

    @Test
    void constructor_ShouldThrowNPEWhenPresentationIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new PrevCommand(null);
        });
    }
}
