package hci;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import listeners.EditButtonListener;
import listeners.OpenFileListener;
import shapes.Polygon;

/**
 * Main class of the program - handles display of the main window
 * @author Michal
 *
 */
public class ImageLabeller extends JFrame {
	private static Logger log =
			 Logger.getLogger(ImageLabeller.class.getName());
	
	public static PolygonList polygonList = new PolygonList();
	public static JButton editTag = new JButton("Edit tag");
	public static JButton deletePolygon = new JButton("Delete shape");
	public static JMenuItem loadTags = new JMenuItem("Load tags for image...");
	public static JMenuItem saveFile = new JMenuItem("Save", KeyEvent.VK_S);
	public static JMenuItem saveAsFile = new JMenuItem("Save as...");

	/**
	 * some java stuff to get rid of warnings
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * main window panel
	 */
	JPanel appPanel = null;
	
	/**
	 * toolbox - put all buttons and stuff here!
	 */
	JPanel toolboxPanel = null;
	
	/**
	 * image panel - displays image and editing area
	 */
	ImagePanel imagePanel = null;
	
	/**
	 * handles New Object button action
	 */
	public void addNewPolygon() {
		imagePanel.addNewPolygon();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		imagePanel.paint(g); //update image panel
	}
	
	/**
	 * sets up application window
	 * @param imageFilename image to be loaded for editing
	 * @throws Exception
	 */
	public void setupGUI() throws Exception {
		JMenuBar menuBar = new JMenuBar();
		final ImagePanel imagePanel = new ImagePanel();
		JMenu fileMenu = new JMenu("File");
		JMenuItem openFile = new JMenuItem("Open...",
                KeyEvent.VK_O);
		JMenuItem loadTags = new JMenuItem("Load tags for image...");
		JMenuItem saveFile = new JMenuItem("Save", KeyEvent.VK_S);
		JMenuItem saveAsFile = new JMenuItem("Save as...");
		
		
		this.addWindowListener(new WindowAdapter() {
		  	public void windowClosing(WindowEvent event) {
		  		for (Polygon polygon : imagePanel.getPolygons()) {
					System.out.println("Saved " + polygon.getTag());
					System.out.println("with size: " + polygon.getSize());
				}

		  		System.out.println("Bye bye!");
		    	System.exit(0);
		  	}
		});

		//setup main window panel
		appPanel = new JPanel();
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);
		
        //Create and set up the image panel.
		imagePanel.setOpaque(true); //content panes must be opaque
        appPanel.add(imagePanel);

        //create toolbox panel
        toolboxPanel = new JPanel();
        toolboxPanel.setLayout(new BoxLayout(toolboxPanel, BoxLayout.Y_AXIS));
        toolboxPanel.add(polygonList);
        
        editTag.addActionListener(new EditButtonListener());
        
        editTag.setEnabled(false);
        deletePolygon.setEnabled(false);
        
        toolboxPanel.add(editTag);
        toolboxPanel.add(deletePolygon);
		loadTags.setEnabled(false);
		saveFile.setEnabled(false);
		saveAsFile.setEnabled(false);
		
		//add toolbox to window
		appPanel.add(toolboxPanel);

		// Set up the menu bar
		openFile.addActionListener(new OpenFileListener(imagePanel, this));
		fileMenu.add(openFile);
		fileMenu.add(loadTags);
		fileMenu.add(saveFile);
		fileMenu.add(saveAsFile);
		
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		
		//display all the stuff
		this.pack();
        this.setVisible(true);
	}
	
	/**
	 * Runs the program
	 * @param argv path to an image
	 */
	public static void main(String argv[]) {
		try {
			//create a window and display the image
			ImageLabeller window = new ImageLabeller();
			window.setupGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
