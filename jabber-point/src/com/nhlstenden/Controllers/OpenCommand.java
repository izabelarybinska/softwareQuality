package com.nhlstenden.Controllers;

import com.nhlstenden.Accessors.Accessor;
import com.nhlstenden.Accessors.XMLAccessor;
import com.nhlstenden.Controllers.Command;
import com.nhlstenden.Presentation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class OpenCommand implements Command
{
    private final Frame frame;
    private Presentation presentation;

    public OpenCommand(Presentation presentation, Frame frame)
    {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute()
    {
        presentation.clear();

        Accessor xmlAccessor = new XMLAccessor();

        try
        {
            xmlAccessor.loadFile(presentation, "jabber-point/test.xml");
            presentation.setSlideNumber(0);
        } catch (IOException exc)
        {
            JOptionPane.showMessageDialog(frame, "IO Exception: " + exc, "Load Error", JOptionPane.ERROR_MESSAGE);
        }

        this.frame.repaint();
    }
}
