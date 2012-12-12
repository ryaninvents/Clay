package com.ramuller.clay.event.swing;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ramuller.clay.event.Event;
import com.ramuller.clay.event.EventListener;



public abstract class SwingEventInput
{
	private ArrayList<EventListener> eventListeners = new ArrayList<EventListener>();

	public void addEventListener(EventListener el) 
	{
		eventListeners.add(el);
	}

	public void removeEventListener(EventListener el) 
	{
		eventListeners.remove(el);
	}

	void event(Event ev) {
		Logger.getLogger(getClass().getName()).log(Level.INFO, String.valueOf(eventListeners.size()));
		for (int i = 0; i < eventListeners.size(); i++) 
		{
			try 
			{
				eventListeners.get(i).event(ev);
			} 
			catch (Throwable t) 
			{
				Logger.getLogger(getClass().getName()).log(Level.FINE, null, t);
			}
		}
	}


}
