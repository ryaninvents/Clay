/**
 * 
 */

import java.io.IOException;

import com.ramuller.clay.display.Display;
import com.ramuller.clay.display.SwingDisplay;
import com.ramuller.clay.event.EventListener;
import com.ramuller.clay.event.KoboTouchInput;
import com.ramuller.clay.event.swing.SwingMouseInput;
import com.ramuller.clay.ui.Applet;

/**
 * @author ryan
 * 
 */
public class Clay extends Applet implements EventListener {

	public Clay(Display display) {
		super(display);
		display.clear();
		setKeyboardVisible(true);
		repaint();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EInkFB fb = null;
		Display mainDisplay = null;
		Clay su;
		if (System.getProperty("user.home").length() < 4) {
			try {
				fb = new EInkFB("/dev/fb0");
				mainDisplay = new EInkDisplay(fb);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			catch (UnsatisfiedLinkError e) {
				e.printStackTrace();
				// System.out.println("Anscheinend kein EInk-Display vorhanden, deshalb Wechsel zu einem Swing-Display!");

			}

			su = new Clay(mainDisplay);

			KoboTouchInput kti = null;
			try {
				kti = new KoboTouchInput("/dev/input/event1", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			kti.addEventListener(su);
			kti.start();

		}

		if (mainDisplay == null) {
			mainDisplay = new SwingDisplay(600, 800);

			su = new Clay(mainDisplay);
			
			SwingMouseInput smi = new SwingMouseInput();
			((SwingDisplay)mainDisplay).frame.addMouseListener(smi);
			smi.addEventListener(su);
			/*
			 * SwingMouseInput smi = null; SwingKeyInput ski = null; smi = new
			 * SwingMouseInput(); ski = new SwingKeyInput();
			 * ((SwingDisplay)mainDisplay).frame.addMouseListener(smi);
			 * ((SwingDisplay)mainDisplay).frame.addMouseMotionListener(smi);
			 * ((SwingDisplay)mainDisplay).frame.addKeyListener(ski);
			 * smi.addEventListener(appMan); ski.addEventListener(appMan);
			 */

		}

	}

}
