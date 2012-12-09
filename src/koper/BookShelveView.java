package koper;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;



public class BookShelveView extends Button 
{

	enum state {Icon, Expanded};
	BookShelve bookshelve = null;
	Font fontShelve = new Font("Arial", Font.PLAIN, 16);
	
	public BookShelveView(Rectangle bounds, BookShelve bs) 
	{
		super(bounds, "");
		// TODO Auto-generated constructor stub
		bookshelve = bs;
		drawBorder = false;
	}

	@Override
	public void paint(Graphics2D gg)
	{
		super.paint(gg);
		Rectangle textBounds = null;
		gg.setColor(Color.black);
		FontMetrics metrics = null;
		String tempText = null;
		gg.setFont(fontShelve);
		metrics = gg.getFontMetrics();
		textBounds = metrics.getStringBounds(bookshelve.name, gg).getBounds();
		tempText = new String(bookshelve.name);
		if (textBounds.width > bounds.width - 10)
		{
			int len = tempText.length();
			len = ((bounds.width - 10) * len) / textBounds.width - 5;
			tempText = tempText.substring(0, len) + "...";
		}
//		tempText = tempText + " - " + String.valueOf(bookshelve.getBookCount());
		gg.drawString(tempText, (bounds.width - textBounds.width) / 2, bounds.height - metrics.getAscent() - 5);
		textBounds = metrics.getStringBounds(String.valueOf(bookshelve.getBookCount()), gg).getBounds();
		gg.drawString(String.valueOf(bookshelve.getBookCount()), (bounds.width - textBounds.width) / 2, bounds.height  );
		BufferedImage icon = bookshelve.getIcon();
		if (icon != null)
		{
			gg.drawImage(icon, (bounds.width - icon.getWidth()) / 2 , 2, null);
		}
		

	}

	
}
