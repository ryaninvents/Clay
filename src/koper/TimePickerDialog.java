package koper;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class TimePickerDialog extends ModalDialog implements ButtonListener
{

	VirtualKey hourKeys[] = new VirtualKey[24];
	VirtualKey minuteKeys[] = new VirtualKey[12];
	Button setButton = null;
	Button nowButton = null;
	Button cancelButton = null;
	TextField timeTextField = null;
	MenuItem label = null;
	MenuBar menuBar = null;
	Integer hour = 0;
	Integer minutes = 0;
	TimeField timeField = null;
	
	public TimePickerDialog(String s, TimeField tf) 
	{
		super();
		// TODO Auto-generated constructor stub
//		updateTimeFields();
		timeField = tf;
		hour = tf.getHour();
		minutes = tf.getMinutes();
		if (!Configuration.isBlackAndWhite)
			getContentPane().setBgColor(Color.LIGHT_GRAY);
		
		menuBar = new MenuBar();
		label = new MenuItem(s, 600);
		label.setFont(new Font("Arial", Font.BOLD, 28));
		label.setColor(Color.WHITE);
		menuBar.add(label);
		
		timeTextField = new TextField(new Rectangle(200, 50, 200, 60), mh2s(hour, minutes));
		timeTextField.setFont(new Font("Arial", Font.PLAIN,36));
		timeTextField.setAlignment(TextField.Alignment.CENTER);
		timeTextField.hideCursor();
		getContentPane().add(timeTextField);
		
		Label hourLabel = new Label(new Rectangle(100, 120, 100, 60), "Stunde");
		hourLabel.setFont(new Font("Arial", Font.BOLD, 28));
		hourLabel.setColor(Color.WHITE);
		getContentPane().add(hourLabel);
		
		Label minuteLabel = new Label(new Rectangle(400, 120, 100, 60), "Minuten");
		minuteLabel.setFont(new Font("Arial", Font.BOLD, 28));
		minuteLabel.setColor(Color.WHITE);
		getContentPane().add(minuteLabel);
		
		for (int i = 0; i < 24; i++)
		{
			hourKeys[i] = new VirtualKey(new Rectangle(22 + (i / 6) * 60 + (i / 12) * 20, 205 + (i % 6) * 60, 55, 55), String.valueOf(i));
			hourKeys[i].addButtonListener(this);
			getContentPane().add(hourKeys[i]);
			
		}
		for (int i = 0; i < 12; i++)
		{
			minuteKeys[i] = new VirtualKey(new Rectangle(392 + (i / 6) * 60, 205 + (i % 6) * 60, 55, 55), String.valueOf(i*5));
			minuteKeys[i].addButtonListener(this);
			getContentPane().add(minuteKeys[i]);
		}
		setButton = new Button(new Rectangle(20, 600, 160, 60), "Set");
		setButton.addButtonListener(this);
		getContentPane().add(setButton);
		
		nowButton = new Button(new Rectangle(220, 600, 160, 60), "Now");
		nowButton.addButtonListener(this);
		getContentPane().add(nowButton);
		
		cancelButton = new Button(new Rectangle(420, 600, 160, 60), "Cancel");
		cancelButton.addButtonListener(this);
		getContentPane().add(cancelButton);
		menuBar.setVisible();
		setMenuBar(menuBar);
		
	}
	
	protected void updateTimeFields()
	{
		GregorianCalendar cal = new GregorianCalendar();
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minutes = cal.get(Calendar.MINUTE);
	}

	public String mh2s(Integer hour, Integer minutes)
	{
		String strHour = "0" + hour.toString();
		String strMinutes = "0" + minutes.toString();
		return strHour.substring(strHour.length() - 2) + ":" + strMinutes.substring(strMinutes.length() - 2);
	}
	
	@Override
	public void buttonClicked(Button source) 
	{
		// TODO Auto-generated method stub
		System.out.println("VirtualKeyboard.buttonClicked: " + source.buttonText);
		for (int i = 0; i < 24; i++)
		{
			if (hourKeys[i].equals(source))
			{
				hour = Integer.valueOf(hourKeys[i].buttonText);
				timeTextField.setText(mh2s(hour, minutes));
				timeTextField.invalidate(true);
			}
		}
		for (int i = 0; i < 12; i++)
		{
			if (minuteKeys[i].equals(source))
			{
				minutes = Integer.valueOf(minuteKeys[i].buttonText);
				timeTextField.setText(mh2s(hour, minutes));
				timeTextField.invalidate(true);
			}
		}
		if (nowButton.equals(source))
		{
			updateTimeFields();
			timeTextField.setText(mh2s(hour, minutes));			
			timeTextField.invalidate(true);
		}
		else if (setButton.equals(source))
		{
			timeField.setTime(Integer.valueOf(hour), Integer.valueOf(minutes));
			closeDialog();
		}
		else if (cancelButton.equals(source))
		{
			closeDialog();
		}		
	}

	@Override
	public void buttonLongClicked(Button source) {
		// TODO Auto-generated method stub
		
	}

}
