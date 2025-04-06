package com.nhlstenden.Slide;

import com.nhlstenden.Controllers.KeyController;
import com.nhlstenden.Menu.MainMenu;
import com.nhlstenden.Presentation;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.Serial;
import javax.swing.JFrame;

public class SlideViewerFrame extends JFrame {
    @Serial
    private static final long serialVersionUID = 3227L;
    private static final String JABTITLE = "Jabberpoint 1.6 - OU";
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;

    private Presentation currentPresentation;
    SlideViewerComponent slideViewerComponent;

    public SlideViewerFrame(String title, Presentation presentation) {
        super(title);
        this.currentPresentation = presentation;

        this.slideViewerComponent = new SlideViewerComponent(presentation,
                presentation.getCurrentSlide());

        presentation.setShowView(slideViewerComponent);

        setupWindow();
    }

    // sets up window properties and components
    private void setupWindow() {
        setTitle(JABTITLE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        getContentPane().add(slideViewerComponent);
        addKeyListener(new KeyController(currentPresentation));

        setMenuBar(new MainMenu(this, currentPresentation, this));
        setSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
    }

    public void setPresentation(Presentation presentation) {
        this.currentPresentation = presentation;
        this.slideViewerComponent.update(presentation, 0);
    }

    public void updatePresentation(Presentation presentation) {
        this.currentPresentation = presentation;
        this.slideViewerComponent.update(presentation, 0);
        repaint();
    }
}