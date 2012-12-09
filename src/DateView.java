import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;


public class DateView extends ComponentContainer
{
	
	Calendar cal;
	List<CalendarEvent> events = new LinkedList<CalendarEvent>();
	boolean currentMonth = true;
	boolean currentDate = false;
	boolean maximized = false;
	boolean hourDivider = false;
	boolean dateVisible = true;
	int hourHeight = 44;
	DateViewListener dvListener;
	enum CalendarViewState {YearView, MonthView, WeekView, DayView};
	CalendarViewState cvs;
	EventView selectedEvent = null;
	
	
	public DateView(Rectangle bounds, int year, int month, int day, DateViewListener l)
	{
		this(bounds, year, month, day, CalendarViewState.MonthView, l);
	}
	
	public DateView(Rectangle bounds, int year, int month, int day, CalendarViewState cvs)
	{
		super(bounds);
		cal = new GregorianCalendar(Configuration.getLocale());
		cal.set(year, month, day, 0, 0, 0);
		this.cvs = cvs;
	}
	
	public DateView(Rectangle bounds, int year, int month, int day, CalendarViewState cvs, DateViewListener l)
	{
		super(bounds);
		cal = new GregorianCalendar(Configuration.getLocale());
		cal.set(year, month, day, 0, 0, 0);
		this.cvs = cvs;
		dvListener = l;
		initView();
	}
	
	public void initView()
	{
		switch (cvs)
		{
			case DayView:
				initDayView();
				break;
			case WeekView:
				initWeekView();
				break;
		}		
	}
	
	public long d2ms(int d)
	{
		return d * 24 * 60 * 60 * 1000;
	}
	
	public long h2ms(int h)
	{
		return h * 60 * 60 * 1000;
	}
	
	public long m2ms(int m)
	{
		return m * 60 * 1000;
	}
	
	public int ms2h(long ms)
	{
		return (int) (ms / (60 * 60 * 1000));
	}
	
	public int ms2m(long ms)
	{
		return (int) (ms / (60 * 1000));
	}
	
	public void initDayView()
	{
		components.clear();
		int x = 55;
		hourHeight = bounds.height / 16;
		for (CalendarEvent ce : events)
		{
			long startTime = ce.start.getTimeInMillis();
			long endTime = ce.end.getTimeInMillis();
			int startHour = ce.start.get(Calendar.HOUR_OF_DAY);
			int startMinute = ce.start.get(Calendar.MINUTE);
			int endHour = ce.end.get(Calendar.HOUR_OF_DAY);
			int endMinute = ce.end.get(Calendar.MINUTE);
			int startY = 0;
			int endY = 0;
			EventView ev;
			if ((startTime < cal.getTimeInMillis()) && (endTime > cal.getTimeInMillis()))
			// event neither starts nor ends today
			{
				startY = 0;
				endY = 16 * hourHeight;
			}
			if (endTime < cal.getTimeInMillis() + 86400000)
			// event ends today 
			{
				if ((endHour > 7) && (endHour < 21))
				{
					endY = (((endHour-7)*60 + endMinute) * hourHeight) / 60;
				}
				else if (endHour < 7)
				{
					endY = hourHeight;
				}
				else if (endHour > 21)
				{
					endY = 14 * hourHeight + (endHour - 21) * 2 * hourHeight / 3;
				}
			}
			else
			// event ends at a later day
			{
				endY = 16 * hourHeight;
			}
			if (startTime > cal.getTimeInMillis())
			// event starts today
			{
				if ((startHour > 7) && (startHour < 21))
				{
					startY = (((startHour-7)*60 + startMinute) * hourHeight) / 60;
				}
				else  if (startHour <= 7)
				{
					startY = startHour * hourHeight / 7;
				}
				else
				{
					startY = 14 * hourHeight + (startHour - 21) * 2 * hourHeight / 3;
				}
			}
			else
			// event started an earlier day
			{
				startY = 0;
			}
			ev = new EventView(new Rectangle(x, startY, 90, endY - startY), ce);
			add(ev);
			x = x + 90;
		}
	}
	
	public void initWeekView()
	{
		
	}
	
	public void addEvent(String summary, String description, Calendar start, Calendar end)
	{
		events.add(new CalendarEvent(summary, description, start, end));
		initDayView();
	}
	
	public void addEvents(List<CalendarEvent> eventList)
	{
		events.addAll(eventList);
		initDayView();
	}
	
	public void setCurrentDate(boolean curDate)
	{
		currentDate = curDate;
	}
	
	public void setCurrentMonth(boolean curMonth)
	{
		currentMonth = curMonth;
	}
	
	public void setHourDivider(boolean b)
	{
		hourDivider = b;
	}
	
	public void hideDate()
	{
		dateVisible = false;
	}
	
	protected void paintDayView(Graphics2D gg)
	{
		System.out.println("SingleDateView.paint");
		gg.setFont(new Font("Arial", Font.PLAIN, 14));
		gg.setStroke(new BasicStroke(1));
		gg.setColor(Color.WHITE);
		gg.fillRect(0, 0, bounds.width, bounds.height);
		gg.setColor(Color.black);
		gg.drawRect(0, 0, bounds.width, bounds.height);
		gg.setFont(new Font("Arial", Font.PLAIN, 14));
		gg.drawString("0-7", 10, 15);
		for (int i = 1; i < 14; i++)
		{
			gg.drawLine(0, i * hourHeight, bounds.width, i* hourHeight);
			gg.drawString(String.valueOf(i+7), 10, i * hourHeight + 15);
		}
		gg.drawLine(0, 14 * hourHeight, bounds.width, 14 * hourHeight);
		gg.drawString("21-24", 10, 14 * hourHeight + 15);
		paintChildren(gg);
		
	}
	
	protected void paintNormalView(Graphics2D gg)
	{
		System.out.println("DateView.paintNormalView: " + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		gg.setFont(new Font("Arial", Font.PLAIN, 14));
		gg.setStroke(new BasicStroke(1));
		if (!currentMonth)
		{
			gg.setColor(new Color(192,192,192));
		}
		else
		{
			gg.setColor(Color.WHITE);
		}
			
		gg.fillRect(0, 0, bounds.width, bounds.height);
		if (currentDate)
		{
			gg.setColor(new Color(192,192,192));
			gg.fillRect(0, 0, bounds.width, 20);
		}
		gg.setColor(Color.black);
		gg.drawRect(0, 0, bounds.width, bounds.height);
		String dateString = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		if (cvs == CalendarViewState.WeekView)
			dateString = Configuration.getWeekdayShortNames()[(cal.get(Calendar.DAY_OF_WEEK) + 6) % 7] + " "  + dateString;
		if (dateVisible)
			gg.drawString(dateString, 5, 15);
		int y = 30;
		gg.setFont(new Font("Arial", Font.PLAIN, 10));
		
		if (hourDivider)
		{
			gg.setColor(new Color(224,224,224));
//			gg.drawString("0-7", 10, 15);
			for (int i = 1; i < 14; i++)
			{
				gg.drawLine(0, i * hourHeight, bounds.width, i* hourHeight);
//				gg.drawString(String.valueOf(i+7), 10, i * hourHeight + 15);
			}
			gg.drawLine(0, 14 * hourHeight, bounds.width, 14 * hourHeight);
//			gg.drawString("21-24", 10, 14 * hourHeight + 15);
			gg.setColor(Color.black);
			for (CalendarEvent ce : events)
			{
				int startHour = ce.start.get(Calendar.HOUR_OF_DAY);
				int startMinute = ce.start.get(Calendar.MINUTE);
				String eventTimeString = i2s(startHour) + ":" + i2s(startMinute);
//				String eventTimeString = String.valueOf(startHour) + "-" + String.valueOf(endHour);
				y = 0;
				if ((startHour > 7) && (startHour < 21))
					y = (startHour - 7) * hourHeight + 15;
				else if (startHour <= 7)
					y = 15;
				else 
					y = 14 * hourHeight + 15;
				gg.drawString(eventTimeString, 5, y);
				gg.drawString(ce.summary, 5, y + 15);
			}

		}
		else
		{
			for (CalendarEvent ce : events)
			{
				String time = i2s(ce.start.get(Calendar.HOUR_OF_DAY)) + ":" + i2s(ce.start.get(Calendar.MINUTE));
				gg.drawString(time, 5, y);
				gg.drawString(ce.summary, 5, y + 15);
				y = y + 30;

			}
		}		
	}
	
	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugPaint)
			System.out.println("DateView.paint");
		switch (cvs)
		{
			case DayView:
				paintDayView(gg);
				break;
			case MonthView:
			case WeekView:
				paintNormalView(gg);
				break;
			default:
			
		}
	}

	private String i2s(int i)
	{
		String s = "0"+String.valueOf(i);
		return s.substring(s.length()-2);
	}
	
	public EventView getEventView(int x, int y)
	{
		Rectangle rect = null;
		for (Component comp : components) 
		{
			if (Configuration.debugEvents)
				System.out.printf(comp.getClass().getName() + " bounds x%d y%d w%d h%d, contains x=%d, y=%d\n", comp.getBounds().x, comp.getBounds().y, comp.getBounds().width, comp.getBounds().height, x, y);
			rect = comp.getBounds();
			if (rect.contains(x, y))
			{
				if (Configuration.debugEvents)
					System.out.println("EventView.getEventView: event found!");
				return (EventView)comp;
			}
		}
		return null;
		
	}
	
	public EventView getSelectedEvent()
	{
		return selectedEvent;
	}

	public void event(Event ev)
	{
		System.out.println("DateView.event");
		if (ev.type == EventType.MouseClicked)
		{
			if (cvs == CalendarViewState.DayView)
			{
				selectedEvent = getEventView(((MouseEvent)ev).x, ((MouseEvent)ev).y);
			}
			if (dvListener != null)
				dvListener.actionPerformed(this);
			
		} else if (ev.type == EventType.MouseLongClicked)
		{
			if (dvListener != null)
				dvListener.newEvent(this);
			
		}
	}
	
}
