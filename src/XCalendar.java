import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.htmlparser.jericho.Source;


public class XCalendar 
{

	CalendarEventList cel = new CalendarEventList(); 
	String filename;
	static DateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm" );

	public XCalendar(String filename)
	{
		Source source = null;
		//        	java.net.URL url = this.getClass().getResource(filename);
		//			System.out.println("XCalendar: fileame: " + filename + " url: " + url);
		File f = new File(Configuration.getDirectory() + File.separator + filename);
		if (f.exists())
		{
			try 
			{
				source = new Source(new FileInputStream(f));
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.filename = filename; 
			net.htmlparser.jericho.Element componentsElem = source.getFirstElement("components");
			if (componentsElem != null)
			{
				List<net.htmlparser.jericho.Element> elementList = componentsElem.getAllElements("vevent");
				for (net.htmlparser.jericho.Element element : elementList) 
				{
					Calendar start = null;
					Calendar end = null;
					String description = null;
					String summary = null;

					net.htmlparser.jericho.Element dtStartElem = element.getFirstElement("dtstart");
					if (dtStartElem != null)
					{
						net.htmlparser.jericho.Element dtElem = dtStartElem.getFirstElement("date-time");
						if (dtElem == null)
							dtElem = dtStartElem.getFirstElement("date");	
						System.out.println("dtStart: " + dtElem.getContent().toString());
						start = getCalendar(dtElem.getContent().toString());
					}
					net.htmlparser.jericho.Element dtEndElem = element.getFirstElement("dtend");
					if (dtEndElem != null)
					{
						net.htmlparser.jericho.Element dtElem = dtEndElem.getFirstElement("date-time");
						System.out.println("dtEnd: " + dtElem.getContent().toString());
						end = getCalendar(dtElem.getContent().toString());
					}
					else
					{
						end = (Calendar) start.clone();
						end.add(Calendar.DAY_OF_MONTH, 1);
					}
					net.htmlparser.jericho.Element summaryElem = element.getFirstElement("summary");
					if (summaryElem != null)
					{
						net.htmlparser.jericho.Element summaryTextElem = summaryElem.getFirstElement("text");
						System.out.println("summary: " + summaryTextElem.getContent().toString());
						summary = summaryTextElem.getContent().toString();
					}
					net.htmlparser.jericho.Element descriptionElem = element.getFirstElement("description");
					if (descriptionElem != null)
					{
						net.htmlparser.jericho.Element descriptionTextElem = descriptionElem.getFirstElement("text");
						System.out.println("description: " + descriptionTextElem.getContent().toString());
						description = descriptionTextElem.getContent().toString();
					}
					cel.add(summary, description, start, end);
				}
			}
		}
		
	}

	
	public void addEvent(CalendarEvent ev)
	{
		cel.add(ev);
		
	}
	
	public void addEvent(String summary, String description, Calendar start, Calendar end)
	{
		cel.add(summary, description, start, end);
		
	}
	
	public void removeEvent(String uid)
	{
		CalendarEvent cer = null;
		for (CalendarEvent ce : cel.getList())
		{
			if (ce.getUID().equalsIgnoreCase(uid))
			{
				cer = ce;
				break;
			}
		}
		if (cer != null)
			cel.getList().remove(cer);
	}
	
	public List<CalendarEvent> getEvent(Calendar date)
	{
		return cel.getEvent(date);
	}

	public CalendarEvent getEventByUID(String uid)
	{
		for (CalendarEvent ce : cel.getList())
		{
			if (ce.getUID().equalsIgnoreCase(uid))
				return ce;
		}
		return null;
	}
	
	public void save(String outFilename)
	{
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(new File(outFilename)));
			writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<icalendar xmlns=\"urn:ietf:params:xml:ns:icalendar-2.0\">\n"+
					 "<vcalendar>\n<properties>\n<calscale>\n<text>GREGORIAN</text>\n</calscale>\n"+
					 "<prodid>\n<text>Sven\'s Kalender</text>\n</prodid>\n<version>\n<text>2.0</text>\n</version>"+
					 "</properties>\n<components>\n");
			for (CalendarEvent ce : cel.events)
			{
				writer.write("<vevent>\n<properties>\n");
				writer.write("<dtstart>\n");
				writer.write("<date-time>" + formatter.format(ce.start.getTime()) + "</date-time>\n");
				writer.write("</dtstart>\n");
				writer.write("<dtend>\n");
				writer.write("<date-time>" + formatter.format(ce.end.getTime()) + "</date-time>\n");
				writer.write("</dtend>\n");
				writer.write("<summary>\n");
				writer.write("<text>" + ce.summary + "</text>\n");
				writer.write("</summary>\n");
				if ((ce.description != null) && (!ce.description.isEmpty()))
				{
					writer.write("<description>\n");
					writer.write("<text>" + ce.description + "</text>\n");
					writer.write("</description>\n");
				}

				writer.write("<uid>\n");
				writer.write("<text>" + ce.uid + "</text>\n");
				writer.write("</uid>\n");
				writer.write("</properties>\n</vevent>\n");
				
			}
			writer.write("</components>\n");
			writer.write("</vcalendar>\n</icalendar>\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static GregorianCalendar getCalendar(String string) {
		// TODO Auto-generated method stub
		int year = Integer.valueOf(string.substring(0, 4));
		int month = Integer.valueOf(string.substring(5, 7))-1;
		int day = Integer.valueOf(string.substring(8, 10));
		int hour = 0;
		int minute = 0;
		
		if (string.indexOf('T') == 10)
		{
			hour = Integer.valueOf(string.substring(11, 13));
			minute = Integer.valueOf(string.substring(14, 16));
			
		}
		
		Date date;
		GregorianCalendar gc = new GregorianCalendar();
		try {
			date = formatter.parse(string);
			gc.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return gc;
		
//		return new GregorianCalendar(year, month, day, hour, minute);
	}
	
}
