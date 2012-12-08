

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;


public abstract class ComponentContainer extends Component
{
	
	protected List<Component> components = new LinkedList<Component>();
	Component focussedComponent = null;
	
	public ComponentContainer(Rectangle bounds)
	{
		super(bounds);
	}
	
	public void paintChildren(Graphics2D gg)
	{
		for (Component comp : components) 
		{
//			gg.setClip(comp.bounds.x, comp.bounds.y, comp.bounds.width, comp.bounds.height);
//			if (comp.invalid)
			if (updateRegion != null)
			{
				if (Configuration.debugPaint)
				{
					String compName = comp.getClass().getName();
					System.out.println("ComponentContainer.paintChildren " + compName + " : updateRegion=" + updateRegion.toString() + " compBounds=" + comp.bounds.toString());
				}
				Rectangle tmpRegion = new Rectangle(comp.bounds);
				tmpRegion.setLocation(tmpRegion.x + bounds.x, tmpRegion.y + bounds.y);
				if (updateRegion.intersects(tmpRegion))
				{
					paintChild(gg, comp);
					comp.validate();
				}
			}
			else
			{
				paintChild(gg, comp);				
			}
			
		}
		validate();
		
	}
	
	public void paint(Graphics2D gg)
	{
		if (Configuration.debugPaint)
			System.out.println("ComponentContainer.paint: " + getClass().getName() + " : updateRegion=" + updateRegion + " bounds=" + bounds.toString());
		super.paint(gg);

		//System.out.println("paint group clip = " + g.getClipBounds() + " rect " + bounds + " pos " + pointRelativeTo(getScreen(), bounds.getLocation()));

		//g.translate(bounds.x + margin.left, bounds.y + margin.top);
		//g.translate(margin.left, margin.top);
//		Shape old = gg.getClip();
//		gg.setClip(old);
		
		// 03.11.2012
		if (visible)
			paintChildren(gg);
	}
	
	public void add(Component comp)
	{
		if (Configuration.debugComponents)
			System.out.println("ComponentContainer.add");
		components.add(comp);
		comp.parent = this;
		// test for regional updating 
//		Rectangle r = comp.bounds;
//		r.x += parent.bounds.x;
//		r.y += parent.bounds.y;
//		if (updateRegion == null)
//			updateRegion = new Rectangle(r);
//		else
//			updateRegion.add(r);
		
		
		if (focussedComponent == null)
		{
			focussedComponent = comp;
			comp.hasFocus = true;
		}
		
	}
	
	public void remove(Component comp)
	{
		if (Configuration.debugComponents)
			System.out.println("ComponentContainer.remove");
		components.remove(comp);
		if (focussedComponent == comp)
			if (components.size() > 0)
				focussedComponent = components.get(1);
			else
				focussedComponent = null;		
	}
	
	public void setFocus(Component comp)
	{
		if (Configuration.debugComponents)
			System.out.println("ComponentContainer.setFocus");
		if (focussedComponent != null)
		{
//			if (focussedComponent != comp)
			// change focus and invalidate only if not already focussed component 
			{
				focussedComponent.hasFocus = false;
				focussedComponent.invalidate(true);
				focussedComponent = comp;
				comp.focus();		

			}
		}
		else
		{
			focussedComponent = comp;
			comp.focus();
		}
	}
	
	public void event(Event ev)
	{
		if (Configuration.debugEvents)
			System.out.println();

		Rectangle rect = null;
		for (Component comp : components) 
		{

			if (comp.isVisible())
			// invisible components cannot get any events
			{
				if (Configuration.debugEvents)
					System.out.printf("ComponentContainer.MouseEvent: " + comp.getClass().getName() + " bounds x%d y%d w%d h%d, contains x=%d, y=%d\n", comp.getBounds().x, comp.getBounds().y, comp.getBounds().width, comp.getBounds().height, ((MouseEvent)ev).x, ((MouseEvent)ev).y);
				rect = comp.getBounds();
				if (rect.contains(((MouseEvent)ev).x, ((MouseEvent)ev).y))
				{
					if (Configuration.debugEvents)
						System.out.println("ComponentContainer.MouseEvent: component found!");
					MouseEvent mev = (MouseEvent)ev;
					mev.x = mev.x - rect.x;
					mev.y = mev.y - rect.y;
					if (((MouseEvent)ev).type == EventType.MouseClicked)
						setFocus(comp);
					comp.event(mev);
					break;
				}
			}
			else
				if (Configuration.debugEvents)
					System.out.println("ComponentContainer.MouseEvent: " + comp.getClass().getName() + " is invisible");
				
		}
		
	}
	
	public void event(KeyEvent ev)
	{
		if (Configuration.debugEvents)
			System.out.println("ComponentContainer.KeyEvent");
		if (focussedComponent != null)
			focussedComponent.event(ev);
	
	}

}
