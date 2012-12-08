

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Component implements MouseEventListener, KeyEventListener
{

	protected Rectangle bounds = new Rectangle(0, 0, 0, 0);
	protected Rectangle updateRegion = null;
	
	boolean fastUpdate = false;
	protected Component parent = null;
	protected Color bgColor = Color.WHITE;
	protected Color color = Color.BLACK;
	protected Color borderColor = Color.BLACK;
	boolean opaque = true;
	boolean visible = true;
	boolean invalid = true;
	boolean hasFocus = false;
	boolean hasBorder = false;
	private ArrayList<ChangeListener> changeListeners = new ArrayList<ChangeListener>();
	
	public Component()
	{
		
	}
	
	public Component(Rectangle bounds)
	{
		this.bounds = bounds;
	}
	
	@Override
//	public void event(MouseEvent ev)
//	{
//		System.out.println("Component.MouseEvent");
//		// TODO Auto-generated method stub
//
//	}
//	
	public void event(Event ev)
	{
		// TODO Auto-generated method stub
		Logger.getLogger(getClass().getName()).log(Level.INFO, null);

	}
	
	public void event(KeyEvent ev) 
	{
		// do nothing
	}
	
	public void paint(Graphics2D gg)
	{
		if (visible)
		{
			if (opaque)
			{
				gg.setColor(bgColor);
				gg.fillRect(0, 0, bounds.width, bounds.height);
			}
			if (hasBorder)
			{
				gg.setColor(borderColor);
				gg.setStroke(new BasicStroke(1));
				gg.drawRect(0, 0, bounds.width, bounds.height);
				
			}
		}
		else
			if (Configuration.debugPaint)
				System.out.println("Component.paint: " + getClass().getName() + " is not visible!");
		invalid = false;
	}
	
	public void setBgColor(Color c)
	{
		bgColor = c;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public void setVisible()
	{
		visible = true;
		repaint();
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public void hide()
	{
		visible = false;
		repaint();
	}
	
	public void paintChild(Graphics2D gg, Component w)
	{
		if (Configuration.debugPaint)
			System.out.println("Component.paintChild: " + w.getClass().getName() + " bounds=" + bounds);
		if (visible)
		{
			gg.translate(w.bounds.x, w.bounds.y);
			w.paint(gg);
			gg.translate(-w.bounds.x, -w.bounds.y);	
		}
	}
	
	public void repaint(boolean fastUpdate)
	{
		if (Configuration.debugPaint)
			System.out.println("Component.repaint: " + getClass().getName() + " fastUpdate=" + fastUpdate);
		this.fastUpdate = fastUpdate;
		
		repaint(bounds.getBounds(), fastUpdate);
	}
	
	public void repaint (Rectangle region, boolean fastUpdate)
	{
//		if (Configuration.debugPaint)
			System.out.println("Component.repaint(region): " + getClass().getName() + " region=" + region + " fastUpdate=" + fastUpdate);
		invalid = true;
		this.fastUpdate = fastUpdate;
		if (parent == null) 
		{
			return;
		}
		region.x += parent.bounds.x;
		region.y += parent.bounds.y;
		if (updateRegion == null)
			updateRegion = new Rectangle(region);
		else
			updateRegion.add(region);
		parent.repaint(region, fastUpdate);
		
	}
	
	public void repaint()
	{
		if (Configuration.debugPaint)
			System.out.println("Component.repaint: " + getClass().getName());
		repaint(bounds.getBounds());
	}
	
	public void repaint (Rectangle region)
	{
		if (Configuration.debugPaint)
			System.out.println("Component.repaint(region): " + getClass().getName() + " region=" + region);
		invalid = true;
		if (parent == null) 
		{
			return;
		}
		updateRegion = new Rectangle(region);
		region.x += parent.bounds.x;
		region.y += parent.bounds.y;
		parent.repaint(region);
		
	}
	
	public void invalidate(boolean fastUpdate)
	{
		if (Configuration.debugPaint)
			System.out.println("Component.invalidate: " + getClass().getName() + " fastUpdate=" + fastUpdate);
		invalid = true;
		this.fastUpdate = fastUpdate;
		if (parent == null) 
		{
			return;
		}
//		parent.invalidate();
		repaint(fastUpdate);
	}
	
	public void invalidate(Rectangle region, boolean fastUpdate)
	{
		if (Configuration.debugPaint)
			System.out.println("Component.invalidate: " + getClass().getName() + " region=" + region + " fastUpdate=" + fastUpdate);
		invalid = true;
		this.fastUpdate = fastUpdate;
		if (parent == null) 
		{
			return;
		}
//		parent.invalidate();
		repaint(region, fastUpdate);
	}
	
	public void invalidateInterior(Rectangle region, boolean fastUpdate)
	{
		if (Configuration.debugPaint)
			System.out.println("Component.invalidateInterior: " + getClass().getName() + " region=" + region + " fastUpdate=" + fastUpdate);
//		invalid = true;
//		this.fastUpdate = fastUpdate;
//		if (parent == null) 
//		{
//			return;
//		}
//		parent.invalidate();
		invalidate(new Rectangle(bounds.x + region.x, bounds.y + region.y, region.width, region.height), fastUpdate);
		
	}
	
	public void focus()
	{
		hasFocus = true;
		invalidate(true);
	}
	
	public void validate()
	{
		updateRegion = null;
		invalid = false;
		fastUpdate = false;
	}
	
	public void setLocation(int x, int y)
	{
		bounds.x = x;
		bounds.y = y;
	}
	
	public Integer getX()
	{
		return bounds.x;
	}
	
	public Integer getY()
	{
		return bounds.y;
	}
	
	public void resize(int width, int height)
	{
		bounds.width = width;
		bounds.height = height;
	}
	
	public Integer getWidth()
	{
		return bounds.width;
	}

	public Integer getHeight()
	{
		return bounds.height;
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public void addChangeListener(ChangeListener cl)
	{
		changeListeners.add(cl);
	}
	
	public void notifyChangeListeners()
	{
		for (int i = 0; i < changeListeners.size(); i++) 
		{
			changeListeners.get(i).notify(this);

		}

	}
	
}
