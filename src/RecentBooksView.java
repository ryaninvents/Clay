import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class RecentBooksView extends ComponentContainer 
{

	int widths[] = {66, 78, 120, 78, 66};
	int heights[] = {88, 104, 160, 104, 88};
	BufferedImage covers[] = new BufferedImage[5];
	
	public RecentBooksView(int height) 
	{
		super(new Rectangle(0,0,Configuration.width, height));
		updateRecentBooks();
	}
	
	public void updateRecentBooks()
	{
		File imagefile = null;
		java.net.URL url = null;
		try 
		{
			for (int i = 0; i < 5; i++)
			{
				System.out.println(Configuration.getDirectory() + File.separator + Configuration.bookRepositoryDir + File.separator + Configuration.recentBookIDs[i].toString() + ".png");
				imagefile = new File(Configuration.getDirectory() + File.separator + Configuration.bookRepositoryDir + File.separator + Configuration.recentBookIDs[i].toString() + ".png");
				if (imagefile.exists())
					covers[i] = ImageIO.read(imagefile);
				else
				{
					url = this.getClass().getResource("no_cover.png");
					covers[i] = ImageIO.read(url);
				}
			}
		} 
		catch (IOException e) 
		{
			System.out.println("RecentBooksView: Image " + imagefile + " not found!");
		}	
	}
	
	@Override
	public void paint(Graphics2D gg)
	{
		super.paint(gg);
		gg.setColor(color);
		final float dash1[] = {1.0f};
		gg.setStroke(new BasicStroke(1.0f,
				BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f, dash1, 0.0f));
		gg.drawLine (5, bounds.height, bounds.width - 10, bounds.height);
		Font font = new Font("Arial", Font.PLAIN, 14);
		gg.setFont(font);
		gg.drawString(Configuration.ressourceStrings.get("Recent Books"), 10, 15);
		int dx = 15;
		int width = 0;
		for (int i = 0; i < widths.length; i++)
			width = width + widths[i];
		int offsetX = (bounds.width - width - 4 * dx) /2;
//		Book books[] = {Book.getBook(Configuration.bookRepositoryDir + File.separator + "no_book.epub")};
		for (int i = 0; i < 5; i++)
		{
			gg.drawImage(covers[i], offsetX, 5 + (190 - heights[i]) /2, widths[i], heights[i], null);
			gg.drawRect(offsetX, 5 + (190 - heights[i]) /2, widths[i], heights[i]);
			offsetX = offsetX + widths[i] + dx;
		}		
	}


}
