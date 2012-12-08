import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;


public class Configuration {

	static boolean drawBorders = false;
	static boolean debugPaint = false;
	static boolean debugEvents = false;
	static boolean debugSwingEvents = false;
	static boolean debugComponents = false;
	static boolean debugRendering = false;
	
	static boolean isBlackAndWhite = true;
	static int statusBarHeight = 25;
	static int menuBarHeight = 55;
	static int width = 600;
	static int height = 800;
	static HashMap<String, String> ressourceStrings = new HashMap<String, String>();
	static HashMap<String, String> weatherIcons = new HashMap<String, String>();
	
	// long click threshold in ms
	static final int longClickThreshold = 500;  
	
	// at least some pixels to be recognized as a swipe gesture
	static final int swipeThreshold = 150;
	
	static Locale locale = Locale.GERMANY;
	
	static boolean isKobo = true;
	
	static public String getDirectory()
	{
		    String name = Configuration.class.getName().replace('.', '/');
		    String s = Configuration.class.getResource("/" + name + ".class").toString();
//		    System.out.println("Configuration path: " + s);
		    if (s.startsWith("jar:"))
		    {
		    s = s.replace('/', File.separatorChar);
		    s = s.substring(0, s.indexOf(".jar")+4);
		    s = s.substring(s.lastIndexOf(':')+1);
		    return s.substring(0, s.lastIndexOf(File.separatorChar));
		    }
		    else 
		    	return System.getProperty("user.dir");
	}
	
	static public String getUserDirectory()
	{
    	return System.getProperty("user.dir");
	}
	
	static String bookRepositoryDir = "books";
	static String allBooksFile = "all_books.xml";
	
	static String bookIDFile = "bookid.prop";
	static String generalPropFilename = "general.prop";
	
	static int bookID = 0;
	
	static Properties generalProperties = new Properties();
	static Properties bookProperties = new Properties();
	static Integer recentBookIDs[] = new Integer[5];
	
	static public void load()
	{
	    System.out.println("User directory: " + System.getProperty("user.dir"));
		File generalPropFile = new File(getDirectory() + File.separator + generalPropFilename);
		if (generalPropFile.exists())
		{
			try 
			{
				FileInputStream fis = new FileInputStream(generalPropFile);
				generalProperties.load(fis);
			} 
			catch (IOException e) 
			{
				// Exception bearbeiten
			}		
		}
		width = Integer.valueOf(generalProperties.getProperty("width", "600"));
		height = Integer.valueOf(generalProperties.getProperty("height", "800"));
		statusBarHeight = Integer.valueOf(generalProperties.getProperty("statusbarheight", "25"));
		menuBarHeight = Integer.valueOf(generalProperties.getProperty("menubarheight", "55"));
		
		File idFile = new File(getDirectory() + File.separator + Configuration.bookIDFile);
		if (idFile.exists())
		{
			try 
			{
				FileInputStream fis = new FileInputStream(idFile);

				bookProperties.load(fis);
				bookID = Integer.valueOf(bookProperties.getProperty("BookID"));
			} 
			catch (IOException e) 
			{
				// Exception bearbeiten
			}		
		}
		else
		{
			bookProperties.setProperty("BookID", String.valueOf(bookID));
		}
		recentBookIDs[0] = Integer.valueOf(bookProperties.getProperty("RecentBookID1", "0"));
		recentBookIDs[1] = Integer.valueOf(bookProperties.getProperty("RecentBookID2", "0"));
		recentBookIDs[2] = Integer.valueOf(bookProperties.getProperty("RecentBookID3", "0"));
		recentBookIDs[3] = Integer.valueOf(bookProperties.getProperty("RecentBookID4", "0"));
		recentBookIDs[4] = Integer.valueOf(bookProperties.getProperty("RecentBookID5", "0"));

	}
	
	static public void store()
	{
		File idFile = new File(getDirectory() + File.separator + Configuration.bookIDFile);
		try 
		{
			bookProperties.store(new FileOutputStream(idFile), "BookID");
		} 
		catch (IOException e) 
		{
			// Exception bearbeiten
		}		
		
	}
	
	static
	{
		ressourceStrings.put("NewEvent", "Termin erstellen");
		ressourceStrings.put("EditEvent", "Termin bearbeiten");
		ressourceStrings.put("Today", "Heute");
		ressourceStrings.put("Tomorrow", "Morgen");
		ressourceStrings.put("Monday", "Montag");
		ressourceStrings.put("Tuesday", "Dienstag");
		ressourceStrings.put("Wednesday", "Mittwoch");
		ressourceStrings.put("Thursday", "Donnerstag");
		ressourceStrings.put("Friday", "Freitag");
		ressourceStrings.put("Saturday", "Samstag");
		ressourceStrings.put("Sunday", "Sonntag");
		ressourceStrings.put("Recent Books", "Kürzlich gelesen");

//		Calendar Applet
		ressourceStrings.put("Save", "Speichern");
		ressourceStrings.put("Remove", "Löschen");
		ressourceStrings.put("Cancel", "Abbrechen");

		
		
//		Weather Applet
		ressourceStrings.put("Temperature Unit", "°C");
		ressourceStrings.put("Sunny", "Sonnig");
		ressourceStrings.put("Clear", "Sonnig");
		ressourceStrings.put("Overcast", "Bewölkt");
		ressourceStrings.put("Partly Cloudy", "Teils bewölkt");
		ressourceStrings.put("Moderate rain", "Mäßiger Regen");
		ressourceStrings.put("Light rain", "Leichter Regen");
		ressourceStrings.put("Light drizzle", "Leichter Sprühregen");
		ressourceStrings.put("Last update", "Aktualisiert um");
		ressourceStrings.put("no data available", "Keine Wetterdaten vorhanden!");
		
		weatherIcons.put("Sunny", "skc.png");
		weatherIcons.put("Clear", "skc.png");
		weatherIcons.put("Overcast", "ovc.png");
		weatherIcons.put("Partly Cloudy", "sct.png");
		weatherIcons.put("Moderate rain", "shra.png");
		weatherIcons.put("Light rain", "ra1.png");
		weatherIcons.put("Light drizzle", "ra1.png");
		weatherIcons.put("Rain", "ra.png");
		
		
		
	}
	
	static public Locale getLocale()
	{
		return locale;
	}
	
	static public String[] getWeekdayNames()
	{
		String weekdays[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		return weekdays;
	}
	
	static public String[] getWeekdayShortNames()
	{
		String weekdays[] = {"So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"};
		return weekdays;
	}
	
	static public String[] getMonthNames()
	{
		String monthNames[] = {"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};
		return monthNames;
	}

	static public String[] getMonthShortNames()
	{
		String monthShortNames[] = {"Jan", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"};
		return monthShortNames;
	}
	
	static public String getRessourceString(String ressourceID)
	{
		String s = ressourceStrings.get(ressourceID);
		if (s == null)
			s = "??????" + ressourceID + "??????";
		return s;
	}

	static public String getWeatherIconName(String ressourceID)
	{
		String s = weatherIcons.get(ressourceID);
		if (s == null)
			s = "skc.png";
		return s;
	}

	public static int getAppletSize() 
	{
		return 140;
	}

	public static int getAppletsPerRow() 
	{
		return 3;
	}

}
