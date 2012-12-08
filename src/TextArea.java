import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.text.BreakIterator;



public class TextArea extends Component 
{
	String fieldText = null;
	Font font = new Font("Arial", Font.PLAIN, 20);
	boolean showCursor = true;

	public TextArea(Rectangle bounds, String string)
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
		System.out.printf("TextArea.paint: %s x=%d, y=%d, focus=%s\n", fieldText, bounds.x, bounds.y, hasFocus);
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
		gg.setColor(Color.BLACK);
		String s[] = fieldText.split("\\s");
		int width = 0;
		int y = 0;
		int dy = 5;
		int cx = 0;
		int cy = 0;
		String textline = "";
		BreakIterator iter = BreakIterator.getLineInstance();
		iter.setText(fieldText);
		for ( int last = iter.first(),next = iter.next(); next != BreakIterator.DONE; last = next, next = iter.next() )
		{
			String part = fieldText.substring( last, next );

			if (width + metrics.getStringBounds(part, gg).getBounds().width < bounds.width - 10)
			{
				if (textline.isEmpty())
					textline = textline + part;
				else
					textline = textline + " " + part;
				width = metrics.getStringBounds(textline, gg).getBounds().width;
					
			}
			else
			{
				gg.drawString(textline, 5, y + metrics.getAscent());
				y = y + metrics.getHeight() + dy;
				cy = y;
				textline = part;
				width = metrics.getStringBounds(textline, gg).getBounds().width;;
				cx = width;
			}
		}
		if (!textline.isEmpty())
		{
			gg.drawString(textline, 5, y + metrics.getAscent());
			cy = y;
			cx = metrics.getStringBounds(textline, gg).getBounds().width;
			
		}
		gg.setStroke(new BasicStroke(1));
		if (hasFocus && showCursor)
			gg.setColor(Color.BLACK);
		else
			gg.setColor(Color.WHITE);
			
			gg.drawLine(cx + 6, cy + 2, cx + 6 , cy + 2 + metrics.getAscent());
	}



	@Override
	public void event(KeyEvent ev) 
	{
		// TODO Auto-generated method stub
		switch (ev.code)
		{
		case KeyEvent.VK_BACK_SPACE:
			if (fieldText.length() > 0)
				fieldText = fieldText.substring(0, fieldText.length()-1);
			break;
		case KeyEvent.VK_ENTER:
			break;
		default:
			fieldText = fieldText + (char)ev.code;

		}
		invalidate(true);
	}

}
