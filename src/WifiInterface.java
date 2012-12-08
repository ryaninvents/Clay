/*
 *  Copyright (c) 2012 Sven Fritz <sven.fritz@yahoo.de>
 *
 *  This program is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program .  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.*;
   
/**
 * Simple interface to turn wireless connection on and off, and to get
 * some status information, e.g. ESSID, IP, Link Quality. 
 * The network needs to be configured already
 * @author fritzs
 */
public class WifiInterface 
{
	
	static int linkQuality = 0;
	static String essid = "";
	static String ipAddress = "0.0.0.0";
	final static String moduleName = "ar6000";
	final static String moduleFilename = "/drivers/freescale/wifi/ar6000.ko";

	/**
	 * Checks whether the kernel module is loaded or not
	 * @author fritzs
	 */
	static public boolean isWirelessModuleLoaded()
	{
		ProcessBuilder pb = new ProcessBuilder("lsmod");
		Process process = null;
		try
		{
			process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			System.out.println("loaded modules:");

			while ((line = br.readLine()) != null) 			
			{
				System.out.println(line);
				if (line.contains(moduleName))
				{
					System.out.println("Wifi module loaded!");
					return true;
				}
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	static public void loadWirelessModule()
	{
		
		ProcessBuilder pb = new ProcessBuilder("/bin/busybox", "insmod", moduleFilename);
		Process process = null;
		try
		{
			process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			System.out.println("load wireless module ...");

			while ((line = br.readLine()) != null) 			
			{
				System.out.println(line);
				if (line.contains("ar6000"))
				{
					System.out.println("Wifi module loaded!");
					break;
				}
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static public void removeWirelessModule()
	{
		
		ProcessBuilder pb = new ProcessBuilder("/bin/busybox", "rmmod", moduleName + ".ko");
		Process process = null;
		try
		{
			process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			System.out.println("removing wireless module ...");

			while ((line = br.readLine()) != null) 			
			{
				System.out.println(line);
				if (line.contains(moduleName))
				{
					System.out.println("Wifi module loaded!");
				}
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	static public boolean isWpaSupplicantRunning()
	{
		// TODO Auto-generated method stub
		ProcessBuilder pb = new ProcessBuilder("ps");
		Process process = null;
		try
		{
			process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			System.out.println("running threads:");

			while ((line = br.readLine()) != null) 			
			{
//				System.out.println(line);
				if (line.contains("wpa_supplicant"))
				{
					System.out.println("wpa_supplicant is running!");
					return true;
				}
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	static public void startWpaSupplicant()
	{
		
		if (!isWirelessModuleLoaded())
		{
			loadWirelessModule();
		}
		ProcessBuilder pb = new ProcessBuilder("wpa_supplicant", "-iwlan0", "-Dwext", "-c/etc/wpa_supplicant/wpa_supplicant.conf", "-B");
		pb.directory(new File("/"));
		Process process = null;
		try
		{
			process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			System.out.println("starting wpa_supplicant ...");

			while ((line = br.readLine()) != null) 			
			{
				System.out.println(line);
				if (line.contains("Failed"))
					System.out.println("failed!");
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	static public void stopWpaSupplicant()
	{
		
		ProcessBuilder pb = new ProcessBuilder("killall", "wpa_supplicant");
		Process process = null;
		try
		{
			process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			System.out.println("stopping wpa_supplicant ...");

			while ((line = br.readLine()) != null) 			
			{
				System.out.println(line);
				if (line.contains("wpa_supplicant"))
					System.out.println("wpa_supplicant is running!");
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	static public void updateWifiStatus()
	{
		String lq = "Link Quality";
		String sid = "ESSID";
		String ipa = "inet Adresse";
		
		ProcessBuilder pb = new ProcessBuilder("iwconfig", "wlan0");
		Process process = null;
		try
		{
			process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			System.out.println("retrieving link quality ...");

			while ((line = br.readLine()) != null) 			
			{
				System.out.println(line);
				if (line.contains(lq))
				{
					int i = line.indexOf(lq);
					int ii = line.indexOf(':', i);
					if (ii < 1)
						ii = line.indexOf('=', i);
					int iii = line.indexOf(' ', ii);
					String lqs = line.substring(ii + 1, iii);
					
					
					System.out.println(lq + ": " + lqs);
					linkQuality = Integer.valueOf(lqs.substring(0, lqs.indexOf('/')));
				}
				else  if (line.contains(sid))
				{
					int i = line.indexOf(sid);
					int ii = line.indexOf(':', i);
					if (ii < 1)
						ii = line.indexOf('=', i);
					int iii = line.indexOf(' ', ii);
					essid = line.substring(ii + 1, iii);
					
					
					System.out.println(sid + ": " + essid);
				}
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pb = new ProcessBuilder("ifconfig", "wlan0");
		try
		{
			process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			System.out.println("retrieving ip address ...");

			while ((line = br.readLine()) != null) 			
			{
				System.out.println(line);
				if (line.contains(ipa))
				{
					int i = line.indexOf(ipa);
					int ii = line.indexOf(':', i);
					if (ii < 1)
						ii = line.indexOf('=', i);
					int iii = line.indexOf(' ', ii);
					ipAddress = line.substring(ii + 1, iii);
					
					
					System.out.println(ipa + ": " + ipAddress);
				}
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
} 
