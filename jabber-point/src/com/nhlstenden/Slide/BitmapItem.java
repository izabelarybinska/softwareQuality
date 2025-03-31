package com.nhlstenden.Slide;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class BitmapItem extends SlideItem
{
	private BufferedImage bufferedImage;
	private String imageName;

	protected static final String FILE = "File ";
	protected static final String NOTFOUND = " not found";

	// level is equal to item-level; name is the name of the file with the Image
	public BitmapItem(int level, String name) {
		super(level);
		imageName = name;
		try {
			bufferedImage = ImageIO.read(new File(imageName));
		} catch (IOException e) {
			System.err.println(FILE + imageName + NOTFOUND);
		}
	}

	// give the filename of the image
	public String getName() {
		return imageName;
	}

	// give the bounding box of the image
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		return new Rectangle(
				(int) (myStyle.indent * scale),
				0,
				(int) (bufferedImage.getWidth(observer) * scale),
				(int) (myStyle.leading * scale) + (int) (bufferedImage.getHeight(observer) * scale)
		);
	}

	// draw the image
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
		int width = x + (int) (myStyle.indent * scale);
		int height = y + (int) (myStyle.leading * scale);
		g.drawImage(
				bufferedImage,
				width,
				height,
				(int) (bufferedImage.getWidth(observer) * scale),
				(int) (bufferedImage.getHeight(observer) * scale),
				observer
		);
	}

	public String toString() {
		return "com.nhlstenden.Slide.BitmapItem[" + getLevel() + "," + imageName + "]";
	}
}