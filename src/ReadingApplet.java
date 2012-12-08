import java.awt.Rectangle;

public class ReadingApplet extends Applet  implements SwipeEventListener
{

	ReadingPanel rp = null;
	
	@Override
	public void event(KeyEvent ev) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void init() 
	{
		// TODO Auto-generated method stub
		setIcon("books.png");
		rp = new ReadingPanel();
		setMainPanel(rp);
	}
	
	public void quit()
	{
		rp.quit();
	}
	
	public void setVisible(Display display)
	{
		rp.init();
		super.setVisible(display);
	}

	@Override
	public void swipeEvent(SwipeEvent ev) {
		// TODO Auto-generated method stub
		rp.processSwipeEvent(ev);
		
	}
	@Override
	public String getName() 
	{
		// TODO Auto-generated method stub
		return "Reader";
	}

}
