


public class SettingsApplet extends Applet
{

	SettingsPanel sp = null; 
	
	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		setIcon("settings_bold.png");
		sp = new SettingsPanel();
		setMainPanel(sp);

	}

	public void setVisible(Display display)
	{
		sp.init();
		super.setVisible(display);
	}

	@Override
	public void event(KeyEvent ev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Settings";
	}
	
}
