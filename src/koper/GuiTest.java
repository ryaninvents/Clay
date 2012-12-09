package koper;



public class GuiTest extends Applet 
{
	SimpleComponentTestPanel sctp = null;

	@Override
	public void event(KeyEvent ev) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void init() 
	{
		// TODO Auto-generated method stub
		setIcon("test.png");
		sctp = new SimpleComponentTestPanel();
		setMainPanel(sctp);

	}

	@Override
	public String getName() 
	{
		// TODO Auto-generated method stub
		return "GuiTest";
	}

}
