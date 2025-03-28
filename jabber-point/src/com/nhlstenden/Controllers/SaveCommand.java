package com.nhlstenden.Controllers;

import com.nhlstenden.Accessors.Accessor;
import com.nhlstenden.Accessors.XMLAccessor;
import com.nhlstenden.Presentation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SaveCommand extends Command
{
    private Frame frame;

    public SaveCommand(Presentation presentation, Frame frame)
    {
        super(presentation);
        this.frame = frame;
    }

    @Override
    public void execute()
    {
        Accessor xmlAccessor = new XMLAccessor();

        try
        {
            xmlAccessor.saveFile(presentation, "dump.xml");
        } catch (IOException exc)
        {
            JOptionPane.showMessageDialog(frame, "IO Exception: " + exc, "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
