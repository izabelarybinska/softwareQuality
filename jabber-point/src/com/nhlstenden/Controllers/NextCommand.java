package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;

public class NextCommand extends Command
{
    public NextCommand(Presentation presentation)
    {
        super(presentation);
    }

    @Override
    public void execute()
    {
        this.presentation.nextSlide();
    }
}
