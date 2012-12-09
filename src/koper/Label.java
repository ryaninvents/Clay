package koper;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;



public class Label extends Component
{
	
	String labelText = null;
	public enum Alignment {LEFT, RIGHT, CENTER};
	Alignment align = Alignment.LEFT;
	Font font = new Font("Arial", Font.BOLD, 14);

	public Label(Rectangle bounds, String string)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
		labelText = string;
	}
	
	public Label(Rectangle bounds, String string, Font font)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
		labelText = string;
		this.font = font;
	}
	
	public Label(Rectangle bounds, String string, Font font, Alignment align)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
		labelText = string;
		this.font = font;
		this.align = align;
	}
	
	public Label(Rectangle bounds, String string, Alignment align)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
		labelText = string;
		this.align = align;
	}
	
	public void setAlignment(Alignment a)
	{
		align = a;
	}
	
	public void setFont(Font f)
	{
		font = f;
	}
	
	public void setText(String s)
	{
		labelText = s;
		invalidate(false);
	}

	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugPaint)
			System.out.printf("Label.paint: %s x=%d, y=%d\n", labelText, bounds.x, bounds.y);
		gg.setColor(color);
		gg.setFont(font);
		FontMetrics metrics = null;
		metrics = gg.getFontMetrics();
		Rectangle textBounds = metrics.getStringBounds(labelText, gg).getBounds();
		int x = 0;
		switch (align)
		{
			case CENTER:
				x = (bounds.width - textBounds.width) / 2;
				break;
			case RIGHT:
				x = (bounds.width - textBounds.width);
		}
		gg.drawString(labelText, x, textBounds.height);
	}

}
