package koper;
import java.awt.Color;
import java.awt.Graphics2D;


public class WhitespaceBox extends InlineElementBox
{
	
	public WhitespaceBox()
	{
		bounds.width = 5;
		bounds.height = 10;
	}

	@Override
	public void layout(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugRendering)
			System.out.printf("WhitespaceBox.layout: height:%d\n", bounds.height);
		drawBorders(gg, Color.yellow);
		
	}

}
