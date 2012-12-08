import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;



public class SimpleComponentTestPanel extends AppletPanel implements ButtonListener
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
	String months[] = Configuration.getMonthNames();
	int currentMonth = 10; // 0 .. 11
	DateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
	GregorianCalendar eventDate;
	Button testButton = null;
	TimePickerDialog tpd = null;
	TimeField startTimeField = null;
	TimeField endTimeField = null;
	DateField startDateField = null;
	DateField endDateField = null;
	MenuItem monthViewItem = null;
	MenuItem weekViewItem = null;
	MenuItem dayViewItem = null;
	MenuItem minusItem = null;
	MenuItem plusItem = null;
	MenuItem labelItem = null;
	MenuBar menuBar = null;
	Label swipeLabel = null;
	
	public SimpleComponentTestPanel() 
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
		
		java.net.URL url = null;
//		System.out.println("Button: imageName: " + imageName + " url: " + url);
	    BufferedImage image = null;
		try 
		{
			url = this.getClass().getResource("calendar_month_view_black.png");
			image = ImageIO.read(url);
			monthViewItem = new MenuItem(image);
			url = this.getClass().getResource("calendar_week_view_black.png");
			image = ImageIO.read(url);
			weekViewItem = new MenuItem(image);
			url = this.getClass().getResource("calendar_day_view_black.png");
			image = ImageIO.read(url);
			dayViewItem = new MenuItem(image);
			url = this.getClass().getResource("left_black.png");
			image = ImageIO.read(url);
			minusItem = new MenuItem(image);
			url = this.getClass().getResource("right_black.png");
			image = ImageIO.read(url);
			plusItem = new MenuItem(image);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		labelItem = new MenuItem("November", 320);
		labelItem.setColor(Color.BLACK);
		
		if (!Configuration.isBlackAndWhite)
			getContentPane().setBgColor(Color.LIGHT_GRAY);
		
		menuBar = new MenuBar();
		menuBar.add(minusItem);
		menuBar.add(labelItem);
		menuBar.add(plusItem);
		menuBar.add(monthViewItem);
		menuBar.add(new MenuSpacer());
		menuBar.add(weekViewItem);
		menuBar.add(new MenuSpacer());
		menuBar.add(dayViewItem);
		menuBar.setBgColor(Color.WHITE);
		menuBar.setColor(Color.BLACK);
//		setMenuBar(menuBar);
		
		getContentPane().add(new Label(new Rectangle(10, 10, 80, 20), "Summary", Label.Alignment.LEFT));
		summary = new TextField(new Rectangle(10, 30, 580, 30), "");
		getContentPane().add(summary);
		
		testButton = new Button(new Rectangle(10,100,200,40), "Test Button");
		testButton.addButtonListener(this);
		getContentPane().add(testButton);
		
		swipeLabel = new Label(new Rectangle(10,200,200, 40), "no swipe");
		getContentPane().add(swipeLabel);
		
//		getContentPane().add(new Label(new Rectangle(10, 60, 80, 20), "Start", Label.Alignment.LEFT));
//		startDateField = new DateField(new Rectangle(10, 80, 390, 60), eventDate);
//		startDateField.setDialog(new DatePickerDialog("Select start date", startDateField));
//		getContentPane().add(startDateField);
//		startTimeField = new TimeField(new Rectangle(410, 80, 180, 60), startHour, startMinute);
//		startTimeField.setDialog(new TimePickerDialog("Select start time", startTimeField));
//		getContentPane().add(startTimeField);
//		
//		getContentPane().add(new Label(new Rectangle(10, 150, 80, 20), "End", Label.Alignment.LEFT));
//		endDateField = new DateField(new Rectangle(10, 170, 390, 60), eventDate);
//		endDateField.setDialog(new DatePickerDialog("Select end date", endDateField));
//		getContentPane().add(endDateField);
//		endTimeField = new TimeField(new Rectangle(410, 170, 180, 60), endHour, endMinute);
//		endTimeField.setDialog(new TimePickerDialog("Select end time", endTimeField));
//		getContentPane().add(endTimeField);
//				
//		getContentPane().add(new Label(new Rectangle(10, 240, 80, 20), "Description", Label.Alignment.LEFT));
		description = new TextArea(new Rectangle(10, 260, bounds.width - 20, 200), "wwwwwwwwwwwwwwwwwwwww wwwwwwwwwwww wwww mkjhfghiu fgihe dsfhe dfiue asdifu");
		getContentPane().add(description);
		getContentPane().setFocus(description);
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

	public void event(Event ev)
	{
		if (ev instanceof SwipeEvent)
		{
			swipeLabel.setText(ev.type.toString());
			if (ev.type == EventType.SwipeLeft)
			{
				if (currentMonth < 11)
				{
					currentMonth++;
					labelItem.setText(months[currentMonth]);
					labelItem.invalidate(true);
				}
			}
			else if (ev.type == EventType.SwipeRight)
			{
				if (currentMonth > 0)
				{
					currentMonth--;
					labelItem.setText(months[currentMonth]);
					labelItem.invalidate(true);
				}
			}

			
		}
		else 
		{
//			swipeLabel.setText("no swipe");
			super.event(ev);
		}
	}


	@Override
	public void buttonClicked(Button source) 
	{
		// TODO Auto-generated method stub
		summary.event(new KeyEvent(EventType.KeyTyped, 0, 92));
	}


	@Override
	public void buttonLongClicked(Button source) {
		// TODO Auto-generated method stub
		
	}

}
