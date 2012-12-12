/**
 * 
 */

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;

import com.ramuller.clay.display.Display;
import com.ramuller.clay.display.SwingDisplay;
import com.ramuller.clay.event.Event;
import com.ramuller.clay.event.EventListener;
import com.ramuller.clay.event.KoboTouchInput;
import com.ramuller.clay.event.swing.SwingMouseInput;
import com.ramuller.clay.ui.keyboard.VirtualKeyboard;

/**
 * @author ryan
 * 
 */
public class Clay implements EventListener {
	Display display;
	JEditorPane html;
	VirtualKeyboard keyboard;

	public Clay(Display display) {
		this.display = display;
		keyboard = new VirtualKeyboard();
		
		display.clear();

		try {
			URL start = getClass().getClassLoader().getResource("index.html");
			html = new JEditorPane(start);

			html.setSize(new Dimension(600, 400));
			html.setPreferredSize(new Dimension(600, 400));
			html.addPropertyChangeListener("page",
					new PropertyChangeListener() {

						public void propertyChange(PropertyChangeEvent arg0) {
							Clay.this.repaint();
						}

					});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void repaint() {
		Graphics2D gg = (Graphics2D) display.getGraphics();
		html.repaint();
		gg.setClip(0, 0, 600, 800);
		gg.fillRect(0, 0, 600, 800);
		html.paint(gg);
		keyboard.update(gg);
		display.repaint(false);
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

	public void event(Event ev) {
		html.setText(ev.describe());
		repaint();
	}

}
