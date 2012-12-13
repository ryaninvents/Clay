package com.ramuller.clay.ui.containers;

import com.ramuller.clay.ui.Component;
import com.ramuller.clay.ui.Container;

public class VerticalFlow extends Container {

	public VerticalFlow(Component parent) {
		super(parent);
	}
	private class VertLayoutInfo extends LayoutInfo{
		/**
		 * Is this component's size fixed?
		 */
		private boolean fixed;
		/**
		 * Component's relative weight
		 */
		private float weight;
		
		public VertLayoutInfo(Component c){
			super(c);
			fixed = true;
			weight=1;
		}
		public VertLayoutInfo(Component c,float w){
			super(c);
			fixed = false;
			weight = w;
		}
		
		public boolean isFixed() {
			return fixed;
		}
		public void setFixed(boolean fixed) {
			this.fixed = fixed;
		}
		public float getWeight() {
			return weight;
		}
		public void setWeight(float weight) {
			this.weight = weight;
		}
		
	}
	public void addComponent(Component c){
		getLayoutList().add(new VertLayoutInfo(c));
		c.setParent(this);
	}
	public void addRow(Component c){
		addComponent(c);
	}
	@Override
	public void reflow() {
		int size = getHeight()/getLayoutList().size();
		int y = 0;
		Component c;
		for(LayoutInfo li:getLayoutList()){
			c = li.getComponent();
			c.setHeight(size);
			c.setY(y);
			c.setX(0);
			c.setWidth(getWidth());
			y+=size;
		}
		reflowChildren();
	}

}
