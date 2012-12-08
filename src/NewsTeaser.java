import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;



public class NewsTeaser extends Button 
{
	
	String title = "";
	String teaser = "";
	Font fontTitle = new Font("Arial", Font.BOLD, 24);
	Font fontTeaser = new Font("Arial", Font.PLAIN, 18);

	public NewsTeaser(Rectangle bounds, String title, String teaser) 
	{
		super(bounds, "");
		// TODO Auto-generated constructor stub
		this.title = title;
		this.teaser = teaser;
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
		gg.setFont(fontTitle);
		metrics = gg.getFontMetrics();
		textBounds = metrics.getStringBounds(title, gg).getBounds();
		tempText = new String(title);
		if (textBounds.width > bounds.width - 10)
		{
			int len = tempText.length();
			len = ((bounds.width - 10) * len) / textBounds.width - 5;
			tempText = tempText.substring(0, len) + "...";
		}
		gg.drawString(tempText, 10, metrics.getAscent() );
		int height = textBounds.height;
		
		gg.setFont(fontTeaser);
		metrics = gg.getFontMetrics();
		textBounds = metrics.getStringBounds(teaser, gg).getBounds();
		tempText = new String(teaser);
		if (textBounds.width > bounds.width - 10)
		{
			int len = tempText.length();
			len = ((bounds.width - 10) * len) / textBounds.width - 5;
			tempText = tempText.substring(0, len) + "...";
		}
		gg.drawString(tempText, 10, height + metrics.getAscent() );
		

	}

}
