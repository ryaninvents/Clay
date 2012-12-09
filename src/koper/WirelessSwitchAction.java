package koper;


public class WirelessSwitchAction extends Action 
{
	enum State {On, Off};
	State wirelessState = State.Off;
	
	public WirelessSwitchAction(State s)
	{
		wirelessState = s;
	}
}
