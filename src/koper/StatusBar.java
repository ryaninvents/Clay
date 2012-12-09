package koper;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;


public class StatusBar extends ComponentContainer {

	BufferedImage batteryFull = null;
	BufferedImage linkQuality100 = null;
	
	public StatusBar(Rectangle bounds) {
		super(bounds);
		this.bgColor = Color.WHITE;
		// TODO Auto-generated constructor stub
		try 
		{
			java.net.URL url = this.getClass().getResource("battery_full.png");
			batteryFull = ImageIO.read(url);
			url = this.getClass().getResource("link_quality_75.png");
			linkQuality100 = ImageIO.read(url);
		} 
		catch (IOException e) 
		{
			System.out.println("Button: Image battery_full not found!");
		}	
	}
	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugPaint)
			System.out.println("StatusBar.paint");
		gg.setColor(bgColor);
		gg.fillRect(0, 0, bounds.width, bounds.height);
		gg.setColor(Color.BLACK);
		gg.setStroke(new BasicStroke(1));
		gg.drawLine(0, bounds.height-1, bounds.width, bounds.height-1);
		gg.drawImage(batteryFull, 2 , 2 , 20, 20, null);
		gg.drawImage(linkQuality100, 25 , 2 , 20, 20, null);

		gg.setFont(new Font("Arial", Font.BOLD, 16));
		GregorianCalendar cal = new GregorianCalendar();
		FontMetrics metrics = null;
		metrics = gg.getFontMetrics();
		String curHour = "0"+ cal.get(Calendar.HOUR_OF_DAY);
		String curMinute = "0"+ cal.get(Calendar.MINUTE);
		String curTime = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Configuration.locale) + ", " + 
						 cal.get(Calendar.DAY_OF_MONTH) + ". " + 
					     cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Configuration.locale) + ", " + 
					     curHour.substring(curHour.length()-2) + ":" + 
					     curMinute.substring(curMinute.length()-2);
		Rectangle textBounds = metrics.getStringBounds(curTime, gg).getBounds();
		gg.drawString(curTime, bounds.width - 10 -textBounds.width, (bounds.height + textBounds.height) / 2 - 4);
	}


}
