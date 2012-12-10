package com.ramuller.clay.ui.keyboard;

import java.awt.Graphics2D;
import java.util.Vector;

public class KeyboardView {
	private VirtualKeyboard parent;

	private Vector<KeyboardRow> rows;
	
	public KeyboardView(VirtualKeyboard parent){
		this.parent = parent;
		rows = new Vector<KeyboardRow>();
	}
	
	public void reflow(){
		int h = parent.getHeight()/rows.size();
		int y = 0;
		for(KeyboardRow row : rows){
			row.setHeight(h);
			row.setY(y);
			row.reflow();
			y+=h;
		}
	}
	public int getWidth(){
		return parent.getWidth();
	} 
	public int getHeight(){
		return parent.getHeight();
	}
	
	public void paint(Graphics2D g){
		for(KeyboardRow row : rows){
			row.paint(g);
		}
	}
	public void addRow(KeyboardRow row){
		rows.add(row);
		reflow();
	}
	public int getX(){
		return parent.getX();
	}

	public int getY() {
		return parent.getY();
	}
}
