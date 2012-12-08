import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class GestureRecognizer implements EventListener 
{

	private ArrayList<EventListener> eventListeners = new ArrayList<EventListener>();
	
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
	
	@Override
	public void event(Event ev) 
	{
		// TODO Auto-generated method stub
		System.out.print(getClass().getName() + ".event: ");
		if (ev instanceof MouseEvent)
		{
			System.out.printf("type=%s, x=%d, y=%d\n",  ev.type.toString(), ((MouseEvent)ev).x, ((MouseEvent)ev).y );
			switch (ev.type)
			{
				case MousePressed:
					start = new Point(((MouseEvent)ev).x, ((MouseEvent)ev).y);
					startTime = ev.when;
					break;
				case MouseReleased:
					end = new Point(((MouseEvent)ev).x, ((MouseEvent)ev).y);
					releaseTime = ev.when;
					SwipeEvent se = recognize(start, startTime, end, releaseTime);
					if (se != null)
						notifyListeners(se);
					break;
				case MouseClicked:
					break;
				case MouseLongClicked:
					break;
			}
		}
		
	}

	public void addSwipeListener(EventListener el) 
	{
		eventListeners.add(el);
		
	}
	void notifyListeners(Event ev) 
	{
		System.out.print(getClass().getName() + ".notifyListeners: ");
		for (int i = 0; i < eventListeners.size(); i++) 
		{
			try 
			{
				eventListeners.get(i).event(ev);
			} 
			catch (Throwable t) 
			{
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, t);
			}
		}
	}

}
