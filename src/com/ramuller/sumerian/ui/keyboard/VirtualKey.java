package com.ramuller.sumerian.ui.keyboard;

import java.awt.Color;
import java.awt.Graphics2D;

public class VirtualKey {
	private int weight;
	private int width;
	private int x;
	private static int margin=2;
	private static int radius=3;
	private char label;
	
	public VirtualKey(char label){
		this.label = label;
		this.weight = 1;
	}

	public int getWidth() {
		return width;
	}

	protected void setWidth(float width) {
		this.width = (int)width;
	}

	public int getX() {
		return x;
	}

	protected void setX(float x) {
		this.x = (int)x;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public void paint(Graphics2D g, int y, int height, boolean invert){
		g.setColor(invert?Color.BLACK:Color.WHITE);
		g.fillRoundRect(x+margin, y+margin, width-2*margin, height-2*margin, radius, radius);
		g.setColor(invert?Color.WHITE:Color.BLACK);
		g.drawString(""+label, x+3, y+(int)(height*0.8f));
	}
}
