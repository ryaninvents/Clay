import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;



public abstract class Applet implements MouseEventListener, KeyEventListener
{
	AppletPanel mainPanel = null;
	BufferedImage appIcon = null;
	boolean visible = false;
	boolean running = false;

	public abstract void init();
	
	public Applet()
	{
		mainPanel = new AppletPanel();
	}
	
	public void setMainPanel(AppletPanel p)
	{
		mainPanel = p;
	}
	
	public void repaint()
	{
		if (visible)
			mainPanel.repaint();
	}
	
	public abstract String getName();
	
//	public void event(MouseEvent ev)
//	{
//		mainPanel.event(ev);
//	}
	
	public BufferedImage getIcon()
	{
		return appIcon;
	}
	
	public void setIcon(String imageName)
	{
		try 
		{
			java.net.URL url = this.getClass().getResource(imageName );
//			System.out.println("setIcon: name: " + imageName + " url: " + url);
		    appIcon = ImageIO.read(url);
		} 
		catch (IOException e) 
		{
			System.out.println("Image not found!");
		}	
	}
	
	public void setVisible(Display display)
	{
		Logger.getLogger(getClass().getName()).log(Level.INFO, null);
		visible = true;
		mainPanel.display = display;
		repaint();
	}
	
	public void hide()
	{
		Logger.getLogger(getClass().getName()).log(Level.INFO, null);
		visible = false;
		mainPanel.display = null;
	}
	
	public void quit()
	{
		running = false;
		hide();
	}
	
	public void event(Event ev)
	{
		mainPanel.event(ev);
	}
	
}
