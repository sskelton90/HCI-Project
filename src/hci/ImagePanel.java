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
import java.io.StringWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

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
	private String currentFile;

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
			this.currentFile = filePath;
			image = ImageIO.read(new File(filePath));
			if (image.getWidth() > 800 || image.getHeight() > 600) {
				int newWidth = image.getWidth() > 800 ? 800 : (image.getWidth() * 600)/image.getHeight();
				int newHeight = image.getHeight() > 600 ? 600 : (image.getHeight() * 800)/image.getWidth();
				System.out.println("SCALING TO " + newWidth + "x" + newHeight );
				Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
				image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
				image.getGraphics().drawImage(scaledImage, 0, 0, this);
			}
			ImageLabeller.loadTags.setEnabled(true);
			ImageLabeller.addTag.setEnabled(true);
			this.polygonsList = new ArrayList<Polygon>();
			ImageLabeller.polygonList.replaceItems(this.polygonsList);
			this.repaint();
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
		
		ImageLabeller.saveAsFile.setEnabled(true);
		if (ImageLabeller.savedOnce)
		{
			ImageLabeller.saveFile.setEnabled(true);
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
	
	public String getPolygonsAsString()
	{
		String xmlString = null;
		
		if (this.polygonsList.isEmpty())
		{
			return null;
		}
		
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = builderFactory.newDocumentBuilder();
			
			//creating a new instance of a DOM to build a DOM tree.
			Document doc = docBuilder.newDocument();
			
			Element root = doc.createElement("TAGSET");
			doc.appendChild(root);
			
			Element image = doc.createElement("IMAGE");
			root.appendChild(image);
			
			Text imageText = doc.createTextNode(this.currentFile);
			image.appendChild(imageText);
			
			Element tags = doc.createElement("TAGS");
			root.appendChild(tags);
			
			for (Polygon polygon: this.polygonsList)
			{
				polygon.getXmlRepresentation(tags, doc);
			}
			
			TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            xmlString = sw.toString();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return xmlString;
	}
	
	public void loadPolygonsFromFile(File xmlFile)
	{
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (xmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList imageName = doc.getElementsByTagName("IMAGE");
			Node node = imageName.item(0);
			
			if (!node.getTextContent().equals(this.currentFile))
			{
				JOptionPane.showMessageDialog(this, "Tagset doesn't match image!\nPlease select matching tagset for this image.", "Incorrect Tagset", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			NodeList tags = doc.getElementsByTagName("TAG");
			System.out.println(tags.getLength() + " tags loaded.");
			this.polygonsList = new ArrayList<Polygon>();
			
			for (int i = 0; i < tags.getLength(); i++)
			{
				Polygon polygon = new Polygon(this, true);
				Element tag = (Element) tags.item(i);
				
				NodeList name = tag.getElementsByTagName("NAME");
				NodeList colour = tag.getElementsByTagName("COLOUR");
				
				polygon.setTag(name.item(0).getTextContent());
				polygon.setColour(Color.decode(colour.item(0).getTextContent()));
				
				NodeList xs = tag.getElementsByTagName("X");
				NodeList ys = tag.getElementsByTagName("Y");
				
				for (int j = 0; j < xs.getLength(); j++)
				{
					int x = Integer.parseInt(xs.item(j).getTextContent());
					int y = Integer.parseInt(ys.item(j).getTextContent());
					System.out.println("Adding: " + x + " and " + y);
					polygon.addPoint(new Point(x, y));
				}
				this.polygonsList.add(polygon);
			}
			
			ImageLabeller.polygonList.replaceItems(this.polygonsList);
			this.repaint();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
