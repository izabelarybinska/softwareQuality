/**
 * Provides classes for handling the presentation's data access and demo content.
 */
package com.nhlstenden.Accessors;

import com.nhlstenden.Presentation;

import java.io.IOException;


public abstract class Accessor {
    public static final String DEMO_NAME = "Demonstration presentation";
    public static final String DEFAULT_EXTENSION = ".xml";

    public static Accessor getDemoAccessor() {
        return new DemoPresentation();
    }

    public Accessor() {
    }

    abstract public void loadFile(Presentation presentation, String firstName) throws IOException;

    abstract public void saveFile(Presentation presentation, String firstName) throws IOException;
}