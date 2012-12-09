package koper;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;

import javax.imageio.ImageIO;


import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;


public class WeatherPanel extends AppletPanel 
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
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
	String observationTime = "";
	String temperature = "";
	String dataFilename = Configuration.getDirectory() + File.separator + "weather.xml";
	String temperatureUnit = Configuration.getRessourceString("Temperature Unit");
	
	Font font = new Font("Arial", Font.PLAIN, 14);
	Font temperatureFont = new Font("Arial", Font.PLAIN, 64);
	Font temperatureForecastFont = new Font("Arial", Font.PLAIN, 48);
	Font descriptionFont = new Font("Arial", Font.PLAIN, 24);
	Font descriptionForecastFont = new Font("Arial", Font.PLAIN, 20);
	
	WeatherCondition weatherCon = null;
	LinkedList <WeatherCondition> weatherForcasts = new LinkedList<WeatherCondition>();
	HashMap<String, BufferedImage> weatherIcons= new HashMap<String, BufferedImage>();
	
	String weatherPropFilename = "weather.prop";
	Properties weatherProperties = new Properties();
	String APIKey = "";
	
	
	public WeatherPanel(Rectangle bounds) 
	{
		super();		
	}

	public void init()
	{
		loadProperties();
		if (!Configuration.isBlackAndWhite)
			getContentPane().setBgColor(Color.WHITE);
		java.net.URL url = null;
		BufferedImage skc = null;
		for (String s : Configuration.weatherIcons.keySet())
		{
		try 
		{
			url = this.getClass().getResource(Configuration.weatherIcons.get(s));
			skc = ImageIO.read(url);
			weatherIcons.put(s, skc);
		} 
		catch (IOException e) 
		{
			System.out.println("WeatherPanel: Image " + s + " not found!");
		}
		}
		File checkFile = new File(dataFilename);
		if (!checkFile.exists())
			getUpdate();
		processXML();
		
		
	}
	
	public void loadProperties()
	{
		File idFile = new File(Configuration.getDirectory() + File.separator + weatherPropFilename);
		if (idFile.exists())
		{
			try 
			{
				FileInputStream fis = new FileInputStream(idFile);

				weatherProperties.load(fis);
				APIKey = weatherProperties.getProperty("APIKey", "xxxx");
			} 
			catch (IOException e) 
			{
				// Exception bearbeiten
			}		
		}
		else
		{
			weatherProperties.setProperty("APIKey", "xxxx");
		}

	}
	
	
	public void getUpdate()
	{
		String requestUrl = "http://free.worldweatheronline.com/feed/weather.ashx?q=brussels&format=xml&num_of_days=4&key=" + weatherProperties.getProperty("APIKey");
		System.out.println(requestUrl);
		URL wwo = null;
		try {
			wwo = new URL(requestUrl);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        BufferedReader in;
        BufferedWriter out;
		try 
		{
			in = new BufferedReader(new InputStreamReader(wwo.openStream()));
			out = new BufferedWriter(new FileWriter(dataFilename));
			String line;
			while ((line = in.readLine()) != null)
			{
				out.write(line);
			}
			in.close();
			out.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void processXML()
	{
		File dataFile = new File(dataFilename);
		if (dataFile.exists())
		{
			FileInputStream fis;
			try {
				fis = new FileInputStream(dataFilename);
				Source source = new Source(fis);
				net.htmlparser.jericho.Element currentConditionElem = source.getFirstElement("current_condition");
				weatherCon = new WeatherCondition(currentConditionElem);
				weatherCon.print();
				ArrayList <Element> weatherForcElems = (ArrayList<Element>) source.getAllElements("weather");

				for (Element e : weatherForcElems)
				{
					WeatherCondition weatherForc = new WeatherCondition(e);
					weatherForcasts.add(weatherForc);
					weatherForc.print();
				}

				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void paint(Graphics2D gg)
	{
		super.paint(gg);
		gg.setColor(Color.BLACK);
		FontMetrics metrics = null;
		Rectangle textBounds;
		if (weatherCon != null)
		{
			gg.setFont(new Font("Arial", Font.PLAIN, 28));
			metrics = gg.getFontMetrics();
			// today
			gg.setFont(font);
			metrics = gg.getFontMetrics();
			gg.drawString(Configuration.ressourceStrings.get("Today"), 10, 40);
			gg.drawString("Aktuell ", 30, 120);
			//		gg.drawString("Max ", 440, 100);
			//		gg.drawString("Min ", 440, 160);
			gg.setFont(descriptionFont);
			metrics = gg.getFontMetrics();
			textBounds = metrics.getStringBounds(Configuration.getRessourceString(weatherCon.weatherDescription), gg).getBounds();
			gg.drawString(Configuration.getRessourceString(weatherCon.weatherDescription), (bounds.width - textBounds.width) / 2, 50);
			gg.setFont(temperatureFont);
			metrics = gg.getFontMetrics();
			textBounds = metrics.getStringBounds(weatherCon.temperature, gg).getBounds();
			gg.drawString(weatherCon.temperature, 100, 150);
			gg.setFont(font);
			gg.drawString(temperatureUnit, 100 + textBounds.width + 10, 150 - metrics.getAscent() + 25);
			//		gg.drawString(temperatureUnit, 500 + textBounds.width + 10, 130 - metrics.getAscent() + 25);
			//		gg.drawString(temperatureUnit, 500 + textBounds.width + 10, 190 - metrics.getAscent() + 25);
			gg.drawImage(weatherIcons.get(weatherCon.weatherDescription), (bounds.width - 133) / 2 , 70 , 133, 133, null);

			if (weatherForcasts.size() > 3)
			{
				final float dash1[] = {1.0f};
				gg.setStroke(new BasicStroke(1.0f,
						BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER,
						10.0f, dash1, 0.0f));
				gg.drawLine (5, bounds.height / 4 , bounds.width - 10, bounds.height / 4 );
				gg.drawLine (5, bounds.height / 2, bounds.width - 10, bounds.height / 2);
				gg.drawLine (bounds.width / 3, bounds.height / 2 + 5, bounds.width / 3, bounds.height -5);
				gg.drawLine (2 * bounds.width / 3, bounds.height / 2 + 5, 2 * bounds.width / 3, bounds.height -5);


				// tomorrow
				gg.setFont(font);
				metrics = gg.getFontMetrics();
				gg.drawString(Configuration.ressourceStrings.get("Tomorrow"), 10, bounds.height / 4 + 30);
				gg.drawString("Max ", 40, bounds.height / 4 + 120);
				gg.drawString("Min ", bounds.width - 160, bounds.height / 4 + 120);
				gg.setFont(descriptionFont);
				metrics = gg.getFontMetrics();
				textBounds = metrics.getStringBounds(Configuration.getRessourceString(weatherForcasts.get(0).weatherDescription), gg).getBounds();
				gg.drawString(Configuration.getRessourceString(weatherForcasts.get(0).weatherDescription), (bounds.width - textBounds.width) / 2, bounds.height / 4 + 40);
				gg.setFont(temperatureFont);
				metrics = gg.getFontMetrics();
				Rectangle maxTextBounds = metrics.getStringBounds(weatherForcasts.get(0).temperatureMax, gg).getBounds();
				gg.drawString(weatherForcasts.get(0).temperatureMax, 100, bounds.height / 4 + 150);
				Rectangle minTextBounds = metrics.getStringBounds(weatherForcasts.get(0).temperatureMin, gg).getBounds();
				gg.drawString(weatherForcasts.get(0).temperatureMin, bounds.width - 100, bounds.height / 4 + 150);
				gg.setFont(font);
				gg.drawString(temperatureUnit, 100 + maxTextBounds.width + 10, bounds.height / 4 + 150 - metrics.getAscent() + 25);
				gg.drawString(temperatureUnit, bounds.width - 100 + minTextBounds.width + 10, bounds.height / 4 + 150 - metrics.getAscent() + 25);
				gg.drawImage(weatherIcons.get(weatherForcasts.get(0).weatherDescription), (bounds.width - 133) / 2  , bounds.height / 4 + 55 , 133, 133, null);

				// day 1 to 3 after tomorrow
				int width = bounds.width / 3;
				for (int i = 0; i < 3; i++)
				{
					gg.setFont(font);
					metrics = gg.getFontMetrics();

					GregorianCalendar gc = new GregorianCalendar();
					try 
					{
						Date d = formatter.parse(weatherForcasts.get(i+1).date);
						gc.setTime(d);
					} 
					catch (ParseException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gg.drawString(Configuration.getRessourceString(Configuration.getWeekdayNames()[(gc.get(Calendar.DAY_OF_WEEK)+6) % 7]), 10 + i*width, bounds.height / 2 + 20);
					gg.drawString("Max ", 40 + i*width, bounds.height / 2 + 250);
					gg.drawString("Min ", 40 + i*width, bounds.height / 2 + 330);
					gg.setFont(descriptionForecastFont);
					metrics = gg.getFontMetrics();
					textBounds = metrics.getStringBounds(Configuration.getRessourceString(weatherForcasts.get(i+1).weatherDescription), gg).getBounds();
					gg.drawString(Configuration.getRessourceString(weatherForcasts.get(i+1).weatherDescription), (width - textBounds.width) / 2 + i*width, bounds.height / 2 + 60);
					gg.setFont(temperatureForecastFont);
					metrics = gg.getFontMetrics();
					maxTextBounds = metrics.getStringBounds(weatherForcasts.get(i+1).temperatureMax, gg).getBounds();
					gg.drawString(weatherForcasts.get(i+1).temperatureMax, 100 + i*width, bounds.height / 2 + 270);
					minTextBounds = metrics.getStringBounds(weatherForcasts.get(i+1).temperatureMin, gg).getBounds();
					gg.drawString(weatherForcasts.get(i+1).temperatureMin, 100 + i*width, bounds.height / 2 + 350);
					gg.setFont(font);
					gg.drawString(temperatureUnit, 100 + maxTextBounds.width + 10 + i*width, bounds.height / 2 + 270 - metrics.getAscent() + 20);
					gg.drawString(temperatureUnit, 100 + minTextBounds.width + 10 + i*width, bounds.height / 2 + 350 - metrics.getAscent() + 20);
					gg.drawImage(weatherIcons.get(weatherForcasts.get(i+1).weatherDescription), (width - 133) / 2+ i*width , bounds.height / 2 + 80 , 133, 133, null);
				}
			}	
		}
		else
		{
			gg.setFont(descriptionFont);
			metrics = gg.getFontMetrics();
			textBounds = metrics.getStringBounds(Configuration.getRessourceString("no data available"), gg).getBounds();
			gg.drawString(Configuration.getRessourceString("no data available"),  (bounds.width - textBounds.width) / 2 , bounds.height / 2);

		}

		// print last update
		gg.setFont(font);
		metrics = gg.getFontMetrics();
		gg.drawString(Configuration.getRessourceString("Last update") + ": " + weatherCon.observationTime, 10, bounds.height - metrics.getAscent() + 5);

	}
	

}
