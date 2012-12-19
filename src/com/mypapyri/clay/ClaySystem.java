package com.mypapyri.clay;

import java.awt.Dimension;

import com.mypapyri.clay.ui.CPanel;

public class ClaySystem {
	private static int dragThreshold = 15;
	private static int swipeThreshold = 100;
	private static int longClickThreshold = 1000;
	
	private static final long launchTime = System.currentTimeMillis();
	private static boolean quickRefresh = false;
	

	public static boolean getQuickRefresh() {
		return quickRefresh;
	}
	public static void setQuickRefresh(boolean quickRefresh) {
		ClaySystem.quickRefresh = quickRefresh;
	}
	public static long getLaunchTime() {
		return launchTime;
	}
	private static final int screenWidth = 600;
	private static final int screenHeight = 800;
	private static final Dimension screenSize = new Dimension(screenWidth,screenHeight);
	
	private static CPanel activeApp;
	
	public static CPanel getActiveApp() {
		return activeApp;
	}
	public static void setActiveApp(CPanel activeApp) {
		ClaySystem.activeApp = activeApp;
	}
	private static boolean landscape = false;
	
	public static boolean isLandscape() {
		return landscape;
	}
	public static void setLandscape(boolean landscape) {
		ClaySystem.landscape = landscape;
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
