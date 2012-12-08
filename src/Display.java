import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public abstract class Display
{
	BufferedImage image;
	// for internal use
	public Graphics2D graphics;
	
	public Display(BufferedImage image) 
	{
		this.image = image;
		graphics = image.createGraphics();
		graphics.setBackground(Color.white);
		graphics.setColor(Color.white);
		System.out.printf("construct Display width=%d height=%d", image.getWidth(), image.getHeight());
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//		graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		graphics.setClip(0, 0, image.getWidth(), image.getHeight());
		graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
		graphics.setColor(Color.black);
	}
	
	public abstract void repaint(Rectangle region);
	
	public abstract void repaint(Rectangle region, boolean fastUpdate);
	
	public abstract void repaint();
	
	public int getWidth()
	{
		return image.getWidth();
	}
	
	public int getHeight()
	{
		return image.getHeight();
	}
	
	
}
