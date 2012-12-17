package com.mypapyri.clay.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import com.ramuller.clay.display.Display;

public class App extends JPanel {
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
	
}
