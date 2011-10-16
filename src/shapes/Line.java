package shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class Line {
	
	private Point point1;
	private Point point2;
	
	public Line(Point point1, Point point2)
	{
		this.point1 = point1;
		this.point2 = point2;
	}
	
	public void paintLine(Graphics2D graphics, boolean selected)
	{
		if (!selected)
		{
			graphics.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
		}
		else
		{
			graphics.setStroke(new BasicStroke(5));
			graphics.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
		}
	}

}
