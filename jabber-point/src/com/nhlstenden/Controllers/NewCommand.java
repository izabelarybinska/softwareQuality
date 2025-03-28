package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;

import java.awt.*;

public class NewCommand implements Command
{
    private Frame frame;
    private Presentation presentation;

    public NewCommand(Presentation presentation, Frame frame)
    {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute()
    {
        this.presentation.clear();
        this.frame.repaint();
    }
}
