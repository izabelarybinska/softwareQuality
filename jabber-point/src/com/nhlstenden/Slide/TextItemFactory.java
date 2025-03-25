package com.nhlstenden.Slide;

public class TextItemFactory implements SlideItemFactory
{
    @Override
    public SlideItem createSlideItem(int level, String content)
    {
        return new TextItem(level, content);
    }
}
