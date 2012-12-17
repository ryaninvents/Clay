/**
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

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
	JPanel panel;
	public Clay(Display display) {
		super(display);
		display.clear();
		setKeyboardVisible(true);
		panel = new JPanel();
		panel.setBackground(Color.red);
		panel.setLayout(new BorderLayout());
		JButton button1,button2,button3,button4,button5;
		button1=new JButton("North");
		button2=new JButton("South");
		button3=new JButton("East");
		button4=new JButton("West");
		button5=new JButton("Center");
		panel.add(button1,BorderLayout.NORTH);
		panel.add(button2,BorderLayout.SOUTH);
		panel.add(button3,BorderLayout.EAST);
		panel.add(button4,BorderLayout.WEST);
		panel.add(button5,BorderLayout.CENTER);
		panel.setSize(SCREEN_SIZE);
		panel.getLayout().layoutContainer(panel);
		repaint();
	}
	//*
	public void paint(Graphics2D g){
		panel.paint(g);
	}//*/

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
			((SwingDisplay)mainDisplay).icon.addMouseListener(smi);
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
