import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

public class MenuController extends MenuBar {

	private static final long serialVersionUID = 227L;

	// Menu constants
	protected static final String ABOUT = "About";
	protected static final String FILE = "File";
	protected static final String EXIT = "Exit";
	protected static final String GOTO = "Go to";
	protected static final String HELP = "Help";
	protected static final String NEW = "New";
	protected static final String NEXT = "Next";
	protected static final String OPEN = "Open";
	protected static final String PAGENR = "Page number?";
	protected static final String PREV = "Prev";
	protected static final String SAVE = "Save";
	protected static final String VIEW = "View";

	// File paths
	protected static final String TESTFILE = "test.xml";
	protected static final String SAVEFILE = "dump.xml";

	// Error messages
	protected static final String IOEX = "IO Exception: ";
	protected static final String LOADERR = "Load Error";
	protected static final String SAVEERR = "Save Error";

	// Instance variables
	private Frame parent; // the frame, only used as parent for the Dialogs
	private Presentation presentation; // Commands are given to the presentation

	public MenuController(Frame frame, Presentation pres) {
		parent = frame;
		presentation = pres;
		initializeMenu();
	}

	// Initialize the menu items
	private void initializeMenu() {
		add(createFileMenu());
		add(createViewMenu());
		add(createHelpMenu());
	}

	// Create and return the File menu
	private Menu createFileMenu() {
		Menu fileMenu = new Menu(FILE);
		fileMenu.add(createMenuItem(OPEN, this::openFile));
		fileMenu.add(createMenuItem(NEW, this::newFile));
		fileMenu.add(createMenuItem(SAVE, this::saveFile));
		fileMenu.addSeparator();
		fileMenu.add(createMenuItem(EXIT, this::exitApp));
		return fileMenu;
	}

	// Create and return the View menu
	private Menu createViewMenu() {
		Menu viewMenu = new Menu(VIEW);
		viewMenu.add(createMenuItem(NEXT, this::nextSlide));
		viewMenu.add(createMenuItem(PREV, this::prevSlide));
		viewMenu.add(createMenuItem(GOTO, this::goToSlide));
		return viewMenu;
	}

	// Create and return the Help menu
	private Menu createHelpMenu() {
		Menu helpMenu = new Menu(HELP);
		helpMenu.add(createMenuItem(ABOUT, this::showAboutBox));
		setHelpMenu(helpMenu); // Needed for portability (Motif, etc.)
		return helpMenu;
	}

	// Create a menu item with the given name and action
	private MenuItem createMenuItem(String name, ActionListener action) {
		MenuItem menuItem = new MenuItem(name, new MenuShortcut(name.charAt(0)));
		menuItem.addActionListener(action);
		return menuItem;
	}

	// Action methods for the menu items

	// Open file action
	private void openFile(ActionEvent actionEvent) {
		presentation.clear();
		Accessor xmlAccessor = new XMLAccessor();
		try {
			xmlAccessor.loadFile(presentation, TESTFILE);
			presentation.setSlideNumber(0);
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(parent, IOEX + exc, LOADERR, JOptionPane.ERROR_MESSAGE);
		}
		parent.repaint();
	}

	// New file action
	private void newFile(ActionEvent actionEvent) {
		presentation.clear();
		parent.repaint();
	}

	// Save file action
	private void saveFile(ActionEvent e) {
		Accessor xmlAccessor = new XMLAccessor();
		try {
			xmlAccessor.saveFile(presentation, SAVEFILE);
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(parent, IOEX + exc, SAVEERR, JOptionPane.ERROR_MESSAGE);
		}
	}

	// Exit application action
	private void exitApp(ActionEvent actionEvent) {
		presentation.exit(0);
	}

	// Next slide action
	private void nextSlide(ActionEvent actionEvent) {
		presentation.nextSlide();
	}

	// Previous slide action
	private void prevSlide(ActionEvent actionEvent) {
		presentation.prevSlide();
	}

	// Go to slide action
	private void goToSlide(ActionEvent actionEvent) {
		String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
		int pageNumber = Integer.parseInt(pageNumberStr);
		presentation.setSlideNumber(pageNumber - 1);
	}

	// Show About box action
	private void showAboutBox(ActionEvent actionEvent) {
		AboutBox.show(parent);
	}
}