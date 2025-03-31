package com.nhlstenden.Accessors;

import com.nhlstenden.Presentation;
//import com.nhlstenden.Slide.BitmapItemFactory;
import com.nhlstenden.Slide.Slide;
//import com.nhlstenden.Slide.TextItemFactory;

class DemoPresentation extends Accessor {

	public void loadFile(Presentation presentation, String unusedFilename) {
		presentation.setTitle("Demo com.nhlstenden.Presentation");

		Slide slide;

		slide = new Slide();
		slide.setTitle("com.nhlstenden.JabberPoint");
		slide.append("text", 1, "The Java com.nhlstenden.Presentation Tool");
		slide.append("text", 2, "Copyright (c) 1996-2000: Ian Darwin");
		slide.append("text", 2, "Copyright (c) 2000-now:");
		slide.append("text", 2, "Gert Florijn and Sylvia Stuurman");
		slide.append("text", 4, "Starting com.nhlstenden.JabberPoint without a filename");
		slide.append("text", 4, "shows this presentation");
		slide.append("text", 1, "Navigate:");
		slide.append("text", 3, "Next slide: PgDn or Enter");
		slide.append("text", 3, "Previous slide: PgUp or up-arrow");
		slide.append("text", 3, "Quit: q or Q");
		presentation.append(slide);

		slide = new Slide();
		slide.setTitle("Demonstration of levels and styles");
		slide.append("text", 1, "Level 1");
		slide.append("text", 2, "Level 2");
		slide.append("text", 1, "Again level 1");
		slide.append("text", 1, "Level 1 has style number 1");
		slide.append("text", 2, "Level 2 has style number 2");
		slide.append("text", 3, "This is how level 3 looks like");
		slide.append("text", 4, "And this is level 4");
		presentation.append(slide);

		slide = new Slide();
		slide.setTitle("The third slide");
		slide.append("text", 1, "To open a new presentation,");
		slide.append("text", 2, "use File->Open from the menu.");
		slide.append("text", 1, " ");
		slide.append("text", 1, "This is the end of the presentation.");
		slide.append("image", 1, "jabber-point/JabberPoint.gif"); // Using image factory
		presentation.append(slide);
	}

	public void saveFile(Presentation presentation, String unusedFilename) {
		throw new IllegalStateException("Save As->Demo! called");
	}
}
