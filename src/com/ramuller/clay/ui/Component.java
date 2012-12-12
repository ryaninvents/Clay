package com.ramuller.clay.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.ramuller.clay.event.Event;
import com.ramuller.clay.event.TouchEvent;

public abstract class Component {
	private int width;
	private int height;
	private int x,y;

	private boolean visible;
	
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
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public boolean inside(TouchEvent ev){
		return new Rectangle(x,y,width,height).contains(ev.x, ev.y);
	}

	public boolean isVisible(){
		return visible;
	}
	public void setVisible(boolean b){
		visible = b;
	}

	public abstract void paint(Graphics2D g);
	public boolean onTouch(TouchEvent ev){return false;}
	
	public void event(Event ev) {
		if(ev instanceof TouchEvent && inside((TouchEvent)ev)){
			this.onTouch((TouchEvent) ev);
		}
	}
	
	public void update(Graphics2D g){
		if(!visible) return;
		g.translate(x, y);
		paint(g);
		g.translate(-x, -y);
	}
}
