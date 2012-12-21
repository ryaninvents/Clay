package com.mypapyri.clay.ui.laf;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

import com.mypapyri.clay.ClaySystem;

public class ClayTheme extends DefaultMetalTheme {
	private static ColorUIResource black = new ColorUIResource(Color.black);
	private static ColorUIResource white = new ColorUIResource(Color.white);
	
	public String getName(){ return "Clay OS Theme"; }

	public FontUIResource getControlTextFont(){
		return new FontUIResource(ClaySystem.getSysFont());
	}
	public FontUIResource getUserTextFont(){
		return new FontUIResource(ClaySystem.getSysFont());
	}

	public ColorUIResource getPrimary1(){
		return black;
	}
	public ColorUIResource getPrimary2(){
		return black;
	}
	public ColorUIResource getPrimary3(){
		return black;
	}
	public ColorUIResource getControlDarkShadow(){
		return black;
	}
	public ColorUIResource getControlHighlight(){
		return black;
	}
	public ColorUIResource getControl(){
		return white;
	}

	public ColorUIResource getControlTextColor(){
		return black;
	}
}
