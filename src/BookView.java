import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;



public class BookView extends Button 
{

	enum State {Icon, Expanded};
	State viewState = State.Icon;
	Book book = null;
	
	public BookView(Rectangle bounds, Book b) 
	{
		super(bounds, "");
		// TODO Auto-generated constructor stub
		drawBorder = false;
		book = b;
	}
	
	public BookView(Rectangle bounds, Book b, State s) 
	{
		super(bounds, "");
		// TODO Auto-generated constructor stub
		drawBorder = false;
		viewState = s;
		book = b;
	}

	@Override
	public void paint(Graphics2D gg)
	{
//		super.paint(gg);
		BufferedImage cover = null;
		java.net.URL url;
		 
		{
			url = this.getClass().getResource("epub.png");
			try {
				cover = ImageIO.read(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		gg.setColor(color);
		final float dash1[] = {1.0f};
		gg.setStroke(new BasicStroke(1.0f,
				BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f, dash1, 0.0f));
		switch (viewState)
		{
		case Icon:
			if (book.cover != null)
				gg.drawImage(book.cover, 0, 0, bounds.width, bounds.height, null);
			else if (cover != null)
				gg.drawImage(cover, 0, 0, bounds.width, bounds.height, null);
			
			break;
		case Expanded:
			if (book.cover != null)
				gg.drawImage(book.cover, 0, 0, 67, 90, null);
			else if (cover != null)
				gg.drawImage(cover, 0, 0, 67, 90, null);
				
			if (book.name != null)
				gg.drawString(book.name, bounds.height + 10, 15);
			if (book.author != null)
				gg.drawString(book.author, bounds.height + 10, 30);
			break;
			
		}
		gg.drawRect(0, 0, bounds.width, bounds.height);
	}
}
