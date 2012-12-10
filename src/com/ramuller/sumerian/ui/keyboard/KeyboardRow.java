package com.ramuller.sumerian.ui.keyboard;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Vector;

public class KeyboardRow {
	private Vector<VirtualKey> keys;
	private int height;
	private int y;
	private KeyboardView parent;
	
	public KeyboardRow(String k,KeyboardView parent){
		int i;
		VirtualKey key;
		this.parent = parent;
		keys = new Vector<VirtualKey>();
		for(i=0;i<k.length();i++){
			key = new VirtualKey(k.charAt(i));
			keys.add(key);
		}
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void reflow(){
		float sum = 0;
		float weightBase,w;
		for(VirtualKey key : keys){
			sum+=key.getWeight();
		}
		weightBase = ((float)parent.getWidth())/sum;
		sum = 0;
		for(VirtualKey key : keys){
			w = weightBase*key.getWeight();
			key.setWidth(w);
			key.setX(sum);
			sum += w;
		}
	}
	
	public void paint(Graphics2D g){
		g.setFont(new Font("SansSerif",Font.PLAIN,(int)(height*0.8f)));
		for(VirtualKey key : keys){
			key.paint(g, y, height, false);
		}
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
