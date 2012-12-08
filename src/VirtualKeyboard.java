

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;



public class VirtualKeyboard extends ComponentContainer implements ButtonListener
{

	String keybLayout[][] = {{"q", "Q", "1"}, {"w", "W", "2"}, {"e", "E", "3"}, {"r", "R", "4"}, {"t", "T", "5"},
							 {"z", "Z", "6"}, {"u", "U", "7"}, {"i", "I", "8"}, {"o", "O", "9"}, {"p", "P", "0"},
							 {"a", "A", ":"}, {"s", "S", "\""},{"d", "D", "\\"},{"f", "F", "<"}, {"g", "G", ">"},
							 {"h", "H", "!"}, {"j", "J", "@"}, {"k", "K", "?"}, {"l", "L", "%"}, 
							 {"y", "Y", "&"}, {"x", "X", "*"}, {"c", "C", "("}, {"v", "V", ")"}, 
							 {"b", "B", "+"}, {"n", "N", "="}, {"m", "M", "-"}};
	
	private ArrayList<KeyEventListener> eventListeners = new ArrayList<KeyEventListener>();
	VirtualKey.KeyboardState state = VirtualKey.KeyboardState.LowerCase;
	
	VirtualKey delButton = null;
	VirtualKey shiftButton = null;
	VirtualKey actionButton = null;
	VirtualKey switchButton = null;
	VirtualKey spaceButton = null;
	VirtualKey normalKeys[] = new VirtualKey[26];
	boolean capslock = false;
	int keySize = 0;
	int keySpace = 0;
	int rowXOffset[] = new int[3];
	
	public VirtualKeyboard(Rectangle bounds) {
		super(bounds);
		
		keySize = (int) (bounds.width / 10 * 0.9);
		keySpace = (bounds.width - 10 * keySize) / 10;
		rowXOffset[0] = (bounds.width - 10 * (keySize + keySpace)) / 2 + 2;
		rowXOffset[1] = (bounds.width - 9 * (keySize + keySpace)) / 2;
		rowXOffset[2] = (bounds.width - 9 * (keySize + keySpace)) / 2 + keySize + keySpace;
		VirtualKey tmpKey = null;
		// 1. row
		for (int i = 0; i < 10; i++)
		{
			normalKeys[i] = new VirtualKey(new Rectangle(rowXOffset[0] + i * (keySize + keySpace), 5, keySize, keySize), keybLayout[i]);
			normalKeys[i].addButtonListener(this);
			add(normalKeys[i]);
		}
		// 2. row
		for (int i = 0; i < 9; i++)
		{
			normalKeys[10 + i] = new VirtualKey(new Rectangle(rowXOffset[1] + i * (keySize + keySpace), 5 + (keySize + keySpace), keySize, keySize), keybLayout[10 + i]);
			normalKeys[10 + i].addButtonListener(this);
			add(normalKeys[10 + i]);
		}
		// 3. row
		for (int i = 0; i < 7; i++)
		{
			normalKeys[19 + i] = new VirtualKey(new Rectangle(rowXOffset[2] + i * (keySize + keySpace), 5 + 2 * (keySize + keySpace), keySize, keySize), keybLayout[19 + i]);
			normalKeys[19 + i].addButtonListener(this);
			add(normalKeys[19 + i]);
		}
		
		shiftButton = new VirtualKey(new Rectangle(rowXOffset[0],5 + 2 * (keySize + keySpace), keySize + (keySize + keySpace) / 2, keySize), new String[] {"^^", "^^", "^^"});
		shiftButton.addButtonListener(this);
		add(shiftButton);
		delButton = new VirtualKey(new Rectangle(rowXOffset[0] + 17 * (keySize + keySpace) / 2,5 + 2 * (keySize + keySpace),keySize + (keySize + keySpace) / 2,keySize), new String[] {"Del", "Del", "Del"});
		delButton.addButtonListener(this);
		add(delButton);
		// 4. row
		switchButton = new VirtualKey(new Rectangle(rowXOffset[0],5 + 3 * (keySize + keySpace),keySize + (keySize + keySpace) / 2,keySize), new String[] {"?123", "?123", "?123"});
		switchButton.addButtonListener(this);
		add(switchButton);
		tmpKey = new VirtualKey(new Rectangle(rowXOffset[0] + 3 * (keySize + keySpace) / 2, 5 + 3 * (keySize + keySpace),keySize,keySize), new String[] {".", ".", "."});
		tmpKey.addButtonListener(this);
		add(tmpKey);
		tmpKey = new VirtualKey(new Rectangle(rowXOffset[0] + 5 * (keySize + keySpace) / 2, 5 + 3 * (keySize + keySpace), keySize,keySize), new String[] {",", ",", ","});
		tmpKey.addButtonListener(this);
		add(tmpKey);
		spaceButton = new VirtualKey(new Rectangle(rowXOffset[0] + 7 * (keySize + keySpace) / 2, 5 + 3 * (keySize + keySpace), keySize + 2 * (keySize + keySpace),keySize), new String[] {"SPACE", "SPACE", "SPACE"});
		spaceButton.addButtonListener(this);
		add(spaceButton);
		tmpKey = new VirtualKey(new Rectangle(rowXOffset[0] + 13 * (keySize + keySpace) / 2, 5 + 3 * (keySize + keySpace), keySize, keySize), new String[] {",", ",", ","});
		tmpKey.addButtonListener(this);
		add(tmpKey);
		actionButton = new VirtualKey(new Rectangle(rowXOffset[0] + 15 * (keySize + keySpace) / 2, 5 + 3 * (keySize + keySpace),keySize + 3 * (keySize + keySpace) / 2, keySize), new String[] {"Return", "Return", "Return"});
		actionButton.addButtonListener(this);
		add(actionButton);
//		tmpButton = new Button(new Rectangle(512,185,85,55), " ");
//		tmpButton.addButtonListener(this);
//		add(tmpButton);
		visible = false;
		if (!Configuration.isBlackAndWhite)
			bgColor = Color.GRAY;
	}


	
	public void addKeyEventListener(KeyEventListener el) 
	{
		eventListeners.add(el);
	}

	public void removeKeyEventListener(KeyEventListener el) 
	{
		eventListeners.remove(el);
	}
	
	public Integer getHeight()
	{
		if (visible)
			return bounds.height;
		else
			return 0;
	}
	

	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugPaint)
		{
			System.out.println("VirtualKeyboard.paint bounds " + bounds.toString());
			if (gg.getClipBounds() != null)
				System.out.println("VirtualKeyboard.paint clip " + gg.getClipBounds().toString());
		}
	
		super.paint(gg);
	}
	
	public void event(Event ev)
	{
		if (visible)
			super.event(ev);
		else
			System.out.println("VirtualKeyboard.event: keyboard is not visible");
			
	}
	
	@Override
	public void buttonClicked(Button source) {
		// TODO Auto-generated method stub
//		if (Configuration.debugEvents)
		{
			System.out.println("VirtualKeyboard.buttonClicked: " + source.buttonText);
		}
		KeyEvent ev;
		if (source == shiftButton)
		{
			if (state == VirtualKey.KeyboardState.LowerCase)
				state = VirtualKey.KeyboardState.UpperCase;
			else
				state = VirtualKey.KeyboardState.LowerCase;
			capslock = false;
			source.bgColor = Color.WHITE;
			for (int i = 0; i < 26; i++)
			{
				normalKeys[i].setState(state);
			}
			invalidate(false);
		}
		else if (source == switchButton)
		{
			if (state == VirtualKey.KeyboardState.Numbers)
				state = VirtualKey.KeyboardState.LowerCase;
			else
				state = VirtualKey.KeyboardState.Numbers;
			for (int i = 0; i < 26; i++)
			{
				normalKeys[i].setState(state);
			}
			invalidate(false);
		}
		else
		{
			if (source == delButton)
				ev = new KeyEvent(EventType.KeyTyped, System.currentTimeMillis(), KeyEvent.VK_BACK_SPACE);
			else if (source == spaceButton)
				ev = new KeyEvent(EventType.KeyTyped, System.currentTimeMillis(), KeyEvent.VK_SPACE);
			else if (source == actionButton)
				ev = new KeyEvent(EventType.KeyTyped, System.currentTimeMillis(), KeyEvent.VK_ENTER);
			else
			{
				ev = new KeyEvent(EventType.KeyTyped, System.currentTimeMillis(), Integer.valueOf(source.buttonText.charAt(0)));
				if (!capslock)
				{
					if (state == VirtualKey.KeyboardState.UpperCase)
					{
						state = VirtualKey.KeyboardState.LowerCase;
						for (int i = 0; i < 26; i++)
						{
							normalKeys[i].setState(VirtualKey.KeyboardState.LowerCase);
						}
						invalidate(false);
					}
				}
			}
			for (int i = 0; i < eventListeners.size(); i++) 
			{
				eventListeners.get(i).event(ev);

			}
		}
	}



	@Override
	public void buttonLongClicked(Button source) 
	{
		// TODO Auto-generated method stub
		if (source == shiftButton)
		{
			if (state == VirtualKey.KeyboardState.LowerCase)
			{
				System.out.println("VirtualKeyboard.buttonLongClicked: set capslock");
				state = VirtualKey.KeyboardState.UpperCase;
				capslock = true;
				source.bgColor = Color.BLACK;
				source.color = Color.WHITE;
			}				
			else
				state = VirtualKey.KeyboardState.LowerCase;
			for (int i = 0; i < 26; i++)
			{
				normalKeys[i].setState(state);
			}
			invalidate(false);
		}
	
	}
}
