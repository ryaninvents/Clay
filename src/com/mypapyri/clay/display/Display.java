package com.mypapyri.clay.display;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.mypapyri.clay.ClaySystem;


public abstract class Display
{
	BufferedImage image;
	// for internal use
	private Graphics2D gg;
	
	public Display(BufferedImage image) 
	{
		this.image = image;
		gg = image.createGraphics();
		
		gg.setBackground(Color.white);
		gg.setColor(Color.white);
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gg.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		gg.setClip(0, 0, image.getWidth(), image.getHeight());
		gg.fillRect(0, 0, image.getWidth(), image.getHeight());
		gg.setColor(Color.black);
	}
	
	public Graphics2D getGraphics(){
		Graphics2D g = (Graphics2D)gg.create();
		if(!ClaySystem.isLandscape()){
			g.rotate(Math.toRadians(-90), 300, 300); 
		}
		return g;
	}
	
	public void clear(){
		gg.setColor(Color.black);
		gg.fillRect(0, 0, image.getWidth(), image.getHeight());
		repaint();
		gg.setColor(Color.white);
		gg.fillRect(0, 0, image.getWidth(), image.getHeight());
		repaint();
	}
	
	public abstract void repaint(Rectangle region);
	
	public abstract void repaint(Rectangle region, boolean fastUpdate);
	
	public abstract void repaint();
	
	public void repaint(boolean fastUpdate){
		repaint(new Rectangle(0,0,getWidth(),getHeight()),fastUpdate);
	}
	
	public int getWidth()
	{
		return image.getWidth();
	}
	
	public int getHeight()
	{
		return image.getHeight();
	}
	
	
}
