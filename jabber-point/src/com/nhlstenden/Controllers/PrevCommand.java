package com.nhlstenden.Controllers;

import com.nhlstenden.Presentation;

import java.util.Objects;

public final class PrevCommand implements Command {
    private final Presentation presentation;

    public PrevCommand(Presentation presentation) {
        this.presentation = Objects.requireNonNull(presentation, "Presentation cannot be null");
    }

    @Override
    public void execute() {
        this.presentation.prevSlide();
    }
}
