package shapes;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * simple class for handling points
 * @author Michal
 *
 */
public class Point extends JLabel implements MouseListener, MouseMotionListener {
	
	private int x = 0;
	private int y = 0;
	private int startDragX, startDragY;
	boolean dragging = false;
	private JPanel frame;
	
	public Point() {
	}
	
	public Point(int x, int y, JPanel frame) {
		this.x = x;
		this.y = y;
		this.frame = frame;
		addMouseListener(this);
		addMouseMotionListener(this);
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

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		System.out.println("Mouse clicked");
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		this.startDragX = e.getX();
		this.startDragY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if (dragging)
		{
			System.out.println("Dragging to " + this.toString());
			dragging = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		int newX = getX() + (e.getX() - startDragX);
		int newY = getY() + (e.getY() - startDragY);
		
		setLocation(newX, newY);
		dragging = true;
		frame.repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) 
	{
		
	}
	
}
