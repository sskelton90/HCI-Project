package hci;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import listeners.DeleteButtonListener;
import listeners.EditButtonListener;
import listeners.LoadTagFileListener;
import listeners.OpenFileListener;
import listeners.SaveAsFileListener;

/**
 * Main class of the program - handles display of the main window
 * @author Michal
 *
 */
public class ImageLabeller extends JFrame {
	private static Logger log =
			 Logger.getLogger(ImageLabeller.class.getName());
	
	public static PolygonList polygonList = new PolygonList();
	public static JButton editTag = new JButton("Edit");
	public static JButton addTag = new JButton("+");
	public static JButton deletePolygon = new JButton("-");
	public static JMenuItem loadTags = new JMenuItem("Load tags for image...");
	public static JMenuItem saveFile = new JMenuItem("Save", KeyEvent.VK_S);
	public static JMenuItem saveAsFile = new JMenuItem("Save as...");
	public static boolean savedOnce = false;
	public static String savedAs = "";
	public static JLabel STATUS = new JLabel("To open an image; click File then Open Image.");

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
		if (imagePanel != null)
		{
			imagePanel.paint(g); //update image panel
		}
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
		JMenuItem openFile = new JMenuItem("Open Image...",
                KeyEvent.VK_O);
		JMenu helpMenu = new JMenu("Help");
		JMenuItem userGuide = new JMenuItem("View User Guide");
		this.addWindowListener(new WindowAdapter() {
		  	public void windowClosing(WindowEvent event) {
		  		if (imagePanel.needSaved)
		  		{
		  			if (savedOnce)
		  			{
		  				int option = JOptionPane.showConfirmDialog(ImageLabeller.this, "You have made changes to the tags.\nDo you want to save your changes before you exit the program?", "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		  				
		  				switch (option)
		  				{
		  				case JOptionPane.YES_OPTION:
		  					String xml = imagePanel.getPolygonsAsString();
		  					
		  					if (xml == null)
		  					{
		  						return;
		  					}
		  					
		  					File file = new File(savedAs);
		  					
		  					BufferedWriter bw;
		  					try {
		  						bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		  						bw.write(xml);
		  						bw.flush();
		  						bw.close();
		  					} catch (Exception exception) {
		  						exception.printStackTrace();
		  					}
		  					System.out.println("Bye bye!");
		  			    	System.exit(0);
		  					break;
		  				case JOptionPane.NO_OPTION:
		  					System.out.println("Bye bye!");
		  			    	System.exit(0);
		  					break;
		  				}
		  			}
		  			else
		  			{
		  				int option = JOptionPane.showConfirmDialog(ImageLabeller.this, "You have made changes to the tags.\nDo you want to save your changes before you exit the program?", "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		  				
		  				switch (option)
		  				{
		  				case JOptionPane.YES_OPTION:
		  					ImageLabeller.saveAsFile.doClick();
		  					System.exit(0);
		  					break;
		  				case JOptionPane.NO_OPTION:
		  					System.exit(0);
		  					break;
		  				}
		  				return;
		  			}
		  		}
		  		System.out.println("Bye bye!");
		  		System.exit(0);
		  	}
		});

		//setup main window panel
		appPanel = new JPanel();
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);

		getContentPane().setLayout(new BorderLayout());
	
		getContentPane().add(STATUS, BorderLayout.SOUTH);
        //Create and set up the image panel.
		imagePanel.setOpaque(true); //content panes must be opaque
        appPanel.add(imagePanel, BorderLayout.WEST);

        //create toolbox panel
        toolboxPanel = new JPanel();
        BoxLayout toolboxPanelLayout = new BoxLayout(toolboxPanel, BoxLayout.Y_AXIS);
        toolboxPanel.setLayout(toolboxPanelLayout);
        polygonList.setMaximumSize(new Dimension(500, 100));
        toolboxPanel.add(polygonList);
        
        JPanel editButtonPanel = new JPanel();
        BoxLayout editButtonLayout = new BoxLayout(editButtonPanel, BoxLayout.X_AXIS);
        editButtonPanel.setLayout(editButtonLayout);
        
        editTag.addActionListener(new EditButtonListener());
        deletePolygon.addActionListener(new DeleteButtonListener(imagePanel));
        addTag.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ImagePanel.currentMode = ImagePanel.MODES.ADDING;
				STATUS.setText("Click on the image to place points. Click and drag to edit points. Press - to delete tag.");
			}
		});
        
        editTag.setEnabled(false);
        deletePolygon.setEnabled(false);
        addTag.setEnabled(false);
        
        editButtonPanel.add(addTag);
        editButtonPanel.add(editTag);
        editButtonPanel.add(deletePolygon);
        
        toolboxPanel.add(editButtonPanel, BorderLayout.EAST);

        ImageLabeller.loadTags.setEnabled(false);
		ImageLabeller.saveFile.setEnabled(false);
		ImageLabeller.saveAsFile.setEnabled(false);
		
		//add toolbox to window
		appPanel.add(toolboxPanel);

		// Set up the menu bar
		openFile.addActionListener(new OpenFileListener(imagePanel, this));
		ImageLabeller.saveAsFile.addActionListener(new SaveAsFileListener(imagePanel, this));
		ImageLabeller.loadTags.addActionListener(new LoadTagFileListener(imagePanel, this));
		
		userGuide.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HelpViewer viewer = new HelpViewer();
				viewer.setVisible(true);
			}
		});
		
		fileMenu.add(openFile);
		fileMenu.add(loadTags);
		fileMenu.add(saveFile);
		fileMenu.add(saveAsFile);
		
		helpMenu.add(userGuide);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
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
			ImageLabeller labeller = new ImageLabeller();
			labeller.setupGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
