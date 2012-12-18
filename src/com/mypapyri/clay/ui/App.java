package com.mypapyri.clay.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import com.mypapyri.clay.ClaySystem;
import com.mypapyri.clay.event.TouchEvent;
import com.mypapyri.clay.event.TouchEventListener;
import com.ramuller.clay.display.Display;

public class App extends JPanel implements TouchEventListener{
	private static final long serialVersionUID = -669809789921280413L;
	private static Display display;
	
	
	
	public static void setDisplay(Display display) {
		App.display = display;
	}

	public App(){
		super();
		
		this.enableInputMethods(true);
		this.enableEvents(~0);
		
		setSize(ClaySystem.getScreenSize());
		
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public void repaint(){
		if(display==null) return;
		Graphics2D g = getDisplay().getGraphics();
		paint(g);
		getDisplay().repaint();
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
	
	public void reflow(){
		getLayout().layoutContainer(this);
	}

	@Override
	public void onTap(TouchEvent e) {
		System.out.println("onTap()");
		processMouseEvent(e);
	}

	@Override
	public void onLongTap(TouchEvent e) {
		System.out.println("onLongTap()");
		dispatchEvent(e);
	}

	@Override
	public void onTouchDown(TouchEvent e) {
		System.out.println("onTouchDown()");
		processMouseEvent(e);
	}

	@Override
	public void onTouchUp(TouchEvent e) {
		System.out.println("onTouchUp()");
		dispatchEvent(e);
	}

	@Override
	public void onDrag(TouchEvent e) {
		System.out.println("onDrag()");
		dispatchEvent(e);
	}
	
}
