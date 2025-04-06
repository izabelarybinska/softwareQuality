package com.nhlstenden;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfSystemProperty(named = "java.awt.headless", matches = "false")
public class JabberPointTest {

    private JabberPoint jabberPoint;
    private Presentation presentation;

    @BeforeEach
    public void setup() {
        presentation = new Presentation();
    }

    @Test
    public void testMainLoadsDemoWhenNoArgs() {
        assertDoesNotThrow(() -> {
            Method mainMethod = JabberPoint.class.getMethod("main", String[].class);
            String[] args = new String[0];

            // Call main with no args (should load demo)
            mainMethod.invoke(null, (Object) args);
        });
    }


    @Test
    public void testMainWithEmptyArgString() throws Exception {
        Method main = JabberPoint.class.getMethod("main", String[].class);
        String[] args = {""};
        assertDoesNotThrow(() -> main.invoke(null, (Object) args));
    }


}
