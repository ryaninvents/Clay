package koper;



/**
 *
 * @author notzed
 */
public class Event {

//	public Display display;
	public EventType type;
	public long when;
	
	public Event(Event ev)
	{
		type = ev.type;
		when = ev.when;
	}
	
	public Event(EventType type, long when) {
		this.type = type;
		this.when = when;
	}
}
