package com.ramuller.clay.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.ramuller.clay.event.Event;
import com.ramuller.clay.event.TouchEvent;

public abstract class Component {
	private int width;
	private int height;
	private int x,y;
	private Component parent;
	
	public void setParent(Component parent) {
		this.parent = parent;
	}

	private boolean fixedHoriz, fixedVert;

	public Component(Component parent){
		this.parent = parent;
	}
	
	public Component getParent() {
		return parent;
	}

	public boolean isFixedHoriz() {
		return fixedHoriz;
	}
	public void setFixedHoriz(boolean fixedHoriz) {
		this.fixedHoriz = fixedHoriz;
	}
	public boolean isFixedVert() {
		return fixedVert;
	}
	public void setFixedVert(boolean fixedVert) {
		this.fixedVert = fixedVert;
	}

	private boolean visible=true;
	
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

	public boolean contains(TouchEvent ev){
		return new Rectangle(x,y,width,height).contains(ev.x, ev.y);
	}

	public boolean containsDupped(TouchEvent ev){
		return new Rectangle(0,0,width-x,height-y).contains(ev.x, ev.y);
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
		if(ev instanceof TouchEvent && contains((TouchEvent)ev)){
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
