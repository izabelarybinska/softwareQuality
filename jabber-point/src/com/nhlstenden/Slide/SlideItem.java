package com.nhlstenden.Slide;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public abstract class SlideItem {
    protected int level = 0;

    public SlideItem(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public abstract Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style);

    public abstract void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer);
}