package koper;


import java.awt.Font;
import java.awt.Rectangle;



public class VirtualKey extends Button 
{
	public enum KeyboardState {LowerCase, UpperCase, Numbers}

	String s[];
	KeyboardState state = KeyboardState.LowerCase;
	
	
	public VirtualKey(Rectangle bounds, String string[]) 
	{
		super(bounds, string[0]);
		s = string;
		// TODO Auto-generated constructor stub
		setFont(new Font("Arial", Font.PLAIN, 28));
	}
	
	public VirtualKey(Rectangle bounds, String string) 
	{
		super(bounds, string);
		s = new String[1];
		s[0] = string;
		// TODO Auto-generated constructor stub
		setFont(new Font("Arial", Font.PLAIN, 28));
	}
	
	public void setState(KeyboardState toState)
	{
		state = toState;
		buttonText = s[toState.ordinal()];
	}
	
	public void event(Event ev)
	{
		System.out.println("VirtualKey.event");
		if (ev instanceof MouseEvent)
		{
			if (ev.type == EventType.MousePressed)
			{
				pressed = true;
//				invalidate(new Rectangle(bounds.x + 4, bounds.y + 4, bounds.width - 8, bounds.height - 8), true);
				invalidateInterior(new Rectangle(4, 4, bounds.width - 8, bounds.height - 8), true);
			}
			else if (ev.type == EventType.MouseReleased)
			{
				pressed = false;
				invalidate(new Rectangle(bounds.x + 4, bounds.y + 4, bounds.width - 8, bounds.height - 8), true);
			}

		}
		super.event(ev);
	}

}
