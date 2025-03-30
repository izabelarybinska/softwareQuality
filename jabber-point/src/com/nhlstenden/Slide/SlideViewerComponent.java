package com.nhlstenden.Slide;

import com.nhlstenden.Presentation;
import javax.swing.*;
import java.awt.*;

public class SlideViewerComponent extends JComponent {
	private static final Color BGCOLOR = Color.WHITE;
	private static final Color COLOR = Color.BLACK;
	private static final int XPOS = 20;
	private static final int YPOS = 20;

	private Presentation presentation;
	private Slide slide;

	public SlideViewerComponent(Presentation presentation, Slide slide)
	{
		this.presentation = presentation;
		this.slide = slide;
	}

	public void setPresentation(Presentation presentation) {
		this.presentation = presentation;
		if (presentation != null) {
			slide = presentation.getCurrentSlide();
		}
	}

	public void update(Presentation newPresentation, int newSlideIndex) {
		this.presentation = newPresentation;

		if (newSlideIndex >= 0 && newSlideIndex < newPresentation.getShowList().size()) {
			this.slide = newPresentation.getSlide(newSlideIndex);
		} else {
			System.out.println("Invalid slide index: " + newSlideIndex);
			return;
		}

		if (slide != null) {
			for (SlideItem slideItem : slide.getSlideItems()) {

				if (slideItem instanceof TextItem) {
					TextItem textItem = (TextItem) slideItem;

					String slideText = slide.getText();
					textItem.setText(slideText);
				}
			}
		} else {
			System.out.println("Current slide is null.");
		}

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(BGCOLOR);
		g.fillRect(0, 0, getSize().width, getSize().height);

		if (presentation.getSlideNumber() < 0 || slide == null) {
			return;
		}

		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.setColor(COLOR);
		g.drawString("com.nhlstenden.Slide.Slide " + (1 + presentation.getSlideNumber()) + " of " +
				presentation.getSize(), XPOS, YPOS);

		Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
		slide.draw(g, area, this);
	}
}
