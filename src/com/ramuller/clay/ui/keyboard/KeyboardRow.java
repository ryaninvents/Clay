package com.ramuller.clay.ui.keyboard;

import com.ramuller.clay.event.KeyEvent;
import com.ramuller.clay.event.KeyEventListener;
import com.ramuller.clay.ui.containers.HorizontalFlow;

public class KeyboardRow extends HorizontalFlow implements KeyEventListener{
	
	public KeyboardRow(String k,KeyboardView parent){
		super(parent);
		int i;
		VirtualKey key;
		for(i=0;i<k.length();i++){
			key = new VirtualKey(k.charAt(i),(int)k.charAt(i),this);
			if(key.getLabel()==' '){
				this.addComponent(key,3);
			}
			this.addComponent(key);
		}
	}

	@Override
	public void event(KeyEvent ev) {
		getParent().event(ev);
	}
}
