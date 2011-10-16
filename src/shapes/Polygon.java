package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import dialogs.AddTagSelectColourDialog;

public class Polygon {
	
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
	private Color colour = Color.GREEN;

	boolean hasBeenEdited = false;
	boolean isSelected = false;
	boolean finishedOnce = false;

	private JPanel panel;
	
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

	public Polygon(JPanel panel)
	{
		this.panel = panel;
		isSelected = false;
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
	
	public void drawPolygon() {
		Graphics2D g = (Graphics2D) this.panel.getGraphics();
		for(int i = 0; i < this.vertices.size(); i++) {
			Point currentVertex = this.vertices.get(i);
			g.setColor(this.colour);
			if (i != 0) {
				Point prevVertex = this.vertices.get(i - 1);
				
				Line l = new Line(prevVertex, currentVertex);
				l.paintLine(g, this.isSelected);
			}
			
			if (i == this.vertices.size() - 1 && finishedOnce)
			{
				Line l = new Line(this.vertices.get(0), currentVertex);
				l.paintLine(g, this.isSelected);
			}
			currentVertex.paintComponent(g);
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
			
			AddTagSelectColourDialog dialog = new AddTagSelectColourDialog(this);
			dialog.setVisible(true);
			
			finishedOnce = true;
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
}
