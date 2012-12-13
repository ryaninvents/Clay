package com.ramuller.clay.ui.keyboard;

import java.util.ArrayList;

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
	
	public void addView(KeyboardView view){
		super.addComponent(view);
	}

	@Override
	public void event(KeyEvent ev) {
		System.out.println("KEY_"+ev.code);
	}


}
