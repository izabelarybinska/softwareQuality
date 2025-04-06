package com.nhlstenden.Slide;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * <p>A tekst item.</p>
 * <p>A com.nhlstenden.Slide.TextItem has drawingfunctionality.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
    private String text;

    private static final String EMPTYTEXT = "No Text Given";

    public TextItem(int level, String string) {
        super(level);
        this.text = (string != null && !string.trim().isEmpty()) ? string : EMPTYTEXT;
    }

    public TextItem() {
        this(0, EMPTYTEXT);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = (text != null && !text.trim().isEmpty()) ? text : EMPTYTEXT;
    }

    //generates an AttributedString for the text
    public AttributedString getAttributedString(Style style, float scale) {
        AttributedString attrStr = new AttributedString(getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());
        return attrStr;
    }

    // Calculates and returns the bounding box for TextItem
    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer,
                                    float scale, Style myStyle) {
        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        int xsize = 0, ysize = (int) (myStyle.leading * scale);
        Iterator<TextLayout> iterator = layouts.iterator();
        while (iterator.hasNext()) {
            TextLayout layout = iterator.next();
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > xsize) {
                xsize = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0) {
                ysize += bounds.getHeight();
            }
            ysize += layout.getLeading() + layout.getDescent();
        }
        return new Rectangle((int) (myStyle.indent * scale), 0, xsize, ysize);
    }

    // draws TextItem
    public void draw(int x, int y, float scale, Graphics g,
                     Style myStyle, ImageObserver o) {
        if (text == null || text.length() == 0) {
            System.out.println("TextItem has no valid text.");
            return;
        }
        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        Point pen = new Point(x + (int) (myStyle.indent * scale),
                y + (int) (myStyle.leading * scale));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(myStyle.color);
        Iterator<TextLayout> it = layouts.iterator();
        while (it.hasNext()) {
            TextLayout layout = it.next();
            pen.y += layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += layout.getDescent();
        }
    }

    // creates text layouts based on style and scale
    private List<TextLayout> getLayouts(Graphics g, Style s, float scale) {
        List<TextLayout> layouts = new ArrayList<TextLayout>();
        AttributedString attrStr = getAttributedString(s, scale);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        float wrappingWidth = (Slide.WIDTH - s.indent) * scale;
        while (measurer.getPosition() < getText().length()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }
        return layouts;
    }

    public String toString() {
        return "com.nhlstenden.Slide.TextItem[" + getLevel() + "," + getText() + "]";
    }
}
