import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;



public class CalendarPanel extends AppletPanel implements MenuItemListener, DateViewListener
{

	int monthHeaderHeight = 0;
	int dayOfWeekHeaderHeight = 30;
	int dayHeight;
	int weekDayHeight;
	int dayWidth;
	int weekDayWidth;
	String months[] = Configuration.getMonthNames();
	String monthsShort[] = Configuration.getMonthShortNames();
	String header = "";
	Calendar monthCal = null;
	Calendar displayedDate = null;
	Button forwardButton = null;
	Button backwardButton = null;
	MenuItem monthViewItem = null;
	MenuItem weekViewItem = null;
	MenuItem dayViewItem = null;
	MenuItem minusItem = null;
	MenuItem plusItem = null;
	MenuItem labelItem = null;
	MenuItem saveItem = null;
	MenuItem removeItem = null;
	MenuItem cancelItem = null;
	MenuItem editLabelItem = null;
	EventEditPanel editEventPanel = null;
	MenuBar menuBar = null;
	MenuBar eventPanelMenuBar = null;
	
	CalendarEventList cel = new CalendarEventList(); 
	XCalendar xCal = null; 
	enum CalendarViewState {YearView, MonthView, WeekView, DayView};
	CalendarViewState currentView = CalendarViewState.MonthView;
	
	public CalendarPanel()
	{
		super();
		if (!Configuration.isBlackAndWhite)
			setBgColor(new Color(208, 208, 208));
	}
	
	public void init()
	{
		// TODO Auto-generated constructor stub
		dayWidth = bounds.width / 7;
		displayedDate = new GregorianCalendar();
		displayedDate.set(Calendar.HOUR_OF_DAY, 0);
		displayedDate.set(Calendar.MINUTE, 0);
		displayedDate.set(Calendar.SECOND, 0);
		displayedDate.set(Calendar.MILLISECOND, 0);
		
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
			url = this.getClass().getResource("save.png");
			image = ImageIO.read(url);
			saveItem = new MenuItem(image);
			url = this.getClass().getResource("trash.png");
			image = ImageIO.read(url);
			removeItem = new MenuItem(image);
			url = this.getClass().getResource("cancel_outlined.png");
			image = ImageIO.read(url);
			cancelItem = new MenuItem(image);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		monthViewItem = new MenuItem("M");
		labelItem = new MenuItem("", 320);
		monthViewItem.addMenuItemListener(this);
		weekViewItem.addMenuItemListener(this);
		dayViewItem.addMenuItemListener(this);
		minusItem.addMenuItemListener(this);
//		plusItem = new MenuItem("+");
		plusItem.addMenuItemListener(this);
//		saveItem = new MenuItem(Configuration.getRessourceString("Save"));
		saveItem.addMenuItemListener(this);
//		removeItem = new MenuItem(Configuration.getRessourceString("Remove"));
		removeItem.addMenuItemListener(this);
//		cancelItem = new MenuItem(Configuration.getRessourceString("Cancel"));
		cancelItem.addMenuItemListener(this);
		editLabelItem = new MenuItem("", 220);
		
		menuBar = new MenuBar();
		menuBar.add(minusItem);
		menuBar.add(labelItem);
		menuBar.add(plusItem);
		menuBar.add(monthViewItem);
		menuBar.add(new MenuSpacer());
		menuBar.add(weekViewItem);
		menuBar.add(new MenuSpacer());
		menuBar.add(dayViewItem);
		
		eventPanelMenuBar = new MenuBar();
		eventPanelMenuBar.components.clear();
//		eventPanelMenuBar.add(minusItem);
//		eventPanelMenuBar.add(plusItem);
		eventPanelMenuBar.add(editLabelItem);
		eventPanelMenuBar.add(saveItem);
		eventPanelMenuBar.add(new MenuSpacer());
		eventPanelMenuBar.add(removeItem);
		eventPanelMenuBar.add(new MenuSpacer());
		eventPanelMenuBar.add(cancelItem);
		eventPanelMenuBar.setFont(new Font("Arial", Font.PLAIN, 20));
		
		xCal = new XCalendar("calendar.xml");
		update();
	}

	protected void update()
	{
		header = months[displayedDate.get(Calendar.MONTH)] + " " + String.valueOf( displayedDate.get(Calendar.YEAR));
		setMenuBar(menuBar);
		
		switch (currentView)
		{
			case DayView:
				initDayView();
				break;
			case WeekView:
				initWeekView();
				break;
			case MonthView:
				initMonthView();
		}
		
	}
	

	
	// one single day represented by a DateViev-Component
	protected void initDayView()
	{
		getContentPane().components.clear();
		header = Configuration.getWeekdayShortNames()[(displayedDate.get(Calendar.DAY_OF_WEEK) + 6) % 7] + " " + String.valueOf( displayedDate.get(Calendar.DAY_OF_MONTH)) + " " + header;
		labelItem.setText(header);
		//		Label l = new Label(new Rectangle(70, 0, 310, monthHeaderHeight), header, new Font("Arial", Font.BOLD, 24));
//		getMenuBar().add(l);
		DateView sdv = new DateView(new Rectangle(1 , 1, getContentPane().getBounds().width - 2, getContentPane().getBounds().height-16), displayedDate.get(Calendar.YEAR), displayedDate.get(Calendar.MONTH), displayedDate.get(Calendar.DAY_OF_MONTH), DateView.CalendarViewState.DayView, this);
		sdv.addEvents(xCal.getEvent(displayedDate));	
		getContentPane().add(sdv);
	}
	
	// 2 columns and 4 rows to cover 7 days of a week
	// every day is represented by a DateViev-Component
	protected void initWeekView()
	{
		getContentPane().components.clear();
		weekDayHeight = (bounds.height - monthHeaderHeight - dayOfWeekHeaderHeight);
		monthCal = (GregorianCalendar)displayedDate.clone();
		monthCal.set(Calendar.DAY_OF_WEEK, 2);
		GregorianCalendar tmpCal = (GregorianCalendar)monthCal.clone();
		GregorianCalendar todayCal = new GregorianCalendar();
		todayCal.setTime(new Date());
		GregorianCalendar startDate = (GregorianCalendar)monthCal.clone();
		GregorianCalendar endDate = (GregorianCalendar)monthCal.clone();
		endDate.add(Calendar.DAY_OF_MONTH, 6);
		if (startDate.get(Calendar.MONTH) == endDate.get(Calendar.MONTH))		
			header = String.valueOf(tmpCal.get(Calendar.DAY_OF_MONTH)) + " - " + String.valueOf(6 + tmpCal.get(Calendar.DAY_OF_MONTH)) + " " + months[displayedDate.get(Calendar.MONTH)] + " " + String.valueOf( displayedDate.get(Calendar.YEAR));
		else
			header = String.valueOf(startDate.get(Calendar.DAY_OF_MONTH)) + " " +  monthsShort[startDate.get(Calendar.MONTH)] + " - " + String.valueOf(endDate.get(Calendar.DAY_OF_MONTH)) + " " +  monthsShort[endDate.get(Calendar.MONTH)] +  " " + String.valueOf( displayedDate.get(Calendar.YEAR));
		labelItem.setText(header);
//		weekDayHeight = (getContentPane().getHeight() - dayOfWeekHeaderHeight -1) / 4;
		weekDayHeight = (getContentPane().getHeight() / 4) - 1;
		weekDayWidth = bounds.width / 2 - 1;
		for (int i = 0; i < 7; i++)
		{
			DateView dv = new DateView(new Rectangle(1 + i  / 4 * weekDayWidth, monthHeaderHeight + 1 + i % 4 * weekDayHeight, weekDayWidth, weekDayHeight), tmpCal.get(Calendar.YEAR), tmpCal.get(Calendar.MONTH), tmpCal.get(Calendar.DAY_OF_MONTH), DateView.CalendarViewState.WeekView, this);
			dv.setCurrentMonth(true);
			if (tmpCal.get(Calendar.DATE) == todayCal.get(Calendar.DATE))
				dv.setCurrentDate(true);
			dv.addEvents(xCal.getEvent(tmpCal));	
			getContentPane().add(dv);
			tmpCal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
	}
	
	// 7 columns for weekdays, 6 rows to cover all days in a month
	// every day is represented by a DateViev-Component
	protected void initMonthView()
	{
		getContentPane().components.clear();
		dayHeight = (getContentPane().getHeight() - dayOfWeekHeaderHeight -1) / 6;
		monthCal = (GregorianCalendar)displayedDate.clone();
		monthCal.set(Calendar.DAY_OF_MONTH, 1);
		GregorianCalendar tmpCal = (GregorianCalendar)monthCal.clone();
		GregorianCalendar todayCal = new GregorianCalendar();
		int dayOfWeek =  (monthCal.get(Calendar.DAY_OF_WEEK) + 5) % 7; 
		tmpCal.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
		labelItem.setText(header);
		Label l;
		for (int i = 0; i < 7; i++)
		{
			l = new Label(new Rectangle(1 + i * dayWidth, monthHeaderHeight + 10, dayWidth, dayOfWeekHeaderHeight), Configuration.getWeekdayShortNames()[(i + 1) % 7]);
			l.setAlignment(Label.Alignment.CENTER);
			getContentPane().add(l);
		}
		for (int i = 0; i < 42; i++)
		{
			DateView dv = new DateView(new Rectangle(1 + ((i ) % 7)* dayWidth, monthHeaderHeight + dayOfWeekHeaderHeight + ((i ) / 7) * dayHeight, dayWidth, dayHeight), tmpCal.get(Calendar.YEAR), tmpCal.get(Calendar.MONTH), tmpCal.get(Calendar.DAY_OF_MONTH), DateView.CalendarViewState.MonthView,this);
			if (tmpCal.get(Calendar.MONTH) == monthCal.get(Calendar.MONTH))
			{
				dv.setCurrentMonth(true);
				if (tmpCal.get(Calendar.DATE) == todayCal.get(Calendar.DATE))
					dv.setCurrentDate(true);
			}
			else
				dv.setCurrentMonth(false);
			dv.addEvents(xCal.getEvent(tmpCal));	
			getContentPane().add(dv);
			System.out.println("CalendarPanel.initMonthView: add DateView: " + dv.cal.get(Calendar.DAY_OF_MONTH));
			tmpCal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
	}
	
	public void paint(Graphics2D gg)
	{
		if (Configuration.debugPaint)
			System.out.println("CalendarPanel.paint");
		super.paint(gg);		
	}
	
	
	public void event(Event ev)
	{
		if (Configuration.debugEvents)
			System.out.println("CalendarPanel.MouseEvent");
		super.event(ev);
		if (ev.type == EventType.MouseClicked)
		{
			if (getContentPane().getBounds().contains(((MouseEvent)ev).x, ((MouseEvent)ev).y))
			{
				for (Component comp : getContentPane().components) 
				{
					//				System.out.printf("Bounds x%d y%d w%d h%d, contains x=%d, y=%d\n", comp.getBounds().x, comp.getBounds().y, comp.getBounds().width, comp.getBounds().height, ((MouseEvent)ev).x, ((MouseEvent)ev).y);
					if (comp.getBounds().contains(((MouseEvent)ev).x - getContentPane().getBounds().x, 
							((MouseEvent)ev).y - getContentPane().getBounds().y))
					{
						if (comp instanceof DateView)
						{
							System.out.printf("Day %d selected\n", ((DateView)comp).cal.get(Calendar.DAY_OF_MONTH));
							currentView = CalendarViewState.DayView;
							displayedDate.setTimeInMillis(((DateView)comp).cal.getTimeInMillis());
							update();
							repaint();
							break;
						}
						else
						{
							MouseEvent mev = (MouseEvent)ev;
							mev.x = mev.x - getContentPane().getBounds().x; 
							mev.y = mev.y - getContentPane().getBounds().y; 
							comp.event(mev);
							break;
						}
					}
				}
			}
		}
		else if (ev.type == EventType.MouseLongClicked)
		{
			if (getContentPane().getBounds().contains(((MouseEvent)ev).x, ((MouseEvent)ev).y))
			{
				for (Component comp : getContentPane().components) 
				{
					//				System.out.printf("Bounds x%d y%d w%d h%d, contains x=%d, y=%d\n", comp.getBounds().x, comp.getBounds().y, comp.getBounds().width, comp.getBounds().height, ((MouseEvent)ev).x, ((MouseEvent)ev).y);
					if (comp.getBounds().contains(((MouseEvent)ev).x - getContentPane().getBounds().x, 
							((MouseEvent)ev).y - getContentPane().getBounds().y))
					{
						if (comp instanceof DateView)
						{
							System.out.printf("Day %d selected\n", ((DateView)comp).cal.get(Calendar.DAY_OF_MONTH));
							currentView = CalendarViewState.DayView;
							displayedDate.setTimeInMillis(((DateView)comp).cal.getTimeInMillis());
							update();
							repaint();
							break;
						}
						else
						{
							MouseEvent mev = (MouseEvent)ev;
							mev.x = mev.x - getContentPane().getBounds().x; 
							mev.y = mev.y - getContentPane().getBounds().y; 
							comp.event(mev);
							break;
						}
					}
				}
			}
		}

	}



	@Override
	public void menuItemClicked(MenuItem source) 
	{
		// TODO Auto-generated method stub
		System.out.println("CalendarPanel.menuItemClicked");
		if (source == plusItem)
		{
			switch (currentView)
			{
			case MonthView:
				displayedDate.add(Calendar.MONTH, 1);
				break;
			case WeekView:
				displayedDate.add(Calendar.DAY_OF_MONTH, 7);
				break;
			case DayView:
				displayedDate.add(Calendar.DAY_OF_MONTH, 1);
				break;
			}
			update();
			repaint();
			
		} 
		else if (source == minusItem)
		{
			switch (currentView)
			{
			case MonthView:
				displayedDate.add(Calendar.MONTH, -1);
				break;
			case WeekView:
				displayedDate.add(Calendar.DAY_OF_MONTH, -7);
				break;
			case DayView:
				displayedDate.add(Calendar.DAY_OF_MONTH, -1);
				break;
			}
			update();
			repaint();
			
		}
		else if (source == monthViewItem)
		{
			currentView = CalendarViewState.MonthView;
			update();
			repaint();
			
		}
		else if (source == weekViewItem)
		{
			currentView = CalendarViewState.WeekView;
			update();
			repaint();
			
		}
		else if (source == dayViewItem)
		{
			currentView = CalendarViewState.DayView;
			update();
			repaint();
			
		}
		else if (source == saveItem)
		{
			if (Configuration.debugEvents)
				System.out.println("CalendarPanel.New event saved");
			CalendarEvent ce = editEventPanel.getEvent();
			if (ce.isValid())
			{
				if (xCal.getEventByUID(ce.uid) != null)
					xCal.removeEvent(ce.uid);
				xCal.addEvent(ce);
				xCal.save("calendar.xml");
				displayedDate = (Calendar)editEventPanel.event.start.clone();
				displayedDate.set(Calendar.HOUR_OF_DAY, 0);
				displayedDate.set(Calendar.MINUTE, 0);
				displayedDate.set(Calendar.SECOND, 0);
				displayedDate.set(Calendar.MILLISECOND, 0);
				
				currentView = CalendarViewState.DayView;
				hideKeyboard(false);
				update();
				repaint();
			}
			else
			{
				System.out.println("Start is after end");
				InformationDialog id = new InformationDialog("Warning", "Start date/time is after End date/time" );
				id.showModal();
			}
			
		}
		else if (source == cancelItem)
		{
			if (Configuration.debugEvents)
				System.out.println("CalendarPanel.New event canceled");
			currentView = CalendarViewState.DayView;
			displayedDate = (Calendar) editEventPanel.event.start.clone();
			displayedDate.set(Calendar.HOUR_OF_DAY, 0);
			displayedDate.set(Calendar.MINUTE, 0);
			displayedDate.set(Calendar.SECOND, 0);
			displayedDate.set(Calendar.MILLISECOND, 0);
			hideKeyboard(false);
			update();
			repaint();		
		}
		else if (source == removeItem)
		{
			if (Configuration.debugEvents)
				System.out.println("CalendarPanel.event removed");
			CalendarEvent ce = editEventPanel.getEvent();
			if (xCal.getEventByUID(ce.uid) != null)
				xCal.removeEvent(ce.uid);
			xCal.save("calendar.xml");
			displayedDate = (Calendar) editEventPanel.event.start.clone();
			displayedDate.set(Calendar.HOUR_OF_DAY, 0);
			displayedDate.set(Calendar.MINUTE, 0);
			displayedDate.set(Calendar.SECOND, 0);
			displayedDate.set(Calendar.MILLISECOND, 0);
			currentView = CalendarViewState.DayView;
			hideKeyboard(false);
			update();
			repaint();	
		}		
	}


	@Override
	public void actionPerformed(DateView source) 
	{
		// TODO Auto-generated method stub
		if (Configuration.debugEvents)
			System.out.println("CalendarPanel.ActionPerformed");
		if (source.getSelectedEvent() != null)
		{
			showKeyboard(false);
			editEventPanel = new EventEditPanel(getContentPane().bounds, source.getSelectedEvent().ce);
			setContentPane(editEventPanel);
			vKeyboard.addKeyEventListener(editEventPanel);
			header = monthsShort[source.cal.get(Calendar.MONTH)] + " " + String.valueOf(source.cal.get(Calendar.YEAR));
			header = Configuration.getWeekdayShortNames()[(source.cal.get(Calendar.DAY_OF_WEEK) + 6) % 7] + ", " + String.valueOf( source.cal.get(Calendar.DAY_OF_MONTH)) + " " + header;
			editLabelItem.setText(Configuration.getRessourceString("EditEvent"));
			setMenuBar(eventPanelMenuBar);
			repaint();			
		}
		else
		{
			currentView = CalendarViewState.DayView;
			displayedDate.setTimeInMillis(source.cal.getTimeInMillis());
			update();
			repaint();
		}
	}


	@Override
	public void newEvent(DateView source) {
		// TODO Auto-generated method stub
		if (Configuration.debugEvents)
			System.out.println("CalendarPanel.newEvent");
		showKeyboard(false);
		Calendar start = (Calendar) source.cal.clone();
		start.set(Calendar.HOUR_OF_DAY, 8);
		start.set(Calendar.MINUTE, 0);
		Calendar end = (Calendar) source.cal.clone();
		end.set(Calendar.HOUR_OF_DAY, 10);
		end.set(Calendar.MINUTE, 0);
		CalendarEvent ce = new CalendarEvent("summary", "description", start, end);
		editEventPanel = new EventEditPanel(getContentPane().bounds, ce);
//		getContentPane().components.clear();
//		getContentPane().add(newEventPanel);
		setContentPane(editEventPanel);
		vKeyboard.addKeyEventListener(editEventPanel);
		header = monthsShort[source.cal.get(Calendar.MONTH)] + " " + String.valueOf(source.cal.get(Calendar.YEAR));
		header = Configuration.getWeekdayShortNames()[(source.cal.get(Calendar.DAY_OF_WEEK) + 6) % 7] + ", " + String.valueOf( source.cal.get(Calendar.DAY_OF_MONTH)) + " " + header;
		editLabelItem.setText(Configuration.getRessourceString("NewEvent"));
		setMenuBar(eventPanelMenuBar);
		repaint();
		
	}

	public void processSwipeEvent(SwipeEvent ev) 
	{
		// TODO Auto-generated method stub
		if (ev.type == EventType.SwipeLeft)
		{
			switch (currentView)
			{
			case MonthView:
				displayedDate.add(Calendar.MONTH, 1);
				break;
			case WeekView:
				displayedDate.add(Calendar.DAY_OF_MONTH, 7);
				break;
			case DayView:
				displayedDate.add(Calendar.DAY_OF_MONTH, 1);
				break;
			}
			update();
			repaint();
			
		}
		else if (ev.type == EventType.SwipeRight)
		{
			switch (currentView)
			{
			case MonthView:
				displayedDate.add(Calendar.MONTH, -1);
				break;
			case WeekView:
				displayedDate.add(Calendar.DAY_OF_MONTH, -7);
				break;
			case DayView:
				displayedDate.add(Calendar.DAY_OF_MONTH, -1);
				break;
			}
			update();
			repaint();
		}
		
	}

}
