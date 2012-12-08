import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;




public class TextField extends Component //implements KeyEventListener
{
	String fieldText = null;
	public enum Alignment {LEFT, RIGHT, CENTER};
	Alignment align = Alignment.LEFT;
	Font font = new Font("Arial", Font.PLAIN, 20);
	boolean showCursor = true;
	boolean isEditable = true;

	public TextField(Rectangle bounds, String string)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
		fieldText = string;
	}
	
	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
//		if (Configuration.debugPaint)
			System.out.printf("TextField.paint: %s x=%d, y=%d, focus=%s\n", fieldText, bounds.x, bounds.y, hasFocus);
		super.paint(gg);
		gg.setColor(Color.BLACK);
		gg.setFont(font);
		FontMetrics metrics = null;
		metrics = gg.getFontMetrics();
		Rectangle textBounds = metrics.getStringBounds(fieldText, gg).getBounds();
		gg.setStroke(new BasicStroke(1));
		gg.setColor(Color.BLACK);
		gg.drawRoundRect(1, 1, bounds.width-2, bounds.height-2, 4, 4);
		if (!hasFocus)
		{
			gg.setColor(Color.WHITE);
		}
		gg.drawRoundRect(2, 2, bounds.width -4, bounds.height-4, 3, 3);
		gg.setColor(color);
		int x = 4;
		switch (align)
		{
			case CENTER:
				x = x + (bounds.width - textBounds.width) / 2;
				break;
			case RIGHT:
				x = x + (bounds.width - textBounds.width);
			default:
					
		}
		gg.drawString(fieldText, x, textBounds.height - 2);
		gg.setStroke(new BasicStroke(1));
		if (hasFocus && showCursor)
			gg.setColor(Color.BLACK);
		else
			gg.setColor(Color.WHITE);
			gg.drawLine(textBounds.width + 6, 4, textBounds.width + 6, textBounds.height);
	}

	public void setAlignment(Alignment a)
	{
		align = a;
	}
	
	public void setText(String s)
	{
		fieldText = s;
		
	}
	public void setFont(Font f)
	{
		font = f;
	}
	
	public void showCursor()
	{
		showCursor = true;
	}
	
	public void hideCursor()
	{
		showCursor = false;
	}
	
	@Override
	public void event(KeyEvent ev) 
	{
		// TODO Auto-generated method stub
		System.out.println("TextField.event: " + ev);
		if (isEditable)
		{
			switch (ev.code)
			{
				case KeyEvent.VK_BACK_SPACE:
					if (fieldText.length() > 0)
						fieldText = fieldText.substring(0, fieldText.length()-1);
					break;
				default:
					fieldText = fieldText + (char)ev.code;
	
			}
			invalidateInterior(new Rectangle(2, 2, bounds.width - 4, bounds.height - 4), true);
		}
	}

}
