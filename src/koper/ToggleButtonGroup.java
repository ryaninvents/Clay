package koper;
import java.awt.Rectangle;



public class ToggleButtonGroup extends ComponentContainer implements ButtonListener
{

	ToggleButton selectedButton = null;
	
	public ToggleButtonGroup(Rectangle bounds) 
	{
		super(bounds);
		// TODO Auto-generated constructor stub
	}
	
	public void add(ToggleButton tb)
	{
		tb.addButtonListener(this);
		super.add(tb);
	}
	
	public void setToggledButton(ToggleButton tb)
	{
		selectedButton = tb;
	}

	@Override
	public void buttonClicked(Button source) 
	{
		// TODO Auto-generated method stub
		System.out.println("ToggleButton clicked");
		if (selectedButton != null)
		{
			selectedButton.toggle();
			selectedButton.repaint();
		}
		selectedButton = (ToggleButton)source;
		selectedButton.toggle();
		selectedButton.repaint();
	}

	@Override
	public void buttonLongClicked(Button source) 
	{
		// TODO Auto-generated method stub
	
	}

	
	
}
