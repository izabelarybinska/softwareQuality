package com.nhlstenden;

import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerComponent;
import com.nhlstenden.Slide.TextItem;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Presentation {
	protected String showTitle;
	private ArrayList<Slide> showList = null;
	private int currentSlideNumber = 0;
	private SlideViewerComponent slideViewComponent = null;

	public Presentation() {
		slideViewComponent = null;
		clear();
	}

	public Presentation(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
		clear();
	}

	public String getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	public ArrayList<Slide> getShowList() {
		return showList;
	}

	public void setShowList(ArrayList<Slide> showList) {
		this.showList = showList;
	}

	public int getCurrentSlideNumber() {
		return currentSlideNumber;
	}

	public void setCurrentSlideNumber(int currentSlideNumber) {
		this.currentSlideNumber = currentSlideNumber;
	}

	public SlideViewerComponent getSlideViewComponent() {
		return slideViewComponent;
	}

	public void setSlideViewComponent(SlideViewerComponent slideViewComponent) {
		this.slideViewComponent = slideViewComponent;
	}

	public int getSize() {
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
	}

	public void setShowView(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
	}

	public int getSlideNumber() {
		return currentSlideNumber;
	}

	public void setSlideNumber(int number) {
		if (number >= 0 && number < showList.size()) {
			currentSlideNumber = number;
			if (slideViewComponent != null) {
				slideViewComponent.update(this, currentSlideNumber);
			}
		} else {
			System.out.println("Invalid slide index: " + number);
		}
	}

	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
		}
	}

	public void nextSlide() {
		if (currentSlideNumber < (showList.size() - 1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	public void clear() {
		showList = new ArrayList<>();
		currentSlideNumber = 0;
	}


	public void append(Slide slide) {
		if (slide == null) return;

		showList.add(slide);
		if (slideViewComponent != null && showList.size() == 1) {
			// If this is the first slide, set it as current
			currentSlideNumber = 0;
			slideViewComponent.update(this, currentSlideNumber);
			System.out.println("Adding slide. Total slides now: " + showList.size());
		}
	}

	public void addSlideWithTextItems() {
		Slide newSlide = new Slide();
		boolean addingItems = true;

		while (addingItems) {
			String content = JOptionPane.showInputDialog(null, "Enter text item for this slide (or cancel to finish):");
			if (content != null && !content.trim().isEmpty()) {
				newSlide.append(new TextItem(1, content));
			} else {
				addingItems = false;
			}
		}

		this.append(newSlide);
	}


	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()) {
			return null;
		}
		return showList.get(number);
	}

	public Slide getCurrentSlide() {
		if (showList.isEmpty()) {
			return null;
		}
		if (currentSlideNumber < 0 || currentSlideNumber >= showList.size()) {
			System.out.println("Warning: Invalid slide index, resetting to first slide.");
			currentSlideNumber = 0;
		}
		return showList.get(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
}
