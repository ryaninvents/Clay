package com.mypapyri.clay;

import java.awt.Dimension;

public class ClaySystemSettings {
	private static int dragThreshold = 15;
	private static int swipeThreshold = 100;
	private static int longClickThreshold = 1000;
	

	private static final int screenWidth = 600;
	private static final int screenHeight = 800;
	private static final Dimension screenSize = new Dimension(screenWidth,screenHeight);
	
	private static boolean portrait = false;
	
	public static boolean isPortrait() {
		return portrait;
	}
	public static void setPortrait(boolean portrait) {
		ClaySystemSettings.portrait = portrait;
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
		ClaySystemSettings.dragThreshold = dragThreshold;
	}
	public static int getSwipeThreshold() {
		return swipeThreshold;
	}
	public static void setSwipeThreshold(int swipeThreshold) {
		ClaySystemSettings.swipeThreshold = swipeThreshold;
	}
	public static int getLongClickThreshold() {
		return longClickThreshold;
	}
	public static void setLongClickThreshold(int longClickThreshold) {
		ClaySystemSettings.longClickThreshold = longClickThreshold;
	}
}
