package com.nhlstenden.Controllers;

import com.nhlstenden.Controllers.Command;
import com.nhlstenden.Presentation;

public class PrevCommand extends Command
{
    public PrevCommand(Presentation presentation)
    {
        super(presentation);
    }

    @Override
    public void execute()
    {
        this.presentation.prevSlide();
    }
}
