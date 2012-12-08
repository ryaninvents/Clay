import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;



public class ToggleKey extends VirtualKey 
{

	boolean toggled = false;
	
	public ToggleKey(Rectangle bounds, String string) 
	{
		super(bounds, string);
		// TODO Auto-generated constructor stub
	}
	
	public void toggle()
	{
		toggled = !toggled;
	}
	
	public void setToggled(boolean b)
	{
		toggled = b;
		repaint(true);
	}
	
	public void paint(Graphics2D gg)
	{
		gg.setColor(Color.black);
		FontMetrics metrics = null;
		if (opaque)
		{
			if (!toggled)
			{
				if (!Configuration.isBlackAndWhite)
					gg.setColor(bgColor);
				else
					gg.setColor(Color.WHITE);
			}
			else
			{
				if (!Configuration.isBlackAndWhite)
					gg.setColor(color);
				else
					gg.setColor(Color.BLACK);
			}				
			gg.fillRect(0, 0, bounds.width, bounds.height);
		}
		gg.setStroke(new BasicStroke(2));
		gg.setColor(Color.BLACK);
		gg.drawRect(0, 0, bounds.width, bounds.height);
		if (toggled)
		{
			if (!Configuration.isBlackAndWhite)
				gg.setColor(bgColor);
			else
				gg.setColor(Color.WHITE);
		}
		else
		{
			if (!Configuration.isBlackAndWhite)
				gg.setColor(color);
			else
				gg.setColor(Color.BLACK);
		}				
		gg.setFont(font);
		metrics = gg.getFontMetrics();
		Rectangle textBounds = metrics.getStringBounds(buttonText, gg).getBounds();
		int height = metrics.getAscent() + metrics.getDescent();
		gg.drawString(buttonText, (bounds.width - textBounds.width)/2, (bounds.height -  height)/2 + metrics.getAscent() );

	}

}
