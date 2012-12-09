package koper;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;


public class RenderedPages 
{

	LinkedList<BufferedImage> pageList = new LinkedList<BufferedImage>();
	Margin margin = new Margin(50,10,50,10);
	int width = 600;
	int height = 800;
	int ty = margin.top;
	int tx = margin.left;
	
	
	BufferedImage curImage = null;
	Graphics2D gg = null;
	
	public BufferedImage getPage(int pageNumber)
	{
		if (pageNumber < pageList.size())
			return pageList.get(pageNumber);
		else
			return null;
	}
	
	public BufferedImage getCurrentPage()
	{
		if (curImage == null)
			newPage();
		return curImage;
	}
	
	public void newPage()
	{
		curImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		pageList.add(curImage);
		gg = curImage.createGraphics();
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gg.setColor(Color.white);
		gg.fillRect(0, 0, width, height);
		gg.setColor(Color.black);
		ty = margin.top;
		tx = margin.left;
	}
	
	public Graphics2D getGraphics()
	{
		return gg;
	}
	
	public int getPageHeight()
	{
		return height;
	}
	
	public void write(String filename)
	{
		int pageCount = 0;
		
		for (BufferedImage img : pageList)
		{
			try 
			{
				ImageIO.write(img, "PNG", new File(Configuration.getDirectory() + File.separator + filename+pageCount));
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pageCount++;

			
		}
	}
	
}
