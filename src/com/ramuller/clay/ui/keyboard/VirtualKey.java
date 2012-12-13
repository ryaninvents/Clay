package com.ramuller.clay.ui.keyboard;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.ramuller.clay.event.EventType;
import com.ramuller.clay.event.KeyEvent;
import com.ramuller.clay.event.KeyEventListener;
import com.ramuller.clay.event.TouchEvent;
import com.ramuller.clay.ui.Component;

public class VirtualKey extends Component{
	private static int margin=2;
	private static int radius=16;
	private char label;
	private boolean invert=false;
	private int keyCode=KeyEvent.VK_0;
	
	ArrayList<KeyEventListener> listeners;
	
	public VirtualKey(char label, int keyCode, KeyboardRow parent){
		super(parent);
		this.label = label;
		this.keyCode = keyCode;
		listeners = new ArrayList<KeyEventListener>();
		addKeyListener(parent);
	}
	
	public boolean isInverted() {
		return invert;
	}

	public void invert(boolean invert) {
		this.invert = invert;
	}

	public VirtualKey(char label,Component p){
		super(p);
		this.label = label;
	}
	
	public void addKeyListener(KeyEventListener l){
		listeners.add(l);
	}

	public void paint(Graphics2D g){
		int height = getHeight();
		paintFrame(g);
		g.drawString(""+label, getX()+3, getY()+(int)(height*0.8f));
	}
	public void paintFrame(Graphics2D g){
		int height = getHeight();
		g.setColor(invert?Color.BLACK:Color.WHITE);
		g.fillRoundRect(getX()+margin, getY()+margin, getWidth()-2*margin, height-2*margin, radius, radius);
		g.setColor(invert?Color.WHITE:Color.BLACK);
		if(!invert)g.drawRoundRect(getX()+margin, getY()+margin, getWidth()-2*margin, height-2*margin, radius, radius);
	}

	public boolean onTouch(TouchEvent ev){
		if(this.contains(ev)){
			KeyEvent ke = new KeyEvent(EventType.KeyTyped, System.currentTimeMillis(), keyCode);
			for(KeyEventListener l:listeners){
				l.event(ke);
			}
			return true;
		}
		return false;
	}
	
}
