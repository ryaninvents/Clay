package com.ramuller.clay.ui;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.ramuller.clay.display.Display;
import com.ramuller.clay.ui.keyboard.VirtualKeyboard;



public class Applet extends Container{
	private Display display;
	
	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 800;
	
	public Applet(Display d){
		super(null);
		display = d;
		setX(0);
		setY(0);
		setWidth(SCREEN_WIDTH);
		setHeight(SCREEN_HEIGHT);
		VirtualKeyboard vk = new VirtualKeyboard(this);
		ArrayList<Container.LayoutInfo> list = getLayoutList();
		Component c = new NullComponent(this);
		list.add(new LayoutInfo(c));
		list.add(new LayoutInfo(vk));
	}

	public void addComponent(Component c){
		getLayoutList().set(0, new LayoutInfo(c));
	}
	
	public void removeComponent(Component c){
		throw new RuntimeException("Can't remove the only component from an applet");
	}
	
	public void setKeyboardVisible(boolean b){
		getComponent(1).setVisible(b);
	}
	
	public void reflow() {
		Component c = getComponent(0);
		c.setX(0);
		c.setY(0);
		c.setWidth(getWidth());
		c.setHeight(getHeight());
	}
	
	public void repaint(){
		Graphics2D gg = (Graphics2D) display.getGraphics();
		gg.setClip(0, 0, 600, 800);
		gg.fillRect(0, 0, 600, 800);
		this.update(gg);
		display.repaint(false);
	}
	
}
