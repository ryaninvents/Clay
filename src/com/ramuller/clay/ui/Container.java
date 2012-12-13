package com.ramuller.clay.ui;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.ramuller.clay.event.TouchEvent;
/**
 * A Container is, simply enough, a component that holds
 * other components.
 * @author ryan
 *
 */
public abstract class Container extends Component {
	
	public Container(Component parent) {
		super(parent);
		layouts = new ArrayList<LayoutInfo>();
	}

	private ArrayList<LayoutInfo> layouts;
	
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
		for(LayoutInfo li : layouts){
			li.getComponent().update(g);
		}
	}
	
	/**
	 * Recalculate the layout. It's important to do
	 * this after a component is added, but this will
	 * likely be automatic.
	 */
	public abstract void reflow();
	
	public void reflowChildren(){
		Component c;
		for(LayoutInfo li:getLayoutList()){
			c = li.getComponent();
			if(c instanceof Container){
				((Container)c).reflow();
			}
		}
	}
	
	protected ArrayList<LayoutInfo> getLayoutList(){
		return layouts;
	}
	
	protected Component getComponent(int i){
		return layouts.get(i).getComponent();
	}
	
	public void addComponent(Component c){
		layouts.add(new LayoutInfo(c));
		c.setParent(this);
	}
	
	public void removeComponent(Component c){
		LayoutInfo marked = null;
		for(LayoutInfo li:layouts){
			if(li.getComponent()==c){
				marked = li;
				break;
			}
		}
		if(marked!=null)
			layouts.remove(marked);
	}
	
	public boolean onTouch(TouchEvent ev){
		
		if(!isVisible()||!contains(ev)){
			return false;
		}
		Component c;
		ev = ev.dup(this);
		for(LayoutInfo li : layouts){
			c = li.getComponent();
			if(!c.isVisible()) continue;
			if(c.onTouch(ev)){
				return true;
			}
		}
		return false;
	}

}
