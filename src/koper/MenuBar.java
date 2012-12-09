package koper;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;



public class MenuBar extends ComponentContainer implements ButtonListener 
{

	Font font = new Font("Arial", Font.PLAIN, 22);
	static final int margin = 5;
	
	public MenuBar() 
	{
		super(new Rectangle(0,Configuration.statusBarHeight, Configuration.width, Configuration.menuBarHeight));
		// TODO Auto-generated constructor stub
		bgColor = Color.WHITE;
		color = Color.BLACK;
		visible = false;
	}

	public void add(MenuItem mi)
	{
		super.add(mi);
		mi.color = color;
		mi.setAlignment(MenuItem.Alignment.CENTER);
		mi.setFont(font);
		
	}
	
	public void setFont(Font f)
	{
		for (Component comp : components) 
		{
			if (comp instanceof MenuItem)
				((MenuItem)comp).setFont(f);
		}
	}
	
	public void paint(Graphics2D gg)
	{
		if (Configuration.debugPaint)
			System.out.println("MenuBar.paint");
//		opaque = false;
//		gg.fillRect(0, 0, bounds.width, bounds.height);
		FontMetrics metrics = null;
		gg.setFont(font);

		metrics = gg.getFontMetrics();
		int nextPos = 10;
		
		// calculate positions for MenuItems before painting
		for (Component comp : components) 
		{
			if (comp instanceof MenuItem)
			{
				int width = 0;
				if (((MenuItem)comp).hasFixedWidth())
					width = comp.bounds.width;
				else
					if (((MenuItem)comp).menuItemText != null)
					width = metrics.getStringBounds(((MenuItem)comp).menuItemText, gg).getBounds().width + 2* margin;
				comp.bounds = new Rectangle(nextPos, 2, width, bounds.height - 4);
				nextPos = nextPos + width;
			}
			else if (comp instanceof MenuSpacer)
			{
				comp.bounds = new Rectangle(nextPos, 2, 10, bounds.height - 8);
				nextPos = nextPos + 10;
				
			}
		}
		super.paint(gg);
		gg.setColor(Color.BLACK);
		gg.setStroke(new BasicStroke(2));
		gg.drawLine(0, bounds.height-2, bounds.width, bounds.height-2);
	}
	@Override
	public void buttonClicked(Button source) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void buttonLongClicked(Button source) 
	{
		// TODO Auto-generated method stub

	}

}
