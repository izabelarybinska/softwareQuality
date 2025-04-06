package com.nhlstenden;

import com.nhlstenden.Slide.Slide;
import com.nhlstenden.Slide.SlideViewerComponent;

import java.util.ArrayList;

public class Presentation {
    protected String showTitle;
    private ArrayList<Slide> showList;
    private int currentSlideNumber = 0;
    private SlideViewerComponent slideViewComponent;

    public Presentation() {
        this.slideViewComponent = null;
        this.clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent) {
        this.slideViewComponent = slideViewerComponent;
        this.clear();
    }

    public int getSize() {
        return this.showList.size();
    }

    public String getTitle() {
        return this.showTitle;
    }

    public void setTitle(String newTitle) {
        this.showTitle = newTitle;
    }

    public void setShowView(SlideViewerComponent slideViewerComponent) {
        this.slideViewComponent = slideViewerComponent;
    }

    public void setSlideNumber(int number) {
        if (number >= 0 && number < this.showList.size()) {
            this.currentSlideNumber = number;
            if (this.slideViewComponent != null) {
                this.slideViewComponent.update(this, this.currentSlideNumber);
            }
        }
    }

    public int getSlideNumber() {
        return this.currentSlideNumber;
    }

    public void prevSlide() {
        if (this.currentSlideNumber > 0) {
            this.setSlideNumber(this.currentSlideNumber - 1);
        }
    }

    public void nextSlide() {
        if (this.currentSlideNumber < (this.showList.size() - 1)) {
            this.setSlideNumber(this.currentSlideNumber + 1);
        }
    }

    public void clear() {
        this.showList = new ArrayList<>();
        this.currentSlideNumber = 0;
    }

    public void append(Slide slide) {
        this.showList.add(slide);
    }

    public Slide getSlide(int number) {
        if (number < 0 || number >= this.getSize()) {
            return null;
        }
        return this.showList.get(number);
    }

    public Slide getCurrentSlide() {
        return this.getSlide(this.currentSlideNumber);
    }

    public void exit(int n) {
        System.exit(n);
    }
}