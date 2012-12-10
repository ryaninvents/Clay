package com.ramuller.clay.ui.keyboard;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;


public class VirtualKeyboard {
	private int width;
	private int height;
	private int x,y;
	
	private Vector<KeyboardView> views;
	
	public VirtualKeyboard(){
		views = new Vector<KeyboardView>();
		width = 600;
		height = 250;
		x = 0;
		y = 800-height;
		init();
		reflow();
	}
	
	public void init(){
		KeyboardView view = new KeyboardView(this);
		KeyboardRow row;
		
		row = new KeyboardRow("qwertyuiop",view);
		view.addRow(row);
		row = new KeyboardRow("asdfghjkl;",view);
		view.addRow(row);
		row = new KeyboardRow("zxcvbnm,.",view);
		view.addRow(row);
		row = new KeyboardRow("# ^",view);
		view.addRow(row);
		addView(view);
	}
	
	public void addView(KeyboardView view){
		views.add(view);
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void paint(Graphics2D g){
		g.translate(x, y);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		views.get(0).paint(g);
		g.translate(-x, -y);
		
	}
	public void reflow(){
		for(KeyboardView view : views){
			view.reflow();
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
