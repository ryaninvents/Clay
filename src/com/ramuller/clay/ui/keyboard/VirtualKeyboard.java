package com.ramuller.clay.ui.keyboard;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import com.ramuller.clay.event.TouchEventListener;
import com.ramuller.clay.ui.Component;


public class VirtualKeyboard extends Component implements TouchEventListener {
	
	private int currentView=0;
	
	private Vector<KeyboardView> views;
	
	public VirtualKeyboard(){
		views = new Vector<KeyboardView>();
		setWidth(600);
		setHeight(250);
		setX(0);
		setY(800-getHeight());
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
	
	public void paint(Graphics2D g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		views.get(currentView).paint(g);
	}
	public void reflow(){
		for(KeyboardView view : views){
			view.reflow();
		}
	}
}
