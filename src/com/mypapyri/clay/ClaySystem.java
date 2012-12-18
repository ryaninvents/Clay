package com.mypapyri.clay;

import java.awt.Dimension;

import com.mypapyri.clay.ui.App;

public class ClaySystem {
	private static int dragThreshold = 15;
	private static int swipeThreshold = 100;
	private static int longClickThreshold = 1000;
	

	private static final int screenWidth = 600;
	private static final int screenHeight = 800;
	private static final Dimension screenSize = new Dimension(screenWidth,screenHeight);
	
	private static App activeApp;
	
	public static App getActiveApp() {
		return activeApp;
	}
	public static void setActiveApp(App activeApp) {
		ClaySystem.activeApp = activeApp;
	}
	private static boolean portrait = false;
	
	public static boolean isPortrait() {
		return portrait;
	}
	public static void setPortrait(boolean portrait) {
		ClaySystem.portrait = portrait;
	}
	public static int getScreenWidth() {
		return screenWidth;
	}
	public static int getScreenHeight() {
		return screenHeight;
	}
	public static Dimension getScreenSize() {
		return screenSize;
	}
	public static int getDragThreshold() {
		return dragThreshold;
	}
	public static void setDragThreshold(int dragThreshold) {
		ClaySystem.dragThreshold = dragThreshold;
	}
	public static int getSwipeThreshold() {
		return swipeThreshold;
	}
	public static void setSwipeThreshold(int swipeThreshold) {
		ClaySystem.swipeThreshold = swipeThreshold;
	}
	public static int getLongClickThreshold() {
		return longClickThreshold;
	}
	public static void setLongClickThreshold(int longClickThreshold) {
		ClaySystem.longClickThreshold = longClickThreshold;
	}
}
