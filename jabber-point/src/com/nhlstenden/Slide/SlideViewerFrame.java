package com.nhlstenden.Slide;

import com.nhlstenden.Controllers.KeyController;
import com.nhlstenden.Menu.FileMenu;
import com.nhlstenden.Menu.HelpMenu;
import com.nhlstenden.Menu.MainMenu;
import com.nhlstenden.Menu.ViewMenu;
import com.nhlstenden.Presentation;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

/**
 * <p>The application window for a slideviewcomponent</p>
 */

public class SlideViewerFrame extends JFrame {
	private static final long serialVersionUID = 3227L;
	private static final String JABTITLE = "Jabberpoint 1.6 - OU";
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;

	private Presentation currentPresentation;
	private SlideViewerComponent slideViewerComponent;

	public SlideViewerFrame(String title, Presentation presentation) {
		super(title);
		this.currentPresentation = presentation;

		Slide initialSlide = presentation.getCurrentSlide();
		if (initialSlide == null) {
			initialSlide = new Slide();
			presentation.append(initialSlide);
		}

		this.slideViewerComponent = new SlideViewerComponent(presentation, initialSlide);
		presentation.setShowView(slideViewerComponent);

		setupWindow();
	}

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

	public void setPresentation(Presentation newPresentation) {
		this.currentPresentation = newPresentation;
		this.slideViewerComponent.update(newPresentation, newPresentation.getCurrentSlide().getSize());
		repaint();
	}
}
