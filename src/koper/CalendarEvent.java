package koper;
import java.util.Calendar;


public class CalendarEvent
{
	String uid;
	Calendar start;
	Calendar end;
	String description;
	String summary;
	static int uidCounter = 1;
	
	public CalendarEvent(String summary, String description, Calendar start, Calendar end)
	{
		this(summary, description, start, end, String.valueOf(uidCounter));
		uidCounter++;
		
	}
	
	public CalendarEvent(String summary, String description, Calendar start, Calendar end, String uid)
	{
		if (summary != null)
			this.summary = summary;
		else
			this.summary = "";
		if (description != null)
			this.description = description;
		else
			this.description = "";
		this.start = start;
		this.end = end;
		this.uid = uid;
	}
	
	public boolean isValid()
	{
		if (end.after(start))
			return true;
		else
			return false;
	}
	
	public  String getUID()
	{
		return uid;
	}

}
