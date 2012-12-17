package com.mypapyri.clay.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.ramuller.clay.display.Display;

public class App extends Container {
	private static final long serialVersionUID = -669809789921280413L;
	private Display display;
	

	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 800;
	public static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT);
	
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
	
}
