package com.nhlstenden.Slide;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

public class Slide {
    private String text = "";
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;
    protected String title;
    protected Vector<SlideItem> items;

    public Slide() {
        items = new Vector<>();
    }

    public Vector<SlideItem> getSlideItems() {
        return items;
    }

    public SlideItem getSlideItem(int number) {
        return items.elementAt(number);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public int getSize() {
        return items.size();
    }

    public void append(SlideItem anItem) {
        items.addElement(anItem);
    }


    public void append(String type, int level, String content) {
        if (content == null) {
            content = "";
        }
        append(SlideItemFactory.createSlideItem(type, level, content));
    }


    //draw slide
    public void draw(Graphics g, Rectangle area, ImageObserver view) {
        float scale = getScale(area);
        int y = area.y;

        SlideItem slideItem = new TextItem(0, getTitle());
        Style style = Style.getStyle(slideItem.getLevel());
        slideItem.draw(area.x, y, scale, g, style, view);
        y += slideItem.getBoundingBox(g, view, scale, style).height;

        for (int number = 0; number < getSize(); number++) {
            slideItem = getSlideItems().elementAt(number);
            style = Style.getStyle(slideItem.getLevel());
            slideItem.draw(area.x, y, scale, g, style, view);
            y += slideItem.getBoundingBox(g, view, scale, style).height;
        }
    }

    //Scale
    private float getScale(Rectangle area) {
        return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
    }
}