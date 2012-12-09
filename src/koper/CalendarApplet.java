package koper;



public class CalendarApplet extends Applet implements SwipeEventListener
{
	
	CalendarPanel cp = null;

	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		setIcon("calendar_bold.png");
		cp = new CalendarPanel();
		setMainPanel(cp);

	}

	public void setVisible(Display display)
	{
		cp.init();
		super.setVisible(display);
	}

	
	@Override
	public void event(KeyEvent ev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Calendar";
	}

	@Override
	public void swipeEvent(SwipeEvent ev) 
	{
		// TODO Auto-generated method stub
		cp.processSwipeEvent(ev);
	}
	
}
