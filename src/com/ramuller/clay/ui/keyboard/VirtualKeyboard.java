package com.ramuller.clay.ui.keyboard;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.ramuller.clay.event.Event;
import com.ramuller.clay.event.KeyEvent;
import com.ramuller.clay.event.KeyEventListener;
import com.ramuller.clay.event.TouchEventListener;
import com.ramuller.clay.ui.Component;
import com.ramuller.clay.ui.containers.PagedLayout;


public class VirtualKeyboard extends PagedLayout implements TouchEventListener, KeyEventListener {
	
	ArrayList<KeyEventListener> listeners;
	
	public VirtualKeyboard(Component parent){
		super(parent);
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
	
	public void paint(Graphics2D g){
		g.setFont(new Font("SansSerif",Font.PLAIN,getHeight()/5));
		super.paint(g);
	}
	
	public void addView(KeyboardView view){
		super.addComponent(view);
	}
	

	public void event(Event ev){
		if(ev instanceof KeyEvent){
			this.event((KeyEvent)ev);
		}
		else super.event(ev);
	}
	

	@Override
	public void event(KeyEvent ev) {
		System.out.print((char)ev.code);
	}


}
