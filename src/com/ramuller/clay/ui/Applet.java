package com.ramuller.clay.ui;

import java.util.ArrayList;

import com.ramuller.clay.display.Display;
import com.ramuller.clay.ui.keyboard.VirtualKeyboard;



public class Applet extends Container{
	private Display display;
	
	public Applet(Display d){
		super(null);
		display = d;
		VirtualKeyboard vk = new VirtualKeyboard(this);
		ArrayList<Container.LayoutInfo> list = getLayoutList();
		list.ensureCapacity(2);
		list.set(1, new LayoutInfo(vk));
	}

	public void addComponent(Component c){
		getLayoutList().set(0, new LayoutInfo(c));
	}
	
	public void removeComponent(Component c){
		throw new RuntimeException("Can't remove the only component from an applet");
	}
	
	public void reflow() {
		Component c = getComponent(0);
		c.setX(0);
		c.setY(0);
		c.setWidth(getWidth());
		c.setHeight(getHeight());
	}
	
}
