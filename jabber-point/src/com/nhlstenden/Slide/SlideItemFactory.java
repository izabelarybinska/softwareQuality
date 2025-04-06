package com.nhlstenden.Slide;

public class SlideItemFactory {
    public static SlideItem createSlideItem(String type, int level, String content) {
        return switch (type.toLowerCase())
        {
            case "text" -> new TextItem(level, content);
            case "image" -> new BitmapItem(level, content);
            default -> throw new IllegalArgumentException("Unknown SlideItem type: " + type);
        };
    }
}