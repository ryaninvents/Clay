package koper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public abstract class AbstractBox
{
	Rectangle bounds = new Rectangle(0,0,0,0);
	Margin margin = new Margin(0,0,0,0);
	Margin padding = new Padding(0,0,0,0);
	Dimension maxDim = new Dimension(0, 0);
	Dimension minDim = new Dimension(0, 0);
	
	public AbstractBox()
	{
		
	}
	
	public void render(RenderedPages rp)
	{
		layout(rp.getGraphics());
	}
	public abstract void layout(Graphics2D gg);
	
	public void drawBorders(Graphics2D gg, Color c)
	{
		if (Configuration.drawBorders)
		{
			Color oldColor = gg.getColor();
			gg.setColor(c);
			gg.drawRect(0, 0, bounds.width, bounds.height);
			gg.setColor(oldColor);
		}

	}

	public AbstractBox getBoxAt(int x, int y)
	{
		if (bounds.contains(x, y))
			return this;
		else
			return null;
			
	}

	
	public Dimension getPreferredSize(Graphics2D gg)
	{
		return new Dimension(bounds.width, bounds.height);
	}
	
	public int getWidth()
	{
		// TODO Auto-generated method stub
		return bounds.width;
	}

	public int getHeight()
	{
		// TODO Auto-generated method stub
		return bounds.height;
	}
	
	public int getMaxHeight()
	{
		// TODO Auto-generated method stub
		return maxDim.height;
	}


	public void resizeWidth(int units)
	{
		bounds.width = bounds.width + units;
	}
	
	public void resizeHeight(int units)
	{
		bounds.height = bounds.height + units;
	}

	public void setPos(int tx, int ty) {
		// TODO Auto-generated method stub
		bounds.x = tx;
		bounds.y = ty;
	}
	
}
