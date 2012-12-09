package koper;
import java.awt.Color;
import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class EventEditPanel extends ContentPane implements ChangeListener
{

	TextField summary = null;
	TextArea description = null;
	TextField startTime = null;
	TextField endTime = null;
	TimePicker start = null;
	int startHour;
	int startMinute;
	int endHour;
	int endMinute;
	int duration;
	DateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
	CalendarEvent event;
	Button pickTimeButton;
	TimePickerDialog tpd = null;
	TimeField startTimeField = null;
	TimeField endTimeField = null;
	DateField startDateField = null;
	DateField endDateField = null;
	
	public EventEditPanel(Rectangle bounds, CalendarEvent event) 
	{
		super(bounds);
		// TODO Auto-generated constructor stub
//		start = new TimePicker(new Rectangle(100, 210, 100, 120));
//		add(start);
		this.event = event;
		startHour = event.start.get(Calendar.HOUR_OF_DAY);
		startMinute = event.start.get(Calendar.MINUTE);
		endHour = event.end.get(Calendar.HOUR_OF_DAY);
		endMinute = event.end.get(Calendar.MINUTE);
		duration = endHour * 60 + endMinute - startHour * 60 - startMinute;
		
		add(new Label(new Rectangle(10, 10, 80, 20), "Summary", Label.Alignment.LEFT));
		summary = new TextField(new Rectangle(10, 30, bounds.width - 20, 30), event.summary);
		add(summary);
		
		add(new Label(new Rectangle(10, 60, 80, 20), "Start", Label.Alignment.LEFT));
		startDateField = new DateField(new Rectangle(10, 80, 390, 60), event.start);
		startDateField.setDialog(new DatePickerDialog("Select start date", startDateField));
		startDateField.addChangeListener(this);
		add(startDateField);
		startTimeField = new TimeField(new Rectangle(410, 80, 180, 60), startHour, startMinute);
		startTimeField.setDialog(new TimePickerDialog("Select start time", startTimeField));
		startTimeField.addChangeListener(this);
		add(startTimeField);
		
		add(new Label(new Rectangle(10, 150, 80, 20), "End", Label.Alignment.LEFT));
		endDateField = new DateField(new Rectangle(10, 170, 390, 60), event.end);
		endDateField.setDialog(new DatePickerDialog("Select end date", endDateField));
		endDateField.addChangeListener(this);
		add(endDateField);
		endTimeField = new TimeField(new Rectangle(410, 170, 180, 60), endHour, endMinute);
		endTimeField.setDialog(new TimePickerDialog("Select end time", endTimeField));
		endTimeField.addChangeListener(this);
		add(endTimeField);
				
		add(new Label(new Rectangle(10, 240, 80, 20), "Description", Label.Alignment.LEFT));
		description = new TextArea(new Rectangle(10, 260, 580, 200), event.description);
		add(description);
		setFocus(summary);
		if (!Configuration.isBlackAndWhite)
			setBgColor(Color.LIGHT_GRAY);
		
	}

	
	public CalendarEvent getEvent() {
		// TODO Auto-generated method stub
		GregorianCalendar startDateTime = new GregorianCalendar();
		GregorianCalendar endDateTime = new GregorianCalendar();
		
		startDateTime.set(startDateField.getYear(), 
						  startDateField.getMonth(), 
						  startDateField.getDay(), 
						  startTimeField.getHour(), 
						  startTimeField.getMinutes());
		endDateTime.set(endDateField.getYear(), 
						endDateField.getMonth(), 
						endDateField.getDay(), 
						endTimeField.getHour(), 
						endTimeField.getMinutes());
		return new CalendarEvent(summary.fieldText, 
								 description.fieldText, 
								 startDateTime, 
								 endDateTime,
								 event.getUID());
	}


	@Override
	public void notify(Component source) 
	{
		// TODO Auto-generated method stub
		System.out.println("EventEditPanel.notify:");
		if (source == startDateField)
		{
			System.out.println("startDateField change:" + startDateField.getDay() + "." + startDateField.getMonth() + "." + startDateField.getYear());
			
		}
		else if (source == endDateField)
		{
			System.out.println("endDateField change:" + endDateField.getDay() + "." + endDateField.getMonth() + "." + endDateField.getYear());
			
		}
		else if (source == startTimeField)
		{
			System.out.println("startTimeField change:" + startTimeField.getHour() + ":" + startTimeField.getMinutes());
			
		}
		else if (source == endTimeField)
		{
			System.out.println("endTimeField change:" + endTimeField.getHour() + ":" + endTimeField.getMinutes());
			
		}

	}



}
