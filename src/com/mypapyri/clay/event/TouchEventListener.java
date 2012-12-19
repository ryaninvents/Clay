package com.mypapyri.clay.event;

import java.awt.event.AWTEventListener;

public interface TouchEventListener extends AWTEventListener {
	public void onTap(TouchEvent e);
	public void onLongTap(TouchEvent e);
	public void onTouchDown(TouchEvent e);
	public void onTouchUp(TouchEvent e);
	public void onDrag(TouchEvent e);
	
}
