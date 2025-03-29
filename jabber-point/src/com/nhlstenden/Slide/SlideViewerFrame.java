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

	public SlideViewerFrame(String title, Presentation presentation) {
		super(title);
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
		presentation.setShowView(slideViewerComponent);
		setupWindow(slideViewerComponent, presentation);
	}

	public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation) {
		setTitle(JABTITLE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		getContentPane().add(slideViewerComponent);
		addKeyListener(new KeyController(presentation));

		setMenuBar(new MainMenu(this, presentation));


		setSize(new Dimension(WIDTH, HEIGHT));
		setVisible(true);
	}
}
