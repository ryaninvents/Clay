/**
 * 
 */
package com.ramuller.sumerian;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.ramuller.sumerian.display.Display;
import com.ramuller.sumerian.display.SwingDisplay;
import com.ramuller.sumerian.display.eink.EInkDisplay;
import com.ramuller.sumerian.display.eink.EInkFB;



/**
 * @author ryan
 *
 */
public class Sumerian extends JPanel implements ActionListener {
	Display display;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4228329570825316212L;
	
	public Sumerian(Display display){
		this.setSize(display.getWidth(), display.getHeight());
		this.display = display;

        JCheckBox checkbox = new JCheckBox("Show Title", true);
        checkbox.setFocusable(false);
        checkbox.addActionListener(this);
        add(checkbox);
        
        repaint();
	}
	
	public void update(Graphics g){
		super.update(g);
		Graphics2D gg = (Graphics2D) display.graphics.create();
		paintAll(gg);
		super.update(gg);
	}
	
	public void paint(Graphics2D g){
		super.paintAll((Graphics)g);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EInkFB fb = null;
		Display mainDisplay = null;
		Sumerian su;
		try
		{
			fb = new EInkFB("/dev/fb0");
			mainDisplay = new EInkDisplay(fb);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsatisfiedLinkError e)
		{
			e.printStackTrace();
//			System.out.println("Anscheinend kein EInk-Display vorhanden, deshalb Wechsel zu einem Swing-Display!");
			
		}
		
		if (mainDisplay == null)
		{
			mainDisplay = new SwingDisplay(600,800);
			/*
			SwingMouseInput smi = null;
			SwingKeyInput ski = null;
			smi = new SwingMouseInput();
			ski = new SwingKeyInput();
			((SwingDisplay)mainDisplay).frame.addMouseListener(smi);
			((SwingDisplay)mainDisplay).frame.addMouseMotionListener(smi);
			((SwingDisplay)mainDisplay).frame.addKeyListener(ski);
			smi.addEventListener(appMan);
			ski.addEventListener(appMan);*/
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
