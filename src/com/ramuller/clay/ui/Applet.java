package com.ramuller.clay.ui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.ramuller.clay.display.Display;
import com.ramuller.clay.event.KeyEvent;
import com.ramuller.clay.event.KeyEventListener;
import com.ramuller.clay.ui.keyboard.VirtualKeyboard;



public class Applet extends Container implements KeyEventListener{
	private Display display;
	
	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 800;
	public static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT);
	
	public Applet(Display d){
		super(null);
		display = d;
		setX(0);
		setY(0);
		setWidth(SCREEN_WIDTH);
		setHeight(SCREEN_HEIGHT);
		VirtualKeyboard vk = new VirtualKeyboard(this);
		ArrayList<Container.LayoutInfo> list = getLayoutList();
		TextDisplay c = new TextDisplay(this);
		vk.addKeyEventListener(c);
		vk.addKeyEventListener(this);
		list.add(new LayoutInfo(c));
		list.add(new LayoutInfo(vk));
		reflow();
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
		display.repaint(true);
	}

	@Override
	public void event(KeyEvent ev) {
		repaint();
	}
	
}
