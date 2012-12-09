package koper;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;



public class MenuSpacer extends Component 
{
	
	public MenuSpacer()
	{
		super();
		color = Color.WHITE;
	}
	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugPaint)
			System.out.printf("MenuSpacer.paint\n");
		gg.setStroke(new BasicStroke(2));
		gg.setColor(color);
		gg.drawLine(bounds.width / 2, 0, bounds.width / 2, bounds.y + bounds.height);
		
	}
}
