//package shapes;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import hci.ImagePanel;
//
//import javax.swing.JFrame;
//
//import org.junit.Test;
//
//public class PolygonTest {
//
//	@Test
//	public void testEquals() {
//		Point p1 = new Point(4, 4, new JFrame());
//		Point p2 = new Point(7, 8, new JFrame());
//		Point p3 = new Point(10, 4, new JFrame());
//		
//		Polygon polygon = new Polygon(new ImagePanel());
//		Polygon polygon2 = new Polygon(new ImagePanel());
//		
//		polygon.addPoint(p1);
//		polygon.addPoint(p2);
//		polygon.addPoint(p3);
//		
//		polygon2.addPoint(p1);
//		polygon2.addPoint(p2);
//		polygon2.addPoint(p3);
//		
//		assertTrue(polygon.equals(polygon2));
//		
//		polygon2.addPoint(p3);
//		
//		assertFalse(polygon.equals(polygon2));
//	}
//
//}
