package com.ramuller.clay.ui.containers;

import java.awt.Graphics2D;

import com.ramuller.clay.event.TouchEvent;
import com.ramuller.clay.ui.Component;
import com.ramuller.clay.ui.Container;

/**
 * Implements a simple layout with "pages," which
 * may be shown one at a time.
 * @author ryan
 *
 */
public class PagedLayout extends Container {
	public PagedLayout(Component parent) {
		super(parent);
	}

	/**
	 * The index of the current page.
	 */
	private int currentPage;
	
	public void paint(Graphics2D g){
		getLayoutList().get(currentPage).getComponent().update(g);
	}
	
	

	public int getCurrentPage() {
		return currentPage;
	}



	public void setCurrentPage(int currentPage) {
		getComponent(this.currentPage).setVisible(false);
		this.currentPage = currentPage;
		getComponent(this.currentPage).setVisible(true);
	}



	public void addComponent(Component c){
		getLayoutList().add(new LayoutInfo(c));
		reflow();
	}

	public void addComponent(Component c,int page){
		getLayoutList().set(page, new LayoutInfo(c));
		reflow();
	}
	
	public Component getVisibleComponent(){
		return getComponent(currentPage);
	}
	
	public void reflow() {
		Component c;
		for(LayoutInfo li:getLayoutList()){
			c = li.getComponent();
			c.setX(0);
			c.setY(0);
			c.setWidth(getWidth());
			c.setHeight(getHeight());
		}
		reflowChildren();
	}

}
