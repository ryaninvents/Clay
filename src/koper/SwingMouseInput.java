package koper;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.MouseInputListener;



public class SwingMouseInput extends SwingEventInput implements MouseInputListener
{

	long timedown = 0;
	
	public void addEventListener(MouseEventListener el)
	{
		super.addEventListener(el);
	}
	
	Point start = null;
	long startTime = 0;
	Point end = null;
	long releaseTime = 0;
	
	public SwipeEvent recognize(Point start, long startTime, Point end, long releaseTime)
	{
		double distance = Math.sqrt(Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2));
		double acceleration = 2.0 * distance / Math.pow(releaseTime - startTime, 2);
		double velocity = distance / (releaseTime - startTime);
		double gradient = 0.0f;
		int dx = end.x - start.x;
		int dy = end.y - start.y;
		if (dx != 0)
			gradient = dy / dx;
		System.out.printf("====== gradient: %.2f distance: %.2f acceleration: %.2f velocity: %.2f\n", gradient, distance, acceleration, velocity);
		if (distance > Configuration.swipeThreshold)
		{
			if (Math.abs(dx) < Math.abs(dy))
				// up or down
			{
				if (dy > 0)
					// swipe down
					return new SwipeEvent(EventType.SwipeDown, releaseTime);
				else
					// swipe up
					return new SwipeEvent(EventType.SwipeUp, releaseTime);				
			}
			else
				// left or right
			{
				if (dx < 0)
					// swipe left
					return new SwipeEvent(EventType.SwipeLeft, releaseTime);
				else
					// swipe right
					return new SwipeEvent(EventType.SwipeRight, releaseTime);				

			}
		}
		return null;
				
	}
	
	// native swing mouseClicked event will not be processed
	// instead a mouseClicked and mouseLongClicked event, respectively, will be fired depending
	// on the period (> Configuration.longClickThreshold) the mouse button was pressed
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugSwingEvents)
			System.out.println("mouseClicked");
//		event(new MouseEvent(EventType.MouseClicked, System.currentTimeMillis(), e.getX(), e.getY() - 30));
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugSwingEvents)
		System.out.println("mousePressed");
		timedown = System.currentTimeMillis();
		start = new Point(e.getX(), e.getY());
		startTime = timedown;
		event(new MouseEvent(EventType.MousePressed, timedown, e.getX(), e.getY()));
		
	}
	

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e)
	{
		long timeup = System.currentTimeMillis();
		// TODO Auto-generated method stub
		if (Configuration.debugSwingEvents)
		System.out.println("mouseReleased");
		event(new MouseEvent(EventType.MouseReleased, timeup , e.getX(), e.getY()));
		end = new Point(e.getX(), e.getY());
		releaseTime = timeup;
		SwipeEvent se = recognize(start, startTime, end, releaseTime);
		if (se != null)
		{
			event(se);
			if (Configuration.debugSwingEvents)
				System.out.println(se.type);
		}
		else
		{
			if (timeup - timedown > Configuration.longClickThreshold)
			{
				event(new MouseEvent(EventType.MouseLongClicked, timeup , e.getX(), e.getY()));
				if (Configuration.debugSwingEvents)
					System.out.println("mouseLongClicked");
			}
			else
			{
				event(new MouseEvent(EventType.MouseClicked, timeup , e.getX(), e.getY()));
				if (Configuration.debugSwingEvents)
					System.out.println("mouseClicked");
			}
		}	
			
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugSwingEvents)
		System.out.printf("mouseEntered x=%d, y=%d\n", e.getX(), e.getY());
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugSwingEvents)
		Logger.getLogger(getClass().getName()).log(Level.INFO, null);
		
	}

	@Override
	public void mouseDragged(java.awt.event.MouseEvent e)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugSwingEvents)
		System.out.println("mouseDragged");
		Logger.getLogger(getClass().getName()).log(Level.INFO, null);
		timedown = System.currentTimeMillis();
		event(new MouseEvent(EventType.MouseDragged, timedown, e.getX(), e.getY()));
		
	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent e)
	{
		// TODO Auto-generated method stub
//		System.out.println("mouseMoved");
//		Logger.getLogger(getClass().getName()).log(Level.INFO, null);
		
	}

}
