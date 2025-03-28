package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;

public class ExitCommand implements Command
{
    protected Presentation presentation;

    public ExitCommand(Presentation presentation)
    {
        this.presentation = presentation;
    }

    @Override
    public void execute()
    {
        this.presentation.exit(0);
    }
}
