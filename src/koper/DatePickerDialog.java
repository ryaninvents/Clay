package koper;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class DatePickerDialog extends ModalDialog implements ButtonListener, MenuItemListener
{

	ToggleKey dayKeys[] = new ToggleKey[31];
	ToggleKey monthKeys[] = new ToggleKey[12];
	ToggleKey yearKeys[] = new ToggleKey[10];
	
	MenuItem setItem = null;
	MenuItem todayItem = null;
	MenuItem cancelItem = null;
	MenuBar menuBar = null;
	
	TextField dateTextField = null;
	MenuItem label = null;
	int day = 0;
	int month = 0;
	int year = 0;
	int currentYear = 0;
	DateField dateField = null;
	String months[] = Configuration.getMonthNames();
	
	public DatePickerDialog(String s, DateField df) 
	{
		super();
		// TODO Auto-generated constructor stub
//		updateDateFields();
		day = df.getDay();
		month = df.getMonth(); // 0 .. 11;
		year = df.getYear();
		currentYear = year;
		menuBar = new MenuBar();
		dateField = df;
		if (!Configuration.isBlackAndWhite)
			getContentPane().setBgColor(Color.LIGHT_GRAY);
		
		label = new MenuItem(s, 300);
		label.setFont(new Font("Arial", Font.BOLD, 28));
		label.setColor(Color.WHITE);
		menuBar.add(label);
		
		dateTextField = new TextField(new Rectangle(100, 10, 420, 60), day + ". " + months[month] + " " + year);
		dateTextField.setFont(new Font("Arial", Font.BOLD,36));
		dateTextField.setAlignment(TextField.Alignment.CENTER);
		dateTextField.hideCursor();
		getContentPane().add(dateTextField);
		
//		Label dayLabel = new Label(new Rectangle(100, 80, 100, 60), "Tag");
//		dayLabel.setFont(new Font("Arial", Font.BOLD, 28));
//		dayLabel.setColor(Color.WHITE);
//		getContentPane().add(dayLabel);
//		
//		Label monthLabel = new Label(new Rectangle(100, 445, 100, 60), "Monat");
//		monthLabel.setFont(new Font("Arial", Font.BOLD, 28));
//		monthLabel.setColor(Color.WHITE);
//		getContentPane().add(monthLabel);
//		
//		Label yearLabel = new Label(new Rectangle(460, 80, 100, 60), "Jahr");
//		yearLabel.setFont(new Font("Arial", Font.BOLD, 28));
//		yearLabel.setColor(Color.WHITE);
//		getContentPane().add(yearLabel);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		int maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 0; i < 31; i++)
		{
			dayKeys[i] = new ToggleKey(new Rectangle(20 + (i % 7) * 60, 100 + (i / 7) * 60, 55, 55), String.valueOf(i+1));
			dayKeys[i].addButtonListener(this);
			if (i < maxDaysInMonth)
				dayKeys[i].visible = true;
			else
				dayKeys[i].visible = false;
				
			getContentPane().add(dayKeys[i]);
			
		}
		dayKeys[day - 1].setToggled(true);
		
		for (int i = 0; i < 5; i++)
		{
			yearKeys[i] = new ToggleKey(new Rectangle(460 , 100 + (i) * 60, 120, 55), String.valueOf(currentYear + i));
			yearKeys[i].addButtonListener(this);
			getContentPane().add(yearKeys[i]);
		}
		yearKeys[year - currentYear].setToggled(true);
		
		for (int i = 0; i < 12; i++)
		{
			monthKeys[i] = new ToggleKey(new Rectangle(20 + (i % 3) * 188, 425 + (i / 3) * 60, 183, 55), months[i]);
			monthKeys[i].addButtonListener(this);
			getContentPane().add(monthKeys[i]);
		}
		monthKeys[month].setToggled(true);
		
//		setButton = new Button(new Rectangle(20, 650, 160, 60), "Set");
//		setButton.addButtonListener(this);
//		getContentPane().add(setButton);
//		
//		nowButton = new Button(new Rectangle(220, 650, 160, 60), "Now");
//		nowButton.addButtonListener(this);
//		getContentPane().add(nowButton);
//		
//		cancelButton = new Button(new Rectangle(420, 650, 160, 60), "Cancel");
//		cancelButton.addButtonListener(this);
//		getContentPane().add(cancelButton);
		
		setItem = new MenuItem("Set");
		setItem.addMenuItemListener(this);
		menuBar.add(setItem);
		menuBar.add(new MenuSpacer());
		todayItem = new MenuItem("Now");
		todayItem.addMenuItemListener(this);
		menuBar.add(todayItem);
		menuBar.add(new MenuSpacer());
		cancelItem = new MenuItem("Cancel");
		cancelItem.addMenuItemListener(this);
		menuBar.add(cancelItem);
		menuBar.setVisible();
		setMenuBar(menuBar);
		
	}
	
	protected void updateDateFields()
	{
		GregorianCalendar cal = new GregorianCalendar();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH)+1;
		year = cal.get(Calendar.YEAR);
	}

	@Override
	public void buttonClicked(Button source) 
	{
		// TODO Auto-generated method stub
		System.out.println("VirtualKeyboard.buttonClicked: " + source.buttonText);
		for (int i = 0; i < 31; i++)
		{
			if (dayKeys[i].equals(source))
			{
				if (day != Integer.valueOf(dayKeys[i].buttonText))
				{
					dayKeys[day - 1].setToggled(false);
					day = Integer.valueOf(dayKeys[i].buttonText);
					dayKeys[day - 1].setToggled(true);
					dateTextField.setText(day + ". " + months[month] + " " + year);
					dateTextField.invalidate(true);}

			}
		}
		for (int i = 0; i < 12; i++)
		{
			if (monthKeys[i].equals(source))
			{
				if (month != i )
				{
					monthKeys[month].setToggled(false);
					month = i;
					monthKeys[i].setToggled(true);
					Calendar cal = Calendar.getInstance();
					cal.set(year, month, 1);
					int maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					for (int j = 0; j < 31; j++)
					{
						if (j < maxDaysInMonth)
							dayKeys[j].setVisible();
						else
							dayKeys[j].hide();
					}
					if (day > maxDaysInMonth)
					{
						dayKeys[day - 1].setToggled(false);
						day = maxDaysInMonth;
						dayKeys[day - 1].setToggled(true);
					}
					dateTextField.setText(day + ". " + months[month] + " " + year);
					dateTextField.invalidate(true);
				}
			}
		}
		for (int i = 0; i < 5; i++)
		{
			if (yearKeys[i].equals(source))
			{
				if (year != Integer.valueOf(yearKeys[i].buttonText))
				{
					yearKeys[year - Integer.valueOf(yearKeys[0].buttonText)].setToggled(false);
					year = Integer.valueOf(yearKeys[i].buttonText);
					yearKeys[i].setToggled(true);
					dateTextField.setText(day + ". " + months[month] + " " + year);
					dateTextField.invalidate(true);
				}
			}
		}
//		if (nowButton.equals(source))
//		{
//			updateDateFields();
//			dateTextField.setText(day + ". " + months[month-1] + " " + year);			
//			dateTextField.invalidate(true);
//		}
//		else if (setButton.equals(source))
//		{
//			dateField.setDate(day, month, year);
//			closeDialog();
//		}
//		else if (cancelButton.equals(source))
//		{
//			closeDialog();
//		}
		
	}

	@Override
	public void buttonLongClicked(Button source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuItemClicked(MenuItem source) 
	{
		// TODO Auto-generated method stub
		if (todayItem.equals(source))
		{
			updateDateFields();
			dateTextField.setText(day + ". " + months[month-1] + " " + year);			
			dateTextField.invalidate(true);
		}
		else if (setItem.equals(source))
		{
			dateField.setDate(day, month, year);
			closeDialog();
		}
		else if (cancelItem.equals(source))
		{
			closeDialog();
		}
		
		
	}

}
