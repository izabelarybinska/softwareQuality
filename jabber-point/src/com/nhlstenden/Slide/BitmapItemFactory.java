package com.nhlstenden.Slide;

public class BitmapItemFactory implements SlideItemFactory
{
    @Override
    public SlideItem createSlideItem(int level, String content)
    {
        return new BitmapItem(level, content);
    }
}
