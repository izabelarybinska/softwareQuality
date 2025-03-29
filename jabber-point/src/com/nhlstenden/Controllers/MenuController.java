//package com.nhlstenden.Controllers;
//
//import com.nhlstenden.AboutBox;
//import com.nhlstenden.Accessors.Accessor;
//import com.nhlstenden.Accessors.XMLAccessor;
//import com.nhlstenden.Presentation;
//
//import java.awt.MenuBar;
//import java.awt.Frame;
//import java.awt.Menu;
//import java.awt.MenuItem;
//import java.awt.MenuShortcut;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import java.io.IOException;
//
//import javax.swing.JOptionPane;
//
//public class MenuController extends MenuBar {
//
//	private static final long serialVersionUID = 227L;
//
//	protected static final String ABOUT = "About";
//	protected static final String FILE = "File";
//	protected static final String EXIT = "Exit";
//	protected static final String GOTO = "Go to";
//	protected static final String HELP = "Help";
//	protected static final String NEW = "New";
//	protected static final String NEXT = "Next";
//	protected static final String OPEN = "Open";
//	protected static final String PAGENR = "Page number?";
//	protected static final String PREV = "Prev";
//	protected static final String SAVE = "Save";
//	protected static final String VIEW = "View";
//
//	protected static final String TESTFILE = "test.xml";
//	protected static final String SAVEFILE = "dump.xml";
//
//	protected static final String IOEX = "IO Exception: ";
//	protected static final String LOADERR = "Load Error";
//	protected static final String SAVEERR = "Save Error";
//
//	private Frame parent;
//	private Presentation presentation;
//
//	public MenuController(Frame frame, Presentation pres) {
//		parent = frame;
//		presentation = pres;
//		initializeMenu();
//	}
//
//	private void initializeMenu() {
//		add(createFileMenu());
//		add(createViewMenu());
//		add(createHelpMenu());
//	}
//
//	private Menu createFileMenu() {
//		Menu fileMenu = new Menu(FILE);
//		fileMenu.add(createMenuItem(OPEN, this::openFile));
//		fileMenu.add(createMenuItem(NEW, this::newFile));
//		fileMenu.add(createMenuItem(SAVE, this::saveFile));
//		fileMenu.addSeparator();
//		fileMenu.add(createMenuItem(EXIT, this::exitApp));
//		return fileMenu;
//	}
//
//	private Menu createViewMenu() {
//		Menu viewMenu = new Menu(VIEW);
//		viewMenu.add(createMenuItem(NEXT, this::nextSlide));
//		viewMenu.add(createMenuItem(PREV, this::prevSlide));
//		viewMenu.add(createMenuItem(GOTO, this::goToSlide));
//		return viewMenu;
//	}
//
//	private Menu createHelpMenu() {
//		Menu helpMenu = new Menu(HELP);
//		helpMenu.add(createMenuItem(ABOUT, this::showAboutBox));
//		setHelpMenu(helpMenu); // Needed for portability (Motif, etc.)
//		return helpMenu;
//	}
//
//	private MenuItem createMenuItem(String name, ActionListener action) {
//		MenuItem menuItem = new MenuItem(name, new MenuShortcut(name.charAt(0)));
//		menuItem.addActionListener(action);
//		return menuItem;
//	}
//
//	public void openFile(ActionEvent actionEvent) {
//		presentation.clear();
//		Accessor xmlAccessor = new XMLAccessor();
//		try {
//			xmlAccessor.loadFile(presentation, TESTFILE);
//			presentation.setSlideNumber(0);
//		} catch (IOException exc) {
//			JOptionPane.showMessageDialog(parent, IOEX + exc, LOADERR, JOptionPane.ERROR_MESSAGE);
//		}
//		parent.repaint();
//	}
//
//	public void newFile(ActionEvent actionEvent) {
//		presentation.clear();
//		parent.repaint();
//	}
//
//	public void saveFile(ActionEvent e) {
//		Accessor xmlAccessor = new XMLAccessor();
//		try {
//			xmlAccessor.saveFile(presentation, SAVEFILE);
//		} catch (IOException exc) {
//			JOptionPane.showMessageDialog(parent, IOEX + exc, SAVEERR, JOptionPane.ERROR_MESSAGE);
//		}
//	}
//
//	public void exitApp(ActionEvent actionEvent) {
//		presentation.exit(0);
//	}
//
//	public void nextSlide(ActionEvent actionEvent) {
//		presentation.nextSlide();
//	}
//
//	public void prevSlide(ActionEvent actionEvent) {
//		presentation.prevSlide();
//	}
//
//	public void goToSlide(ActionEvent actionEvent) {
//		String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
//		int pageNumber = Integer.parseInt(pageNumberStr);
//		presentation.setSlideNumber(pageNumber - 1);
//	}
//
//	public void showAboutBox(ActionEvent actionEvent) {
//		AboutBox.show(parent);
//	}
//}