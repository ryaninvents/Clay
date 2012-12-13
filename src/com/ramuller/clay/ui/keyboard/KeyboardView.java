package com.ramuller.clay.ui.keyboard;

import com.ramuller.clay.event.Event;
import com.ramuller.clay.event.KeyEvent;
import com.ramuller.clay.event.KeyEventListener;
import com.ramuller.clay.ui.containers.VerticalFlow;

public class KeyboardView extends VerticalFlow implements KeyEventListener{
	
	public KeyboardView(VirtualKeyboard parent){
		super(parent);
	}

	public void event(Event ev){
		if(ev instanceof KeyEvent){
			this.event((KeyEvent)ev);
		}
		else super.event(ev);
	}
	
	@Override
	public void event(KeyEvent ev) {
		getParent().event(ev);
	}
}
