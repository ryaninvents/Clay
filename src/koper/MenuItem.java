package koper;


import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;



public class MenuItem extends Component 
{
	LinkedList <MenuItemListener> menuItemListeners = new LinkedList<MenuItemListener>();
	
	String menuItemText = null;
	public enum Alignment {LEFT, RIGHT, CENTER};
	Alignment align = Alignment.LEFT;
	Font font = new Font("Arial", Font.BOLD, 20);
	protected boolean fixedWidth = false;
	BufferedImage image = null;

	public MenuItem(String string)
	{
		super();
		// TODO Auto-generated constructor stub
		menuItemText = string;
	}
	
	public MenuItem(String string, int width)
	{
		super();
		// TODO Auto-generated constructor stub
		menuItemText = string;
		bounds.width = width;
		fixedWidth = true;
	}
	
	public MenuItem(BufferedImage bi)
	{
		image = bi;
		bounds.width = image.getWidth();
		bounds.height = image.getHeight();
		fixedWidth = true;
	}
	
	public void setText(String s)
	{
		menuItemText = s;
	}
	
	public boolean hasFixedWidth()
	{
		return fixedWidth;
	}
	
	public void setAlignment(Alignment a)
	{
		align = a;
	}
	
	public void setFont(Font f)
	{
		font = f;
	}

	public void addMenuItemListener(MenuItemListener l)
	{
		menuItemListeners.add(l);
	}

	public void removeButtonListener(ButtonListener l)
	{
		menuItemListeners.remove(l);
	}


	
	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugPaint)
			System.out.printf("MenuItem.paint: %s x=%d, y=%d\n", menuItemText, bounds.x, bounds.y);
		gg.setColor(color);
		gg.setFont(font);
		FontMetrics metrics = null;
		metrics = gg.getFontMetrics();

		int x = 0;
		if (image != null)
		{
			gg.drawImage(image, 2 , 2 , null);
		}
		if (menuItemText != null)
		{
			Rectangle textBounds = metrics.getStringBounds(menuItemText, gg).getBounds();			
			switch (align)
			{
			case CENTER:
				x = (bounds.width - textBounds.width) / 2;
				break;
			case RIGHT:
				x = (bounds.width - textBounds.width);
			}
			//		gg.drawRect(0, 0, bounds.width, bounds.height);
			gg.drawString(menuItemText, x, (bounds.height + textBounds.height)/2-8);
		}
	}
	
	public void event(Event ev)
	{
		if (Configuration.debugEvents)
			System.out.println("Button.event");
		for (MenuItemListener l : menuItemListeners) 
		{
			if (ev.type == EventType.MouseClicked)
				l.menuItemClicked(this);
		}
	}
	


}
