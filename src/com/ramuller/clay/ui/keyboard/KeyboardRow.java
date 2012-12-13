package com.ramuller.clay.ui.keyboard;

import com.ramuller.clay.event.Event;
import com.ramuller.clay.event.KeyEvent;
import com.ramuller.clay.event.KeyEventListener;
import com.ramuller.clay.ui.containers.HorizontalFlow;

public class KeyboardRow extends HorizontalFlow implements KeyEventListener{
	
	public KeyboardRow(String k,KeyboardView parent){
		super(parent);
		int i,code;
		VirtualKey key;
		for(i=0;i<k.length();i++){
			code = (int) k.charAt(i);
			switch((char)code){
			case '\u21e7':
				code = KeyEvent.VK_SHIFT;
				break;
			case '\u2190':
				code = KeyEvent.VK_BACK_SPACE;
				break;
			case '\u2026':
				code = KeyEvent.VK_SHIFT2;
				break;
			}
			key = new VirtualKey(k.charAt(i),code,this);
			if(key.getLabel()==' '){
				this.addComponent(key,3);
			}else
				this.addComponent(key);
		}
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
