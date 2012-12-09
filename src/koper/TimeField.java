package koper;
import java.awt.Font;
import java.awt.Rectangle;


public class TimeField extends TextField 
{
	Integer hour = 0;
	Integer minutes = 0;
	TimePickerDialog tpd = null;
	
	public TimeField(Rectangle bounds, int iHour, int iMinutes)
	{
		super(bounds, "");
		// TODO Auto-generated constructor stub
		setFont(new Font("Arial", Font.PLAIN,36));
		setAlignment(TextField.Alignment.CENTER);
		setTimeInternally(iHour, iMinutes);
		showCursor = false;
		isEditable = false;
	}
	
	private void setTimeInternally(int iHour, int iMinutes)
	{
		hour = iHour;
		minutes = iMinutes;
		String strHour = "0" + hour.toString();
		String strMinutes = "0" + minutes.toString();
		setText(strHour.substring(strHour.length() - 2) + ":" + strMinutes.substring(strMinutes.length() - 2));		
	}
	
	public void setTime(int iHour, int iMinutes)
	{
		setTimeInternally(iHour, iMinutes);
		notifyChangeListeners();
		invalidate(true);
	}
	
	public void setDialog(TimePickerDialog dlg)
	{
		tpd = dlg;
	}
	
	public Integer getHour()
	{
		return hour;
	}
	
	public Integer getMinutes()
	{
		return minutes;
	}

	public void focus()
	{
		super.focus();
		if (tpd != null)
			tpd.showModal();
	}
		

}
