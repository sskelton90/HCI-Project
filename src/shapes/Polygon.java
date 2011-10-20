package shapes;

import hci.ImagePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import dialogs.AddTagSelectColourDialog;

public class Polygon 
{

	/**
	 * Each {@link Polygon} has a set of vertices.
	 */
	private ArrayList<Point> vertices = new ArrayList<Point>();

	/**
	 * Each {@link Polygon} has a tag.
	 */
	private String tag = "";

	/**
	 * Each {@link Polygon} has {@link Color}.
	 */
	private Color colour = pickRandomColour();

	boolean hasBeenEdited = false;
	boolean isSelected = false;
	boolean finishedOnce = false;
	boolean hidden = false;
	boolean loadedFromFile = false;

	private JPanel panel;
	
	public Polygon(JPanel panel, boolean loadedFromFile)
	{
		this.panel = panel;
		this.loadedFromFile = loadedFromFile;
		this.isSelected = false;
	}

	public Polygon(JPanel panel)
	{
		this.panel = panel;
		isSelected = false;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	public boolean isHasBeenEdited() {
		return hasBeenEdited;
	}

	public void setHasBeenEdited(boolean hasBeenEdited) {
		this.hasBeenEdited = hasBeenEdited;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public int getSize()
	{
		return this.vertices.size();
	}
	
	public void updatePoint(int point, int x, int y)
	{
		Point p = this.getPoint(point);
		p.setX(x);
		p.setY(y);
	}


	public void addPoint(Point point)
	{
		vertices.add(point);
	}

	public void addPointWithIndex(Point point, int index)
	{
		vertices.remove(index);
		vertices.add(index, point);
	}

	public Point getPoint(int index)
	{
		return this.vertices.get(index);
	}

	public void setHidden()
	{
		this.hidden = true;
	}

	public void drawPolygon(Graphics g) {
		if (!hidden)
		{
			for(int i = 0; i < this.vertices.size(); i++) {
				Point currentVertex = this.vertices.get(i);
				g.setColor(this.colour);
				if (i != 0) {
					Point prevVertex = this.vertices.get(i - 1);

					Line l = new Line(prevVertex, currentVertex);
					l.paintLine((Graphics2D) g, this.isSelected);
				}

				if (i == this.vertices.size() - 1 && finishedOnce)
				{
					Line l = new Line(this.vertices.get(0), currentVertex);
					l.paintLine((Graphics2D) g, this.isSelected);
				}
				currentVertex.paintComponent(g);
			}
		}
	}

	public void finishPolygon(Graphics graphics) {
		//if there are less than 3 vertices than nothing to be completed
		if (this.vertices.size() >= 3 && !finishedOnce) {
			Point firstVertex = this.vertices.get(0);
			Point lastVertex = this.vertices.get(this.vertices.size() - 1);

			Graphics2D g = (Graphics2D) graphics;
			g.setColor(this.colour);
			Line l = new Line(lastVertex, firstVertex);
			l.paintLine(g, this.isSelected);

			if (!loadedFromFile)
			{
				AddTagSelectColourDialog dialog = new AddTagSelectColourDialog(this, (ImagePanel) panel);
				dialog.setVisible(true);
			}

			finishedOnce = true;
		}
	}
	
	public void getXmlRepresentation(Element tags, Document doc)
	{
		Element tag = doc.createElement("TAG");
		tags.appendChild(tag);
		
		Element name = doc.createElement("NAME");
		tag.appendChild(name);
		
		Text nameText = doc.createTextNode(this.tag);
		name.appendChild(nameText);
		
		Element colour = doc.createElement("COLOUR");
		tag.appendChild(colour);
		
		Text colourText = doc.createTextNode("0x" + Integer.toHexString(this.colour.getRGB()).substring(2));
		colour.appendChild(colourText);
		
		Element vertices = doc.createElement("VERTICES");
		tag.appendChild(vertices);
		
		for (Point p : this.vertices)
		{
			Element vertex = doc.createElement("VERTEX");
			vertices.appendChild(vertex);
			
			Element x = doc.createElement("X");
			vertex.appendChild(x);
			Text xValue = doc.createTextNode(Integer.toString(p.getX()));
			x.appendChild(xValue);
			
			Element y = doc.createElement("Y");
			vertex.appendChild(y);
			Text yValue = doc.createTextNode(Integer.toString(p.getY()));
			y.appendChild(yValue);
		}
	}

	public Graphics getGraphics()
	{
		return this.panel.getGraphics();
	}

	public String toString()
	{
		return this.tag;
	}
	
	public String toStringVertices()
	{
		String output = null;
		for (Point p : this.vertices)
		{
			output += p.toString();
			output += " ";
		}
		
		return output;
	}

	public void select()
	{
		this.isSelected = true;
		this.panel.repaint();
	}

	public void unselect()
	{
		this.isSelected = false;
		this.panel.repaint();
	}

	public boolean equals(Polygon p)
	{
		if (p.getSize() != this.getSize())
		{
			return false;
		}

		for (int i = 0; i < p.getSize(); i++)
		{
			if (!p.getPoint(i).equals(this.getPoint(i)))
			{
				return false;
			}
		}

		return true;
	}
	
	private Color pickRandomColour()
	{
		Random randomGenerator = new Random();
		int r = randomGenerator.nextInt(255);
		int g = randomGenerator.nextInt(255);
		int b = randomGenerator.nextInt(255);
		Color colour = new Color(r, g, b, 255);
		
		return colour;
	}
}
