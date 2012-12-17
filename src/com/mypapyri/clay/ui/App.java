package com.mypapyri.clay.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import com.mypapyri.clay.event.TouchEvent;
import com.mypapyri.clay.event.TouchEventListener;
import com.ramuller.clay.display.Display;

public class App extends JPanel implements TouchEventListener{
	private static final long serialVersionUID = -669809789921280413L;
	private Display display;
	
	
	public App(Display d){
		super();
		display = d;
	}
	
	public void repaint(){
		Graphics2D g = display.getGraphics();
		paint(g);
		display.repaint();
	}
	
	public void repaint(int x, int y, int width, int height){
		Rectangle r = new Rectangle(x,y,width,height);
		Graphics2D g = display.getGraphics();
		paint(g);
		display.repaint(r);
	}
	
	public void reflow(){
		getLayout().layoutContainer(this);
	}

	@Override
	public void onTap(TouchEvent e) {
		
	}

	@Override
	public void onLongTap(TouchEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouchDown(TouchEvent e) {
		processMouseEvent(e);
	}

	@Override
	public void onTouchUp(TouchEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrag(TouchEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
