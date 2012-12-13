package com.ramuller.clay.event.swing;

import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.MouseInputListener;

import com.ramuller.clay.event.EventType;
import com.ramuller.clay.event.SwipeEvent;
import com.ramuller.clay.event.TouchEvent;
import com.ramuller.clay.event.TouchEventListener;



public class SwingMouseInput extends SwingEventInput implements MouseInputListener
{

	static final int dragThreshold = 15;
	static final int swipeThreshold = 100;
	static final int longClickThreshold = 1000;
	long timedown = 0;
	
	public void addEventListener(TouchEventListener el)
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
		if (distance > swipeThreshold)
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
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e)
	{
		timedown = System.currentTimeMillis();
		start = new Point(e.getX(), e.getY());
		startTime = timedown;
		event(new TouchEvent(EventType.MousePressed, timedown, e.getX(), e.getY()));
		
	}
	

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e)
	{/*
		long timeup = System.currentTimeMillis();
		event(new TouchEvent(EventType.MouseReleased, timeup , e.getX(), e.getY()));
		end = new Point(e.getX(), e.getY());
		releaseTime = timeup;
		SwipeEvent se = recognize(start, startTime, end, releaseTime);
		if (se != null)
		{
			event(se);
		}
		else
		{
			if (timeup - timedown > longClickThreshold)
			{
				event(new TouchEvent(EventType.MouseLongClicked, timeup , e.getX(), e.getY()));
			}
			else
			{
				event(new TouchEvent(EventType.MouseClicked, timeup , e.getX(), e.getY()));
			}
		}	
			
		*/
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e)
	{		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e)
	{
	}

	@Override
	public void mouseDragged(java.awt.event.MouseEvent e)
	{
		/*Logger.getLogger(getClass().getName()).log(Level.INFO, null);
		timedown = System.currentTimeMillis();
		event(new TouchEvent(EventType.MouseDragged, timedown, e.getX(), e.getY()));
		*/
	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent e)
	{
		// TODO Auto-generated method stub
//		System.out.println("mouseMoved");
//		Logger.getLogger(getClass().getName()).log(Level.INFO, null);
		
	}

}
