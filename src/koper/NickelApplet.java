package koper;



public class NickelApplet extends Applet 
{

	@Override
	public void event(KeyEvent ev) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void init() 
	{
		// TODO Auto-generated method stub
		setIcon("nickel.png");

	}

	
	public void setVisible(Display display)
	{
		System.out.println("starting nickel .....");
		// #/usr/local/Kobo/nickel -qws &
		if (Configuration.isKobo)
		{
			AppManager.fb.close();
//			ProcessBuilder pb = new ProcessBuilder("/mnt/onboard/run_nickel.sh", "&");
//			Process process = null;
//			try
//			{
//				process = pb.start();
//				InputStream is = process.getInputStream();
//				InputStreamReader isr = new InputStreamReader(is);
//				BufferedReader br = new BufferedReader(isr);
//				String line;
//
////				while ((line = br.readLine()) != null) 			
////				{
////					System.out.println(line);
////				}
//			} 
//			catch (IOException e) 
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		Configuration.store();
		System.exit(0);
	}


	@Override
	public String getName() 
	{
		// TODO Auto-generated method stub
		return "Start nickel";
	}

}
