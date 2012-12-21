package com.mypapyri.clay.ui;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.mypapyri.clay.event.TouchEvent;
import com.mypapyri.clay.event.TouchEventListener;

public class Panel extends JPanel implements TouchEventListener{
	private static final long serialVersionUID = -669809789921280413L;

	public Panel(){
		super();
		setVisible(true);
	}
	/*
	public void paint(Graphics g){
		for(Component c:getComponents()){
			g.translate(c.getX(), c.getY());
			c.paint(g);
			g.translate(-c.getX(), -c.getY());
		}
	}
	*/
	public void reflow(){
		getLayout().layoutContainer(this);
	}

	@Override
	public void onTap(TouchEvent e) {
		Component c = findComponentAt(e.getX(), e.getY());
		if(c!=null&&c!=this){
			ActionEvent ae = new ActionEvent(c,ActionEvent.ACTION_PERFORMED,"");
			ActionListener[] listeners = c.getListeners(ActionListener.class);
			for(ActionListener l:listeners){
				l.actionPerformed(ae);
			}
			if(c instanceof TouchEventListener){
				((TouchEventListener)c).onTap(e);
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
