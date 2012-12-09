package koper;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Calendar;


public class DateField extends TextField 
{
	int day = 0;
	int month = 0;
	int year = 0;
	DatePickerDialog dpd = null;
	String months[] = Configuration.getMonthNames();
	
	public DateField(Rectangle bounds, int iDay, int iMonth, int iYear)
	{
		super(bounds, "");
		// TODO Auto-generated constructor stub
		setFont(new Font("Arial", Font.PLAIN,36));
		setAlignment(TextField.Alignment.CENTER);
		setDate(iDay, iMonth, iYear);
		showCursor = false;
		isEditable = false;
	}
	
	public DateField(Rectangle bounds, Calendar cal)
	{
		super(bounds, "");
		// TODO Auto-generated constructor stub
		setFont(new Font("Arial", Font.PLAIN,36));
		setAlignment(TextField.Alignment.CENTER);
		setDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
		showCursor = false;
		isEditable = false;
	}

	private void setDateInternally(int iDay, int iMonth, int iYear)
	{
		day = iDay;
		month = iMonth;
		year = iYear;
		setText(day + ". " + months[month] + " " + year);
	}
	
	// day   = 1 .. 31
	// month = 0 .. 11
	// year  = -max_int .. + max_int
	
	public void setDate(int iDay, int iMonth, int iYear)
	{
		setDateInternally(iDay, iMonth, iYear);
		notifyChangeListeners();
		invalidate(true);
	}
	
	public int getDay()
	{
		return day;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void setDialog(DatePickerDialog dlg)
	{
		dpd = dlg;
	}
	
	public void focus()
	{
		super.focus();
		if (dpd != null)
			dpd.showModal();
	}
	

	

}
