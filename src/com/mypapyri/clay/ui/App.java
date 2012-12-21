package com.mypapyri.clay.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.mypapyri.clay.ClaySystem;
import com.mypapyri.clay.display.Display;
import com.mypapyri.clay.ui.keyboard.Keyboard;

public class App extends Panel {
	private static final long serialVersionUID = -669809789921280413L;
	
	private static Display display;
	Keyboard vk;

	
	public App(){
		super();
		vk = new Keyboard();
		
		setSize(ClaySystem.getScreenSize());
		
	}
	
	public static void setDisplay(Display display) {
		App.display = display;
	}
	

	public Display getDisplay(){
		return display;
	}
	
	public void repaint(){
		if(display==null) return;
		Graphics2D g = getDisplay().getGraphics();
		paint(g);
		getDisplay().repaint(ClaySystem.getQuickRefresh());
	}
	
	public void repaint(long w){
		repaint();
	}
	
	public void repaint(int x, int y, int width, int height){
		Rectangle r = new Rectangle(x,y,width,height);
		Graphics2D g = getDisplay().getGraphics();
		paint(g);
		getDisplay().repaint(r);
	}
	
	public void repaint(long w, int x, int y, int wi, int h){
		repaint(x,y,wi,h);
	}
	

}
