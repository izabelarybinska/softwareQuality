package com.nhlstenden.Controllers;

import com.nhlstenden.Controllers.Command;
import com.nhlstenden.Presentation;

public class PrevCommand implements Command
{
    private Presentation presentation;

    public PrevCommand(Presentation presentation)
    {
        this.presentation = presentation;
    }

    @Override
    public void execute()
    {
        this.presentation.prevSlide();
    }
}
