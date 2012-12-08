import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Calendar;



public class EventView extends Component 
{

	CalendarEvent ce = null;
	
	public EventView(Rectangle bounds, CalendarEvent ev)
	{
		super(bounds);
		ce = ev;
	}
	
	private String i2s(int i)
	{
		String s = "0"+String.valueOf(i);
		return s.substring(s.length()-2);
	}
	
	public void paint(Graphics2D gg)
	{
		System.out.println("EventView.paint");
		int startHour = ce.start.get(Calendar.HOUR_OF_DAY);
		int startMinute = ce.start.get(Calendar.MINUTE);
		long duration = ce.end.getTimeInMillis() - ce.start.getTimeInMillis();
		int endHour = ce.end.get(Calendar.HOUR_OF_DAY);
		int endMinute = ce.end.get(Calendar.MINUTE);
		String eventTimeString = i2s(startHour) + ":" + i2s(startMinute);
		if (duration > 5 * 60 * 1000)
		// more than 5 minutes
			eventTimeString = eventTimeString + "-" + i2s(endHour) + ":" + i2s(endMinute);
		FontMetrics metrics = gg.getFontMetrics();
		gg.setColor(Color.WHITE);
		gg.fillRect(0, 0, bounds.width, bounds.height);
		gg.setColor(Color.BLACK);
		gg.drawRect(0, 0, bounds.width, bounds.height);
		if (bounds.height > 2 * metrics.getHeight())
		{
			gg.drawString(eventTimeString, 2, 15);
			gg.drawString(ce.summary, 2, 30);
		}
		else if (startHour <= 7)
			gg.drawString(eventTimeString + "  " + ce.summary, 2, 15);
		else 
			gg.drawString(eventTimeString + "  " + ce.summary, 2, 15);

	}


}
