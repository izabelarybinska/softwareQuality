package com.nhlstenden.Slide;

import com.nhlstenden.Presentation;
import javax.swing.*;
import java.awt.*;

public class SlideViewerComponent extends JComponent {
	private static final Color BGCOLOR = Color.WHITE;
	private static final Color COLOR = Color.BLACK;
	private static final int XPOS = 20;
	private static final int YPOS = 20;

	protected Presentation presentation;
	protected Slide slide;

	public SlideViewerComponent(Presentation presentation, Slide slide) {
		this.presentation = presentation;
		this.slide = slide;
	}

	public void setPresentation(Presentation presentation) {
		this.presentation = presentation;
		if (presentation != null) {
			slide = presentation.getCurrentSlide();
		}
	}

	public SlideViewerFrame getParentFrame() {
		return (SlideViewerFrame) SwingUtilities.getWindowAncestor(this);
	}

	public void update(Presentation presentation, int slideNumber) {
		this.presentation = presentation;
		this.slide = presentation.getSlide(slideNumber);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (slide == null || presentation.getSlideNumber() < 0) {
			return; 
		}

		g.setColor(BGCOLOR);
		g.fillRect(0, 0, getSize().width, getSize().height);

		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.setColor(COLOR);
		g.drawString("com.nhlstenden.Slide.Slide " + (1 + presentation.getSlideNumber()) + " of " +
				presentation.getSize(), XPOS, YPOS);

		Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
		slide.draw(g, area, this);
	}
}
