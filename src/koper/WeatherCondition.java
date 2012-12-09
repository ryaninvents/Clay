package koper;
import net.htmlparser.jericho.Element;


public class WeatherCondition 
{
	
	String observationTime = null;
	String date = null;
	String temperature = null;
	String temperatureMax = null;
	String temperatureMin = null;
	String windSpeed = null;
	String windDirection = null;
	String precipation = null;
	String humidity = null;
	String visibility = null;
	String pressure = null;
	String cloudcover = null;
	String weatherCode = null;
	String weatherDescription = null;

	public WeatherCondition(Element source)
	{
        Element observationTimeElem = source.getFirstElement("observation_time");
        if (observationTimeElem != null)
        	observationTime = observationTimeElem.getContent().toString();
        Element dateElem = source.getFirstElement("date");
        if (dateElem != null)
        	date = dateElem.getContent().toString();
        Element temperatureElem = source.getFirstElement("temp_c");
        if (temperatureElem != null)
        	temperature = temperatureElem.getContent().toString();
        Element temperatureMaxElem = source.getFirstElement("tempmaxc");
        if (temperatureMaxElem != null)
        	temperatureMax = temperatureMaxElem.getContent().toString();
        Element temperatureMinElem = source.getFirstElement("tempminc");
        if (temperatureMinElem != null)
        	temperatureMin = temperatureMinElem.getContent().toString();
        Element windspeedElem = source.getFirstElement("windspeedkmph");
        if (windspeedElem != null)
        	windSpeed = windspeedElem.getContent().toString();
        Element windDirectionElem = source.getFirstElement("winddirection");
        if (windDirectionElem != null)
        	windDirection = windDirectionElem.getContent().toString();
        Element precipElem = source.getFirstElement("precipmm");
        if (precipElem != null)
        	precipation = precipElem.getContent().toString();
        Element humidityElem = source.getFirstElement("humidity");
        if (humidityElem != null)
        	humidity = humidityElem.getContent().toString();
        Element visibilityElem = source.getFirstElement("visibility");
        if (visibilityElem != null)
        	visibility = visibilityElem.getContent().toString();
        Element pressureElem = source.getFirstElement("pressure");
        if (pressureElem != null)
        	pressure = pressureElem.getContent().toString();
        Element cloudcoverElem = source.getFirstElement("cloudcover");
        if (cloudcoverElem != null)
        	cloudcover = cloudcoverElem.getContent().toString();
        Element weatherCodeElem = source.getFirstElement("weathercode");
        if (weatherCodeElem != null)
        	weatherCode = weatherCodeElem.getContent().toString();
        Element weatherDescElem = source.getFirstElement("weatherdesc");
        if (weatherDescElem != null)
        {
        	
        	weatherDescription = weatherDescElem.getContent().toString().substring(9, weatherDescElem.getContent().toString().length() - 3).trim();
        	
        }
		
	}
	
	public void print()
	{
		if (observationTime != null)
			System.out.println("Observation time: " + observationTime);
		if (date != null)
			System.out.println("Date            : " + date);
		if (temperature != null)
			System.out.println("Temperature     : " + temperature);
		if (temperatureMax != null)
			System.out.println("max Temperature : " + temperatureMax);
		if (temperatureMin != null)
			System.out.println("min Temperature : " + temperatureMin);
		if (weatherCode != null)
			System.out.println("Weathercode     : " + weatherCode);
		if (weatherDescription != null)
			System.out.println("Description     : " + weatherDescription);
        System.out.println("Windspeed       : " + windSpeed);
        System.out.println("Wind direction  : " + windDirection);
        System.out.println("Precipation(MM) : " + precipation);
		if (humidity != null)
        System.out.println("Humidity        : " + humidity);
		if (visibility != null)
        System.out.println("Visiblity       : " + visibility);
		if (pressure != null)
        System.out.println("Pressure        : " + pressure);
		if (cloudcover != null)
        System.out.println("Cloudcover      : " + cloudcover);

	}
	
}
