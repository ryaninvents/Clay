package com.mypapyri.clay.event;

import java.util.EventListener;

public interface TouchEventListener extends EventListener {
	public void onTap(TouchEvent e);
	public void onLongTap(TouchEvent e);
	public void onTouchDown(TouchEvent e);
	public void onTouchUp(TouchEvent e);
	public void onDrag(TouchEvent e);
	
}
