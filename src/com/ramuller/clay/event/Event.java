package com.ramuller.clay.event;

/**
*
* @author notzed
*/
public class Event {

//	public Display display;
	private EventType type;
	private long when;
	
	public Event(Event ev)
	{
		setType(ev.getType());
		setWhen(ev.getWhen());
	}
	
	public Event(EventType type, long when) {
		this.setType(type);
		this.setWhen(when);
	}
	
	public String describe(){
		return getType().toString();
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public long getWhen() {
		return when;
	}

	public void setWhen(long when) {
		this.when = when;
	}
}
