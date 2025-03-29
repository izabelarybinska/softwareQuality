package com.nhlstenden.Menu;

import com.nhlstenden.Presentation;
import java.awt.*;
import javax.swing.JOptionPane;

public class ViewMenu extends Menu {
    protected static final String NEXT = "Next";
    protected static final String PREV = "Prev";
    protected static final String GOTO = "Go to";
    protected static final String VIEW = "View";

    public ViewMenu(Frame parent, Presentation presentation) {
        super(VIEW);

        add(createMenuItem(NEXT, e -> presentation.nextSlide()));
        add(createMenuItem(PREV, e -> presentation.prevSlide()));
        add(createMenuItem(GOTO, e -> {
            String pageNumberStr = JOptionPane.showInputDialog("Enter Page Number:");
            try {
                int pageNumber = Integer.parseInt(pageNumberStr);
                presentation.setSlideNumber(pageNumber - 1);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid page number!");
            }
        }));
    }

    private MenuItem createMenuItem(String name, java.awt.event.ActionListener action) {
        MenuItem menuItem = new MenuItem(name);
        menuItem.addActionListener(action);
        return menuItem;
    }
}
