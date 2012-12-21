package com.mypapyri.clay;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import com.mypapyri.clay.ui.Panel;

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
	private static final Dimension screenSize = new Dimension(screenWidth,
			screenHeight);

	private static Panel activeApp;

	public static Panel getActiveApp() {
		return activeApp;
	}

	public static void setActiveApp(Panel activeApp) {
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

	private static Font ubuntu;
	
	public static Font getUbuntu(){
		if(ubuntu != null) return ubuntu;
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, new File("/mnt/sd/assets/fonts/Ubuntu-R.ttf"));
			f = f.deriveFont(24f);
			ubuntu = f;
			return f;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Font f = new Font("SansSerif",Font.PLAIN,24);
			return f;
		}
	}
}
