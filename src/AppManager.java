import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class AppManager implements EventListener //MouseEventListener, KeyEventListener

//public class AppManager implements MouseEventListener, KeyEventListener

{
	private ArrayList<Applet> applets = new ArrayList<Applet>();
	Applet activeApplet = null;
//	static Display display = null;
	static AppManager appMan = null;
	static EInkFB fb = null;
	static Display mainDisplay = null;
	
	static boolean isRunning = false; 

	
	// panel of the AppManager to display applet icons, i.e. the desktop
	AppletPanel mainPanel = null;
	
	// if a modal dialog is displayed the AppManager directs every input to this dialog
	ModalDialog modalDlg = null;
	
//	VirtualKeyboard vKeyboard = null;
	
	GestureRecognizer gr = null;

	
	// the directory where we keep the plugin classes
	String pluginsDir;

	// a list where we keep an initialized object of each plugin class
	List<PluginFunction> plugins;


	
	public AppManager(Display display)
	{

		mainPanel = new AppletPanel();


//		this.display = display;

		mainPanel.display = display;
		pluginsDir = System.getProperty("user.dir") + File.separator + "plugins";

		plugins = new ArrayList<PluginFunction>();
		mainPanel.getContentPane().add(new RecentBooksView(200));
		
		System.out.println(Configuration.getDirectory());
//		System.setSecurityManager(new PluginSecurityManager(pluginsDir));
		
//		gr = new GestureRecognizer();
		
	}
	
	public static AppManager getAppManager()
	{
		if (appMan == null)
			appMan = new AppManager(mainDisplay);
		return appMan;
	}
	
	public Display getDisplay()
	{
		return mainDisplay;

	}
	
	public void addApplet(final Applet a) 
	{
		
		Button b = null;
		applets.add(a);
		a.init();

		int spacer = 5;
		int dy = 220;
		int dx = 30;
		int size = (mainPanel.getContentPane().bounds.width - 2 * dx - Configuration.getAppletsPerRow() * spacer) / Configuration.getAppletsPerRow();
		int xPos = dx + ((applets.size()-1) % Configuration.getAppletsPerRow()) * (size + spacer);
		int yPos = dy + ((applets.size()-1) / Configuration.getAppletsPerRow()) * (size + spacer);
		b = new AppletButton(new Rectangle( xPos, yPos, size, size), a.getName(), a.getIcon());
		b.setAlignment(Button.Alignment.BOTTOM);
		b.setFont(new Font("Arial", Font.PLAIN, 14));


		b.addButtonListener(new ButtonListener(){

			@Override
			public void buttonClicked(Button source) {
				System.out.println("Calendar was clicked " + source.toString());
				if (activeApplet != null)
					activeApplet.quit();
				activeApplet = a;
//				a.init();
				a.setVisible(mainDisplay);
			}

			@Override
			public void buttonLongClicked(Button source) {
				// TODO Auto-generated method stub
				
			}
		});
		mainPanel.getContentPane().add(b);
//		mainPanel.repaint();
	}

	public void removeEventListener(Applet a) 
	{
		applets.remove(a);
	}

	public void showModalDialog(ModalDialog dlg)
	{
		dlg.display = mainDisplay;
		modalDlg = dlg;
		dlg.setVisible();
		dlg.repaint();
	}
	
	public void closeModalDialog(ModalDialog dlg)
	{
		if (modalDlg == dlg)
		{
			dlg.hide();
			modalDlg = null;
		}
		if (activeApplet != null)
			activeApplet.repaint();
		else
			mainPanel.repaint();
		
	}


	public void event(Event ev)
	{
		// TODO Auto-generated method stub
		System.out.println("================================================================================");
		System.out.println("AppManager.event BEGIN");
		System.out.println("================================================================================");
		if (ev instanceof MouseEvent)
		{
			switch (ev.type)
			{
				case MousePressed:
//					System.out.println(getClass().getName() + ".event:MousePressed");
					break;
				case MouseReleased:
//					System.out.println(getClass().getName() + ":Event:MouseReleased");
					break;
				case MouseClicked:
//					System.out.println(getClass().getName() + ":Event:MouseClicked");
					break;
				case MouseLongClicked:
//					System.out.println(getClass().getName() + ":Event:MouseLongClicked");
					break;
			}
			System.out.printf(getClass().getName() + ":Event type=%s, x=%d, y=%d\n",  ev.type.toString(), ((MouseEvent)ev).x, ((MouseEvent)ev).y );
			if (modalDlg != null)
				modalDlg.event((MouseEvent) ev);
			else if (activeApplet != null)
				activeApplet.event(new MouseEvent((MouseEvent) ev));
			else
				mainPanel.event(new MouseEvent((MouseEvent) ev));
		}
		else if (ev instanceof SwipeEvent)
		{
			SwipeEvent se = (SwipeEvent) ev;
			System.out.printf(getClass().getName() + ":Event type=%s\n",  se.type.toString());
			if (modalDlg != null)
				modalDlg.event(se);
			else if (activeApplet != null)
			{
				Class[] ifc = activeApplet.getClass().getInterfaces();
				for (int j=0; j<ifc.length; j++) 
				{
					if (ifc[j].getName().equals("SwipeEventListener")) 
					{
						((SwipeEventListener)activeApplet).swipeEvent(se);
						break;
					}
				}
			}
			else
				mainPanel.event(se);
		}
		else if (ev instanceof KeyEvent)
		{
			KeyEvent kev = (KeyEvent) ev;
			if (Configuration.debugEvents)
			System.out.printf("Event type=, code=%d\n",  kev.code);
			if (kev.type == EventType.HomeButton)
			{
				if (Configuration.debugEvents)
					System.out.println(getClass().getName() + ":Event:HomeButton pressed\n");
				if (activeApplet != null)
				{
					activeApplet.quit();
					activeApplet = null;
				}				
				mainPanel.repaint();
			}
			else if (kev.type == EventType.PowerButton)
			{
				if (Configuration.debugEvents)
					System.out.println(getClass().getName() + ":Event:PowerButton pressed\n");
				stop();
				fb.close();
				System.exit(0);
			}			 
		}
		System.out.println("================================================================================");
		System.out.println("AppManager.event END");
		System.out.println("================================================================================");
	}
	
	public void event(MouseEvent ev)
	{

		switch (ev.type)
		{
			case MousePressed:
				System.out.println(getClass().getName() + ":MouseEvent:MousePressed");
				break;
			case MouseReleased:
				System.out.println(getClass().getName() + ":MouseEvent:MouseReleased");
				break;
			case MouseClicked:
				System.out.println(getClass().getName() + ":MouseEvent:MouseClicked");
				break;
			case PowerButton:
				System.out.println(getClass().getName() + ":MouseEvent:PowerButton");
				stop();
				break;
			case HomeButton:
				System.out.println(getClass().getName() + ":MouseEvent:HomeButton");
				break;
		}
//		System.out.printf("Event type=, x=%d, y=%d\n",  ev.x, ev.y );
		if (modalDlg != null)
			modalDlg.event(ev);
		else if (activeApplet != null)
			activeApplet.event(ev);



	}
	
	public void event(KeyEvent ev)
	{
		switch (ev.type)
		{
			case PowerButton:
				System.out.println(getClass().getName() + ":KeyEvent:PowerButton");
				break;
			case HomeButton:
				//				drawCalendar();
				System.out.println(getClass().getName() + ":KeyEvent:HomeButton");
				stop();
				break;
		}


		if (modalDlg != null)
			modalDlg.event(ev);
		else if (activeApplet != null)
			activeApplet.event(ev);

	}
	
	
	public void start()
	{
		isRunning = true;
		while (isRunning)
		{
			try
			{
				Thread.sleep(100);
				
			}
			catch(InterruptedException ie)
			{
				System.out.println("Thread-Error!");
			}
			
		}

	}
	
	public synchronized void stop()
	{
		isRunning = false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Configuration.load();
		try
		{
			fb = new EInkFB("/dev/fb0");
			mainDisplay = new EInkDisplay(fb);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsatisfiedLinkError e)
		{
			e.printStackTrace();
//			System.out.println("Anscheinend kein EInk-Display vorhanden, deshalb Wechsel zu einem Swing-Display!");
			
		}
		
		if (mainDisplay == null)
		{

			System.out.println("Anscheinend kein EInk-Display vorhanden, deshalb Wechsel zu einem Swing-Display!");
			mainDisplay = new SwingDisplay(Configuration.width, Configuration.height);
			appMan = new AppManager(mainDisplay);

//			display = new SwingDisplay(600, 800);
//			appMan = new AppManager(display);

			
			
			SwingMouseInput smi = null;
			SwingKeyInput ski = null;
			smi = new SwingMouseInput();
			ski = new SwingKeyInput();
			((SwingDisplay)mainDisplay).frame.addMouseListener(smi);
			((SwingDisplay)mainDisplay).frame.addMouseMotionListener(smi);
			((SwingDisplay)mainDisplay).frame.addKeyListener(ski);
			smi.addEventListener(appMan);
			ski.addEventListener(appMan);
			
		}
		else
		{
			appMan = new AppManager(mainDisplay);
			KoboTouchInput kti = null;
			KoboKeyInput kki = null;
			try
			{
				kti = new KoboTouchInput("/dev/input/event1", true);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try
			{
				kki = new KoboKeyInput("/dev/input/event0");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			kti.addEventListener(appMan);
			kti.start();
			kki.addEventListener(appMan);
			kki.start();
			fb.update(3, EInkFB.UPDATE_INVERT);
			fb.update(4, EInkFB.UPDATE_WAIT);

		}
		appMan.addApplet(new CalendarApplet());
		appMan.addApplet(new NewsApplet());
//		appMan.addApplet(new MailApplet());
		appMan.addApplet(new NotesApplet());
		appMan.addApplet(new GraphicsDemoApplet());
		appMan.addApplet(new SketchApplet());
		appMan.addApplet(new ReadingApplet());
		appMan.addApplet(new WeatherApplet());
		appMan.addApplet(new SettingsApplet());
//		appMan.addApplet(new NickelApplet());
		appMan.addApplet(new GuiTest());
//		appMan.getPlugins();
		

//		appMan.addApplet(new GraphicsDemoApplet());
//		appMan.addApplet(new SketchApplet());

		appMan.mainPanel.repaint();
		
		appMan.start();

//		fb.close();
	}


}
