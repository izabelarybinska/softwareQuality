package com.nhlstenden.Accessors;

import com.nhlstenden.Presentation;
import com.nhlstenden.Slide.BitmapItemFactory;
import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.TextItemFactory;

class DemoPresentation extends Accessor {

	public void loadFile(Presentation presentation, String unusedFilename) {
		presentation.setTitle("Demo com.nhlstenden.Presentation");

		//C
		TextItemFactory textFactory = new TextItemFactory();
		BitmapItemFactory imageFactory = new BitmapItemFactory();

		Slide slide;

		slide = new Slide();
		slide.setTitle("com.nhlstenden.JabberPoint");
		slide.append(textFactory, 1, "The Java com.nhlstenden.Presentation Tool");
		slide.append(textFactory, 2, "Copyright (c) 1996-2000: Ian Darwin");
		slide.append(textFactory, 2, "Copyright (c) 2000-now:");
		slide.append(textFactory, 2, "Gert Florijn and Sylvia Stuurman");
		slide.append(textFactory, 4, "Starting com.nhlstenden.JabberPoint without a filename");
		slide.append(textFactory, 4, "shows this presentation");
		slide.append(textFactory, 1, "Navigate:");
		slide.append(textFactory, 3, "Next slide: PgDn or Enter");
		slide.append(textFactory, 3, "Previous slide: PgUp or up-arrow");
		slide.append(textFactory, 3, "Quit: q or Q");
		presentation.append(slide);

		slide = new Slide();
		slide.setTitle("Demonstration of levels and styles");
		slide.append(textFactory, 1, "Level 1");
		slide.append(textFactory, 2, "Level 2");
		slide.append(textFactory, 1, "Again level 1");
		slide.append(textFactory, 1, "Level 1 has style number 1");
		slide.append(textFactory, 2, "Level 2 has style number 2");
		slide.append(textFactory, 3, "This is how level 3 looks like");
		slide.append(textFactory, 4, "And this is level 4");
		presentation.append(slide);

		slide = new Slide();
		slide.setTitle("The third slide");
		slide.append(textFactory, 1, "To open a new presentation,");
		slide.append(textFactory, 2, "use File->Open from the menu.");
		slide.append(textFactory, 1, " ");
		slide.append(textFactory, 1, "This is the end of the presentation.");
		slide.append(imageFactory, 1, "jabber-point/JabberPoint.gif"); // Using image factory
		presentation.append(slide);
	}

	public void saveFile(Presentation presentation, String unusedFilename) {
		throw new IllegalStateException("Save As->Demo! called");
	}
}
