import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;



public class SketchPanel extends AppletPanel 
{

	LinkedList<Point> points = null;
	LinkedList<LinkedList<Point>> lines = new LinkedList<LinkedList<Point>>();
	Point lastPoint;
	Point newPoint;
	
	public SketchPanel() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void paint(Graphics2D gg)
	{
		System.out.println("SketchPanel.paint");
		gg.setColor(Color.WHITE);
		gg.fillRect(0, 0, bounds.width, bounds.height);
		gg.setColor(Color.BLACK);
		for (LinkedList<Point> l : lines)
		{
			Point startPoint = null;
			for (Point p : l)
			{
				if (startPoint == null)
					startPoint = p;
				else
				{
					gg.drawLine(startPoint.x, startPoint.y, p.x, p.y);
					startPoint = p;
				}
			}
		}
	}
	
	public void event(Event ev)
	{
		if (Configuration.debugEvents)
			System.out.println("SketchPanel.Event");
//		super.event(ev);
		if (ev.type == EventType.MousePressed)
		{
			System.out.printf("SketchPanel.MousePressed x=%d y=%d\n", ((MouseEvent)ev).x, ((MouseEvent)ev).y);
			if (getContentPane().getBounds().contains(((MouseEvent)ev).x, ((MouseEvent)ev).y))
			{
				System.out.printf("SketchPanel.add line\n");
				points = new LinkedList<Point>();
				newPoint = new Point(((MouseEvent)ev).x, ((MouseEvent)ev).y);
				points.add(newPoint);
				lines.add(points);
				invalidate(true);
			}
		}
		if (ev.type == EventType.MouseDragged)
		{
			System.out.printf("SketchPanel.MouseDragged x=%d y=%d\n", ((MouseEvent)ev).x, ((MouseEvent)ev).y);
			if (getContentPane().getBounds().contains(((MouseEvent)ev).x, ((MouseEvent)ev).y))
			{
				System.out.printf("SketchPanel.add points x=%d y=%d\n", ((MouseEvent)ev).x, ((MouseEvent)ev).y);
				lastPoint = newPoint;
				newPoint = new Point(((MouseEvent)ev).x, ((MouseEvent)ev).y);
				points.add(newPoint);
				int left = Math.min(lastPoint.x, newPoint.x);
				int top = Math.min(lastPoint.y, newPoint.y);
				int width = Math.abs(lastPoint.x - newPoint.x);
				int height = Math.abs(lastPoint.y - newPoint.y);
				repaint(new Rectangle(left -5 , top -5 , width + 10, height + 10), true);
			}
		}
		else if (ev.type == EventType.MouseReleased)
		{
			if (getContentPane().getBounds().contains(((MouseEvent)ev).x, ((MouseEvent)ev).y))
			{
//				lines.add(points);
//				points.clear();
				
			}
		}
	}

}
