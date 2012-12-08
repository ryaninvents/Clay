import java.awt.Rectangle;


public class WeatherApplet extends Applet
{
	WeatherPanel wp = null;
	
	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		setIcon("sct.png");
		wp = new WeatherPanel(new Rectangle(0,0,600,800));
		setMainPanel(wp);

	}
	
	public void setVisible(Display display)
	{
		wp.init();
		super.setVisible(display);
	}


	@Override
	public void event(KeyEvent ev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Weather";
	}
	
}
