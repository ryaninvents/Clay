package koper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class BatteryInterface 
{

	static public String getStatus()
	{
		File statusFile = new File("/sys/class/power_supply/mc13892_bat/status");
		String 	status = "cannot retrieve battery status";

		String line;
		if (statusFile.exists())
		{
			try 
			{
				FileReader fr = new FileReader(statusFile);
				BufferedReader br = new BufferedReader(fr);
				line = br.readLine();		
				if (line != null)
					status = line;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}
	
}
