package shapes;

import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * simple class for handling points
 * @author Michal
 *
 */
public class Point extends JLabel {
	
	private int x = 0;
	private int y = 0;
	
	public Point() {
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public void paintComponent(Graphics graphics)
	{
		graphics.fillOval(this.getX() - 5, this.getY() - 5, 10, 10);
	}
	
	public boolean equals(Point p)
	{
		return (this.x == p.getX() && this.y == p.getY());
	}
	
	public boolean contains(java.awt.Point point)
	{
		return (this.x <= point.x + 5) && (this.x >= point.x - 5) && (this.y <= point.y + 5) && (this.x >= point.y - 5);
	}
	
	public String toString()
	{
		return "Point: " + x + " " + y;
	}

}
