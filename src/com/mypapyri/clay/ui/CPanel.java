package com.mypapyri.clay.ui;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.mypapyri.clay.ClaySystem;
import com.mypapyri.clay.event.TouchEvent;
import com.mypapyri.clay.event.TouchEventListener;
import com.ramuller.clay.display.Display;

public class CPanel extends JPanel implements TouchEventListener{
	private static final long serialVersionUID = -669809789921280413L;
	

	public CPanel(){
		super();
		
		setSize(ClaySystem.getScreenSize());
		
		setFont(new Font("SansSerif",Font.PLAIN,32));
		
	}
	
	public void reflow(){
		getLayout().layoutContainer(this);
	}

	@Override
	public void onTap(TouchEvent e) {
		Component c = findComponentAt(e.getX(), e.getY());
		if(c!=null){
			ActionEvent ae = new ActionEvent(c,ActionEvent.ACTION_PERFORMED,"");
			ActionListener[] listeners = c.getListeners(ActionListener.class);
			for(ActionListener l:listeners){
				l.actionPerformed(ae);
			}
			if(c.isFocusable()){
				
			}
		}
	}

	@Override
	public void onLongTap(TouchEvent e) {
		//System.out.println("onLongTap()");
		dispatchEvent(e);
	}

	@Override
	public void onTouchDown(TouchEvent e) {
		//System.out.println("onTouchDown()");
		processMouseEvent(e);
	}

	@Override
	public void onTouchUp(TouchEvent e) {
		//System.out.println("onTouchUp()");
		dispatchEvent(e);
	}

	@Override
	public void onDrag(TouchEvent e) {
		//System.out.println("onDrag()");
		dispatchEvent(e);
	}

	@Override
	public void eventDispatched(AWTEvent evt) {
	}
	
}
