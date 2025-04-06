package com.nhlstenden;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.*;

import java.awt.*;

import static org.mockito.Mockito.*;

class AboutBoxTest {

    @Test
    void testShow() {
        Frame mockFrame = mock(Frame.class);

        try (MockedStatic<JOptionPane> mockedJOptionPane = Mockito.mockStatic(JOptionPane.class)) {
            AboutBox.show(mockFrame);

            mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(
                    eq(mockFrame),
                    anyString(),
                    eq("About com.nhlstenden.JabberPoint"),
                    eq(JOptionPane.INFORMATION_MESSAGE)
            ));
        }
    }
}
