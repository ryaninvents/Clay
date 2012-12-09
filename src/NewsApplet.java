


public class NewsApplet extends Applet implements SwipeEventListener
{
	NewsPanel np = null;

	@Override
	public void init()
	{
		setIcon("news_bold.png");
		np = new NewsPanel();
		setMainPanel(np);

	}

	public void setVisible(Display display)
	{
		np.init();
		super.setVisible(display);
	}

	@Override
	public void event(KeyEvent ev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "News";
	}

	@Override
	public void swipeEvent(SwipeEvent ev) {
		// TODO Auto-generated method stub
		np.processSwipeEvent(ev);
		
	}
	
}
