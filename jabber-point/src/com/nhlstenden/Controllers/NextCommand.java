package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;

public class NextCommand implements Command
{
    private Presentation presentation;

    public NextCommand(Presentation presentation)
    {
        this.presentation = presentation;
    }

    @Override
    public void execute()
    {
        this.presentation.nextSlide();
    }
}
