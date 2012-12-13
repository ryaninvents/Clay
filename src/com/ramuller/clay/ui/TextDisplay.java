package com.ramuller.clay.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JTextArea;

import com.ramuller.clay.event.KeyEvent;
import com.ramuller.clay.event.KeyEventListener;

/**
 * A component that displays text using Swing's capabilities.
 * @author ryan
 *
 */
public class TextDisplay extends Component implements KeyEventListener{
	private JTextArea text;
	private String str;
	
	public TextDisplay(Component parent) {
		super(parent);
		text = new JTextArea();
		text.setSize(600,600);
		text.setFont(new Font("SansSerif",Font.PLAIN,24));
		text.setMargin(new Insets(10,10,10,10));
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		str="";
	}

	public void setWidth(int w){
		super.setWidth(w);
		text.setSize(w, text.getHeight());
	}
	
	public void setHeight(int h){
		super.setHeight(h);
		text.setSize(text.getWidth(),h);
	}
	
	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		text.update(g);
	}

	@Override
	public void event(KeyEvent ev) {
		if(ev.code==KeyEvent.VK_BACK_SPACE){
			if(str.length()==0) return;
			str = str.substring(0, str.length()-1);
			text.setText(str+"|");
			return;
		}
		if(ev.code==KeyEvent.VK_SHIFT||ev.code==KeyEvent.VK_SHIFT2) return;
		str+=(char)ev.code;
		text.setText(str+"|");
	}

}
