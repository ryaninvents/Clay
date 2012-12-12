package com.ramuller.clay.ui;

import java.awt.Graphics2D;
import java.util.Vector;

import com.ramuller.clay.event.TouchEvent;
/**
 * A Container is, simply enough, a component that holds
 * other components.
 * @author ryan
 *
 */
public abstract class Container extends Component {
	
	private Vector<LayoutInfo> components;
	/**
	 * Each LayoutInfo item holds a component as well
	 * as the information required to calculate the
	 * component's location in the given layout. For
	 * instance, a horizontal layout might include a
	 * float parameter that determines the component's
	 * weight, or a border layout might use an enum to
	 * determine NORTH, SOUTH, EAST, WEST, or CENTER.
	 * @author ryan
	 *
	 */
	protected class LayoutInfo{
		private Component component;
		
		public LayoutInfo(Component c){
			component = c;
		}

		public Component getComponent() {
			return component;
		}

		public void setComponent(Component component) {
			this.component = component;
		}
	}
	/**
	 * Paints this component and all subcomponents.
	 */
	public void paint(Graphics2D g) {
		for(LayoutInfo li : components){
			li.getComponent().update(g);
		}
	}
	
	/**
	 * Recalculate the layout. It's important to do
	 * this after a component is added, but this will
	 * likely be automatic.
	 */
	public abstract void reflow();
	
	protected Vector<LayoutInfo> getComponents(){
		return components;
	}
	
	public void addComponent(Component c){
		components.add(new LayoutInfo(c));
	}
	
	public void removeComponent(Component c){
		LayoutInfo marked = null;
		for(LayoutInfo li:components){
			if(li.getComponent()==c){
				marked = li;
				break;
			}
		}
		if(marked!=null)
			components.remove(marked);
	}
	
	public void onTouch(TouchEvent ev){
		Component c;
		for(LayoutInfo li : components){
			c = li.getComponent();
			if(c.inside(ev)){
				c.onTouch(ev.dup(c));
			}
		}
	}

}
