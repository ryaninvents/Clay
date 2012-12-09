package koper;


import java.awt.Graphics2D;
import java.awt.Rectangle;


/**
*
* @author sfritz
*/

public class AppletPanel extends ComponentContainer
{

	Display display = null;
	StatusBar statusBar = null;
	MenuBar menuBar = null;
	VirtualKeyboard vKeyboard = null;
	ContentPane contentPane = null;
	ModalDialog modalDialog = null;
	
	public AppletPanel()
	{
		super(new Rectangle(0, 0, Configuration.width, Configuration.height));
		opaque = false;
		statusBar = new StatusBar(new Rectangle(0, 0, bounds.width, Configuration.statusBarHeight));
		add(statusBar);
//		menuBar = new MenuBar(new Rectangle(0, Configuration.statusBarHeight + 1, bounds.width, Configuration.menuBarHeight));
//		add(menuBar);
		vKeyboard = new VirtualKeyboard(new Rectangle(0, 550, bounds.width, 0));
		add(vKeyboard);
//		if (menuBar != null)
//			contentPane = new ContentPane(new Rectangle(0, Configuration.statusBarHeight + Configuration.menuBarHeight + 1, bounds.width, bounds.height-Configuration.statusBarHeight-vKeyboard.getHeight()-menuBar.getHeight()));
//		else
		contentPane = new ContentPane(new Rectangle(0, Configuration.statusBarHeight + 1, bounds.width, bounds.height-Configuration.statusBarHeight-vKeyboard.getHeight()));			
		add(contentPane);
	}

	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugPaint)
			System.out.println("Panel.paint");
//		super.paint(gg);
		gg.setColor(bgColor);
		gg.fillRect(0, 0, bounds.width, bounds.height);
		if (statusBar != null)
		{
			gg.translate(statusBar.bounds.x, statusBar.bounds.y);
			statusBar.paint(gg);
			gg.translate(-statusBar.bounds.x, -statusBar.bounds.y);
		}
		if (menuBar != null)
		{
			gg.translate(menuBar.bounds.x, menuBar.bounds.y);
			menuBar.paint(gg);
			gg.translate(-menuBar.bounds.x, -menuBar.bounds.y);
		}
		if (contentPane != null)
		{
			gg.translate(contentPane.bounds.x, contentPane.bounds.y);
			contentPane.paint(gg);
			gg.translate(-contentPane.bounds.x, -contentPane.bounds.y);
		}
		if (vKeyboard != null)
		{
			gg.translate(vKeyboard.bounds.x, vKeyboard.bounds.y);
			vKeyboard.paint(gg);
			gg.translate(-vKeyboard.bounds.x, -vKeyboard.bounds.y);	
		}	
	}

	public void repaint(boolean fastUpdate)
	{
		if (Configuration.debugPaint)
			System.out.println("Panel.repaint");
		repaint(bounds, fastUpdate);
	}
	
	public void repaint(Rectangle region, boolean fastUpdate)
	{
//		if (Configuration.debugPaint)
			System.out.println("Panel.repaint(region) " + region.toString() + " fastUpdate=" + fastUpdate);
		updateRegion = new Rectangle(region);
		if (display != null)
		{
			// update region of the panel
			Graphics2D gg = (Graphics2D) display.graphics.create();
			gg.setClip(region.x, region.y, region.width, region.height);
			paint(gg);
			gg.setClip(null);
			// update modified region on the display fb
			display.repaint(region, fastUpdate);
		}
	}
	public void repaint()
	{
		if (Configuration.debugPaint)
			System.out.println("Panel.repaint");
		repaint(bounds);
	}
	
	public void repaint(Rectangle region)
	{
		if (Configuration.debugPaint)
			System.out.println("Panel.repaint(region) " + region.toString());
		updateRegion = new Rectangle(region);
		if (display != null)
		{
			// update region of the panel
			Graphics2D gg = (Graphics2D) display.graphics.create();
			gg.setClip(region.x, region.y, region.width, region.height);
			paint(gg);
			gg.setClip(null);
			// update modified region on the display fb
			display.repaint(region, fastUpdate);
		}
	}
	
	
	public void invalidate()
	{
		if (Configuration.debugPaint)
			System.out.println("Panel.invalidate");
		repaint();
		
	}
	
	public void invalidate(boolean fastUpdate)
	{
		if (Configuration.debugPaint)
			System.out.println("Panel.invalidate(fastUpdate)");
		repaint(fastUpdate);
		
	}
	
	public void setContentPane(ContentPane cp)
	{
		if (cp != null)
		{
			if (contentPane != null)
				remove(contentPane);
			contentPane = cp;
			if (menuBar != null)
				contentPane.setLocation(contentPane.getX(), menuBar.getY() + menuBar.getHeight() + 1);
			else
				contentPane.setLocation(contentPane.getX(), statusBar.getHeight() + 1 );				
			add(contentPane);
		}
	}

	public ContentPane getContentPane() {
		return contentPane;
	}

	public void setMenuBar(MenuBar mb)
	{
		if (menuBar != null)
			remove(menuBar);
		if (mb != null)
		{
			if (menuBar == null)
			{
				if (contentPane != null)
				{
					contentPane.setLocation(contentPane.getX(), mb.getY() + mb.getHeight() + 1);
					contentPane.resize(contentPane.getWidth(), contentPane.getHeight() - mb.getHeight());
				}				
			}
			menuBar = mb;
			menuBar.setVisible();
			menuBar.setLocation(0, statusBar.getHeight() + 1);
			add(menuBar);
		}
		else
		{
			if (contentPane != null)
			{
				contentPane.setLocation(contentPane.getX(), statusBar.getHeight() + 1);
				contentPane.resize(contentPane.getWidth(), contentPane.getHeight() + menuBar.getHeight());
			}				
			menuBar = null;
		}
	}
	
	public MenuBar getMenuBar() {
		return menuBar;
	}

	
	public void showKeyboard(boolean immediateRepaint)
	{
		vKeyboard.visible = true;
		vKeyboard.bounds.height = 250;
		contentPane.resize(contentPane.getWidth(), contentPane.getHeight() - vKeyboard.getHeight());
		if (immediateRepaint)
			repaint();
	}
	
	public void hideKeyboard(boolean immediateRepaint)
	{
//		vKeyboard.bounds.height = 0;
		contentPane.resize(contentPane.getWidth(), contentPane.getHeight() + vKeyboard.getHeight());
		vKeyboard.visible = false;
		if (immediateRepaint)
			repaint();
//		repaint();
	}
	
	public void showModalDialog(ModalDialog mDialog)
	{
		modalDialog = mDialog;
		repaint();
	}
	
	public Display getDisplay()
	{
		return display;
	}
//	public void setContentPane(ContentPane contentPane) {
//		this.contentPane = contentPane;
//	}
	
//	public void add(Component comp)
//	{
//		if (Configuration.debugComponents)
//			System.out.println("Panel.add");
//		super.add(comp);
////		repaint();
////		display.repaint();
//		
//	}


//	public void event(Event ev)
//	{
//		System.out.println("Panel.event " + ev);
//		if (menuBar != null)
//			menuBar.event(ev);
//		if (contentPane != null)
//			contentPane.event(ev);
//		if (vKeyboard != null)
//			vKeyboard.event(ev);
//	}
}
