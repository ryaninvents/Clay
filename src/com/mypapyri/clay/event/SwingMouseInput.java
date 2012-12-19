package com.mypapyri.clay.event;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import com.mypapyri.clay.ClaySystem;


public class SwingMouseInput extends EventInput<TouchEvent,TouchEventListener> implements MouseInputListener
{

	long timedown = 0;
	
	
	Point start = null;
	long startTime = 0;
	Point end = null;
	long releaseTime = 0;
	
	
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
		fireEvent(new TouchEvent(timedown, e.getX(), e.getY(),TouchEvent.MOUSE_PRESSED));
		
	}
	

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e)
	{
		long timeup = System.currentTimeMillis();
		fireEvent(new TouchEvent(timeup , e.getX(), e.getY(),TouchEvent.MOUSE_RELEASED));
		end = new Point(e.getX(), e.getY());
		releaseTime = timeup;
		if (timeup - timedown > ClaySystem.getLongClickThreshold())
		{
			fireEvent(new TouchEvent(timeup , e.getX(), e.getY(),TouchEvent.MOUSE_LONG_CLICKED));
			
		}
		else
		{
			fireEvent(new TouchEvent(timeup , e.getX(), e.getY(),TouchEvent.MOUSE_CLICKED));
		}
			
		
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

	@Override
	public void readEvent() throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void fireEvent(TouchEvent ev) {
		if (ClaySystem.isLandscape())
			ev = ev.toLandscape();
		
		int type = ev.getModifiers();
		ArrayList<TouchEventListener> listeners = getListeners();

		for (TouchEventListener l : listeners) {

			switch (type) {
			case TouchEvent.MOUSE_CLICKED:
				l.onTap(ev);
				break;
			case TouchEvent.MOUSE_LONG_CLICKED:
				l.onLongTap(ev);
				break;
			case TouchEvent.MOUSE_PRESSED:
				l.onTouchDown(ev);
				break;
			case TouchEvent.MOUSE_RELEASED:
				l.onTouchUp(ev);
				break;
			case TouchEvent.MOUSE_DRAGGED:
				l.onDrag(ev);
				break;
			}
		}
	}

}
