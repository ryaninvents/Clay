package koper;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;



public class Indicator extends Component 
{
	
	int max = 0;
	int current = 1;
	int size = 20;
	int distX = 50;
	
	public Indicator(Rectangle bounds, int max, int current)
	{
		super(bounds);
		this.max = max;
		int dist = (bounds.width - max * size) / (max);
		if (dist < distX)
			distX = dist + size;
		this.current = current;
	}
	
	public void forward()
	{
		if (current < max)
			current++;
	}
	
	public void back()
	{
		if (current > 1)
			current--;
		
	}
	
	public void setMax(int max)
	{
		this.max = max;
		int dist = (bounds.width - max * size) / (max);
		if (dist < distX)
			distX = dist + size;
	}

	@Override
	public void paint(Graphics2D gg)
	{
		super.paint(gg);
		int offsetX = (bounds.width - distX* (max - 1)) / 2;
		gg.setStroke(new BasicStroke(1));
		gg.setColor(Color.BLACK);
		for (int i = 0; i < max; i++)
		{
			if (i != current - 1)
				gg.drawRect(offsetX + i * distX, (bounds.height -size) / 2, size, size);
			else
				gg.fillRect(offsetX + i * distX, (bounds.height -size) / 2, size, size);				
		}
	}
	
}
