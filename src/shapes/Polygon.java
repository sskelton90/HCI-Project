package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

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

	public Polygon()
	{
		isSelected = true;
	}
	
	public void addPoint(Point point)
	{
		if (isSelected)
		{
			vertices.add(point);
		}
	}
	
	public Point getPoint(int index)
	{
		return this.vertices.get(index);
	}
	
	public void drawPolygon(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setColor(this.colour);
		for(int i = 0; i < this.vertices.size(); i++) {
			Point currentVertex = this.vertices.get(i);
			if (i != 0) {
				Point prevVertex = this.vertices.get(i - 1);
				g.drawLine(prevVertex.getX(), prevVertex.getY(), currentVertex.getX(), currentVertex.getY());
			}
			g.fillOval(currentVertex.getX() - 5, currentVertex.getY() - 5, 10, 10);
		}
	}
	
	public void finishPolygon(Graphics graphics) {
		//if there are less than 3 vertices than nothing to be completed
		if (this.vertices.size() >= 3) {
			Point firstVertex = this.vertices.get(0);
			Point lastVertex = this.vertices.get(this.vertices.size() - 1);
		
			Graphics2D g = (Graphics2D) graphics;
			g.setColor(this.colour);
			g.drawLine(firstVertex.getX(), firstVertex.getY(), lastVertex.getX(), lastVertex.getY());
		}
	}

}
