import java.awt.Color;
import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class ComponentTestPanel extends AppletPanel implements ButtonListener
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
	GregorianCalendar eventDate;
	Button pickTimeButton;
	TimePickerDialog tpd = null;
	TimeField startTimeField = null;
	TimeField endTimeField = null;
	DateField startDateField = null;
	DateField endDateField = null;
	Label swipeLabel = null;
	
	public ComponentTestPanel() 
	{
		super();
		// TODO Auto-generated constructor stub
//		start = new TimePicker(new Rectangle(100, 210, 100, 120));
//		add(start);
		this.eventDate = new GregorianCalendar();
		startHour = 8;
		startMinute = 0;
		endHour = 9;
		endMinute = 0;
		duration = endHour * 60 + endMinute - startHour * 60 - startMinute;
		
		getContentPane().setBgColor(Color.LIGHT_GRAY);
		getContentPane().add(new Label(new Rectangle(10, 10, 80, 20), "Summary", Label.Alignment.LEFT));
		summary = new TextField(new Rectangle(10, 30, 580, 30), "");
		getContentPane().add(summary);
		
		getContentPane().add(new Label(new Rectangle(10, 60, 80, 20), "Start", Label.Alignment.LEFT));
		startDateField = new DateField(new Rectangle(10, 80, 390, 60), eventDate);
		startDateField.setDialog(new DatePickerDialog("Select start date", startDateField));
		getContentPane().add(startDateField);
		startTimeField = new TimeField(new Rectangle(410, 80, 180, 60), startHour, startMinute);
		startTimeField.setDialog(new TimePickerDialog("Select start time", startTimeField));
		getContentPane().add(startTimeField);
		
		getContentPane().add(new Label(new Rectangle(10, 150, 80, 20), "End", Label.Alignment.LEFT));
		endDateField = new DateField(new Rectangle(10, 170, 390, 60), eventDate);
		endDateField.setDialog(new DatePickerDialog("Select end date", endDateField));
		getContentPane().add(endDateField);
		endTimeField = new TimeField(new Rectangle(410, 170, 180, 60), endHour, endMinute);
		endTimeField.setDialog(new TimePickerDialog("Select end time", endTimeField));
		getContentPane().add(endTimeField);
				
		getContentPane().add(new Label(new Rectangle(10, 240, 80, 20), "Description", Label.Alignment.LEFT));
		description = new TextArea(new Rectangle(10, 260, 580, 200), "");
		getContentPane().add(description);
		
		swipeLabel = new Label(new Rectangle(10,200,200, 40), "no swipe");
		getContentPane().add(swipeLabel);
		
//		getContentPane().setFocus(summary);
		showKeyboard(true);
//		pickTimeButton = new Button(new Rectangle(10,10, 180, 60), "pick time");
//		pickTimeButton.addButtonListener(this);
//		add(pickTimeButton);
		vKeyboard.addKeyEventListener(getContentPane());
		
	}

	
	public CalendarEvent getEvent() {
		// TODO Auto-generated method stub
		GregorianCalendar startCal = new GregorianCalendar();
		GregorianCalendar endCal = new GregorianCalendar();
		
		startCal.set(eventDate.get(Calendar.YEAR), 
					 eventDate.get(Calendar.MONTH), 
					 eventDate.get(Calendar.DAY_OF_MONTH), 
					 startHour, 
					 startMinute);
		endCal.set(eventDate.get(Calendar.YEAR), 
		 	 	   eventDate.get(Calendar.MONTH), 
				   eventDate.get(Calendar.DAY_OF_MONTH), 
				   endHour, 
				   endMinute);
		return new CalendarEvent(summary.fieldText, 
								 description.fieldText, 
								 startCal, 
								 endCal);
	}


	@Override
	public void buttonClicked(Button source) 
	{
		// TODO Auto-generated method stub
		if (source == pickTimeButton)
		{
			System.out.println("pick time");
//			TimePickerDialog tpd = new TimePickerDialog(new Rectangle(0,0,600,800), "Select start time");
//			tpd.showModal();
		};

	}


	@Override
	public void buttonLongClicked(Button source) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void event(Event ev)
	{
		if (ev instanceof SwipeEvent)
		{
			swipeLabel.setText(ev.type.toString());
			
		}
		else 
		{
			swipeLabel.setText("no swipe");
			super.event(ev);
		}
	}

}
