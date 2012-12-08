import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class CalendarEventList {
	
	List<CalendarEvent> events = new LinkedList<CalendarEvent>();
	
	

	public CalendarEventList()
	{
		
	}
	
	public void add(CalendarEvent event)
	{
		events.add(event);
	}
	
	public void add(String summary, String description, Calendar start, Calendar end)
	{
		events.add(new CalendarEvent(summary, description, start, end));
	}
	
	
	public List<CalendarEvent> getEvent(Calendar date)
	{
		List<CalendarEvent> tmpEvents = new LinkedList<CalendarEvent>();
		for (CalendarEvent event : events)
			if (
				((event.start.getTimeInMillis() >= date.getTimeInMillis()) &&
				(event.start.getTimeInMillis() < (date.getTimeInMillis()+86400000))) ||
				((event.end.getTimeInMillis() >= date.getTimeInMillis()) &&
				(event.end.getTimeInMillis() < (date.getTimeInMillis()+86400000))) ||
				((event.start.getTimeInMillis() < date.getTimeInMillis()) &&
				(event.end.getTimeInMillis() > (date.getTimeInMillis()+86400000)))
				)
				tmpEvents.add(event);
		
		return tmpEvents;
	}
	
	public List<CalendarEvent> getList()
	{
		return events;
	}
	
}
