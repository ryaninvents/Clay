package com.ramuller.clay.ui.keyboard;

import java.awt.Color;
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
		listeners = new ArrayList<KeyEventListener>();
		setWidth(600);
		setHeight(250);
		setX(0);
		setY(800-getHeight());
		init();
		reflow();
	}
	
	public void init(){
		KeyboardView view;
		KeyboardRow row;
		
		view = new KeyboardView(this);
		
		row = new KeyboardRow("qwertyuiop",view);
		view.addRow(row);
		row = new KeyboardRow("asdfghjkl'",view);
		view.addRow(row);
		row = new KeyboardRow("zxcvbnm,.",view);
		view.addRow(row);
		row = new KeyboardRow("^ <",view);
		view.addRow(row);
		addView(view);
		
		view = new KeyboardView(this);
		
		row = new KeyboardRow("QWERTYUIOP",view);
		view.addRow(row);
		row = new KeyboardRow("ASDFGHJKL\"",view);
		view.addRow(row);
		row = new KeyboardRow("ZXCVBNM!?",view);
		view.addRow(row);
		row = new KeyboardRow("^ <",view);
		view.addRow(row);
		addView(view);
	}
	
	public void paint(Graphics2D g){
		g.setFont(new Font("SansSerif",Font.PLAIN,getHeight()/5));
		g.setColor(Color.gray);
		g.fillRect(0,0,getWidth(),getHeight());
		super.paint(g);
	}
	
	public void addView(KeyboardView view){
		super.addComponent(view);
	}
	
	public void addKeyEventListener(KeyEventListener l){
		listeners.add(l);
	}

	public void event(Event ev){
		
		if(ev instanceof KeyEvent){
			this.event((KeyEvent)ev);
		}
		else super.event(ev);
	}
	

	@Override
	public void event(KeyEvent ev) {
		switch(getCurrentPage()){
			case 0:
				if(ev.code==KeyEvent.VK_SHIFT){
					setCurrentPage(1);
				}
				break;
			case 1:
				setCurrentPage(0);
		}
		for(KeyEventListener l:listeners){
			l.event(ev);
		}
	}


}
