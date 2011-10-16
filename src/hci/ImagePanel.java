package hci;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import shapes.Point;
import shapes.Polygon;

/**
 * Handles image editing panel
 * @author Michal
 *
 */
public class ImagePanel extends JPanel implements MouseListener {
	/**
	 * some java stuff to get rid of warnings
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * image to be tagged
	 */
	BufferedImage image = null;
	
	/**
	 * list of current polygon's vertices 
	 */
	Polygon currentPolygon = null;
	
	/**
	 * list of polygons
	 */
	ArrayList<Polygon> polygonsList = null;
	
	boolean tagsUpdated = false;
	
	public static Polygon currentlySelectedPolygon = null;
	
	public static enum MODES {ADDING, EDITING, STARTUP};
	public static MODES currentMode = MODES.STARTUP;

	private int currentlySelectedPoint;

	/**
	 * default constructor, sets up the window properties
	 */
	public ImagePanel() {
		currentPolygon = new Polygon(this);
		polygonsList = new ArrayList<Polygon>();

		this.setVisible(true);

		Dimension panelSize = new Dimension(800, 600);
		this.setSize(panelSize);
		this.setMinimumSize(panelSize);
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);
		
		addMouseListener(this);
	}
	
	/**
	 * extended constructor - loads image to be labelled
	 * @param imageName - path to image
	 * @throws Exception if error loading the image
	 */
	public ImagePanel(String imageName) throws Exception{
		this();
		
		if (imageName == null)
		{
			return;
		}
		
		setImage(imageName);
	}

	/**
	 * Displays the image
	 */
	public void ShowImage() {
		Graphics g = this.getGraphics();
		
		if (image != null) {
			g.drawImage(
					image, 0, 0, null);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//display image
		ShowImage();
		
		//display all the completed polygons
		if (polygonsList.size() != 0)
		{
			for(Polygon polygon : polygonsList) {
				polygon.drawPolygon();
				polygon.finishPolygon(this.getGraphics());
			}
		}
		
		//display current polygon
		if (currentPolygon != null)
		{
			this.currentPolygon.drawPolygon();
		}
		
	}
	
	public void setImage(String filePath)
	{
		try {
			image = ImageIO.read(new File(filePath));
			if (image.getWidth() > 800 || image.getHeight() > 600) {
				int newWidth = image.getWidth() > 800 ? 800 : (image.getWidth() * 600)/image.getHeight();
				int newHeight = image.getHeight() > 600 ? 600 : (image.getHeight() * 800)/image.getWidth();
				System.out.println("SCALING TO " + newWidth + "x" + newHeight );
				Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
				image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
				image.getGraphics().drawImage(scaledImage, 0, 0, this);
			}
			this.repaint();
			ImageLabeller.addTag.setEnabled(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * moves current polygon to the list of polygons and makes pace for a new one
	 */
	public void addNewPolygon() {
		//finish the current polygon if any
		if (currentPolygon != null && currentPolygon.getSize() >= 2) {
			currentPolygon.finishPolygon(this.getGraphics());
			polygonsList.add(currentPolygon);
		}
		currentPolygon = new Polygon(this);
	}
	
	public void resetShape()
	{
		currentPolygon = new Polygon(this);
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// If no image, don't register click
		if (image == null)
		{
			return;
		}
		
		int x = e.getX();
		int y = e.getY();
		
		//check if the cursos withing image area
		if (x > image.getWidth() || y > image.getHeight()) {
			//if not do nothing
			return;
		}
		
		Graphics2D g = (Graphics2D)this.getGraphics();
		
		//if the left button than we will add a vertex to poly
		if (e.getButton() == MouseEvent.BUTTON1) {
			
			switch (currentMode)
			{
			case ADDING:
				g.setColor(Color.GREEN);
				
				// If this is the first point of the polygon again, or user double clicks finish it
				if (isFirstPoint(x,y) || e.getClickCount() == 2)
				{
					addNewPolygon();
					return;
				}
				
				if (currentPolygon.getSize() != 0) {
					Point lastVertex = currentPolygon.getPoint(currentPolygon.getSize() - 1);
					g.drawLine(lastVertex.getX(), lastVertex.getY(), x, y);
				}
				g.fillOval(x-5,y-5,10,10);
				
				currentPolygon.addPoint(new Point(x, y));
				break;
			case EDITING:
				break;
			case STARTUP:
				JOptionPane.showMessageDialog(this, "It looks like you're trying to add a new tag.\nClick the \"+\" button in the toolbox to add a new tag.", "Adding a new tag?", JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			
		} 
	}

	private boolean isFirstPoint(int x, int y) {
		if (currentPolygon.getSize() <= 1)
		{
			return false;
		}
		
		Point firstPoint = currentPolygon.getPoint(0);
		return x > firstPoint.getX() - 5 && x < firstPoint.getX() + 5 && y < firstPoint.getY() + 5 && y > firstPoint.getY() - 5;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public ArrayList<Polygon> getPolygons()
	{
		return this.polygonsList;
	}
	
	public void deletePolygon(Polygon polygon)
	{
		this.polygonsList.remove(polygon);
		ImageLabeller.polygonList.removePolygon(currentlySelectedPolygon);
		this.currentPolygon = new Polygon(this);
		currentlySelectedPolygon = null;
		this.repaint();
	}
	
	public Point findPoint(java.awt.Point point)
	{
		for (Polygon polygon : this.polygonsList)
		{
			for (int i = 0; i < polygon.getSize(); i++)
			{
				if (polygon.getPoint(i).contains(point))
				{
					currentlySelectedPolygon = polygon;
					currentlySelectedPoint = i;
					return polygon.getPoint(i);
				}
			}
		}
		
		return null;
	}

}
