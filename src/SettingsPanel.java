import java.awt.Rectangle;



public class SettingsPanel extends AppletPanel implements ActionListener
{

	ToggleButton tbWirelessOn = null;
	ToggleButton tbWirelessOff = null;
	ToggleButtonGroup tbg = null;
	
	public SettingsPanel() 
	{
		super();
		// TODO Auto-generated constructor stub
//		if (!dpb.isWirelessModuleLoaded())
//		{
//			dpb.loadWirelessModule();
//		}
//		if (!dpb.isWpaSupplicantRunning())
//		{
//			dpb.startWpaSupplicant();
//		}
		
	}
	
	public void init()
	{
		WifiInterface.updateWifiStatus();
		getContentPane().add(new Label(new Rectangle(10, 10, 80, 20), "Link quality: " + WifiInterface.linkQuality, Label.Alignment.LEFT));
		getContentPane().add(new Label(new Rectangle(10, 30, 80, 20), "ESSID       : " + WifiInterface.essid, Label.Alignment.LEFT));
		getContentPane().add(new Label(new Rectangle(10, 50, 80, 20), "IP address  : " + WifiInterface.ipAddress, Label.Alignment.LEFT));
		getContentPane().add(new Label(new Rectangle(10, 70, 80, 20), "Battery     : " + BatteryInterface.getStatus(), Label.Alignment.LEFT));
		tbg = new ToggleButtonGroup(new Rectangle(300, 10, 200, 20));
		tbWirelessOn = new ToggleButton(new Rectangle(0,0,100,55), "On");
		tbWirelessOn.setAction(new WirelessSwitchAction(WirelessSwitchAction.State.On));
		tbWirelessOn.addActionListener(this);
		tbWirelessOff = new ToggleButton(new Rectangle(105,0,100,55), "Off");
		tbWirelessOff.setAction(new WirelessSwitchAction(WirelessSwitchAction.State.Off));
		tbWirelessOff.addActionListener(this);
		tbg.add(tbWirelessOn);
		tbg.add(tbWirelessOff);
		if (WifiInterface.isWpaSupplicantRunning())
		{
			tbWirelessOn.setToggled(true);
			tbg.setToggledButton(tbWirelessOn);
		}
		else
		{
			tbWirelessOff.setToggled(true);
			tbg.setToggledButton(tbWirelessOff);
		}	
		getContentPane().add(tbg);
		
	}

	@Override
	public void performAction(Action a) 
	{
		// TODO Auto-generated method stub
		System.out.println("perform wireless action");
		if (a instanceof WirelessSwitchAction)
		{
			WirelessSwitchAction wsa = (WirelessSwitchAction)a;
			switch (wsa.wirelessState)
			{
			case On:
				System.out.println("Switch wifi on");
				
				if (Configuration.isKobo)
					WifiInterface.startWpaSupplicant();
				
				break;
			case Off:
				System.out.println("Switch wifi off");
				if (Configuration.isKobo)
					WifiInterface.stopWpaSupplicant();
				break;
				
			}
		}
		
	}

}
