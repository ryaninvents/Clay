package com.mypapyri.clay.event;

/*
 *  Copyright (c) 2012 Michael Zucchi
 *
 *  This file is part of ReaderZ, a Java e-ink reader application.
 *
 *  ReaderZ is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ReaderZ is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ReaderZ.  If not, see <http://www.gnu.org/licenses/>.
 */


//import au.notzed.gadgetz.Display;
//import au.notzed.gadgetz.Event;
//import au.notzed.gadgetz.EventType;
//import au.notzed.gadgetz.MouseEvent;
import java.awt.Event;
import java.awt.Point;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;


/**
 * Read the touch input on the kobo.
 * @author notzed
 */
public class KoboTouchInput extends EventInput{

	// drag threshold - anything less than this is a click
	static final int dragThreshold = 15;
	static final int swipeThreshold = 100;
	static final int longClickThreshold = 1000;
	
	/*
	Event type 0 (Sync)
	Event type 1 (Key)
	Event code 258 (Btn2)
	Event code 330 (Touch)
	Event type 3 (Absolute)
	Event code 0 (X)
	Value    308
	Min        0
	Max     1200
	Event code 1 (Y)
	Value    328
	Min        0
	Max     1600
	Event code 16 (Hat0X)
	Value      0
	Min        0
	Max     1200
	Event code 17 (Hat0Y)
	Value      0
	Min        0
	Max     1600
	Event code 24 (Pressure)
	Value      0
	Min        0
	Max     2048
	 * 
	 */
	
	ReadableByteChannel event;
	ByteBuffer data = ByteBuffer.allocate(16).order(ByteOrder.nativeOrder());
	// initial press location - for filtering out small drags
	boolean dragged = false;
	boolean portrait = true;
	int xdown;
	int ydown;
	long timedown;
	// current state
	public int xpos = 0;
	public int ypos = 0;
	int press = 0; // not sure exactly what this does
	public int touch;
	//
	boolean mouse1 = false;
	Event lastEvent;
	LinkedList<Event> synthesised = new LinkedList<Event>();

	public KoboTouchInput(String path, boolean portrait) throws IOException 
	{
		super(path);
		this.portrait = portrait;
	}

	Point start = null;
	long startTime = 0;
	Point end = null;
	long releaseTime = 0;
	
	

	
	@Override
	public Event readEvent() throws IOException 
	{
		if (!synthesised.isEmpty()) 
		{
			return synthesised.poll();
		}

		boolean sync = false;
		int xout = xpos;
		int yout = ypos;
		int pout = press;
		do 
		{
			switch (readRawEvent()) 
			{
				case 0:
					sync = true;
					break;
				case 1:
					switch (code) 
					{
						case 258:
							break;
						case 330:
							touch = value;
							break;
					}
					break;
				case 3:
					switch (code) 
					{
						case 0: // x
							xout = value;
							break;
						case 1: // y
							yout = value;
							break;
						case 24: // pressure
							pout = value;
							break;
					}
			}

		} while (!sync);

		if (touch > 0) 
		{
			xpos = xout;
			ypos = yout;
			press = pout;
		}

		EventType eventType = null;
		SwipeEvent se = null;
		if (mouse1) 
		{
			if (touch == 0) 
			{
				
				long timeup = time_s * 1000 + time_us / 1000;
				eventType = EventType.MouseReleased;
				mouse1 = false;
				EventType synthEventType;
				if (portrait)
					end = new Point(600 - ypos, xpos);
				else
					end = new Point(xpos, ypos);
				releaseTime = timeup;
				se = recognize(start, startTime, end, releaseTime);
				if (se != null)
				{
					synthesised.add(se);					
				}
				else
				// not a swipe
				{
					if (timeup - timedown > longClickThreshold)
						synthEventType = EventType.MouseLongClicked;
					else
						synthEventType = EventType.MouseClicked;

					// Create clicked events if the finger didn't move (enough)
					if (!dragged) 
					{
						if (portrait)
							synthesised.add(new TouchEvent(synthEventType, time_s * 1000 + time_us / 1000, 600 - ypos, xpos));
						else
							synthesised.add(new TouchEvent(synthEventType, time_s * 1000 + time_us / 1000, xpos, ypos));
					}
				}
			} 
//			else if (dragged) 
//			{
//				eventType = EventType.MouseDragged;
//				mouse1 = true;
//			} 
//			else if (Math.abs(xdown - xpos) >= dragThreshold
//					|| Math.abs(ydown - ypos) >= dragThreshold) 
//			{
//				dragged = true;
//				eventType = EventType.MouseDragged;
//				mouse1 = true;
//			}
		} 
		else 
		{
			if (touch == 1) 
			{
				eventType = EventType.MousePressed;
				mouse1 = true;
				dragged = false;
				xdown = xpos;
				ydown = ypos;
				timedown = time_s * 1000 + time_us / 1000;
				if (portrait)
					start = new Point(600 - ypos, xpos);
				else
					start = new Point(xpos, ypos);
				startTime = timedown;
			}
		}

		if (eventType == null) {
			return null;
		}

		// convert to mouse event
		if (portrait)
		{
//			int ypos1 = ypos;
//			ypos = xpos;
//			xpos = 600 - ypos1;
			lastEvent = new TouchEvent(eventType, time_s * 1000 + time_us / 1000, 600 - ypos, xpos);
		}
		else
			lastEvent = new TouchEvent(eventType, time_s * 1000 + time_us / 1000, xpos, ypos);
		return lastEvent;
	}



	@Override
	public void fireEvent(Event ev) {
		// TODO Auto-generated method stub
		
	}
}
