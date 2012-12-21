package com.mypapyri.clay.ui;

import java.awt.Graphics;

import javax.swing.JTextField;

public class TextField extends JTextField {
	private static final long serialVersionUID = 2616923095100129586L;

	public void paint(Graphics g){
		super.paint(g);
		getCaret().setVisible(true);
		getCaret().paint(g);
	}
}